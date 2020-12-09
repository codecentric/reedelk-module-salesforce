package com.reedelk.salesforce.internal.http;

import org.json.JSONObject;

public class HttpAccessToken {

    private final String id;
    private final String issuedAt;
    private final String tokenType;
    private final String signature;
    private final String instanceUrl;
    private final String accessToken;

    private HttpAccessToken(String accessToken, String instanceUrl, String id, String tokenType, String issuedAt, String signature) {
        this.id = id;
        this.issuedAt = issuedAt;
        this.signature = signature;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.instanceUrl = instanceUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public String getId() {
        return id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getSignature() {
        return signature;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id='" + id + '\'' +
                ", issuedAt='" + issuedAt + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", signature='" + signature + '\'' +
                ", instanceUrl='" + instanceUrl + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    // Salesforce serialized Access Token Response
    public static HttpAccessToken from(JSONObject jsonObject) {
        String accessToken = jsonObject.getString("access_token");
        String instanceUrl = jsonObject.getString("instance_url");
        String id = jsonObject.getString("id");
        String tokenType = jsonObject.getString("token_type");
        String issuedAt = jsonObject.getString("issued_at");
        String signature = jsonObject.getString("signature");
        return new HttpAccessToken(accessToken, instanceUrl, id, tokenType, issuedAt, signature);
    }
}
