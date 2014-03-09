package com.okrest;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static com.github.restdriver.clientdriver.RestClientDriver.*;

public class BaseTest {

    public static final byte[] EMPTY_REQUEST = new byte[0];

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public ClientDriverRule driver = new ClientDriverRule();

    public void setupDriver(String url, String body, int code, String contentType) {
        driver.addExpectation(
                onRequestTo(url).withMethod(ClientDriverRequest.Method.GET),
                giveResponse(body).withStatus(code).withContentType(contentType)
        );
    }

    public void setupDriver(ClientDriverRequest.Method method, String url, int code, String contentType) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method),
                giveEmptyResponse().withStatus(code).withContentType(contentType)
        );
    }

    public void setupDriver(ClientDriverRequest.Method method, String url, String body, int code, String contentType) {
        driver.addExpectation(
                onRequestTo(url).withMethod(method),
                giveResponse(body).withStatus(code).withContentType(contentType)
        );
    }
}
