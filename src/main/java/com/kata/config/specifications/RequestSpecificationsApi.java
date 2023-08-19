package com.kata.config.specifications;

import com.kata.config.Constants;
import com.kata.config.DataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.kata.config.ServiceValues.randomNumber;

public class RequestSpecificationsApi {

    public static RequestSpecBuilder specBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_PATH_API)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());
    }

    public static RequestSpecification reqSpecGetAllCustomers() {
        return specBuilder()
                .setBasePath(Constants.CUSTOMERS)
                .build();
    }

    public static <T> RequestSpecification reqSpecGetCustomersId(T id) {
        return specBuilder()
                .setBasePath(Constants.CUSTOMERS_ID + id)
                .build();
    }

    public static <T> RequestSpecification reqSpecGetCustomersByPhoneNumber(T phoneNumber) {
        return specBuilder()
                .setBasePath(Constants.PATH_GET_CUSTOMER_BY_PHONE_NUMBER)
                .addQueryParam("phoneNumber", phoneNumber)
                .build();
    }

    public static RequestSpecification reqSpecPutCustomers(Integer id, String body) {
        return specBuilder()
                .setBasePath(Constants.CUSTOMERS + "/" + id)
                .setBody(DataProvider.readJsonAsString(body))
                .build();
    }

    public static <T> RequestSpecification reqSpecDeleteCustomers(T id) {
        return specBuilder()
                .setBasePath(Constants.CUSTOMERS_ID + id)
                .build();
    }

    public static RequestSpecification reqSpecPostCustomers(String body) {
        return specBuilder()
                .setBody(DataProvider.readJsonAsString(body)
                        .replace("{phoneNumber}", randomNumber()))
                .setBasePath(Constants.CUSTOMERS)
                .build();
    }
}
