package com.reedelk.salesforce.internal.commons;

import com.reedelk.runtime.api.commons.FormattedMessage;

public class Messages {

    public enum RecordDelete implements FormattedMessage {

        OBJECT_ID_EMPTY("The object id is empty. The object id must not be empty (DynamicValue=[%s]).");

        private final String message;

        RecordDelete(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }

    public enum RecordUpdate implements FormattedMessage {

        OBJECT_ID_EMPTY("The object id is empty. The object id must not be empty (DynamicValue=[%s]).");

        private final String message;

        RecordUpdate(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }

    public enum RecordGet implements FormattedMessage {

        OBJECT_ID_EMPTY("The object id is empty. The object id must not be empty (DynamicValue=[%s]).");

        private final String message;

        RecordGet(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }

    public enum SOQLQuery implements FormattedMessage {

        SOQL_QUERY_EMPTY("The SOQL query is empty. The SOQL query must not be empty (DynamicValue=[%s]).");

        private final String message;

        SOQLQuery(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }

    public enum ObjectDescribe implements FormattedMessage {

        OBJECT_NAME_EMPTY("The object name is empty. The object name must not be empty (DynamicValue=[%s]).");

        private final String message;

        ObjectDescribe(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }

    public enum TokenProvider implements FormattedMessage {

        TOKEN_FETCH_ERROR("Could not retrieve access token, cause=[%s]");

        private final String message;

        TokenProvider(String message) {
            this.message = message;
        }

        @Override
        public String template() {
            return message;
        }
    }
}
