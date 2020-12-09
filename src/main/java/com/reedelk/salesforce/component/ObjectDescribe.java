package com.reedelk.salesforce.component;

import com.reedelk.runtime.api.annotation.*;
import com.reedelk.runtime.api.component.ProcessorSync;
import com.reedelk.runtime.api.flow.FlowContext;
import com.reedelk.runtime.api.message.Message;
import com.reedelk.runtime.api.message.MessageAttributes;
import com.reedelk.runtime.api.message.MessageBuilder;
import com.reedelk.runtime.api.message.content.MimeType;
import com.reedelk.runtime.api.script.ScriptEngineService;
import com.reedelk.runtime.api.script.dynamicvalue.DynamicString;
import com.reedelk.salesforce.internal.exception.ObjectDescribeException;
import com.reedelk.salesforce.internal.exception.RecordUpdateException;
import com.reedelk.salesforce.internal.http.HttpAuthAwareRequestExecutor;
import com.reedelk.salesforce.internal.http.HttpClientProvider;
import com.reedelk.salesforce.internal.http.HttpDescribe;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.function.Function;

import static com.reedelk.runtime.api.commons.ComponentPrecondition.Configuration.requireNotNull;
import static com.reedelk.runtime.api.commons.ComponentPrecondition.Configuration.requireNotNullOrBlank;
import static com.reedelk.salesforce.internal.commons.Messages.ObjectDescribe.OBJECT_NAME_EMPTY;
import static org.osgi.service.component.annotations.ServiceScope.PROTOTYPE;

@ModuleComponent("Salesforce Object Describe")
@ComponentOutput(attributes = MessageAttributes.class, payload = Object.class, description = "DescribeObject Output description")
@ComponentInput(payload = Object.class, description = "DescribeObject Input description")
@Description("The Salesforce Object Describe Component retrieves all the metadata for an object, " +
        "including information about each field, URLs, and child relationships.")
@Component(service = ObjectDescribe.class, scope = PROTOTYPE)
public class ObjectDescribe implements ProcessorSync {

    @Property("Configuration")
    @Description("Salesforce authentication configuration.")
    private SalesforceConfiguration configuration;

    @Property("Object Name")
    @Hint("AccountOwnerSharingRule")
    @Example("AccountContactRole")
    @Description("The name of the Salesforce object to describe.")
    private DynamicString objectName;

    @Reference
    ScriptEngineService scriptService;

    @Override
    public void initialize() {
        requireNotNull(RecordCreate.class, configuration, "Salesforce configuration must be provided.");
        configuration.validate(RecordCreate.class);
        requireNotNullOrBlank(RecordCreate.class, objectName, "Salesforce object name must be provided.");
    }

    @Override
    public Message apply(FlowContext flowContext, Message message) {

        String evaluatedObjectName = scriptService.evaluate(objectName, flowContext, message)
                .orElseThrow(() -> new RecordUpdateException(OBJECT_NAME_EMPTY.format(objectName.value())));

        HttpDescribe request = new HttpDescribe(configuration.getInstanceName(), evaluatedObjectName);

        String result = HttpAuthAwareRequestExecutor.execute(request, configuration, this, exceptionSupplier);

        return MessageBuilder.get(RecordCreate.class)
                .withString(result, MimeType.APPLICATION_JSON)
                .build();
    }

    @Override
    public void dispose() {
        HttpClientProvider.release(configuration, this);
    }

    private final Function<String, ObjectDescribeException> exceptionSupplier = ObjectDescribeException::new;

    public void setConfiguration(SalesforceConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setObjectName(DynamicString objectName) {
        this.objectName = objectName;
    }
}
