package com.mvii3iv.sat.components.user;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;

public class Users {

    @Id
    private ObjectId _id;
    private String id;
    private String image;
    private String name;
    private String lastName;
    private String common;
    private String pass;
    private String role;
    private String taxRegime;

    public Users(String id, String name, String common, String role) {
        this.id = id;
        this.name = name;
        this.common = common;
        this.role = role;
    }

    public Users() {
    }

    public String getRole() {
        return role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTaxRegime() {
        return taxRegime;
    }

    public void setTaxRegime(String taxRegime) {
        this.taxRegime = taxRegime;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
