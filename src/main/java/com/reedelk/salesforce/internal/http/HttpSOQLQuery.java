package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpGet;

import static com.reedelk.salesforce.internal.commons.Default.SALESFORCE_QUERY;

public class HttpSOQLQuery extends HttpGet implements HttpBaseRequest {

    public HttpSOQLQuery(String instanceName, String query) {
        super(String.format(SALESFORCE_QUERY, instanceName, query));
    }

    @Override
    public void setAccessToken(String accessToken) {
        HttpHeaders.addAuthorization(this, accessToken);
    }
}
