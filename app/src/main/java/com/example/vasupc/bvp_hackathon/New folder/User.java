package com.example.vasupc.bvp_hackathon.Model;

/**
 * Created by VasuPC on 07-03-2018.
 */

public class User {

    private String name ;
    private String gender ;
    private String email ;
    private String password ;
    private Family family;

    public User(){

    }

    public User(String name,String gender,String email,String password,Family family){
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.family = family ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

}
