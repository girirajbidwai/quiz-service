from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import PromptTemplate
from langchain.chains import LLMChain

import os
os.environ["GOOGLE_API_KEY"] = "AIzaSyD4JMbHJxhtzdoiGiqNGc2QRfPuiSrt37g"

# 1. Create a prompt
template = "Explain the concept of {topic} in simple terms."
prompt = PromptTemplate(
    input_variables=["topic"],
    template=template,
)

# 2. Use Gemini (Pro or Flash)
llm = ChatGoogleGenerativeAI(model="gemini-2.0-flash", temperature=0.7)

# 3. Create a chain
chain = LLMChain(llm=llm, prompt=prompt)

# 4. Run the chain
response = chain.run("Quantum Computing")
print(response)