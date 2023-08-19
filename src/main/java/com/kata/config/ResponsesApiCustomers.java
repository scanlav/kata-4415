package com.kata.config;

import com.kata.config.specifications.RequestSpecificationsApi;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ResponsesApiCustomers {

    public static ValidatableResponse responseGetAllCustomers() {
        return given().spec(RequestSpecificationsApi.reqSpecGetAllCustomers())
                .get().then();
    }

    public static <T> ValidatableResponse responseGetCustomersId(T id) {
        return given().spec(RequestSpecificationsApi.reqSpecGetCustomersId(id))
                .get().then();
    }

    public static <T> ValidatableResponse responseGetCustomerByPhoneNumber(T phoneNumber) {
        return given().spec(RequestSpecificationsApi.reqSpecGetCustomersByPhoneNumber(phoneNumber))
                .get().then();
    }

    public static ValidatableResponse responsePutCustomers(Integer id, String body) {
        return given().spec(RequestSpecificationsApi.reqSpecPutCustomers(id, body))
                .put().then();
    }

    public static <T> ValidatableResponse responseDeleteCustomers(T id) {
        return given().spec(RequestSpecificationsApi.reqSpecDeleteCustomers(id))
                .delete().then();
    }

    public static ValidatableResponse responsePostCustomers(String body) {
        return given().spec(RequestSpecificationsApi.reqSpecPostCustomers(body))
                .post().then();
    }
}
