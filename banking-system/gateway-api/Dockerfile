# Базовый образ
FROM openjdk:22-slim

# Установка рабочей директории
WORKDIR /app

# Копирование артефактов сборки
COPY target/gateway-api-latest.jar app.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]