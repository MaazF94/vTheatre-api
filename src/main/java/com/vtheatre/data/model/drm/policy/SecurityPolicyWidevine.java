package com.vtheatre.data.model.drm.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vtheatre.config.drm.security.widevine.HdcpSrmRule;
import com.vtheatre.config.drm.security.widevine.RequiredCgmsFlags;
import com.vtheatre.config.drm.security.widevine.RequiredHdcpVersion;
import com.vtheatre.config.drm.security.widevine.WidevineSecurityLevel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize()
@JsonPropertyOrder({ "security_level", "required_hdcp_version", "required_cgms_flags", "disable_analog_output",
        "hdcp_srm_rule" })
public class SecurityPolicyWidevine {

    @JsonProperty("security_level")
    private Integer securityLevel;
    @JsonProperty("required_hdcp_version")
    private String requiredHdcpVersion;
    @JsonProperty("required_cgms_flags")
    private String requiredCgmsFlags;
    @JsonProperty("disable_analog_output")
    private Boolean disableAnalogOutput;
    @JsonProperty("hdcp_srm_rule")
    private String hdcpSrmRule;

    public SecurityPolicyWidevine() {
    }

    public SecurityPolicyWidevine securityLevel(WidevineSecurityLevel securityLevel) {
        this.securityLevel = securityLevel.getValue();
        return this;
    }

    public SecurityPolicyWidevine requiredHdcpVersion(RequiredHdcpVersion requiredHdcpVersion) {
        this.requiredHdcpVersion = requiredHdcpVersion.getValue();
        return this;
    }

    public SecurityPolicyWidevine requiredCgmsFlags(RequiredCgmsFlags requiredCgmsFlags) {
        this.requiredCgmsFlags = requiredCgmsFlags.getValue();
        return this;
    }

    public SecurityPolicyWidevine disableAnalogOutput(boolean disableAnalogOutput) {
        this.disableAnalogOutput = disableAnalogOutput;
        return this;
    }

    public SecurityPolicyWidevine hdcpSrmRule(HdcpSrmRule hdcpSrmRule) {
        this.hdcpSrmRule = hdcpSrmRule.getValue();
        return this;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public String getRequiredHdcpVersion() {
        return requiredHdcpVersion;
    }

    public String getRequiredCgmsFlags() {
        return requiredCgmsFlags;
    }

    public Boolean getDisableAnalogOutput() {
        return disableAnalogOutput;
    }

    public String getHdcpSrmRule() {
        return hdcpSrmRule;
    }

}
