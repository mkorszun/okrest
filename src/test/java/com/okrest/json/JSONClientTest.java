package com.okrest.json;

import com.okrest.BaseTest;
import com.okrest.fixtures.TestObject;
import com.okrest.http.HTTPException;
import com.okrest.http.HTTPMethod;
import com.okrest.utils.QueryParamsBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static com.github.restdriver.clientdriver.ClientDriverRequest.Method;
import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

public class JSONClientTest extends BaseTest {

    public static final String APPLICATION_JSON = "application/json";

    @Test
    public void testSendWithBody() throws IOException, HTTPException {

        driver.addExpectation(
                onRequestTo("/resource").withMethod(Method.PUT).withBody("body", APPLICATION_JSON),
                giveResponse(new TestObject().build(123), APPLICATION_JSON).withStatus(200)
        );

        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "resource", "body".getBytes()).getId());
    }

    @Test
    public void testSendWithParamsAndBody() throws IOException, HTTPException {

        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();

        driver.addExpectation(
                onRequestTo("/resource").withMethod(Method.PUT).withBody("body", APPLICATION_JSON).withParams(params),
                giveResponse(new TestObject().build(123), APPLICATION_JSON).withStatus(200)
        );

        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "resource", "body".getBytes(), params).getId());
    }

    @Test
    public void testSendNoBody() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriver(Method.GET, "/resource", new TestObject().build(123), 200, APPLICATION_JSON, params);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.GET, "resource", params).getId());
    }

    @Test
    public void testSendWithParamsNoBody() throws IOException, HTTPException {
        setupDriverForEmptyRequest(Method.GET, "/resource", 200, new TestObject().build(123), APPLICATION_JSON);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.GET, "resource").getId());
    }

    @Test
    public void testSendWithBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyResponse(Method.PUT, "/resource", "[1, 2]", APPLICATION_JSON);
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "/resource", "[1, 2]".getBytes());
    }

    @Test
    public void testSendNoBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyRequestResponse(Method.DELETE, "/resource");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.DELETE, "/resource");
    }
}