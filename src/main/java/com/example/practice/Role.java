package com.example.practice;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String code;

    Role(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
