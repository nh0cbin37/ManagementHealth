package com.example.project;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ThucAn implements Serializable {

    int Id_food;

    public int getId_food() {
        return Id_food;
    }

    public void setId_food(int id_food) {
        Id_food = id_food;
    }

    public String getTenThucAn() {
        return TenThucAn;
    }

    public void setTenThucAn(String tenThucAn) {
        TenThucAn = tenThucAn;
    }

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public double getDam() {
        return Dam;
    }

    public void setDam(double dam) {
        Dam = dam;
    }

    public double getBeo() {
        return Beo;
    }

    public void setBeo(double beo) {
        Beo = beo;
    }

    public double getBot_Duong() {
        return Bot_Duong;
    }

    public void setBot_Duong(double bot_Duong) {
        Bot_Duong = bot_Duong;
    }

    public double getXo() {
        return Xo;
    }

    public void setXo(double xo) {
        Xo = xo;
    }

    public int getBuoi() {
        return Buoi;
    }

    public void setBuoi(int buoi) {
        Buoi = buoi;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getLoaiBMI() {
        return LoaiBMI;
    }

    public void setLoaiBMI(String loaiBMI) {
        LoaiBMI = loaiBMI;
    }

    String TenThucAn;
    String Soluong;

    public String getSoluong() {
        return Soluong;
    }

    public void setSoluong(String soluong) {
        Soluong = soluong;
    }

    int calo;

    public ThucAn(int id_food, String tenThucAn, String Soluong,int calo, double dam, double beo, double bot_Duong, double xo, int buoi, String loai, String hinhAnh, String loaiBMI) {
        Id_food = id_food;
        TenThucAn = tenThucAn;
        this.Soluong = Soluong;
        this.calo = calo;
        Dam = dam;
        Beo = beo;
        Bot_Duong = bot_Duong;
        Xo = xo;
        Buoi = buoi;
        Loai = loai;
        HinhAnh = hinhAnh;
        LoaiBMI = loaiBMI;
    }

    double Dam;
    double Beo;
    double Bot_Duong;
    double Xo;
    int Buoi;

    public ThucAn(String tenThucAn,String Soluong, int calo, double dam, double beo, double bot_Duong, double xo, int buoi, String loai, String hinhAnh, String loaiBMI) {
        TenThucAn = tenThucAn;
        this.Soluong = Soluong;
        this.calo = calo;
        Dam = dam;
        Beo = beo;
        Bot_Duong = bot_Duong;
        Xo = xo;
        Buoi = buoi;
        Loai = loai;
        HinhAnh = hinhAnh;
        LoaiBMI = loaiBMI;
    }

    String Loai;
    String HinhAnh;
    String LoaiBMI;
    public String toString() {
        return  TenThucAn ;
    }
}
