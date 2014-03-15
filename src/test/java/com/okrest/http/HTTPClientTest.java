package com.okrest.http;

import com.okrest.BaseTest;
import com.okrest.fixtures.ErrorFixture;
import com.okrest.utils.QueryParamsBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.GET;

public class HTTPClientTest extends BaseTest {

    @Test
    public void testOK() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").set("d", 1).build();
        setupDriver(GET, "/path", 200, "body", "text/plain", params);
        byte[] resp = new HTTPClient(driver.getBaseUrl()).request(HTTPMethod.GET, "/path", params);
        Assert.assertEquals("body", new String(resp));
    }

    @Test
    public void testJsonError() throws IOException, HTTPException {
        exception.expect(HTTPException.class);
        exception.expectMessage("unauthorized");
        String body = new ErrorFixture().withReason("unauthorized").build();
        setupDriverForEmptyRequest(GET, "/path", 401, body, "application/json");
        new HTTPClient(driver.getBaseUrl()).request(HTTPMethod.GET, "/path");
    }

    @Test
    public void testPlainError() throws IOException, HTTPException {
        exception.expect(HTTPException.class);
        exception.expectMessage("Unauthorized");
        setupDriverForEmptyRequest(GET, "/path", 401, "", "text/plain");
        new HTTPClient(driver.getBaseUrl()).request(HTTPMethod.GET, "path");
    }

    @Test
    public void testMalformedURL() throws IOException, HTTPException {
        exception.expect(HTTPException.class);
        exception.expectMessage("Malformed url: dd://fakeurl/path");
        new HTTPClient("dd://fakeurl").request(HTTPMethod.GET, "path");
    }
}
