package utils;

import static testrunner.BaseTest.properties;

public class Context {

    private String environment; // TODO Make this a Java Object
    private String contextUrl;

    public Context() {
        this.setEnvironment(properties.getString("environment"));
        this.setContextUrl(properties.getString("url"));
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    public String getContextUrl() {
        return contextUrl;
    }
}
