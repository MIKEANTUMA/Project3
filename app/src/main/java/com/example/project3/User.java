package com.example.project3;

import java.util.HashMap;

public class User {


    private String email;
    private String password;
    private String userType;
    private int score;
    private HashMap<String, Integer> attempt;

    public User(){}


    public User(HashMap<String,Object> user){
        this.email = user.get("email").toString();
        this.password = user.get("password").toString();
        this.userType = user.get("usertype").toString();
        this.score = Integer.parseInt(user.get("score").toString());
        this.attempt = (HashMap<String, Integer>) user.get("attempt");
    }

    public User(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.score = user.getScore();
        this.userType = user.getUserType();
        this.attempt = user.getAttempt();
    }

    public User(String email, String password, String userType,int score, HashMap<String, Integer> attempt) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.score = score;
        this.attempt = attempt;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getAttempt(String key) {
        return attempt.get(key);
    }
    public HashMap getAttempt() {
        return attempt;
    }

    public void setAttempt(HashMap<String, Integer> attempt) {
        this.attempt = attempt;
    }
}
