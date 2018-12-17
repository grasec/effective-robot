package com.alexey.loginsignin;

import java.security.InvalidParameterException;

public class User {
    public static final String DELIMITER = "&";
    private String userName, password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userAsString){
        if (userAsString == null){
            throw new InvalidParameterException();
        }
        String[] parts = userAsString.split(DELIMITER);
        if (parts.length !=2 ){
            throw new  InvalidParameterException("must be two parts");
        }
        this.userName = parts[0];
        this.password = parts[1];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userName + DELIMITER + password;
    }
}
