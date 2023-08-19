import com.kata.config.TestConfigProperties;

import io.qameta.allure.*;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.Constants.CUSTOMERS;
import static com.kata.config.ResponsesApiCustomers.*;
import static com.kata.config.ServiceValues.*;

@Epic(CUSTOMERS)
@Story("Изменение данных клиента методом PUT")
public class MethodPutTest extends TestConfigProperties {

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Тест падает с ошибкой сервера 500. Неважно, какие данные передаются, всегда ошибка 500")
    public void testPutCustomers() {
        ValidatableResponse customer = responsePostCustomers("create-customers");
        Integer id = getId(customer);
        String nameBeforeChange = getFirstName(responseGetCustomersId(id));

        Assertions.assertEquals(nameBeforeChange, "Petr");

        ValidatableResponse updateCustomer = responsePutCustomers(id, "create-customers-update");
        String nameAfterChange = getFirstName(updateCustomer);

        Assertions.assertEquals(nameAfterChange, "Иван");
    }
}
