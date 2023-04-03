package com.fyp.scroli.Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Person implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "uid")
    private int id;
    @ColumnInfo(name = "fname")
    private String first_name;
    @ColumnInfo(name = "lname")
    private String last_name;
    private String email;
    private String gender;
    private String avatar;
    //private Bitmap avatar_pic;
    private String country;
    private String city;
    private String about;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Person(int id,
                  String first_name,
                  String last_name,
                  String email,
                  String gender,
                  String avatar,
                  String country,
                  String city,
                  String about) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.gender = gender;
        this.avatar = avatar;
        this.country = country;
        this.city = city;
        this.about = about;
       // this.avatar_pic = null;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}