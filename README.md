# WebChecker

**Detect website changes and get notified instantly.**

WebChecker is a REST API that monitors websites for content changes at configurable intervals and sends email alerts.

Built with Spring Boot 3, JPA, MySQL, Playwright, and Docker.

---

## Features

- Monitor websites with custom check intervals
- JavaScript-rendered content support via Playwright
- Automated background scheduler
- Email notifications for new content
- REST API for all operations
- Dockerized (App + MySQL)
- Unit and integration tests

---

## Quick Start

```
git clone https://github.com/Alae-V/webchecker.git
cd webchecker
mvn clean package -DskipTests
mvn spring-boot:run
```

API: `http://localhost:8080`

### Run with Docker

```
docker-compose up -d
```

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/websites` | Add a website |
| GET | `/api/websites` | Get all websites |
| GET | `/api/websites/{id}` | Get one website |
| DELETE | `/api/websites/{id}` | Delete a website |
| POST | `/api/check/{id}` | Manual check |
| POST | `/api/check/all` | Check all |

---

## Configuration

Email settings in `application.properties`:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

---

## Testing

```
mvn test
```

---

## Known Issues

- Playwright downloads browser binaries on first run
- SMTP rate limits apply

---

## Author

Ala Ben Salah 
