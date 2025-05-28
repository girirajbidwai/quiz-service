import os
import json
import sys
import io
import traceback
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import PromptTemplate

os.environ["GOOGLE_API_KEY"] = "AIzaSyD4JMbHJxhtzdoiGiqNGc2QRfPuiSrt37g"

# Fix Unicode print issue
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Prompt
template = """
Generate {num_questions} multiple-choice questions (MCQs) for grade {grade} on the subject "{subject}" and topic "{topic}".

Each question must contain:
- questionText: string
- marks: integer (default 2)
- negativeMarks: integer (default 0)
- optionA–D: 4 options
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

llm = ChatGoogleGenerativeAI(model="gemini-1.5-flash", temperature=0.7)
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

    try:
        # Remove ```json ... ``` if Gemini returns markdown
        cleaned_output = output.strip()

        # Remove markdown-style formatting if present
        if cleaned_output.startswith("```json"):
            cleaned_output = cleaned_output.removeprefix("```json").removesuffix("```").strip()
        elif cleaned_output.startswith("```"):
            cleaned_output = cleaned_output.removeprefix("```").removesuffix("```").strip()

        # Now parse
        return json.loads(cleaned_output)

    except json.JSONDecodeError as e:
        print("JSON parsing failed:", e)
        print("Raw output:\n", output)
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
