This repository is a Maven-based API test/automation framework using Rest Assured and TestNG. The goal of these instructions is to give an AI coding agent the minimal, actionable knowledge needed to work productively in this codebase.

- Architecture (big picture):
  - Request/HTTP layer: `BaseApiClient` uses `RequestSpecFactory.defaultSpec()` and applies filters from `RestAssuredFilters.defaultFilters()` to every request. See [src/main/java/com/company/framework/core/BaseApiClient.java](src/main/java/com/company/framework/core/BaseApiClient.java).
  - Clients: Lightweight client classes (e.g., `AccountClient`, `BookStoreClient`) extend `BaseApiClient` and call endpoints defined in `Endpoints`. See [src/main/java/com/company/framework/clients/AccountClient.java](src/main/java/com/company/framework/clients/AccountClient.java).
  - Configuration: `ConfigManager` loads `src/main/resources/config/application-{env}.properties` (default `env=qa`) and exposes typed getters. See [src/main/java/com/company/framework/config/ConfigManager.java](src/main/java/com/company/framework/config/ConfigManager.java).
  - Auth/state: `TokenManager` is an in-memory cache for tokens keyed by username — tests and clients rely on this pattern. See [src/main/java/com/company/framework/auth/TokenManager.java](src/main/java/com/company/framework/auth/TokenManager.java).
  - Assertions/validation: `ApiAssertions` contains small wrappers around TestNG asserts; JSON schema validation uses `SchemaValidatorUtil` with schemas in `src/main/resources/schemas/`. See [src/main/java/com/company/framework/assertions/SchemaValidatorUtil.java](src/main/java/com/company/framework/assertions/SchemaValidatorUtil.java).

- Key coding patterns & conventions (what to follow):
  - Use client classes that extend `BaseApiClient` for HTTP interactions; reuse `Endpoints` constants (no hard-coded URLs).
  - Use `ConfigManager.get(...)`, `getInt(...)`, `getBool(...)` for configuration instead of System.getenv or hard-coded values.
  - Use `TokenManager` to share authentication tokens between test steps; do not persist tokens to disk.
  - Logging and masking: request/response logging is conditionally enabled via properties (`log.request`, `log.response`); sensitive fields are masked with `MaskingUtil` (used by `RestAssuredFilters`). See [src/main/java/com/company/framework/core/RestAssuredFilters.java](src/main/java/com/company/framework/core/RestAssuredFilters.java).
  - Retry logic: prefer `RetryUtil.retry(...)` for flaky remote calls rather than ad hoc loops.

- Build / test / debug workflows (practical commands):
  - Run full test suite (uses TestNG suite XML):

    mvn test -Denv=qa

  - Run with a different environment config file:

    mvn test -Denv=dev

  - Run a single TestNG test class (maven surefire):

    mvn -Dtest=com.company.tests.demoqa.AccountTests test -Denv=qa

  - The TestNG suite file is at [src/test/resources/testng.xml](src/test/resources/testng.xml). Use it when running with IDE/Test runner.
  - Allure is included as a dependency; if you want to produce reports locally, run tests then generate/serve using your local Allure CLI (not bundled):

    mvn test -Denv=qa
    allure serve target/allure-results


- Integration points & external dependencies discovered from code:
  - Rest Assured (+ JSON Schema Validator) — HTTP client and schema checks (pom.xml).
  - Allure for test reporting (`allure-testng`, `allure-rest-assured`) — filters are applied in `RestAssuredFilters`.
  - Jackson for JSON mapping — used in `JsonUtil`.
  - Properties-based environment selection: `ConfigManager` expects `application-{env}.properties` under `src/main/resources/config/`.

- Files to check when making changes (examples):
  - HTTP behavior and filters: [src/main/java/com/company/framework/core/RequestSpecFactory.java](src/main/java/com/company/framework/core/RequestSpecFactory.java) and [src/main/java/com/company/framework/core/RestAssuredFilters.java](src/main/java/com/company/framework/core/RestAssuredFilters.java).
  - Endpoints and request models: [src/main/java/com/company/framework/config/Endpoints.java](src/main/java/com/company/framework/config/Endpoints.java) and [src/main/java/com/company/framework/models/requests](src/main/java/com/company/framework/models/requests).
  - Schema validation examples: `src/main/resources/schemas/` (e.g., [src/main/resources/schemas/books-schema.json](src/main/resources/schemas/books-schema.json)).

- Small examples an agent can use immediately:
  - To call generate-token and cache it:
    - Instantiate `GenerateTokenRequest`, call `new AccountClient().generateToken(req)`, then store token via `TokenManager.put(username, token)`.
  - To validate a response schema:
    - `SchemaValidatorUtil.validateSchema(response, "schemas/books-schema.json")`.

- What NOT to change without asking: 
  - Do not change how `ConfigManager` loads properties (env switching) — tests rely on `-Denv` behavior.
  - Avoid altering `RestAssuredFilters.defaultFilters()` order; Allure/Masking/logging depend on that setup.

If any section is unclear or you want the file expanded with more examples (for example real property keys and values from `src/main/resources/config`), tell me which area to expand. I'll iterate on this file.
