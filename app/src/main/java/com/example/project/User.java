package com.example.project;

import java.io.Serializable;

public class User  implements Serializable {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    int id;
    String userName;
    String Name;

    public User(int id, String name, String userName, String password, String gender, double weight, double height, double BMI) {
        this.id = id;
        this.userName = userName;
        Name = name;
        Password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.BMI = BMI;
    }

    String Password;
    String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    double weight;

    public User(String name,String userName,  String password, String gender, double weight, double height, double BMI) {
        this.userName = userName;
        Name = name;
        Password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.BMI = BMI;
    }

    double height;
    double BMI;




}

