package com.kata.config;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

public class ServiceValues {

    public static Integer getId(ValidatableResponse response) {
        return response
                .body("id", Matchers.notNullValue())
                .extract().jsonPath().getInt("id");
    }

    public static String getPhoneNumber(ValidatableResponse response) {
        return response
                .body("phoneNumber", Matchers.notNullValue())
                .extract().jsonPath().getString("phoneNumber");
    }

    public static String getFirstName(ValidatableResponse response) {
        return response
                .body("firstName", Matchers.notNullValue())
                .extract().jsonPath().getString("firstName");
    }

    public static String randomNumber() {
        return "+7" + RandomStringUtils.randomNumeric(10);
    }
}
