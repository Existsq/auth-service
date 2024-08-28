# Используйте официальный образ OpenJDK в качестве базового
FROM openjdk:21-jdk-slim

# Установите рабочую директорию в контейнере
WORKDIR /app

# Копируйте собранный JAR файл в рабочую директорию контейнера
COPY build/libs/application.jar application.jar

# Определите команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/application.jar"]

# Откройте порт, который будет использовать ваше приложение
EXPOSE 8081