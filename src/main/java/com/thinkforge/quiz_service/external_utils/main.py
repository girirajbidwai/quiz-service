import warnings
import logging
from typing import List
from dotenv import load_dotenv

import psycopg2
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field, validator
from langchain.output_parsers import PydanticOutputParser
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain
from langchain_google_genai import ChatGoogleGenerativeAI

# ─── suppress Python warnings ──────────────────────────────────────────────────
warnings.filterwarnings("ignore")

# ─── configure uvicorn logging level ─────────────────────────────────────────
logging.getLogger("uvicorn").setLevel(logging.WARNING)
logging.getLogger("uvicorn.error").setLevel(logging.WARNING)
logging.getLogger("uvicorn.access").setLevel(logging.WARNING)

load_dotenv()

# ─── Supabase Postgres credentials (inlined) ──────────────────────────────────
DB_USER = "postgres.xcxgjwhboudprgswfynp"
DB_PASSWORD = "Thinkforge@11"
DB_HOST = "aws-0-ap-south-1.pooler.supabase.com"
DB_PORT = 5432
DB_NAME = "postgres"

# ─── 1) extended Pydantic schema ────────────────────────────────────────────────
class QuizQuestion(BaseModel):
    question: str = Field(description="The text of the quiz question")
    options: List[str] = Field(description="Exactly four answer choices", min_items=4, max_items=4)
    answer: str = Field(description="The correct answer, must be one of the options")
    hint: str = Field(description="A short hint to help answer the question")

    @validator("options")
    def must_have_four_options(cls, v):
        if len(v) != 4:
            raise ValueError("There must be exactly four options.")
        return v

    @validator("answer")
    def answer_in_options(cls, v, values):
        if "options" in values and v not in values["options"]:
            raise ValueError("Answer must be one of the provided options.")
        return v

class Quiz(BaseModel):
    questions: List[QuizQuestion] = Field(description="List of quiz questions")

class QuizRequest(BaseModel):
    num_questions: int
    class_name: str
    subject: str
    topic: str
    difficulty: str
    additional_info_context: str

# ─── 2) build parser + prompt (with hint instruction) ─────────────────────────
quiz_parser = PydanticOutputParser(pydantic_object=Quiz)

template = """
Generate {num_questions} multiple‑choice questions for a {class_name} class on the subject "{subject}" and topic "{topic}".
Each question must have exactly four options, label them clearly (e.g. A., B., C., D.).
Use the difficulty level "{difficulty}" for all questions.
Also provide a concise hint for each question.
Use the additional info "{additional_info_context}" as extra context when crafting questions (this does not go into the schema output).

{format_instructions}
"""

prompt = PromptTemplate(
    template=template,
    input_variables=[
        "num_questions", "class_name", "subject", "topic",
        "difficulty", "additional_info_context"
    ],
    partial_variables={"format_instructions": quiz_parser.get_format_instructions()},
)

llm = ChatGoogleGenerativeAI(model="gemini-1.5-pro", temperature=0)
chain = prompt | llm | quiz_parser

# ─── 3) helper: store into Supabase Postgres with integer id primary key ─────
import uuid

def save_quiz_to_db(req: QuizRequest, quiz: Quiz) -> uuid.UUID:
    conn = psycopg2.connect(
        user=DB_USER,
        password=DB_PASSWORD,
        host=DB_HOST,
        port=DB_PORT,
        dbname=DB_NAME,
    )
    cur = conn.cursor()

    # Assume quiz_id is generated for each quiz submission
    quiz_id = "863e34f0-1a75-42bb-8900-2465e0153f33"
    # You may want to store the quiz info in a `quiz` table before this

    for q in quiz.questions:
        option_a, option_b, option_c, option_d = q.options

        # Assuming answer is like "A." or matches option exactly; map it to 'A', 'B', etc.
        options_map = {
            option_a: "A",
            option_b: "B",
            option_c: "C",
            option_d: "D",
        }
        correct_option = options_map.get(q.answer)
        if not correct_option:
            raise ValueError(f"Answer '{q.answer}' not found in options: {q.options}")

        cur.execute(
            """
            INSERT INTO public.question (
                quiz_id, question_text,
                marks, negative_marks,
                option_a, option_b, option_c, option_d,
                correct_option, hint
            ) VALUES (
                %s, %s,
                %s, %s,
                %s, %s, %s, %s,
                %s, %s
            )
            """,
            [
                quiz_id,                  # quiz_id
                q.question,               # question_text
                4,                        # marks (example default)
                -1,                       # negative_marks (example default)
                option_a, option_b, option_c, option_d,
                correct_option,           # 'A', 'B', etc.
                q.hint
            ]
        )

    conn.commit()
    cur.close()
    conn.close()
    return quiz_id


# ─── 4) FastAPI endpoint ──────────────────────────────────────────────────────
app = FastAPI(title="Quiz Generator API")

@app.post("/generate-quiz", response_model=Quiz)
async def generate_quiz_endpoint(request: QuizRequest):
    try:
        quiz = chain.invoke(request.dict())
        save_quiz_to_db(request, quiz)
        return quiz
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# ─── 5) entrypoint for Windows reload ─────────────────────────────────────────
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True, log_level="warning")
