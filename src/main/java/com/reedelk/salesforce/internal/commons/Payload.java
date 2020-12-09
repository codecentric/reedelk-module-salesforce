package com.reedelk.salesforce.internal.commons;

import com.reedelk.runtime.api.component.Implementor;
import org.json.JSONObject;

import java.util.Map;

import static com.reedelk.runtime.api.commons.ComponentPrecondition.Input;

public class Payload {

    @SuppressWarnings("unchecked")
    public static String toJson(Class<? extends Implementor> implementor, Object payload) {
        Input.requireTypeMatchesAny(implementor, payload, String.class, Map.class);
        if (payload instanceof String) {
            // We assume it is already json.
            return (String) payload;
        } else {
            // Must convert java to json
            JSONObject object = new JSONObject((Map<Object,Object>) payload);
            return object.toString();
        }
    }
}
