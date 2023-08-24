package com.kata.config.serviceCustomers.preparationDataCustomers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

import static com.kata.config.serviceCustomers.constants.ConstantsJson.*;

public class ServiceValues {

    public static String getField(ValidatableResponse response, String field) {
        return response
                .body(field, Matchers.notNullValue())
                .extract().jsonPath().getString(field);
    }

    public static String getId(ValidatableResponse response) {
        return getField(response, ID);
    }

    public static String getPhoneNumber(ValidatableResponse response) {
        return getField(response, PHONE_NUMBER);
    }

    public static String randomNumber() {
        return "+7" + RandomStringUtils.randomNumeric(10);
    }
}
