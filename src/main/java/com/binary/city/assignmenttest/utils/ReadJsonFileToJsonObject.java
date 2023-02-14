package com.binary.city.assignmenttest.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonFileToJsonObject {

    public  JSONObject read() throws IOException {
        String file="C:\\Users\\pmugomba\\Documents\\My Files\\assignment-test-backend\\assignment-test\\src\\main\\resources\\Openapi\\response.json";
        String content=new String(Files.readAllBytes(Paths.get(file)));
        return new JSONObject(content);
    }
}
