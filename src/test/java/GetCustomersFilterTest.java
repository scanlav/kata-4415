import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.Constants.CUSTOMERS;
import static com.kata.config.ResponsesApiCustomers.*;
import static com.kata.config.ServiceValues.*;

import static com.kata.config.checks.CheckAnswers.checkFieldsBadResponse;
import static com.kata.config.checks.CheckAnswers.checkFieldsCustomerNotFound;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic(CUSTOMERS + "/filter")
@Story("Получение клиента по номеру телефона методом GET")
public class GetCustomersFilterTest {

    @Test
    @DisplayName("Получение клиента по номеру телефона")
    public void testGetCustomersByPhoneNumber() {
        Integer id = getId(responsePostCustomers("create-customers"));
        String phoneNumber = getPhoneNumber(responseGetCustomersId(id));
        String actualResult = getFirstName(responseGetCustomerByPhoneNumber(phoneNumber));
        String expectedResult = getFirstName(responseGetCustomersId(id));

        assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("Попытка получения клиента с передачей в номер телефона лишних цифр")
    public void testGetCustomersByPhoneNumberConcat() {
        String phoneNumber = getPhoneNumber(responsePostCustomers("create-customers"));

        assertTrue(checkFieldsCustomerNotFound(responseGetCustomerByPhoneNumber(phoneNumber)));
    }

    @Test
    @DisplayName("Попытка получения клиента по несуществующему номеру телефона")
    public void testGetCustomersByPhoneNumberInt() {
        Integer id = getId(responsePostCustomers("create-customers"));
        String phoneNumber = getPhoneNumber(responseGetCustomersId(id));

        responseDeleteCustomers(id);

        assertTrue(checkFieldsCustomerNotFound(responseGetCustomerByPhoneNumber(phoneNumber)));
    }

    @Test
    @DisplayName("Попытка получения клиента, с передачей спецсимволов вместо номера телефона")
    @Description("Сервис должен возвращать код 400, но вместо этого он пытается найти клиента.")
    public void testGetCustomersByPhoneNumberChar() {
        assertTrue(checkFieldsBadResponse(responseGetCustomerByPhoneNumber('&')));
    }
}
