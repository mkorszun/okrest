package com.okrest.json;

import com.okrest.BaseTest;
import com.okrest.fixtures.TestObject;
import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

public class JSONClientTest extends BaseTest {

    @Test
    public void testSendWithBody() throws IOException, HTTPException {

        driver.addExpectation(
                onRequestTo("/resource").withMethod(Method.PUT).withBody("body", "application/json"),
                giveResponse(new TestObject().build(123), "application/json").withStatus(200)
        );

        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "resource", "body".getBytes()).getId());
    }

    @Test
    public void testSendNoBody() throws IOException, HTTPException {
        setupDriverForEmptyRequest(Method.GET, "/resource", 200, new TestObject().build(123), "application/json");
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.GET, "resource").getId());
    }

    @Test
    public void testSendWithBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyResponse(Method.PUT, "/resource", "[1, 2]", "application/json");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "/resource", "[1, 2]".getBytes());
    }

    @Test
    public void testSendNoBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyRequestResponse(Method.DELETE, "/resource");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.DELETE, "/resource");
    }
}