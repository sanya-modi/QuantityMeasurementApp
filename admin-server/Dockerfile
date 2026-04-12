ARG MAVEN_BUILDER_IMAGE
ARG JAVA_RUNTIME_IMAGE
ARG APP_USER_ID

FROM ${MAVEN_BUILDER_IMAGE} AS builder

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests && \
    cp target/*.jar target/app.jar

FROM ${JAVA_RUNTIME_IMAGE}

ARG SERVICE_PORT
ARG APP_USER_ID

WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --create-home --uid ${APP_USER_ID} appuser

ENV JAVA_OPTS="" \
    SERVICE_PORT=${SERVICE_PORT}

COPY --from=builder /workspace/target/app.jar /app/app.jar
RUN chown appuser:appuser /app/app.jar

USER appuser

EXPOSE ${SERVICE_PORT}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]
