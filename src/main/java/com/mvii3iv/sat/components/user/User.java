package com.mvii3iv.sat.components.user;

public class User {
    private String rfc;
    private String name;
    private String common;
    private String pass;

    public User(String rfc, String name, String common) {
        this.rfc = rfc;
        this.name = name;
        this.common = common;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }
}
