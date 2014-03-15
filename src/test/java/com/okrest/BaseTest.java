package com.okrest;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Map;

import static com.github.restdriver.clientdriver.RestClientDriver.*;

public class BaseTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public ClientDriverRule driver = new ClientDriverRule();

    public void setupDriver(ClientDriverRequest.Method method, String url, int code, String respBody, String respContentType, Map<String, Object> params) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method).withParams(params),
                giveResponse(respBody, respContentType).withStatus(code)
        );
    }

    public void setupDriver(ClientDriverRequest.Method method, String url, int code, String reqBody, String reqContentType, String respBody, String respContentType, Map<String, Object> params) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method).withBody(reqBody, reqContentType).withParams(params),
                giveResponse(respBody, respContentType).withStatus(code)
        );
    }

    public void setupDriver(ClientDriverRequest.Method method, String url, int code, String reqBody, String reqContentType, String respBody, String respContentType) {
        setupDriver(method, url, code, reqBody, reqContentType, respBody, respContentType, Collections.EMPTY_MAP);
    }

    public void setupDriverForEmptyRequest(ClientDriverRequest.Method method, String url, int code, String respBody, String respContentType) {
        setupDriverForEmptyRequest(method, url, code, respBody, respContentType, Collections.EMPTY_MAP);
    }

    public void setupDriverForEmptyRequest(ClientDriverRequest.Method method, String url, int code, String respBody, String respContentType, Map<String, Object> params) {
        setupDriver(method, url, code, respBody, respContentType, params);
    }

    public void setupDriverForEmptyResponse(ClientDriverRequest.Method method, String url, String respBody, String respContentType) {
        setupDriverForEmptyResponse(method, url, respBody, respContentType, Collections.EMPTY_MAP);
    }

    public void setupDriverForEmptyResponse(ClientDriverRequest.Method method, String url, String respBody, String respContentType, Map<String, Object> params) {
        setupDriver(method, url, 204, respBody, respContentType, params);
    }

    public void setupDriverForEmptyRequestResponse(ClientDriverRequest.Method method, String url) {
        setupDriverForEmptyRequestResponse(method, url, Collections.EMPTY_MAP);
    }

    public void setupDriverForEmptyRequestResponse(ClientDriverRequest.Method method, String url, Map<String, Object> params) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method).withParams(params),
                giveEmptyResponse().withStatus(204)
        );
    }
}
