package com.okrest.utils;

import com.okrest.http.HTTPException;

import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public class Utils {

    public static URI buildURI(String backend, String resource) {
        UriBuilder builder = UriBuilder.fromPath(backend);
        builder.path(resource);
        return builder.build();
    }

    public static URI buildURI(String backend, String resource, Map<String, Object> params) {
        UriBuilder builder = UriBuilder.fromPath(backend);
        builder.path(resource);

        for (Map.Entry<String, Object> e : params.entrySet()) {
            builder.queryParam(e.getKey(), e.getValue());
        }

        return builder.build();
    }

    public static boolean in(String a, String b) {
        return a.toLowerCase().contains(b.toLowerCase());
    }

    public static byte[] readBytes(InputStream in) throws IOException, HTTPException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            return out.toByteArray();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
