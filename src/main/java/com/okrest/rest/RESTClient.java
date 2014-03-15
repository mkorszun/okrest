package com.okrest.rest;

import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import com.okrest.json.JSONClient;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class RESTClient<T> extends JSONClient<T> {

    public RESTClient(String url) {
        super(url);
    }

    public RESTClient(String url, Class<T> target) {
        super(url, target);
    }

    public T read(long id) throws IOException, HTTPException {
        return read(id, EMPTY_PARAMS);
    }

    public T read(long id, Map<String, Object> params) throws IOException, HTTPException {
        return send(HTTPMethod.GET, String.valueOf(id), params);
    }

    public void create(T object) throws IOException, HTTPException {
        create(object, EMPTY_PARAMS);
    }

    public void create(T object, Map<String, Object> params) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.POST, mapper.writeValueAsBytes(object), params);
    }

    public void delete(long id) throws IOException, HTTPException {
        delete(id, EMPTY_PARAMS);
    }

    public void delete(long id, Map<String, Object> params) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.DELETE, String.valueOf(id), params);
    }

    public void update(long id, T object) throws IOException, HTTPException {
        update(id, object, EMPTY_PARAMS);
    }

    public void update(long id, T object, Map<String, Object> params) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.PUT, String.valueOf(id), mapper.writeValueAsBytes(object), params);
    }

    public ArrayList<T> list() throws IOException, HTTPException {
        return list(EMPTY_PARAMS);
    }

    public ArrayList<T> list(Map<String, Object> params) throws IOException, HTTPException {
        return mapper.readValue(client.request(HTTPMethod.GET, ROOT_RESOURCE, params), arrayList(target));
    }

    private JavaType arrayList(Class clazz) {
        return mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
    }
}
