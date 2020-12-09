package com.reedelk.salesforce.internal.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpAccessTokenPost extends HttpPost implements HttpBaseRequest {

    public HttpAccessTokenPost(String authorizationUrl, List<NameValuePair> params) {
        super(authorizationUrl);
        setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
    }
    @Override
    public void setAccessToken(String accessToken) {

    }
}
