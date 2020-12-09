package com.reedelk.salesforce.internal.commons;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.io.IOException;

public class Disposables {

    public static void closeSilently(CloseableHttpAsyncClient httpclient) {
        try {
            httpclient.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
