package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class RecordUpdateException extends PlatformException {

    public RecordUpdateException(String message) {
        super(message);
    }

    public RecordUpdateException(String message, Throwable exception) {
        super(message, exception);
    }
}
