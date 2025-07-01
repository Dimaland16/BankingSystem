# Базовый образ
FROM openjdk:22-slim

# Установка рабочей директории
WORKDIR /app

# Устанавливаем curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Копирование артефактов сборки
COPY target/loan-service-0.0.1.jar app.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8082

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]