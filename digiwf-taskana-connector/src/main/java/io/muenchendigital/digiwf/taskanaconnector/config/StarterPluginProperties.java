package io.muenchendigital.digiwf.taskanaconnector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "digiwf")
public class StarterPluginProperties {

    private Boolean exceptionOrLogProcessVariableIssues;
    private String outgoingTopic;
    private String incomingTopic;
    private String outtopic;

    public Boolean getExceptionOrLogProcessVariableIssues() {
        return exceptionOrLogProcessVariableIssues;
    }

    public void setExceptionOrLogProcessVariableIssues(Boolean exceptionOrLogProcessVariableIssues) {
        this.exceptionOrLogProcessVariableIssues = exceptionOrLogProcessVariableIssues;
    }

    public String getOutgoingTopic() {
        return outgoingTopic;
    }

    public void setOutgoingTopic(String outgoingTopic) {
        this.outgoingTopic = outgoingTopic;
    }

    public String getIncomingTopic() {
        return incomingTopic;
    }

    public void setIncomingTopic(String incomingTopic) {
        this.incomingTopic = incomingTopic;
    }

    public String getOuttopic() {
        return outtopic;
    }

    public void setOuttopic(String outtopic) {
        this.outtopic = outtopic;
    }

    
}
