package com.okrest.http;

import com.okrest.utils.Utils;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class HTTPClient {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private static final int MAX_IDLE_CONNECTIONS = 20;
    private static final long KEEP_ALIVE_DURATION_MS = 300000L;

    private String backendURL;
    private OkHttpClient client;

    public HTTPClient(String backendURL) {
        this();
        this.backendURL = backendURL;
    }

    public HTTPClient() {
        this.client = new OkHttpClient();
        this.client.setConnectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION_MS));
    }

    public byte[] request(HTTPMethod method, String resource) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource));
    }

    public byte[] request(HTTPMethod method, String resource, Map<String, Object> params) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource, params));
    }

    public byte[] request(HTTPMethod method, String resource, byte[] body) throws IOException, HTTPException {
        return request(method, Utils.buildURI(backendURL, resource), body);
    }

    private byte[] request(HTTPMethod method, URI resource, byte[] body) throws IOException, HTTPException {
        OutputStream out = null;
        HttpURLConnection connection = null;

        try {
            URL url = resource.toURL();
            connection = client.open(url);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method.name());
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            out = connection.getOutputStream();
            out.write(body);
            HTTPStream stream = new HTTPStream(connection);
            return stream.read();
        } catch (MalformedURLException e) {
            throw new HTTPException("Malformed url: " + resource);
        } finally {

            if (out != null) {
                out.close();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private byte[] request(HTTPMethod method, URI resource) throws IOException, HTTPException {
        HttpURLConnection connection = null;

        try {
            connection = client.open(resource.toURL());
            connection.setRequestMethod(method.name());
            HTTPStream stream = new HTTPStream(connection);
            return stream.read();
        } catch (MalformedURLException e) {
            throw new HTTPException("Malformed url: " + resource);
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }
    }
}