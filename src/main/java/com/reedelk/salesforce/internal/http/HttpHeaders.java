package com.reedelk.salesforce.internal.http;

import org.apache.http.HttpRequest;

public class HttpHeaders {

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static void addContentTypeJson(HttpRequest request) {
        request.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }

    public static void addAuthorization(HttpRequest request, String authorization) {
        request.removeHeaders(HEADER_AUTHORIZATION);
        request.addHeader(HEADER_AUTHORIZATION, "Bearer " + authorization);
    }
}
