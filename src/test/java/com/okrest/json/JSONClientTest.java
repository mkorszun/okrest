package com.okrest.json;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.okrest.BaseTest;
import com.okrest.fixtures.TestObject;
import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JSONClientTest extends BaseTest {

    @Test
    public void testSendWithBody() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.PUT, "/resource", new TestObject().build(123), 200, "application/json");
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "resource", "body".getBytes()).getId());
    }

    @Test
    public void testSendNoBody() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.PUT, "/resource", new TestObject().build(123), 200, "application/json");
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "resource").getId());
    }

    @Test
    public void testSendWithBodyNoResponse() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.PUT, "/resource", 204, "application/json");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "resource", "body".getBytes());
    }

    @Test
    public void testSendNohBodyNoResponse() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.PUT, "/resource", 204, "application/json");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "resource");
    }
}
