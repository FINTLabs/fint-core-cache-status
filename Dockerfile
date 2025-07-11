FROM gradle:8.14.2-jdk21 as builder

USER gradle
COPY . .
RUN gradle --no-daemon clean build

FROM gcr.io/distroless/java21
ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
COPY --from=builder /home/gradle/build/libs/*.jar /data/app.jar
USER nonroot:nonroot
EXPOSE 8080
CMD ["/data/app.jar"]