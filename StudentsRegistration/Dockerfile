FROM openjdk:17-oracle
WORKDIR /app
COPY /target/classes/* ./init
COPY target/StudentsRegistration-0.0.1-SNAPSHOT.jar app.jar
ENV INIT=true
ENV INIT_PATH=/app/init/default-students.txt
CMD ["java", "-jar", "app.jar"]