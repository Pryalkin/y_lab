package org.example.model;

public class DTO {

    private String message;

    @Override
    public String toString() {
        return "{" +
                "message:'" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
