# ===============================
# 🏗️ Build Stage - Maven Build
# ===============================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests


# ===============================
# 🚀 Runtime Stage - Java + Python
# ===============================
FROM eclipse-temurin:21-jre

# Install Python and venv
RUN apt-get update && \
    apt-get install -y python3 python3-pip python3-venv && \
    apt-get clean

WORKDIR /app

# Set up virtual environment for Python
RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# Copy Python script and requirements.txt (both are now in root)
COPY generate.py .
COPY requirements.txt .

# Install Python dependencies inside the virtual environment
RUN pip install -r requirements.txt

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose app port
EXPOSE 8080

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
