# ===============================
# 🏗️ Build Stage - Maven Build
# ===============================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests


# ===============================
# 🚀 Runtime Stage - Java + Python
# ===============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Install Python and setup venv
RUN apt-get update && \
    apt-get install -y --no-install-recommends python3 python3-pip python3-venv && \
    rm -rf /var/lib/apt/lists/*

RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# Copy Python scripts
COPY generate.py .
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy built Spring Boot JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables (⚠️ Replace with --env-file or docker run -e for secrets in production)
ENV DATABASE_URL=jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres
ENV DATABASE_USERNAME=postgres.xcxgjwhboudprgswfynp
ENV DATABASE_PASSWORD=Thinkforge@11

# Create non-root user (required by some cloud platforms like Choreo)
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid 10014 \
    choreo

USER 10014

# Expose Spring Boot port
EXPOSE 8080

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
