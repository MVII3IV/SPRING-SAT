package com.mvii3iv.sat.components.login;


public class User {

    private String rfc;
    private String pass;

    public User() {
    }

    public User(String rfc, String pass) {
        this.rfc = rfc;
        this.pass = pass;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
