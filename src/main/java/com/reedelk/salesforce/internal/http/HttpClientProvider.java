package com.reedelk.salesforce.internal.http;

import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.runtime.api.exception.PlatformException;
import com.reedelk.salesforce.component.SalesforceConfiguration;
import com.reedelk.salesforce.internal.commons.Default;
import com.reedelk.salesforce.internal.commons.Disposables;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientProvider {

    private static final Map<String, HttpClientHolder> CONFIG_ID_CLIENT_MAP = new HashMap<>();

    static class HttpClientHolder {
        CloseableHttpAsyncClient client;
        List<ProcessorSync> users = new ArrayList<>();
    }

    public static synchronized CloseableHttpAsyncClient provide(SalesforceConfiguration configuration, ProcessorSync user) {
        if (!CONFIG_ID_CLIENT_MAP.containsKey(configuration.getId())) {
            // We must create a brand new client
            RequestConfig requestConfig = createRequestConfig();
            PoolingNHttpClientConnectionManager pool = createConnectionPool();
            CloseableHttpAsyncClient httpClient = createHttpClient(pool, requestConfig);
            httpClient.start();

            HttpClientHolder holder = new HttpClientHolder();
            holder.client = httpClient;
            CONFIG_ID_CLIENT_MAP.put(configuration.getId(), holder);
        }

        HttpClientHolder client = CONFIG_ID_CLIENT_MAP.get(configuration.getId());
        if (!client.users.contains(user)) client.users.add(user);
        return client.client;
    }

    public static synchronized void release(SalesforceConfiguration configuration, ProcessorSync user) {
        if (CONFIG_ID_CLIENT_MAP.containsKey(configuration.getId())) {
            HttpClientHolder holder = CONFIG_ID_CLIENT_MAP.get(configuration.getId());
            holder.users.remove(user);
            if (holder.users.isEmpty()) {
                // We must remove the client if there are no users using it.
                Disposables.closeSilently(holder.client);
                CONFIG_ID_CLIENT_MAP.remove(configuration.getId());
                HttpAccessTokenProvider.release(configuration);
            }
        }
    }

    private static PoolingNHttpClientConnectionManager createConnectionPool() {
        DefaultConnectingIOReactor connectingIOReactor = createIO();
        PoolingNHttpClientConnectionManager pool = new PoolingNHttpClientConnectionManager(connectingIOReactor);
        pool.setDefaultMaxPerRoute(Default.MAX_REQ_PER_ROUTE);
        pool.setMaxTotal(Default.MAX_REQ_TOTAL);
        return pool;
    }

    private static CloseableHttpAsyncClient createHttpClient(PoolingNHttpClientConnectionManager pool, RequestConfig requestConfig) {
        return HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(pool)
                .build();
    }

    private static RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(Default.CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(Default.SOCKET_TIMEOUT)
                .setConnectTimeout(Default.CONNECT_TIMEOUT)
                .build();
    }

    private static DefaultConnectingIOReactor createIO() {
        try {
            return new DefaultConnectingIOReactor();
        } catch (IOReactorException exception) {
            // TODO: Fixme
            throw new PlatformException(exception.getMessage());
        }
    }
}
