package com.reedelk.salesforce.component;

import com.reedelk.runtime.api.annotation.*;
import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.runtime.api.flow.FlowContext;
import com.reedelk.runtime.api.message.Message;
import com.reedelk.runtime.api.message.MessageAttributes;
import com.reedelk.runtime.api.message.MessageBuilder;
import com.reedelk.runtime.api.script.ScriptEngineService;
import com.reedelk.runtime.api.script.dynamicvalue.DynamicString;
import com.reedelk.salesforce.internal.exception.SOQLQueryException;
import com.reedelk.salesforce.internal.http.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.net.URLEncoder;
import java.util.function.Function;

import static com.reedelk.runtime.api.commons.ComponentPrecondition.Configuration.requireNotNull;
import static com.reedelk.salesforce.internal.commons.Messages.SOQLQuery.SOQL_QUERY_EMPTY;
import static org.osgi.service.component.annotations.ServiceScope.PROTOTYPE;

@ModuleComponent("Salesforce SOQL Query")
@ComponentOutput(
        attributes = MessageAttributes.class,
        payload = String.class,
        description = "A JSON response containing the results of the SOQL Query.")
@ComponentInput(
        payload = Object.class,
        description = "The component input is used to evaluate the dynamic " +
                "value provided for the SOQL query.")
@Description("The SOQL Query Component allows to use the Salesforce Object Query Language (SOQL) " +
        "to search your organization’s Salesforce data for specific information. " +
        "If the initial query returns only part of the results, the end of the response will contain a field called 'nextRecordsUrl'. " +
        "The 'nextRecordsUrl' can be used to retrieve the next batch of results and it can be repeated until all records have been retrieved.")
@Component(service = SOQLQuery.class, scope = PROTOTYPE)
public class SOQLQuery implements ProcessorSync {

    @Property("Configuration")
    @Description("Salesforce authentication configuration.")
    private SalesforceConfiguration configuration;

    @Property("SOQL Query")
    @Example("SELECT name from Account")
    @Hint("SELECT Id, Name FROM Account WHERE Name = 'Sandy'")
    @Description("The Salesforce Object Query (SOQL) to be used to search for organization's Salesforce data.")
    private DynamicString query;

    @Property("Next Records URL")
    @Example("/services/data/v20.0/query/01gD0000002HU6KIAW-2000")
    @Hint("/services/data/v20.0/query/01gD0000002HU6KIAW-2000")
    @Description("The next records URL returned by the first invocation of this component.")
    private DynamicString nextRecordsURL;

    @Reference
    ScriptEngineService scriptService;

    @Override
    public void initialize() {
        requireNotNull(RecordCreate.class, configuration, "Salesforce configuration must be provided.");
        configuration.validate(RecordCreate.class);
    }

    @Override
    public Message apply(FlowContext flowContext, Message message) {

        String evaluatedNextRecordsURL = scriptService
                .evaluate(nextRecordsURL, flowContext, message).orElse(null);

        HttpBaseRequest request;
        if (evaluatedNextRecordsURL == null) {
            String evaluatedQuery = scriptService.evaluate(query, flowContext, message)
                    .orElseThrow(() -> new SOQLQueryException(SOQL_QUERY_EMPTY.format(query.value())));
            String encodedQuery = URLEncoder.encode(evaluatedQuery);
            request = new HttpSOQLQuery(configuration.getInstanceName(), encodedQuery);
        } else {
            request = new HttpSOQLQueryWithNextRecords(configuration.getInstanceName(), evaluatedNextRecordsURL);
        }

        String result = HttpAuthAwareRequestExecutor.execute(request, configuration, this, exceptionSupplier);

        return MessageBuilder.get(SOQLQuery.class)
                .withJson(result)
                .build();
    }

    @Override
    public void dispose() {
        HttpClientProvider.release(configuration, this);
    }

    private final Function<String, SOQLQueryException> exceptionSupplier = SOQLQueryException::new;

    public void setConfiguration(SalesforceConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setQuery(DynamicString query) {
        this.query = query;
    }

    public void setNextRecordsURL(DynamicString nextRecordsURL) {
        this.nextRecordsURL = nextRecordsURL;
    }
}
