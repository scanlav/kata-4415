import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.specifications.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsJson.*;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.specifications.preparationDataCustomers.ServiceValues.getField;
import static com.kata.config.serviceCustomers.specifications.preparationResponses.ResponsesApiCustomers.*;

@Epic(CUSTOMERS)
@Story("Удаление клиента методом GET")
public class DeleteCustomersTest {

    @Test
    @DisplayName("Удаление клиента")
    @Description("Проверка корректного удаления клиента.")
    public void testDeleteCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String id = getField(customer, ID);
        String firstName = getField(customer, FIRST_NAME);
        String lastName = getField(customer, LAST_NAME);
        String phoneNumber = getField(customer, PHONE_NUMBER);

        checkRequiredFieldsCorrectCreatedCustomers(customer, firstName, lastName, phoneNumber);

        ValidatableResponse responseDeleteCustomers = responseDeleteCustomers(id);
        ValidatableResponse responseGetCustomers = responseGetCustomersId(id);

        checkStatusCode(responseDeleteCustomers, 204);
        checkStatusCode(responseGetCustomers, 404);
    }

    @Test
    @DisplayName("Удаление несуществующего клиента.")
    @Description("Попытка удалить несуществующего клиента. Ожидаем 404")
    public void testDeleteCustomersNotFound() {
        ValidatableResponse response = responseDeleteCustomers(0);

        checkStatusCode(response, 404);
        checkErrorMessage(response, NOT_FOUND);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле Id строкового значения")
    @Description("Попытка получить клиента, передав в поле ID строковое значение. Ожидаем 400")
    public void testDeleteCustomersIdString() {
        ValidatableResponse response = responseGetCustomersId("str");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле id спецсимвола")
    @Description("Пробуем получить клиента, передав в поле ID спецсимвол &. Ожидаем 400")
    public void testDeleteCustomersIdChar() {
        ValidatableResponse response = responseDeleteCustomers('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
