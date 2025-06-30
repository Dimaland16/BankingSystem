# Базовый образ
FROM openjdk:22-slim

# Установка рабочей директории
WORKDIR /app

# Копирование артефактов сборки
COPY target/eureka-server-latest.jar app.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8761

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]