package com.example.project;

import java.io.Serializable;

public class Workout_ID_Users implements Serializable {
    public Workout_ID_Users(int id_Pratice, int ID_User, int status) {
        Id_Pratice = id_Pratice;
        this.ID_User = ID_User;
        this.status = status;
    }

    int Id_Pratice;
    int ID_User;

    public int getId_Pratice() {
        return Id_Pratice;
    }

    public void setId_Pratice(int id_Pratice) {
        Id_Pratice = id_Pratice;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status;


}
