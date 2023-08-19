import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.Constants.CUSTOMERS;
import static com.kata.config.ResponsesApiCustomers.*;
import static com.kata.config.ServiceValues.getFirstName;
import static com.kata.config.ServiceValues.getId;
import static com.kata.config.checks.CheckAnswers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(CUSTOMERS)
@Story("Получение клиентов методом GET")
public class MethodGetTest {

    @Test
    @DisplayName("Получение всех клиентов")
    public void testGetAllCustomers() {
       Integer status = responseGetAllCustomers().extract().statusCode();

       assertEquals(status, 200);
    }

    @Test
    @DisplayName("Получение клиента по id")
    public void testGetCustomersId() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        Integer id = getId(customer);
        String expectedFirstName = getFirstName(customer);
        String actualFirstName = getFirstName(responseGetCustomersId(id));

        assertEquals(expectedFirstName, actualFirstName);
    }

    @Test
    @DisplayName("Попытка получения клиента с передачей в поле id строки")
    public void testGetCustomersString() {
        assertTrue(checkFieldsBadResponse(responseGetCustomersId("str")));
    }

    @Test
    @DisplayName("Попытка получения клиента, передачей спецсимвола в поле Id")
    public void testGetCustomersCharId() {
        assertTrue(checkFieldsBadResponse(responseGetCustomersId('^')));
    }

    @Test
    @DisplayName("Попытка превышения максимального значения поля id")
    @Description("Согласно документации, тип данных поля id int32. Но по факту сервис принимает и большее " +
            "значение")
    public void testGetCustomersMaxValueExcessId() {
        assertTrue(checkFieldsBadResponse(responseGetCustomersId(Integer.MAX_VALUE)));
    }

    @Test
    @DisplayName("Попытка получения несуществующего клиента")
    public void testGetUnknownCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        assertTrue(checkFieldsCorrectCreatedCustomer(customer));
        Integer id = getId(customer);

        responseDeleteCustomers(id);

        assertTrue(checkFieldsCustomerNotFound(responseGetCustomersId(id)));
    }
}
