package com.okrest.rest;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.okrest.BaseTest;
import com.okrest.fixtures.TestObject;
import com.okrest.http.HTTPException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class RESTActionTest extends BaseTest {

    @Test
    public void testCreate() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.POST, "/resource", "", 201, "application/json");
        RESTAction action = new RESTAction(driver.getBaseUrl() + "/resource");
        action.create(new TestObject());
    }

    @Test
    public void testUpdate() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.PUT, "/resource/1", "", 200, "application/json");
        RESTAction action = new RESTAction(driver.getBaseUrl() + "/resource");
        action.update(1, new TestObject());
    }

    @Test
    public void testRead() throws IOException, HTTPException {
        setupDriver("/resource/11", new TestObject().build(11), 200, "application/json");
        RESTAction<TestObject> action = new RESTAction(driver.getBaseUrl() + "/resource", TestObject.class);
        Assert.assertEquals(11, action.read(11).getId());
    }

    @Test
    public void testDelete() throws IOException, HTTPException {
        setupDriver(ClientDriverRequest.Method.DELETE, "/resource/666", 200, "application/json");
        RESTAction<TestObject> action = new RESTAction(driver.getBaseUrl() + "/resource");
        action.delete(666);
    }

    @Test
    public void testList() throws IOException, HTTPException {
        setupDriver("/resource", new TestObject().build(11, 22, 33), 200, "application/json");
        RESTAction<TestObject> action = new RESTAction(driver.getBaseUrl() + "/resource", TestObject.class);

        ArrayList<TestObject> results = action.list();

        Assert.assertEquals(3, results.size());
        Assert.assertEquals(11, results.get(0).getId());
        Assert.assertEquals(22, results.get(1).getId());
        Assert.assertEquals(33, results.get(2).getId());
    }
}
