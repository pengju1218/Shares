package com.csdn.share.util.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 歌曲
 */
public class Care implements Parcelable {

    private int id;// id


    private String name;//歌曲名称
    private String code;//歌曲名称
    private String remark;//歌曲名称


    public Care() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Creator<Care> getCreator() {
        return CREATOR;
    }


    public static final Creator<Care> CREATOR = new Creator<Care>() {

        public Care createFromParcel(Parcel source) {
            Care song = new Care();
            song.id = source.readInt();
            song.code = source.readString();
            song.name = source.readString();
            song.remark = source.readString();


            return song;
        }

        public Care[] newArray(int size) {
            return new Care[size];
        }

    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(remark);


    }

}