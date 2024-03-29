package com.vtheatre.data.model.drm.policy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created By NY on 2020-01-14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalKeyPolicyNcg {
    @JsonProperty("cek")
    private String cek;

    public ExternalKeyPolicyNcg(String cek) {
        this.cek = cek;
    }

    public String getCek() {
        return cek;
    }
}