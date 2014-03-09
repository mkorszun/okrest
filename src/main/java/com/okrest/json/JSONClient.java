package com.okrest.json;

import com.okrest.http.HTTPClient;
import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JSONClient<T> {

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

    public void sendNoResponse(HTTPMethod method, String resource) throws IOException, HTTPException {
        client.request(method, resource);
    }

    public void sendNoResponse(HTTPMethod method, String resource, byte[] input) throws IOException, HTTPException {
        client.request(method, resource, input);
    }

    public T send(HTTPMethod method, String resource) throws IOException, HTTPException {
        return mapper.readValue(client.request(method, resource), target);
    }

    public T send(HTTPMethod method, String resource, byte[] input) throws IOException, HTTPException {
        return mapper.readValue(client.request(method, resource, input), target);
    }
}
