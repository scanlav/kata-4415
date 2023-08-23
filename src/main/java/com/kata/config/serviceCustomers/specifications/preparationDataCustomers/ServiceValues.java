package com.kata.config.serviceCustomers.specifications.preparationDataCustomers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

public class ServiceValues {

    public static String getField(ValidatableResponse response, String field) {
        return response
                .body(field, Matchers.notNullValue())
                .extract().jsonPath().getString(field);
    }

    public static String randomNumber() {
        return "+7" + RandomStringUtils.randomNumeric(10);
    }
}
