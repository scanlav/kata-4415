package com.kata.config.serviceCustomers.specifications.checks;

import com.kata.config.serviceCustomers.specifications.matchers.DateMatchers;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public class CheckAnswers {

    public static void checkAllFieldsCorrectCreatedCustomer(ValidatableResponse response, String firstName,
                                                                           String lastName, String phoneNumber, String email,
                                                                           String dateOfBirth  ) {
        response
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo(firstName))
                .body("lastName", Matchers.equalTo(lastName))
                .body("phoneNumber", Matchers.equalTo(phoneNumber))
                .body("email", Matchers.equalTo(email))
                .body("dateOfBirth", Matchers.equalTo(dateOfBirth))
                .body("loyalty.bonusCardNumber", Matchers.notNullValue())
                .body("loyalty.status", Matchers.notNullValue())
                .body("loyalty.discountRate", Matchers.notNullValue())
                .body("updatedAt", DateMatchers.isToday())
                .body("createdAt", DateMatchers.isToday());
    }

    public static void checkRequiredFieldsCorrectCreatedCustomers(ValidatableResponse response,
                                                                String firstName, String lastName,
                                           String phoneNumber) {
        response
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo(firstName))
                .body("lastName", Matchers.equalTo(lastName))
                .body("phoneNumber", Matchers.equalTo(phoneNumber));
    }

    public static void checkFieldCustomer(ValidatableResponse response, String field, String value) {
        response
                .body(field, Matchers.equalTo(value));
    }

    public static void checkStatusCode(ValidatableResponse response, Integer statusCode) {
        response
                .statusCode(statusCode);
    }

    public static void checkErrorMessage(ValidatableResponse response, String errors) {
        if (response.extract().statusCode() == 404) {
            response
                    .body("errors", Matchers.hasItem(errors));
        } else if (response.extract().statusCode() == 400) {
            response
                    .body("error", Matchers.equalTo(errors));
        }
    }

    public static void checkErrorMessage(ValidatableResponse response, String[] errors) {
        for (String error: errors) {
            response
                    .body("errors", Matchers.hasItem(error));
        }
    }
}
