import com.kata.config.TestConfigProperties;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.*;

import static com.kata.config.ResponsesApiCustomers.*;
import static com.kata.config.checks.CheckAnswers.*;
import static com.kata.config.Constants.CUSTOMERS;
import static com.kata.config.ServiceValues.getFirstName;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(CUSTOMERS)
@Story("Создание пользователя методом POST")
public class MethodPostTest extends TestConfigProperties {

    @Test
    @DisplayName("Создание покупателя только с обязательными полями")
    public void testCreateNewCustomersRequiredFields() {
        String actual = getFirstName(responsePostCustomers("create-customers"));

        Assertions.assertEquals(actual, "Petr");
    }

    @Test
    @DisplayName("Создание клиента с именем и фамилией на кириллице")
    public void testCreatedCustomersCyrillicFirstLastName() {
        String actual = getFirstName(responsePostCustomers("create-customers-cyrillic"));

        Assertions.assertEquals(actual, "Петр");
    }

    @Test
    @DisplayName("Создание покупателя со всеми заполненными полями")
    @Description("Сервис должен игнорировать заполнение полей, которые генерируются на стороне сервиса. Но " +
            "вместо это он отвечает кодом 400")
    public void testCreateNewCustomersAllFields() {
        ValidatableResponse customer = responsePostCustomers("create-customers-all-fields");

        assertTrue(checkFieldsCorrectCreatedCustomer(customer));
    }

    @Test
    @DisplayName("Попытка создания покупателя, без заполнения обязательных полей")
    public void testCreateNewCustomersWithoutRequiredFields() {
        assertTrue(checkFieldsPostBadResponse(
                responsePostCustomers("create-customers-without-required-fields")));
    }

    @Test
    @DisplayName("Вместо firstName/lastName передаем число")
    public void testCreateNewCustomersFirstNameNumbers() {
        assertTrue(checkFieldsCorrectCreatedCustomer(
                        responsePostCustomers("create-customers-firstName-numbers")));
    }

    @Test
    @DisplayName("В дату рождения передаем данные в формате 0000-00-00")
    public void testCreateNewCustomersBirthdayZero() {
        assertTrue(checkFieldsBadResponse(
                responsePostCustomers("create-customers-birthday-zero")));
    }
}
