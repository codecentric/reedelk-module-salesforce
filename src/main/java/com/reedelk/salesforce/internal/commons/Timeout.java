package com.reedelk.salesforce.internal.commons;

public class Timeout {

    public static int ofSeconds(int seconds) {
        return seconds * 1000;
    }
}
