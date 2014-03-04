package com.okrest.request;

import com.okrest.http.HTTPException;

import java.io.IOException;
import java.util.ArrayList;

public interface Request<T> {

    public T read(long id) throws IOException, HTTPException;

    public void create(T object) throws IOException, HTTPException;

    public void delete(long id) throws IOException, HTTPException;

    public void update(long id, T object) throws IOException, HTTPException;

    public ArrayList<T> list() throws IOException, HTTPException;
}
