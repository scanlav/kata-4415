import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.randomNumber;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.responsePostCustomers;

@Epic(CUSTOMERS)
@Story("Создание клиента методом POST")
public class PostCreateCustomersTest {

    @Test
    @DisplayName("Создание клиента только с обязательными полями")
    @Description("Создаем клиента только с обязательными для заполнения полями. Проверяем корректность " +
            "полей в ответе.")
    public void testCreateNewCustomersRequiredFields() {
        String phoneNumber = randomNumber();
        Response customer = responsePostCustomers("create-customers", phoneNumber);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Petr", "Petrov", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента с именем и фамилией на кириллице")
    @Description("Создание клиента с русскими именем и фамилией. Проверяем корректность заполнения полей в " +
            "ответе")
    public void testCreatedCustomersCyrillicFirstLastName() {
        String phoneNumber = randomNumber();
        Response customer = responsePostCustomers("create-customers-cyrillic", phoneNumber);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Петр", "Петров", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента со всеми заполненными полями")
    @Description("Сервис должен игнорировать заполнение полей, которые генерируются на стороне сервиса. Но " +
            "вместо это он отвечает кодом 400")
    public void testCreateNewCustomersAllFields() {
        String phoneNumber = randomNumber();
        Response customer = responsePostCustomers("create-customers-all-fields", phoneNumber);

        checkAllFieldsCorrectCreatedCustomer(
                customer,
                "Petr",
                "Petrov",
                phoneNumber,
                "string@mail.com",
                "2001-01-01"
        );
    }

    @Test
    @DisplayName("Создание клиента, без заполнения обязательных полей")
    @Description("Пытаемся создать клиента без заполнения основных полей. Проверяем сообщения ошибок в " +
            "ответе сервиса, ожидаем 400")
    public void testCreateNewCustomersWithoutRequiredFields() {
        String phoneNumber = randomNumber();
        Response response = responsePostCustomers("create-customers-without-required-fields", phoneNumber);

        checkStatusCode(response, 400);
        checkErrorMessage(response, MISSING_FIELDS);
    }

    @Test
    @DisplayName("Создание клиента. Вместо firstName/lastName передаем число")
    @Description("Создаем клиента, вместо имени и фамилии передаем числа. Т.к. в документации на это " +
            "ограничений нет, ожидаем корректное создание клиента.")
    public void testCreateNewCustomersFirstNameNumbers() {
        String phoneNumber = randomNumber();
        Response customer = responsePostCustomers("create-customers-firstLastName-numbers", phoneNumber);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "123", "123", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента. В дату рождения передаем данные в формате 0000-00-00")
    @Description("Пытаемся создать клиента с нулевой датой рождения. Ожидаем 400")
    public void testCreateNewCustomersBirthdayZero() {
        String phoneNumber = randomNumber();
        Response response = responsePostCustomers("create-customers-birthday-zero", phoneNumber);

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
