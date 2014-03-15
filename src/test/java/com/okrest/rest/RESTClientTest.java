package com.okrest.rest;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.okrest.BaseTest;
import com.okrest.fixtures.TestObject;
import com.okrest.http.HTTPException;
import com.okrest.utils.QueryParamsBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.github.restdriver.clientdriver.ClientDriverRequest.Method.*;

public class RESTClientTest extends BaseTest {

    @Test
    public void testCreate() throws IOException, HTTPException {
        setupDriverForEmptyResponse(POST, "/resource", new TestObject().build(1), "application/json");
        new RESTClient(driver.getBaseUrl() + "/resource").create(new TestObject());
    }

    @Test
    public void testCreateWithParams() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyResponse(POST, "/resource", new TestObject().build(1), "application/json", params);
        new RESTClient(driver.getBaseUrl() + "/resource").create(new TestObject(), params);
    }

    @Test
    public void testUpdate() throws IOException, HTTPException {
        setupDriverForEmptyResponse(PUT, "/resource/1", new TestObject().build(1), "application/json");
        new RESTClient(driver.getBaseUrl() + "/resource").update(1, new TestObject());
    }

    @Test
    public void testUpdateWithParams() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyResponse(PUT, "/resource/1", new TestObject().build(1), "application/json", params);
        new RESTClient(driver.getBaseUrl() + "/resource").update(1, new TestObject(), params);
    }

    @Test
    public void testRead() throws IOException, HTTPException {
        setupDriverForEmptyRequest(GET, "/resource/11", 200, new TestObject().build(11), "application/json");
        RESTClient<TestObject> action = new RESTClient(driver.getBaseUrl() + "/resource", TestObject.class);
        Assert.assertEquals(11, action.read(11).getId());
    }

    @Test
    public void testReadWithParams() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyRequest(GET, "/resource/11", 200, new TestObject().build(11), "application/json", params);
        RESTClient<TestObject> action = new RESTClient(driver.getBaseUrl() + "/resource", TestObject.class);
        Assert.assertEquals(11, action.read(11, params).getId());
    }

    @Test
    public void testDelete() throws IOException, HTTPException {
        setupDriverForEmptyRequestResponse(ClientDriverRequest.Method.DELETE, "/resource/666");
        new RESTClient(driver.getBaseUrl() + "/resource").delete(666);
    }

    @Test
    public void testDeleteWithParams() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyRequestResponse(ClientDriverRequest.Method.DELETE, "/resource/666", params);
        new RESTClient(driver.getBaseUrl() + "/resource").delete(666, params);
    }

    @Test
    public void testList() throws IOException, HTTPException {
        setupDriverForEmptyRequest(GET, "/resource", 200, new TestObject().build(11, 22, 33), "application/json");
        RESTClient<TestObject> action = new RESTClient(driver.getBaseUrl() + "/resource", TestObject.class);

        ArrayList<TestObject> results = action.list();

        Assert.assertEquals(3, results.size());
        Assert.assertEquals(11, results.get(0).getId());
        Assert.assertEquals(22, results.get(1).getId());
        Assert.assertEquals(33, results.get(2).getId());
    }

    @Test
    public void testListWithParams() throws IOException, HTTPException {
        Map<String, Object> params = new QueryParamsBuilder().set("a", true).set("b", "c").build();
        setupDriverForEmptyRequest(GET, "/resource", 200, new TestObject().build(11, 22, 33), "application/json", params);
        RESTClient<TestObject> action = new RESTClient(driver.getBaseUrl() + "/resource", TestObject.class);

        ArrayList<TestObject> results = action.list(params);

        Assert.assertEquals(3, results.size());
        Assert.assertEquals(11, results.get(0).getId());
        Assert.assertEquals(22, results.get(1).getId());
        Assert.assertEquals(33, results.get(2).getId());
    }
}
