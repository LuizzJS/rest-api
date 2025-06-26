package com.luiz.restapi.entities;

public class Response {
    String message;
    Boolean ok;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response() {
    }

    public Response(String message, Boolean ok) {
        this.message = message;
        this.ok = ok;
    }
}
