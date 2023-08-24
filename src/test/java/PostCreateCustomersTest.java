import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.getPhoneNumber;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.responsePostCustomers;

@Epic(CUSTOMERS)
@Story("Создание клиента методом POST")
public class PostCreateCustomersTest {

    @Test
    @DisplayName("Создание клиента только с обязательными полями")
    @Description("Создаем клиента только с обязательными для заполнения полями. Проверяем корректность " +
            "полей в ответе.")
    public void testCreateNewCustomersRequiredFields() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String phoneNumber = getPhoneNumber(customer);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Petr", "Petrov", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента с именем и фамилией на кириллице")
    @Description("Создание клиента с русскими именем и фамилией. Проверяем корректность заполнения полей в " +
            "ответе")
    public void testCreatedCustomersCyrillicFirstLastName() {
        ValidatableResponse customer = responsePostCustomers("create-customers-cyrillic");
        String phoneNumber = getPhoneNumber(customer);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Петр", "Петров", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента со всеми заполненными полями")
    @Description("Сервис должен игнорировать заполнение полей, которые генерируются на стороне сервиса. Но " +
            "вместо это он отвечает кодом 400")
    public void testCreateNewCustomersAllFields() {
        ValidatableResponse customer = responsePostCustomers("create-customers-all-fields");
        String phoneNumber = getPhoneNumber(customer);

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
        ValidatableResponse response = responsePostCustomers("create-customers-without-required-fields");

        checkStatusCode(response, 400);
        checkErrorMessage(response, MISSING_FIELDS);
    }

    @Test
    @DisplayName("Создание клиента. Вместо firstName/lastName передаем число")
    @Description("Создаем клиента, вместо имени и фамилии передаем числа. Т.к. в документации на это " +
            "ограничений нет, ожидаем корректное создание клиента.")
    public void testCreateNewCustomersFirstNameNumbers() {
        ValidatableResponse customer = responsePostCustomers("create-customers-firstLastName-numbers");
        String phoneNumber = getPhoneNumber(customer);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "123", "123", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента. В дату рождения передаем данные в формате 0000-00-00")
    @Description("Пытаемся создать клиента с нулевой датой рождения. Ожидаем 400")
    public void testCreateNewCustomersBirthdayZero() {
        ValidatableResponse response = responsePostCustomers("create-customers-birthday-zero");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
