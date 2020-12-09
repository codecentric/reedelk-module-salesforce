package com.reedelk.salesforce.internal.commons;

import org.apache.http.StatusLine;

public class HttpResponseStatus {

    private HttpResponseStatus() {
    }

    public static boolean isSuccessful(StatusLine statusLine) {
        int code = statusLine.getStatusCode();
        return ((200 <= code) && (code <= 299));
    }

    public static boolean isUnAuthorized(StatusLine statusLine) {
        return statusLine.getStatusCode() == Default.UNAUTHORIZED_STATUS_CODE;
    }
}
