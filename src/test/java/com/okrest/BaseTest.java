package com.okrest;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static com.github.restdriver.clientdriver.RestClientDriver.*;

public class BaseTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public ClientDriverRule driver = new ClientDriverRule();

    public void setupDriver(ClientDriverRequest.Method method, String url, String body, int code, String contentType, Map<String, Object> params) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method).withParams(params),
                giveResponse(body, contentType).withStatus(code)
        );
    }

    public void setupDriverForEmptyRequest(ClientDriverRequest.Method method, String url, int code, String returnedBody, String contentType) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method),
                giveResponse(returnedBody, contentType).withStatus(code)
        );
    }

    public void setupDriverForEmptyResponse(ClientDriverRequest.Method method, String url, String expectedBody, String contentType) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method).withBody(expectedBody, contentType),
                giveEmptyResponse().withStatus(204)
        );
    }

    public void setupDriverForEmptyRequestResponse(ClientDriverRequest.Method method, String url) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method),
                giveEmptyResponse().withStatus(204)
        );
    }
}
