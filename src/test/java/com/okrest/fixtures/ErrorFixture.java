package com.okrest.fixtures;

import org.json.simple.JSONObject;

public class ErrorFixture {

    private String reason;

    public ErrorFixture withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String build() {
        JSONObject error = new JSONObject();
        error.put("error", reason);
        return error.toJSONString();
    }
}
