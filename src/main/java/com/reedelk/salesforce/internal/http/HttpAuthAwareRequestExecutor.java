package com.reedelk.salesforce.internal.http;

import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.runtime.api.exception.PlatformException;
import com.reedelk.salesforce.component.SalesforceConfiguration;
import com.reedelk.salesforce.internal.exception.Unauthorized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class HttpAuthAwareRequestExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpAuthAwareRequestExecutor.class);

    public static String execute(HttpBaseRequest request,
                                 SalesforceConfiguration configuration,
                                 ProcessorSync processorSync,
                                 Function<String,? extends PlatformException> exceptionSupplier) {
        String token = HttpAccessTokenProvider.provide(configuration, processorSync);
        if (logger.isDebugEnabled()) {
            logger.debug("Auth Token: " + token);
        }
        request.setAccessToken(token);
        try {
            return HttpRequestExecutor.execute(request, configuration, processorSync, exceptionSupplier);
        } catch (Unauthorized unauthorized) {
            HttpAccessTokenProvider.refresh(configuration, processorSync);
            String newToken = HttpAccessTokenProvider.provide(configuration, processorSync);
            if (logger.isDebugEnabled()) {
                logger.debug("Refreshed, Auth Token: " + newToken);
            }
            request.setAccessToken(newToken);
            return HttpRequestExecutor.execute(request, configuration, processorSync, exceptionSupplier);
        }
    }
}
