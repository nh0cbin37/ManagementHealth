package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Workout implements Serializable, Parcelable {
    int id_practice;
    String name_practice;
    String Detail;
    int time;

    protected Workout(Parcel in) {
        id_practice = in.readInt();
        name_practice = in.readString();
        Detail = in.readString();
        time = in.readInt();
        videview_practice = in.readString();
        LoaiBMI = in.readString();
        rang = in.readString();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getLoaiBMI() {
        return LoaiBMI;
    }

    public void setLoaiBMI(String loaiBMI) {
        LoaiBMI = loaiBMI;
    }





    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    String videview_practice;
    String LoaiBMI;

    String rang;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public int getId_practice() {
        return id_practice;
    }

    public void setId_practice(int id_practice) {
        this.id_practice = id_practice;
    }

    public String getName_practice() {
        return name_practice;
    }

    public void setName_practice(String name_practice) {
        this.name_practice = name_practice;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getVideview_practice() {
        return videview_practice;
    }

    public void setVideview_practice(String videview_practice) {
        this.videview_practice = videview_practice;
    }


    public Workout(int id_practice, String name_practice, int time, String detail, String videview_practice,String LoaiBMI,String rang) {
        this.id_practice = id_practice;
        this.name_practice = name_practice;
        Detail = detail;
        this.videview_practice = videview_practice;
        this.time = time;
        this.LoaiBMI = LoaiBMI;

        this.rang=rang;
    }


    public Workout(String name_practice, int time, String detail, String videview_practice,String LoaiBMI,String rang) {
        this.name_practice = name_practice;
        Detail = detail;
        this.videview_practice = videview_practice;
        this.time = time;
        this.LoaiBMI = LoaiBMI;

        this.rang=rang;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id_practice);
        dest.writeString(name_practice);
        dest.writeString(Detail);
        dest.writeInt(time);
        dest.writeString(videview_practice);
        dest.writeString(LoaiBMI);
        dest.writeString(rang);
    }
}
