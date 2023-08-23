import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.specifications.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsJson.PHONE_NUMBER;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.specifications.preparationDataCustomers.ServiceValues.getField;
import static com.kata.config.serviceCustomers.specifications.preparationResponses.ResponsesApiCustomers.responseGetCustomerByPhoneNumber;
import static com.kata.config.serviceCustomers.specifications.preparationResponses.ResponsesApiCustomers.responsePostCustomers;

@Epic(CUSTOMERS + "/filter")
@Story("Получение клиента по номеру телефона методом GET")
public class GetCustomersFilterTest {

    @Test
    @DisplayName("Получение клиента по номеру телефона")
    @Description("Получение клиента по номеру телефона. Создаем клиента, проверяем корректность заполнения " +
            "полей при получении клиента с помощью метода.")
    public void testGetCustomersByPhoneNumber() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String phoneNumber = getField(customer, PHONE_NUMBER);

        checkRequiredFieldsCorrectCreatedCustomers(
                responseGetCustomerByPhoneNumber(phoneNumber),
                "Petr",
                "Petrov",
                phoneNumber
        );
    }

    @Test
    @DisplayName("Получение клиента с передачей в номер телефона лишних цифр")
    @Description("Получение клиента с номером телефона нестандартной длины. Из теста видно, что ограничение" +
            " на длину не установлено, что некорректно. Но в документации длина не указана, за ошибку " +
            "считать нельзя.")
    public void testGetCustomersByPhoneNumberConcat() {
        String phoneNumber = getField(responsePostCustomers("create-customers"), PHONE_NUMBER);
        ValidatableResponse response = responseGetCustomerByPhoneNumber(phoneNumber);

        checkErrorMessage(response, NOT_FOUND);
    }

    @Test
    @DisplayName("Получение клиента по несуществующему номеру телефона")
    @Description("Пытаемся поулчить клиента по несуществующему номеру телефона. Ожидаем 404")
    public void testGetCustomersByPhoneNumberInt() {
        ValidatableResponse response = responseGetCustomerByPhoneNumber("+0000000000");

        checkErrorMessage(response, NOT_FOUND);
        checkStatusCode(response, 404);
    }

    @Test
    @DisplayName("Получение клиента с передачей спецсимволов вместо номера телефона")
    @Description("Сервис должен возвращать код 400, но вместо этого он пытается найти клиента.")
    public void testGetCustomersByPhoneNumberChar() {
        ValidatableResponse response = responseGetCustomerByPhoneNumber('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
