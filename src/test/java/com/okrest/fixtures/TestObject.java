package com.okrest.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;

public class TestObject {

    @JsonProperty("id")
    private long id = 1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String build(long id) {
        JSONObject object = new JSONObject();
        object.put("id", id);
        return object.toJSONString();
    }

    public String build(long... ids) {
        JSONArray array = new JSONArray();
        for (long id : ids) {
            JSONObject object = new JSONObject();
            object.put("id", id);
            array.add(object);
        }
        return array.toJSONString();
    }
}
