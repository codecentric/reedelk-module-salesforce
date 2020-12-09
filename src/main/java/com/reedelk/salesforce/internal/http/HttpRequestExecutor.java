package com.reedelk.salesforce.internal.http;

import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.runtime.api.exception.PlatformException;
import com.reedelk.salesforce.component.SalesforceConfiguration;
import com.reedelk.salesforce.internal.commons.Default;
import com.reedelk.salesforce.internal.commons.HttpEntityUtils;
import com.reedelk.salesforce.internal.commons.HttpResponseStatus;
import com.reedelk.salesforce.internal.exception.Unauthorized;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static com.reedelk.salesforce.internal.commons.Default.ATTEMPT_DELAY_SECONDS;

public class HttpRequestExecutor {

    public static String execute(HttpBaseRequest request,
                                 SalesforceConfiguration configuration,
                                 ProcessorSync processorSync,
                                 Function<String,? extends PlatformException> exceptionSupplier) {

        HttpResponse response = Failsafe.with(new RetryPolicy<>()
                .withDelay(Duration.of(ATTEMPT_DELAY_SECONDS, ChronoUnit.SECONDS))
                .withMaxAttempts(Default.MAX_RETRY_ATTEMPTS)
                .handle(Exception.class))
                .onFailure(objectExecutionCompletedEvent -> {
                    Throwable failure = objectExecutionCompletedEvent.getFailure();
                    exceptionSupplier.apply(failure.getMessage());
                })
                .get(() -> executeRequest(request, configuration, processorSync));

        StatusLine statusLine = response.getStatusLine();
        String responseData = HttpEntityUtils.toString(response.getEntity(), exceptionSupplier);
        if (HttpResponseStatus.isSuccessful(statusLine)) {
            return responseData;
        } else if (HttpResponseStatus.isUnAuthorized(statusLine)) {
            throw new Unauthorized();
        } else {
            throw exceptionSupplier.apply(responseData);
        }
    }

    private static HttpResponse executeRequest(HttpBaseRequest request,
                                               SalesforceConfiguration configuration,
                                               ProcessorSync processorSync) throws ExecutionException, InterruptedException {
        CloseableHttpAsyncClient httpclient = HttpClientProvider.provide(configuration, processorSync);
        return httpclient.execute(request, null).get();
    }
}