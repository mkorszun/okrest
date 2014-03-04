package com.okrest.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class Error implements Serializable {

    @JsonProperty("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
