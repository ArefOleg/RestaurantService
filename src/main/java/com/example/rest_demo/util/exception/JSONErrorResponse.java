package com.example.rest_demo.util.exception;

public class JSONErrorResponse {
    private String error;

    public JSONErrorResponse() {

    }

    public JSONErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
