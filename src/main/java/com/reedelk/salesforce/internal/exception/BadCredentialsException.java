package com.reedelk.salesforce.internal.exception;

import com.reedelk.runtime.api.exception.PlatformException;

public class BadCredentialsException extends PlatformException {

    public BadCredentialsException(String message) {
        super(message);
    }
}
