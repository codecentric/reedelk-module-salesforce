package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpGet;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_OBJECT_GET;

public class HttpRecordGet extends HttpGet implements HttpBaseRequest {

    public HttpRecordGet(String instanceName, String objectName, String objectId) {
        super(String.format(SALESFORCE_OBJECT_GET, instanceName, objectName, objectId));
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
