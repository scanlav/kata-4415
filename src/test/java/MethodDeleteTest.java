
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.Constants.CUSTOMERS;
import static com.kata.config.ServiceValues.getId;
import static com.kata.config.ResponsesApiCustomers.*;
import static com.kata.config.checks.CheckAnswers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(CUSTOMERS)
@Story("Удаление клиента методом GET")
public class MethodDeleteTest {

    @Test
    @DisplayName("Удаление клиента")
    public void testDeleteCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        Integer id = getId(customer);

        assertTrue(checkFieldsCorrectCreatedCustomer(customer));

        responseDeleteCustomers(id);

        assertTrue(checkFieldsCustomerNotFound(responseGetCustomersId(id)));
    }

    @Test
    @DisplayName("Попытка удаления несуществующего клиента")
    public void testDeleteCustomersNotFound() {
        assertTrue(checkFieldsCustomerNotFound(responseDeleteCustomers(0)));
    }

    @Test
    @DisplayName("Передача в поле Id строкового значения")
    public void testDeleteCustomersIdString() {
        assertTrue(checkFieldsBadResponse(responseDeleteCustomers("str")));
    }

    @Test
    @DisplayName("Передача в поле id спецсимвола")
    public void testDeleteCustomersIdChar() {
        assertTrue(checkFieldsBadResponse(responseDeleteCustomers('&')));
    }
}
