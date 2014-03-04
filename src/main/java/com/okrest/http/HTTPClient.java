package com.okrest.http;

import com.okrest.utils.Utils;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

public class HTTPClient {

    private static final String BACKEND_URL = "https://staging-plantstore.cloudcontrolled.com";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private static final int MAX_IDLE_CONNECTIONS = 20;
    private static final long KEEP_ALIVE_DURATION_MS = 300000L;

    private OkHttpClient client;
    private String backendURL = BACKEND_URL;

    public HTTPClient(String backendURL) {
        this();
        this.backendURL = backendURL;
    }

    public HTTPClient() {
        this.client = new OkHttpClient();
        this.client.setConnectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION_MS));
    }

    public byte[] request(String method, String resource) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource));
    }

    public byte[] request(String method, String resource, Map<String, Object> params) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource, params));
    }

    public byte[] request(String method, String resource, byte[] body) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource), body);
    }

    private byte[] request(String method, URI resource, byte[] body) throws IOException, HTTPException {
        OutputStream out = null;
        HttpURLConnection connection = null;

        try {
            connection = client.open(resource.toURL());
            connection.setRequestMethod(method);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            out = connection.getOutputStream();
            out.write(body);
            HTTPStream stream = new HTTPStream(connection);
            return stream.read();
        } catch (MalformedURLException e) {
            throw new HTTPException(e);
        } finally {

            if (out != null) {
                out.close();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private byte[] request(String method, URI resource) throws IOException, HTTPException {
        HttpURLConnection connection = null;

        try {
            connection = client.open(resource.toURL());
            connection.setRequestMethod(method);
            HTTPStream stream = new HTTPStream(connection);
            return stream.read();
        } catch (MalformedURLException e) {
            throw new HTTPException(e);
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }
    }
}