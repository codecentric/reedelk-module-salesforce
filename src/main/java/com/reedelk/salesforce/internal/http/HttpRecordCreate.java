package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.nio.charset.StandardCharsets;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_OBJECT_CREATE;

public class HttpRecordCreate extends HttpPost implements HttpBaseRequest {

    public HttpRecordCreate(String instanceName, String objectName, String payload) {
        super(String.format(SALESFORCE_OBJECT_CREATE, instanceName, objectName));
        setEntity(new StringEntity(payload, StandardCharsets.UTF_8));
        HttpHeaders.addContentTypeJson(this);
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
