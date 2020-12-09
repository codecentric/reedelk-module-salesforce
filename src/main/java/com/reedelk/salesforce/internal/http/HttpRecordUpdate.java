package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;

import java.nio.charset.StandardCharsets;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_OBJECT_PATCH;

public class HttpRecordUpdate extends HttpPatch implements HttpBaseRequest {

    public HttpRecordUpdate(String instanceName, String objectName, String objectId, String payload) {
        super(String.format(SALESFORCE_OBJECT_PATCH, instanceName, objectName, objectId));
        setEntity(new StringEntity(payload, StandardCharsets.UTF_8));
        HttpHeaders.addContentTypeJson(this);
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
