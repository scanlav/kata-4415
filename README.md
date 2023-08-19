<h3>Практическая задача</h3>
<p>1. Подключить к проекту зависимость <i>rest-assured</i></p>

<p>2. Добавить конфигурационный файл test.properties, реализовать получение URL тестируемого сервиса из test.
properties</p>

Пример использования:

    RestAssured.baseURI = TestProperties.getValue("api.base.url");

<p>3. Реализовать загрузку json из файла, который может быть использован в качестве request body в тестовых 
запросах</p>

Пример использования:

    String requestBody = DataProvider.getTestData("requests/create-customer.json");

<p>4. Покрыть автотестами методы сервиса (позитивные + негативные сценарии, не менее 20 автотестов):</p>

    POST /customers

    GET /customers/{id}

    PUT /customers/{id}

    DELETE /customers/{id}

    GET /customers/filter

<p>5. Для валидации timestamps в ответах сервиса (поля createdAt, updatedAt) реализовать собственный Matcher.
Например, isToday() или isAfter(LocalDateTime timestamp).</p>
    Пример использования:

```Java
RestAssured.given()
        .body(requestBody)
        .when().post("/customers")
        .then().body("updatedAt", CustomMatchers.isAfter(requestTime));
```

<p>6. Найти дефект в работе сервиса (как минимум, один). Добавить краткое описание дефекта в комментарии к 
автотесту, который его обнаруживает.</p>

<p>7. Опубликовать проект на GitHub и приложить ссылку на него</p>