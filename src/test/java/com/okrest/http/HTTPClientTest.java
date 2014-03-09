package com.okrest.http;

import com.okrest.BaseTest;
import com.okrest.fixtures.ErrorFixture;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class HTTPClientTest extends BaseTest {

    @Test
    public void testOK() throws IOException, HTTPException {
        setupDriver("/path", "body", 200, "text/plain");
        HTTPClient httpClient = new HTTPClient(driver.getBaseUrl());
        String resp = new String(httpClient.request(HTTPMethod.GET, "/path"));
        Assert.assertEquals("body", resp);
    }

    @Test
    public void testJsonError() throws IOException, HTTPException {
        exception.expect(HTTPException.class);
        exception.expectMessage("unauthorized");
        String body = new ErrorFixture().withReason("unauthorized").build();
        setupDriver("/path", body, 401, "application/json");
        new HTTPClient(driver.getBaseUrl()).request(HTTPMethod.GET, "/path");
    }

    @Test
    public void testPlainError() throws IOException, HTTPException {
        exception.expect(HTTPException.class);
        exception.expectMessage("Unauthorized");
        setupDriver("/path", "", 401, "text/plain");
        new HTTPClient(driver.getBaseUrl()).request(HTTPMethod.GET, "path");
    }
}
