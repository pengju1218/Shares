package com.csdn.share.util.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 歌曲
 */
public class Share implements Parcelable {

    private int id;// id


    private String name;//歌曲名称
    private String code;//歌曲名称
    private String date;//歌曲名称
    private String shape;//歌曲名称
    private String remark;//歌曲名称


    public Share() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Creator<Share> getCreator() {
        return CREATOR;
    }


    public static final Creator<Share> CREATOR = new Creator<Share>() {

        public Share createFromParcel(Parcel source) {
            Share song = new Share();
            song.id = source.readInt();
            song.code = source.readString();
            song.name = source.readString();
            song.date = source.readString();
            song.shape = source.readString();
            song.remark = source.readString();


            return song;
        }

        public Share[] newArray(int size) {
            return new Share[size];
        }

    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(shape);
        dest.writeString(remark);
        dest.writeString(date);

    }

}