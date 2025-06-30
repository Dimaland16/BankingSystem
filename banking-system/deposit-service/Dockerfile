# Базовый образ
FROM openjdk:22-slim

# Установка рабочей директории
WORKDIR /app

# Копирование артефактов сборки
COPY target/deposit-service-0.0.1.jar app.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8083

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]