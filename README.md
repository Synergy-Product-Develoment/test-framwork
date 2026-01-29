# API Automation Framework (DemoQA Swagger) - Enterprise Design

## Tech
- Java 17
- Maven
- Rest Assured
- TestNG
- Allure Reports
- Logback

## Run tests
```bash
mvn clean test -Denv=qa
```

## Generate Allure report
1) Install allure CLI
2) Run:
```bash
mvn clean test -Denv=qa
allure serve target/allure-results
```

## Notes
- Base URL configured in: `src/main/resources/config/application-qa.properties`
- Schema files in: `src/main/resources/schemas`
