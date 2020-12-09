package com.reedelk.salesforce.internal.commons;

import com.reedelk.runtime.api.exception.PlatformException;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.function.Function;

public class HttpEntityUtils {

    public static String toString(HttpEntity entity, Function<String,? extends PlatformException> exceptionSupplier) {
        try {
            return entity != null ? EntityUtils.toString(entity) : null;
        } catch (IOException exception) {
            throw exceptionSupplier.apply(exception.getMessage());
        }
    }
}
