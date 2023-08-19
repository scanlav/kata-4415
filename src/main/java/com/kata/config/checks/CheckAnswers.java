package com.kata.config.checks;

import com.kata.config.matchers.DateMatchers;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public class CheckAnswers {

    public static Boolean checkFieldsCorrectCreatedCustomer(ValidatableResponse response) {
        return response == response
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.notNullValue())
                .body("lastName", Matchers.notNullValue())
                .body("phoneNumber", Matchers.notNullValue())
                .body("loyalty.bonusCardNumber", Matchers.notNullValue())
                .body("loyalty.status", Matchers.notNullValue())
                .body("loyalty.discountRate", Matchers.notNullValue())
                .body("updatedAt", DateMatchers.isToday())
                .body("createdAt", DateMatchers.isToday());
    }

    public static Boolean checkFieldsBadResponse(ValidatableResponse response) {
        return response == response
                .statusCode(400)
                .body("error", Matchers.equalTo("Bad Request"));
    }

    public static Boolean checkFieldsPostBadResponse(ValidatableResponse response) {
        return response == response
                .statusCode(400)
                .body("errors", Matchers.hasItem("Mandatory field missing: phoneNumber"))
                .body("errors", Matchers.hasItem("Mandatory field missing: firstName"))
                .body("errors", Matchers.hasItem("Mandatory field missing: lastName"));
    }

    public static Boolean checkFieldsCustomerNotFound(ValidatableResponse response) {
        return response == response
                .statusCode(404)
                .body("errors", Matchers.hasItem("Customer not found"));
    }
}
