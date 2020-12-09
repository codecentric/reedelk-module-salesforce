package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpGet;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_OBJECT_DESCRIBE;

public class HttpDescribe extends HttpGet implements HttpBaseRequest {

    public HttpDescribe(String instanceName, String objectName) {
        super(String.format(SALESFORCE_OBJECT_DESCRIBE, instanceName, objectName));
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
