package com.okrest.request;

import com.okrest.http.HTTPClient;
import com.okrest.http.HTTPException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;

public class Action<T> implements Request<T> {

    private HTTPClient client;
    private ObjectMapper mapper;

    public Action(String url) {
        this.client = new HTTPClient(url);
        this.mapper = new ObjectMapper();
    }

    @Override
    public T read(long id) throws IOException, HTTPException {
        byte[] body = client.request("GET", String.valueOf(id));
        return mapper.readValue(body, new TypeReference<T>() {
        });
    }

    @Override
    public void create(T object) throws IOException, HTTPException {
        byte[] input = mapper.writeValueAsBytes(object);
        client.request("POST", "", input);
    }

    @Override
    public void delete(long id) throws IOException, HTTPException {
        client.request("DELETE", String.valueOf(id));
    }

    @Override
    public void update(long id, T object) throws IOException, HTTPException {
        byte[] input = mapper.writeValueAsBytes(object);
        client.request("POST", String.valueOf(id), input);
    }

    @Override
    public ArrayList<T> list() throws IOException, HTTPException {
        byte[] body = client.request("GET", "");
        return mapper.<ArrayList>readValue(body, new TypeReference<ArrayList<T>>() {
        });
    }
}
