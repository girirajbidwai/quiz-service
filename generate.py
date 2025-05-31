import os
import json
import sys
import io
import re
import traceback
from dotenv import load_dotenv  # 👈 Load .env in dev environments
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import PromptTemplate

# Load .env if present
load_dotenv()

# Get the API key from environment
GOOGLE_API_KEY = os.getenv("GEMINI_API_KEY")
if not GOOGLE_API_KEY:
    raise EnvironmentError("GEMINI_API_KEY is not set in environment variables")

# Fix Unicode print issue
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Prompt template
template = """
Generate {num_questions} multiple-choice questions (MCQs) for grade {grade} on the subject "{subject}" and topic "{topic}".

Each question must contain:
- questionText: string
- marks: integer (default 2)
- negativeMarks: integer (default 0)
- optionA–D: 4 options (always output them as JSON strings, even if numeric)
- correctOption: one of "A", "B", "C", or "D"
- hint: a short explanation

Format as a JSON array of objects like this:
[
  {{
    "questionText": "...",
    "marks": 2,
    "negativeMarks": 0,
    "optionA": "...",
    "optionB": "...",
    "optionC": "...",
    "optionD": "...",
    "correctOption": "A",
    "hint": "..."
  }},
  ...
]
"""

prompt = PromptTemplate(
    input_variables=["grade", "subject", "topic", "num_questions"],
    template=template
)

llm = ChatGoogleGenerativeAI(
    model="gemini-1.5-flash",
    temperature=0.7,
    google_api_key=GOOGLE_API_KEY
)
chain = prompt | llm

def generate_quiz(grade, subject, topic, num_questions):
    input_data = {
        "grade": grade,
        "subject": subject,
        "topic": topic,
        "num_questions": num_questions
    }

    output = chain.invoke(input_data).content

    if not output.strip():
        raise ValueError("Gemini returned an empty response.")

    cleaned_output = output.strip()
    if cleaned_output.startswith("```json"):
        cleaned_output = cleaned_output.removeprefix("```json").removesuffix("```").strip()
    elif cleaned_output.startswith("```"):
        cleaned_output = cleaned_output.removeprefix("```").removesuffix("```").strip()

    try:
        cleaned_output = re.sub(
            r'("option[ABCD]": )(-?\d+)([,\n])',
            r'\1"\2"\3',
            cleaned_output
        )
        cleaned_output = re.sub(
            r'("option[ABCD]": )([^"\n][^"]*?)(",)',
            r'\1"\2"\3',
            cleaned_output
        )

        parsed = json.loads(cleaned_output)

        for question in parsed:
            for key in ("optionA", "optionB", "optionC", "optionD"):
                raw = question.get(key)
                if isinstance(raw, str):
                    candidate = raw.strip()
                    if candidate.startswith("{") and candidate.endswith("}"):
                        try:
                            inner = json.loads(candidate)
                            if isinstance(inner, dict) and "text" in inner:
                                question[key] = inner["text"]
                        except json.JSONDecodeError:
                            pass

        return parsed

    except json.JSONDecodeError as e:
        print("JSON parsing failed:", e)
        print("=== RAW OUTPUT ===\n", output)
        print("=== CLEANED OUTPUT (after regex) ===\n", cleaned_output)
        raise

if __name__ == "__main__":
    try:
        grade = sys.argv[1]
        subject = sys.argv[2]
        topic = sys.argv[3]
        num_questions = sys.argv[4]

        result = generate_quiz(grade, subject, topic, num_questions)
        print(json.dumps(result, indent=2, ensure_ascii=False))
    except Exception as e:
        print("ERROR:", str(e))
        traceback.print_exc()
        sys.exit(1)
