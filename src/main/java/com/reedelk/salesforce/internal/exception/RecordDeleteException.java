package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class RecordDeleteException extends PlatformException {

    public RecordDeleteException(String message) {
        super(message);
    }

    public RecordDeleteException(String message, Throwable exception) {
        super(message, exception);
    }
}
