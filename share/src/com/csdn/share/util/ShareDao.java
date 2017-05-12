package com.csdn.share.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShareDao {
    private DBShare dbHpler;

    public ShareDao(Context context) {
        dbHpler = new DBShare(context);
    }

    /**
     * 添加
     */
    public long add(Share song) {
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBData.SHARE_CODE, song.getCode());
        values.put(DBData.SHARE_DATE, song.getDate());

        values.put(DBData.SHARE_NAME, song.getName());
        values.put(DBData.SHARE_SHAPE, song.getShape());
        values.put(DBData.SHARE_REMARK, song.getRemark());
        long rs = db.insert(DBData.SHARE_TABLENAME, DBData.SHARE_CODE, values);
        db.close();
        return rs;
    }


    public List<Share> getALL() {

        List<Share> shares = new ArrayList<>();
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBData.SHARE_TABLENAME, null);
        while (cursor.moveToNext()) {
            Share share = new Share();

            share.setRemark(cursor.getString(cursor.getColumnIndex(DBData.SHARE_REMARK)));
            share.setShape(cursor.getString(cursor.getColumnIndex(DBData.SHARE_SHAPE)));
            share.setDate(cursor.getString(cursor.getColumnIndex(DBData.SHARE_DATE)));
            share.setName(cursor.getString(cursor.getColumnIndex(DBData.SHARE_NAME)));
            share.setCode(cursor.getString(cursor.getColumnIndex(DBData.SHARE_CODE)));
            shares.add(share);
        }
        cursor.close();
        db.close();
        return shares;
    }


    //删除照片信息
    public boolean query(String code) {


        SQLiteDatabase db = dbHpler.getWritableDatabase();
        Cursor cursor = db.query(DBData.SHARE_TABLENAME, new String[]{"name"}, "code=?", new String[]{code}, null, null, null);

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
        int i = db.delete(DBData.SHARE_TABLENAME, DBData.SHARE_CODE + "=?", new String[]{code});

        return i;
    }

    public void delete() {
        SQLiteDatabase db = dbHpler.getWritableDatabase();
        db.execSQL("delete from " + DBData.SHARE_TABLENAME);
        db.close();


    }
}
