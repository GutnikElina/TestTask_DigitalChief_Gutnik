# Test Task

## Описание
Приложение на основе Spring Boot с использованием PostgreSQL и Elasticsearch, которое предоставляет API для работы с продуктами.

## Требования

- **Docker**: Для запуска контейнеров с приложением, базой данных и Elasticsearch.
- **Maven**: Для сборки проекта (необязательно, так как проект может быть собран внутри Docker).
- **Java 17**: Если планируется сборка и запуск приложения локально без Docker.

## Установка

Откройте консоль (например, PowerShell) для выполнения команд. Перейдите в директорию, в которую хотите клонировать удаленный репозиторий с помощью команды cd.
Примечание: Убедитесь, что Docker Desktop запущен перед выполнением следующих шагов.
1. Клонирование репозитория:
   ```bash
   git clone https://github.com/GutnikElina/TestTask_DigitalChief_Gutnik

2. Переход в директорию проекта:
   ```bash
   cd TestTask_DigitalChief_Gutnik
   
3. Сборка проекта с помощью Maven:
   ```bash
   mvn clean install

4. Запустите Docker Compose:
   ```bash
   docker-compose up --build

5. Ожидание: После выполнения команды docker-compose up --build, понадобится некоторое время для загрузки, чтобы все сервисы (приложение, PostgreSQL и Elasticsearch) полностью запустились и стали доступны.

6. После успешного запуска, приложение будет доступно по адресу http://localhost:8080.

## Примечание
При неудачной сборке проекта и выводе сообщения - 
dependency failed to start: container test-task-elasticsearch-1 is unhealthy. Попробуйте снова:
   ```bash
   docker-compose down -v
   docker-compose up --build

