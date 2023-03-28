package com.example.project;

import java.io.Serializable;

public class CaloUsers implements Serializable {
    String LoaiBMI;
    int Calo;
    double bot;

    public String getLoaiBMI() {
        return LoaiBMI;
    }

    public void setLoaiBMI(String loaiBMI) {
        LoaiBMI = loaiBMI;
    }

    public int getCalo() {
        return Calo;
    }

    public void setCalo(int calo) {
        Calo = calo;
    }

    public double getBot() {
        return bot;
    }

    public void setBot(double bot) {
        this.bot = bot;
    }

    public double getXo() {
        return xo;
    }

    public void setXo(double xo) {
        this.xo = xo;
    }

    public double getDam() {
        return dam;
    }

    public void setDam(double dam) {
        this.dam = dam;
    }

    public double getBeo() {
        return beo;
    }

    public void setBeo(double beo) {
        this.beo = beo;
    }

    double xo;
    double dam;

    public CaloUsers(String loaiBMI, int calo, double bot, double xo, double dam, double beo) {
        LoaiBMI = loaiBMI;
        Calo = calo;
        this.bot = bot;
        this.xo = xo;
        this.dam = dam;
        this.beo = beo;
    }

    double beo;
}
