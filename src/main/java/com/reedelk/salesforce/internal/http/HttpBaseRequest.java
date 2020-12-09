package com.reedelk.salesforce.internal.http;

import org.apache.http.client.methods.HttpUriRequest;

public interface HttpBaseRequest extends HttpUriRequest {

    void setAccessToken(String accessToken);

}
