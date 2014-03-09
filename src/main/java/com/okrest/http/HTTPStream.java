package com.okrest.http;

import com.okrest.model.Error;
import com.okrest.utils.Utils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;

import static com.okrest.utils.Utils.readBytes;

public class HTTPStream {

    public static final String APPLICATION_JSON = "application/json";

    private HttpURLConnection connection;
    protected ObjectMapper mapper;

    public HTTPStream(HttpURLConnection connection) {
        this.connection = connection;
        this.mapper = new ObjectMapper();
    }

    public byte[] read() throws IOException, HTTPException {
        if (connection.getResponseCode() == 200 || connection.getResponseCode() == 201 || connection.getResponseCode() == 204) {
            return readBytes(connection.getInputStream());
        } else {
            return readError();
        }
    }

    private byte[] readError() throws IOException, HTTPException {
        if (connection.getContentType() != null && Utils.in(connection.getContentType(), APPLICATION_JSON)) {
            byte[] errorBody = readBytes(connection.getErrorStream());
            Error error = mapper.readValue(errorBody, Error.class);
            throw new HTTPException(error.getError());
        } else {
            throw new HTTPException(connection.getResponseMessage());
        }
    }
}
