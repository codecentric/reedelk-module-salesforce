package com.reedelk.salesforce.internal.commons;

public class Default {

    public static final int SOCKET_TIMEOUT = Timeout.ofSeconds(10);
    public static final int CONNECTION_REQUEST_TIMEOUT = Timeout.ofSeconds(10);
    public static final int CONNECT_TIMEOUT = Timeout.ofSeconds(10);
    public static final int MAX_REQ_PER_ROUTE = 20;
    public static final int MAX_REQ_TOTAL = 20;

    private static final String SALESFORCE_API = "https://%s.salesforce.com/services/data/v49.0/";
    public static final String SALESFORCE_OBJECT_DESCRIBE = SALESFORCE_API + "sobjects/%s/describe/";
    public static final String SALESFORCE_OBJECT_CREATE = SALESFORCE_API + "sobjects/%s/";
    public static final String SALESFORCE_OBJECT_DELETE = SALESFORCE_API + "sobjects/%s/%s";
    public static final String SALESFORCE_OBJECT_GET = SALESFORCE_API + "sobjects/%s/%s";
    public static final String SALESFORCE_OBJECT_GET_WITH_FIELDS = SALESFORCE_OBJECT_GET + "?fields=%s";
    public static final String SALESFORCE_OBJECT_PATCH = SALESFORCE_API + "sobjects/%s/%s";
    public static final String SALESFORCE_QUERY= SALESFORCE_API + "query/?q=%s";
    public static final String SALESFORCE_QUERY_WITH_NEXT_TOKEN = "https://%s.salesforce.com%s";

    public static final int ATTEMPT_DELAY_SECONDS = 2;
    public static final int MAX_RETRY_ATTEMPTS = 3;
    public static final int UNAUTHORIZED_STATUS_CODE = 401;
    public static final String AUTHORIZATION_URL = "https://login.salesforce.com/services/oauth2/token";

}
