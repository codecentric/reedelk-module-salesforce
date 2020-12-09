package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class RecordGetException extends PlatformException {

    public RecordGetException(String message) {
        super(message);
    }

    public RecordGetException(String message, Throwable exception) {
        super(message, exception);
    }
}
