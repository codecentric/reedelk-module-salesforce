package com.reedelk.salesforce.component;

import com.reedelk.runtime.api.annotation.*;
import com.reedelk.runtime.api.component.Implementor;
import com.reedelk.runtime.api.component.ProcessorSync;
import org.osgi.service.component.annotations.Component;

import static com.reedelk.runtime.api.commons.ComponentPrecondition.Configuration.requireNotBlank;
import static org.osgi.service.component.annotations.ServiceScope.PROTOTYPE;

@Shared
@Component(service = SalesforceConfiguration.class, scope = PROTOTYPE)
public class SalesforceConfiguration implements Implementor {

    @Property("id")
    @Hidden
    private String id;

    @Property("Consumer Key")
    @Example("1X3MVG9SOw8KERNN0_LlwLCV")
    @Hint("1X3MVG9SOw8KERNN0_LlwLCVOlHs3CTh2LqgmwA_MM")
    @Description("The connected app Consumer Key.")
    @Mandatory
    private String clientId;

    @Property("Consumer Secret")
    @Hint("1XA0B649DA83045CA")
    @Example("1XA0B649DA83045CA")
    @Description("The connected app Client Secret.")
    @Mandatory
    private String clientSecret;

    @Property("Username")
    @Hint("user@domain.com")
    @Example("user@domain.com")
    @Description("The account username to be used for login authentication.")
    @Mandatory
    private String username;

    @Property("Password")
    @Password
    @Hint("myPassword")
    @Example("myPassword")
    @Description("The account password to be used for login authentication.")
    @Mandatory
    private String password;

    @Property("Instance Name")
    @Combo(editable = true, prototype = "XXXXXX", comboValues = {
            "AP0",
            "AP3",
            "AP4",
            "AP5",
            "AP6",
            "AP7",
            "AP8",
            "AP9",
            "AP10",
            "AP11",
            "AP12",
            "AP13",
            "AP14",
            "AP15",
            "AP16",
            "AP17",
            "AP18",
            "AP19",
            "AP20",
            "AP21",
            "AP22",
            "AP28",
            "EU7",
            "EU8",
            "EU10",
            "EU12",
            "EU13",
            "EU14",
            "EU15",
            "EU16",
            "EU17",
            "EU18",
            "EU19",
            "EU25",
            "EU26",
            "EU27",
            "EU28",
            "EU29",
            "EU30",
            "EU31",
            "EU32",
            "EU33",
            "EU34",
            "EU35",
            "EU36",
            "EU37",
            "EU38",
            "EU39",
            "EU40",
            "UM1",
            "UM2",
            "UM3",
            "UM4",
            "UM5",
            "UM6",
            "UM7",
            "NA21",
            "NA32",
            "NA37",
            "NA39",
            "NA44",
            "NA45",
            "NA46",
            "NA47",
            "NA49",
            "NA52",
            "NA53",
            "NA54",
            "NA57",
            "NA58",
            "NA59",
            "NA60",
            "NA61",
            "NA62",
            "NA64",
            "NA65",
            "NA66",
            "NA67",
            "NA68",
            "NA69",
            "NA70",
            "NA71",
            "NA72",
            "NA73",
            "NA74",
            "NA75",
            "NA76",
            "NA77",
            "NA79",
            "NA80",
            "NA81",
            "NA82",
            "NA83",
            "NA84",
            "NA85",
            "NA86",
            "NA87",
            "NA88",
            "NA89",
            "NA90",
            "NA91",
            "NA92",
            "NA93",
            "NA94",
            "NA95",
            "NA96",
            "NA97",
            "NA98",
            "NA99",
            "NA100",
            "NA101",
            "NA102",
            "NA103",
            "NA104",
            "NA105",
            "NA107",
            "NA109",
            "NA110",
            "NA111",
            "NA112",
            "NA113",
            "NA114",
            "NA115",
            "NA116",
            "NA117",
            "NA118",
            "NA119",
            "NA120",
            "NA121",
            "NA122",
            "NA123",
            "NA124",
            "NA125",
            "NA126",
            "NA127",
            "NA129",
            "NA130",
            "NA131",
            "NA132",
            "NA134",
            "NA135",
            "NA136",
            "NA141",
            "NA142",
            "NA146",
            "NA154",
            "NA155",
            "NA171",
            "NA172",
            "NA173",
            "NA174",
            "NA196",
            "CS1",
            "CS2",
            "CS3",
            "CS4",
            "CS5",
            "CS6",
            "CS7",
            "CS8",
            "CS9",
            "CS10",
            "CS11",
            "CS12",
            "CS13",
            "CS14",
            "CS15",
            "CS16",
            "CS17",
            "CS18",
            "CS19",
            "CS20",
            "CS21",
            "CS22",
            "CS23",
            "CS24",
            "CS25",
            "CS26",
            "CS27",
            "CS28",
            "CS29",
            "CS30",
            "CS31",
            "CS32",
            "CS33",
            "CS34",
            "CS35",
            "CS36",
            "CS37",
            "CS40",
            "CS41",
            "CS42",
            "CS43",
            "CS44",
            "CS45",
            "CS47",
            "CS50",
            "CS51",
            "CS52",
            "CS53",
            "CS54",
            "CS57",
            "CS58",
            "CS59",
            "CS60",
            "CS61",
            "CS62",
            "CS63",
            "CS64",
            "CS65",
            "CS66",
            "CS67",
            "CS68",
            "CS69",
            "CS70",
            "CS71",
            "CS72",
            "CS73",
            "CS74",
            "CS75",
            "CS76",
            "CS77",
            "CS78",
            "CS79",
            "CS80",
            "CS81",
            "CS82",
            "CS83",
            "CS84",
            "CS85",
            "CS86",
            "CS87",
            "CS88",
            "CS89",
            "CS90",
            "CS91",
            "CS92",
            "CS93",
            "CS94",
            "CS95",
            "CS96",
            "CS97",
            "CS98",
            "CS99",
            "CS100",
            "CS101",
            "CS102",
            "CS105",
            "CS106",
            "CS107",
            "CS108",
            "CS109",
            "CS110",
            "CS111",
            "CS112",
            "CS113",
            "CS114",
            "CS115",
            "CS116",
            "CS117",
            "CS123",
            "CS124",
            "CS125",
            "CS126",
            "CS127",
            "CS128",
            "CS129",
            "CS132",
            "CS133",
            "CS137",
            "CS138",
            "CS142",
            "CS148",
            "CS151",
            "CS152"})
    @Hint("NA57")
    @Example("UM1")
    @Mandatory
    @Description("The Salesforce instance name to be used to retrieve data.")
    private String instanceName;
    
    @Property("Authorization URL")
    @Example("https://login.salesforce.com/services/oauth2/token")
    @Hint("https://login.salesforce.com/services/oauth2/token")
    @DefaultValue("https://login.salesforce.com/services/oauth2/token")
    @Description("The authorization URL to be used to get the Authentication token.")
    private String authorizationURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void validate(Class<? extends ProcessorSync> component) {
        requireNotBlank(component, clientId, "Salesforce Client ID must be provided");
        requireNotBlank(component, clientSecret, "Salesforce Client Secret must be provided.");
        requireNotBlank(component, username, "Salesforce username must be provided.");
        requireNotBlank(component, password, "Salesforce password must be provided.");
        requireNotBlank(component, instanceName, "Salesforce instance must be provided.");
    }
}
