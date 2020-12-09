package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class SOQLQueryException extends PlatformException {

    public SOQLQueryException(String message) {
        super(message);
    }

    public SOQLQueryException(String message, Throwable exception) {
        super(message, exception);
    }
}
