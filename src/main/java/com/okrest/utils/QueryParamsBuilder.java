package com.okrest.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.net.URLEncoder.encode;

public class QueryParamsBuilder {

    Map<String, Object> params = new HashMap<String, Object>();

    public QueryParamsBuilder set(String key, String val) throws UnsupportedEncodingException {
        params.put(key, encode(val, "UTF-8"));
        return this;
    }

    public QueryParamsBuilder set(String key, long val) throws UnsupportedEncodingException {
        set(key, Long.toString(val));
        return this;
    }

    public QueryParamsBuilder set(String key, boolean val) throws UnsupportedEncodingException {
        set(key, String.valueOf(val));
        return this;
    }

    public Map<String, Object> build() {
        return params;
    }
}
