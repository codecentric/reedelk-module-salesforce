package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpGet;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_OBJECT_GET_WITH_FIELDS;

public class HttpRecordGetWithFields extends HttpGet implements HttpBaseRequest {

    public HttpRecordGetWithFields(String instanceName, String objectName, String objectId, String fields) {
        super(String.format(SALESFORCE_OBJECT_GET_WITH_FIELDS, instanceName, objectName, objectId, fields));
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
