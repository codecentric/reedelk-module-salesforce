package com.reedelk.salesforce.internal.http;

import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.salesforce.component.SalesforceConfiguration;
import com.reedelk.salesforce.internal.commons.Default;
import com.reedelk.salesforce.internal.commons.Messages;
import com.reedelk.salesforce.internal.exception.TokenProviderException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.*;

class HttpAccessTokenProvider {

    private static final String GRANT_TYPE = "password";

    private static final Map<String, String> CONFIGURATION_TOKEN_MAP = new HashMap<>();

    public static synchronized String provide(SalesforceConfiguration configuration, ProcessorSync processorSync) {
        if (CONFIGURATION_TOKEN_MAP.containsKey(configuration.getId())) {
            return CONFIGURATION_TOKEN_MAP.get(configuration.getId());
        }

        HttpAccessToken accessToken = request(configuration, processorSync);
        CONFIGURATION_TOKEN_MAP.put(configuration.getId(), accessToken.getAccessToken());
        return accessToken.getAccessToken();
    }

    public static synchronized void refresh(SalesforceConfiguration configuration, ProcessorSync processorSync) {
        HttpAccessToken newToken = request(configuration, processorSync);
        CONFIGURATION_TOKEN_MAP.put(configuration.getId(), newToken.getAccessToken());
    }

    public static void release(SalesforceConfiguration configuration) {
        CONFIGURATION_TOKEN_MAP.remove(configuration.getId());
    }

    private static HttpAccessToken request(SalesforceConfiguration configuration, ProcessorSync processorSync) {
        String authorizationUrl = Optional.ofNullable(configuration.getAuthorizationURL()).orElse(Default.AUTHORIZATION_URL);
        List<NameValuePair> params = createUrlEncodedFormEntity(configuration);
        HttpAccessTokenPost request = new HttpAccessTokenPost(authorizationUrl, params);
        String result = HttpRequestExecutor.execute(request, configuration, processorSync, cause -> {
            String message = Messages.TokenProvider.TOKEN_FETCH_ERROR.format(cause);
            return new TokenProviderException(message);
        });
        JSONObject responseToken = new JSONObject(result);
        return HttpAccessToken.from(responseToken);
    }

    private static List<NameValuePair> createUrlEncodedFormEntity(SalesforceConfiguration configuration) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
        params.add(new BasicNameValuePair("client_id", configuration.getClientId()));
        params.add(new BasicNameValuePair("client_secret", configuration.getClientSecret()));
        params.add(new BasicNameValuePair("username", configuration.getUsername()));
        params.add(new BasicNameValuePair("password", configuration.getPassword()));
        return params;
    }
}
