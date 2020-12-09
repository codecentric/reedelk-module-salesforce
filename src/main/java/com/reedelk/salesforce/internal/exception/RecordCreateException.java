package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class RecordCreateException extends PlatformException {

    public RecordCreateException(String message) {
        super(message);
    }

    public RecordCreateException(String message, Throwable exception) {
        super(message, exception);
    }
}
