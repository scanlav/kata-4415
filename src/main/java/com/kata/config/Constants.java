package com.kata.config;

import org.apache.commons.lang3.RandomStringUtils;

import static com.kata.config.TestConfigProperties.getValue;

public class Constants {

    public final static String BASE_PATH_API = getValue("api.base.url");
    public final static String CUSTOMERS = "/customers";
    public final static String CUSTOMERS_ID = "/customers/";
    public final static String PATH_GET_CUSTOMER_BY_PHONE_NUMBER = "/customers/filter";
}
