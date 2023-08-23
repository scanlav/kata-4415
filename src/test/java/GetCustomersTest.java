import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.specifications.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsJson.ID;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsJson.PHONE_NUMBER;
import static com.kata.config.serviceCustomers.specifications.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.specifications.preparationDataCustomers.ServiceValues.getField;
import static com.kata.config.serviceCustomers.specifications.preparationResponses.ResponsesApiCustomers.*;

@Epic(CUSTOMERS)
@Story("Получение клиентов методом GET")
public class GetCustomersTest {

    @Test
    @DisplayName("Получение всех клиентов")
    @Description("Проверяем корректную работу метода получения всех клиентов в базе. Ожидаем 200")
    public void testGetAllCustomers() {
       ValidatableResponse response = responseGetAllCustomers();

       checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("Получение клиента по id")
    @Description("Получаем клиента по корректному ID. Сначала создаем клиента, получаем его ID и получаем " +
            "этого же клиента с помощью метода. ")
    public void testGetCustomersId() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String id = getField(customer, ID);
        String phoneNumber = getField(responseGetCustomersId(id), PHONE_NUMBER);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Petr", "Petrov", phoneNumber);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле id строки")
    @Description("Пытаемся получить клиента с передачей в поле ID строкового значения. Ожидаем 400")
    public void testGetCustomersString() {
        ValidatableResponse response = responseGetCustomersId("str");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение клиента c передачей спецсимвола в поле Id")
    @Description("Пытаемся получить клиента с передачей спецсимвола & в поле ID. Ожидаем 400")
    public void testGetCustomersCharId() {
        ValidatableResponse response = responseGetCustomersId('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Превышение максимального значения поля id при получении клиента")
    @Description("Согласно документации, тип данных поля id int32. Но по факту сервис принимает и большее " +
            "значение")
    public void testGetCustomersMaxValueExcessId() {
        ValidatableResponse response = responseGetCustomersId(Integer.MAX_VALUE);

        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение несуществующего клиента")
    @Description("Пытаемся получить несуществующего клиента. Что бы проверка была корректной, сначала " +
            "создаем клиента, получаем его ID, затем удаляем, и пробуем получить с помощью метода. Ожидаем " +
            "404")
    public void testGetUnknownCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String id = getField(customer, ID);
        responseDeleteCustomers(id);
        ValidatableResponse response = responseGetCustomersId(id);

        checkStatusCode(response, 404);
        checkErrorMessage(response, NOT_FOUND);
    }
}
