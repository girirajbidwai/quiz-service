# ===============================
# 🏗️ Build Stage - Maven Build
# ===============================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Prepare Maven dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source and build JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests


# ===============================
# 🚀 Runtime Stage - Java + Python
# ===============================
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Install Python and create venv
RUN apt-get update && \
    apt-get install -y --no-install-recommends python3 python3-pip python3-venv && \
    rm -rf /var/lib/apt/lists/*

# Create Python virtual environment
RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# Copy Python files
COPY generate.py .
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy JAR from builder stage
COPY --from=build /app/target/*.jar app.jar

# Create non-root user with known UID/GID (e.g., required by Choreo)
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid 10014 \
    choreo

# Switch to unprivileged user
USER 10014

# Expose application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
