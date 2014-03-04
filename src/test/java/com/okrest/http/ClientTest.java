package com.okrest.http;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static com.github.restdriver.clientdriver.RestClientDriver.giveResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

public class ClientTest {

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
}
