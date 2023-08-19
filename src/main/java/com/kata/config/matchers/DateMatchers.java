package com.kata.config.matchers;

import org.hamcrest.Matcher;

public class DateMatchers {

    public static Matcher<Object> isToday() {
        return  new IsToday<>();
    }
}
