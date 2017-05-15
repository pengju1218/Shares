package com.csdn.share.util.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.csdn.share.util.DBData;
import com.csdn.share.util.DBShare;
import com.csdn.share.util.bean.Care;
import com.csdn.share.util.bean.Share;

import java.util.ArrayList;
import java.util.List;

public class CareDao {
    private DBShare dbHpler;

    public CareDao(Context context) {
        dbHpler = new DBShare(context);
    }

    /**
     * 添加
     */
    public long add(Care song) {
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBData.CARE_CODE, song.getCode());
        values.put(DBData.CARE_NAME, song.getName());
        values.put(DBData.CARE_REMARK, song.getRemark());
        long rs = db.insert(DBData.CARE_TABLENAME, DBData.CARE_CODE, values);
        db.close();
        return rs;
    }


    public List<Care> getALL() {

        List<Care> shares = new ArrayList<>();
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBData.CARE_TABLENAME, null);
        while (cursor.moveToNext()) {
            Care care = new Care();
            care.setRemark(cursor.getString(cursor.getColumnIndex(DBData.CARE_REMARK)));
            care.setName(cursor.getString(cursor.getColumnIndex(DBData.CARE_NAME)));
            care.setCode(cursor.getString(cursor.getColumnIndex(DBData.CARE_CODE)));
            shares.add(care);
        }
        cursor.close();
        db.close();
        return shares;
    }


    //删除照片信息
    public boolean query(String code) {


        SQLiteDatabase db = dbHpler.getWritableDatabase();
        Cursor cursor = db.query(DBData.CARE_TABLENAME, new String[]{"name"}, "code=?", new String[]{code}, null, null, null);

        while (cursor.moveToNext()) {

            String name = cursor.getString(0);//获取第二列的值
            if (name != null) {
                return true;
            }
        }
        cursor.close();
        db.close();

        return false;

    }

    //删除照片信息
    public int deleteShare(String code) {
        SQLiteDatabase db = dbHpler.getReadableDatabase();
        int i = db.delete(DBData.CARE_TABLENAME, DBData.CARE_CODE + "=?", new String[]{code});

        return i;
    }

    public void delete() {
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        db.execSQL("delete from " + DBData.CARE_TABLENAME);
        db.close();


    }
}
