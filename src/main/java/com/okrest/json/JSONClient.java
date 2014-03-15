package com.okrest.json;

import com.okrest.http.HTTPClient;
import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JSONClient<T> {

    protected static final String ROOT_RESOURCE = "";
    protected static final Map<String, Object> EMPTY_PARAMS = Collections.EMPTY_MAP;

    protected Class<T> target;
    protected HTTPClient client;
    protected ObjectMapper mapper;

    public JSONClient(String url) {
        this(url, (Class<T>) Void.class);
    }

    public JSONClient(String url, Class<T> target) {
        this.target = target;
        this.client = new HTTPClient(url);
        this.mapper = new ObjectMapper();
    }

    public void sendNoResponse(HTTPMethod method) throws IOException, HTTPException {
        sendNoResponse(method, ROOT_RESOURCE);
    }

    public void sendNoResponse(HTTPMethod method, String resource) throws IOException, HTTPException {
        sendNoResponse(method, resource, EMPTY_PARAMS);
    }

    public void sendNoResponse(HTTPMethod method, byte[] input) throws IOException, HTTPException {
        sendNoResponse(method, ROOT_RESOURCE, input);
    }

    public void sendNoResponse(HTTPMethod method, String resource, byte[] input) throws IOException, HTTPException {
        sendNoResponse(method, resource, input, EMPTY_PARAMS);
    }

    public void sendNoResponse(HTTPMethod method, Map<String, Object> params) throws IOException, HTTPException {
        sendNoResponse(method, ROOT_RESOURCE, params);
    }

    public void sendNoResponse(HTTPMethod method, String resource, Map<String, Object> params) throws IOException, HTTPException {
        client.request(method, resource, params);
    }

    public void sendNoResponse(HTTPMethod method, byte[] input, Map<String, Object> params) throws IOException, HTTPException {
        sendNoResponse(method, ROOT_RESOURCE, input, params);
    }

    public void sendNoResponse(HTTPMethod method, String resource, byte[] input, Map<String, Object> params) throws IOException, HTTPException {
        client.request(method, resource, input, params);
    }

    public T send(HTTPMethod method) throws IOException, HTTPException {
        return send(method, ROOT_RESOURCE);
    }

    public T send(HTTPMethod method, String resource) throws IOException, HTTPException {
        return send(method, resource, EMPTY_PARAMS);
    }

    public T send(HTTPMethod method, byte[] input) throws IOException, HTTPException {
        return send(method, ROOT_RESOURCE, input);
    }

    public T send(HTTPMethod method, String resource, byte[] input) throws IOException, HTTPException {
        return send(method, resource, input, EMPTY_PARAMS);
    }

    public T send(HTTPMethod method, Map<String, Object> params) throws IOException, HTTPException {
        return send(method, ROOT_RESOURCE, params);
    }

    public T send(HTTPMethod method, String resource, Map<String, Object> params) throws IOException, HTTPException {
        return mapper.readValue(client.request(method, resource, params), target);
    }

    public T send(HTTPMethod method, byte[] input, Map<String, Object> params) throws IOException, HTTPException {
        return send(method, ROOT_RESOURCE, input, params);
    }

    public T send(HTTPMethod method, String resource, byte[] input, Map<String, Object> params) throws IOException, HTTPException {
        return mapper.readValue(client.request(method, resource, input, params), target);
    }
}
