package com.mvii3iv.sat.components.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {

    @Id
    private ObjectId _id;
    private String rfc;
    private String name;
    private String common;
    private String pass;

    public Users(String rfc, String name, String common) {
        this.rfc = rfc;
        this.name = name;
        this.common = common;
    }

    public Users() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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
