import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.checkFieldCustomer;
import static com.kata.config.serviceCustomers.constants.ConstantsJson.FIRST_NAME;
import static com.kata.config.serviceCustomers.constants.ConstantsJson.ID;
import static com.kata.config.serviceCustomers.constants.ConstantsService.CUSTOMERS;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.getField;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.*;

@Epic(CUSTOMERS)
@Story("Изменение данных клиента методом PUT")
public class PutUpdateCustomersTest {

    @Test
    @DisplayName("Изменение данных клиента")
    @Description("Тест падает с ошибкой сервера 500. Неважно, какие данные передаются, всегда ошибка 500")
    public void testPutCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        String id = getField(customer, ID);
        ValidatableResponse response = responseGetCustomersId(id);

        checkFieldCustomer(response, FIRST_NAME, "Petr");

        responsePutCustomers(id, "create-customers-update");
        checkFieldCustomer(response, FIRST_NAME, "Иван");
    }
}
