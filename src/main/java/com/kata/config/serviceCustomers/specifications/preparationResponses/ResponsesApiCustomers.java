package com.kata.config.serviceCustomers.specifications.preparationResponses;

import com.kata.config.serviceCustomers.specifications.Specifications;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ResponsesApiCustomers {

    public static ValidatableResponse responseGetAllCustomers() {
        return given().spec(Specifications.reqSpecGetAllCustomers())
                .get().then();
    }

    public static <T> ValidatableResponse responseGetCustomersId(T id) {
        return given().spec(Specifications.reqSpecGetCustomersId(id))
                .get().then();
    }

    public static <T> ValidatableResponse responseGetCustomerByPhoneNumber(T phoneNumber) {
        return given().spec(Specifications.reqSpecGetCustomersByPhoneNumber(phoneNumber))
                .get().then();
    }

    public static <T> ValidatableResponse responsePutCustomers(T id, String body) {
        return given().spec(Specifications.reqSpecPutCustomers(id, body))
                .put().then();
    }

    public static <T> ValidatableResponse responseDeleteCustomers(T id) {
        return given().spec(Specifications.reqSpecDeleteCustomers(id))
                .delete().then();
    }

    public static ValidatableResponse responsePostCustomers(String body) {
        return given().spec(Specifications.reqSpecPostCustomers(body))
                .post().then();
    }
}
