package com.example.qlhh;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable {
    private int idloai;
    private String tenloai;
    private String tenloaikd;

    public Goods(){}

    public Goods(int idloai, String tenloai, String tenloaikd) {
        this.idloai = idloai;
        this.tenloai = tenloai;
        this.tenloaikd = tenloaikd;
    }

    protected Goods(Parcel in) {
        idloai = in.readInt();
        tenloai = in.readString();
        tenloaikd = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idloai);
        dest.writeString(tenloai);
        dest.writeString(tenloaikd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getTenloaikd() {
        return tenloaikd;
    }

    public void setTenloaikd(String tenloaikd) {
        this.tenloaikd = tenloaikd;
    }
}

