package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpGet;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_QUERY_WITH_NEXT_TOKEN;

public class HttpSOQLQueryWithNextRecords extends HttpGet implements HttpBaseRequest {

    public HttpSOQLQueryWithNextRecords(String instanceName, String nextRecords) {
        super(String.format(SALESFORCE_QUERY_WITH_NEXT_TOKEN, instanceName, nextRecords));
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}

