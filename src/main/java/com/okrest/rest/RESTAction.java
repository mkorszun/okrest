package com.okrest.rest;

import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import com.okrest.json.JSONClient;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;

public class RESTAction<T> extends JSONClient<T> {

    private static final String ROOT_RESOURCE = "";

    public RESTAction(String url) {
        super(url);
    }

    public RESTAction(String url, Class<T> target) {
        super(url, target);
    }

    public T read(long id) throws IOException, HTTPException {
        return send(HTTPMethod.GET, String.valueOf(id));
    }

    public void create(T object) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.POST, ROOT_RESOURCE, mapper.writeValueAsBytes(object));
    }

    public void delete(long id) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.DELETE, String.valueOf(id));
    }

    public void update(long id, T object) throws IOException, HTTPException {
        sendNoResponse(HTTPMethod.PUT, String.valueOf(id), mapper.writeValueAsBytes(object));
    }

    public ArrayList<T> list() throws IOException, HTTPException {
        return mapper.readValue(client.request(HTTPMethod.GET, ROOT_RESOURCE), arrayList(target));
    }

    private JavaType arrayList(Class clazz) {
        return mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
    }
}
