package com.example.lenovo_pc.myce;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import javax.inject.Singleton;

/**
 * Created by Lenovo-PC on 06/11/2016.
 */

public class Account {

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String fullName = "";
    private String position = "";
    private String email = "";
    private String password = "";
    private int itempos = 0;
    private ArrayList<ContentValues> employees = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        fullName = firstName + " " + lastName;
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public ArrayList<ContentValues> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<ContentValues> employees) {
        this.employees = employees;
    }

    public int getItempos() {
        return itempos;
    }

    public void setItempos(int itempos) {
        this.itempos = itempos;
    }

}
