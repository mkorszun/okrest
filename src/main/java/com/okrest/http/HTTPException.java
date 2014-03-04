package com.okrest.http;

public class HTTPException extends Exception {

    public HTTPException(String msg) {
        super(msg);
    }

    public HTTPException(Throwable t) {
        super(t);
    }
}
