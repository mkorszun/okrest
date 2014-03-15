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

public class JSONClientTest extends BaseTest {

    public static final String APPLICATION_JSON = "application/json";

    @Test
    public void testSendWithBody() throws IOException, HTTPException {
        setupDriver(Method.PUT, "/", 200, "body", APPLICATION_JSON, new TestObject().build(123), APPLICATION_JSON);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "body".getBytes()).getId());
    }

    @Test
    public void testSendWithParamsAndBody() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriver(Method.PUT, "/", 200, "body", APPLICATION_JSON, new TestObject().build(123), APPLICATION_JSON, params);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.PUT, "body".getBytes(), params).getId());
    }

    @Test
    public void testSendNoBody() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyRequest(Method.GET, "/", 200, new TestObject().build(123), APPLICATION_JSON, params);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.GET, params).getId());
    }

    @Test
    public void testSendWithParamsNoBody() throws IOException, HTTPException {
        setupDriverForEmptyRequest(Method.GET, "/", 200, new TestObject().build(123), APPLICATION_JSON);
        JSONClient<TestObject> action = new JSONClient(driver.getBaseUrl(), TestObject.class);
        Assert.assertEquals(123, action.send(HTTPMethod.GET).getId());
    }

    @Test
    public void testSendWithBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyResponse(Method.PUT, "/", "[1, 2]", APPLICATION_JSON);
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "[1, 2]".getBytes());
    }

    @Test
    public void testSendWithParamsAndBodyNoResponse() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyResponse(Method.PUT, "/", "[1, 2]", APPLICATION_JSON, params);
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.PUT, "[1, 2]".getBytes(), params);
    }


    @Test
    public void testSendNoBodyNoResponse() throws IOException, HTTPException {
        setupDriverForEmptyRequestResponse(Method.DELETE, "/");
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.DELETE);
    }

    @Test
    public void testSendWithParamsNoBodyNoResponse() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyRequestResponse(Method.DELETE, "/", params);
        new JSONClient(driver.getBaseUrl()).sendNoResponse(HTTPMethod.DELETE, params);
    }
}