package com.anubhav.ecommercein4;

/**
 * Created by anubh on 14-Dec-16.
 */

public class Users {
    String name,email,mobileno,password;
    int loggedinstatus;

    public Users(String name, String email, String mobileno, String password) {
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.password = password;
        this.loggedinstatus = 0;
    }

    public Users(String name, String email, String mobileno, String password, int loggedinstatus) {
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.password = password;
        this.loggedinstatus = loggedinstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoggedinstatus() {
        return loggedinstatus;
    }

    public void setLoggedinstatus(int loggedin) {
        this.loggedinstatus = loggedin;
    }
}
