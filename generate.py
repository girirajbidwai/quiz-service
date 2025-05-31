import os
import json
import sys
import io
import re
import traceback
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import PromptTemplate

os.environ["GOOGLE_API_KEY"] = "AIzaSyDyS4kixPVf5p6JenhYbDLFo_NVS1AlQiw"

# Fix Unicode print issue
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Prompt template (unchanged)
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

    # -----------------------------------------------------------
    # 1) Strip away any Markdown fences (```json …``` or ``````)
    # -----------------------------------------------------------
    cleaned_output = output.strip()
    if cleaned_output.startswith("```json"):
        cleaned_output = cleaned_output.removeprefix("```json").removesuffix("```").strip()
    elif cleaned_output.startswith("```"):
        cleaned_output = cleaned_output.removeprefix("```").removesuffix("```").strip()

    try:
        # -----------------------------------------------------------
        # 2) Fix unquoted values so json.loads() will succeed:
        #    a) Wrap any bare-integer option in quotes
        #    b) Insert missing leading quote for textual options
        # -----------------------------------------------------------
        # 2a) Wrap bare integers in quotes
        cleaned_output = re.sub(
            r'("option[ABCD]": )(-?\d+)([,\n])',
            r'\1"\2"\3',
            cleaned_output
        )

        # 2b) Insert missing leading quote for textual options
        cleaned_output = re.sub(
            r'("option[ABCD]": )([^"\n][^"]*?)(",)',
            r'\1"\2"\3',
            cleaned_output
        )

        # Parse into Python objects
        parsed = json.loads(cleaned_output)

        # -----------------------------------------------------------
        # 3) Unwrap any optionX that is still a JSON-string like "{\"text\": \"…\"}"
        #    → replace it with the inner "…"
        # -----------------------------------------------------------
        for question in parsed:
            for key in ("optionA", "optionB", "optionC", "optionD"):
                raw = question.get(key)
                if isinstance(raw, str):
                    candidate = raw.strip()
                    # If it looks like a JSON object string, parse and extract "text"
                    if candidate.startswith("{") and candidate.endswith("}"):
                        try:
                            inner = json.loads(candidate)
                            # If there is a "text" field, replace the value
                            if isinstance(inner, dict) and "text" in inner:
                                question[key] = inner["text"]
                        except json.JSONDecodeError:
                            # Leave it as-is if it isn’t valid JSON
                            pass

        return parsed

    except json.JSONDecodeError as e:
        # For debugging: print raw + cleaned
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
        # Print final JSON with plain strings for optionA–D
        print(json.dumps(result, indent=2, ensure_ascii=False))
    except Exception as e:
        print("ERROR:", str(e))
        traceback.print_exc()
        sys.exit(1)
