package com.csdn.share.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBShare extends SQLiteOpenHelper {
	//
	
	
	
	
	
	
	
	public DBShare(Context context) {
		super(context, DBData.DATABASE_NAME, null,DBData.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		// 创建歌曲表
		db.execSQL("CREATE TABLE " + DBData.SHARE_TABLENAME + "("
				+ DBData.SHARE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DBData.SHARE_CODE + " NVARCHAR(100),"
				+ DBData.SHARE_NAME  +" NVARCHAR(100),"
				+ DBData.SHARE_DATE + " NVARCHAR(100),"
				+ DBData.SHARE_SHAPE + " NVARCHAR(100),"
				+ DBData.SHARE_REMARK + " NVARCHAR(500))"
			);

		// 创建歌曲表
		db.execSQL("CREATE TABLE " + DBData.CARE_TABLENAME + "("
				+ DBData.CARE_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ DBData.CARE_CODE + " NVARCHAR(100),"
				+ DBData.CARE_NAME  +" NVARCHAR(100),"
				+ DBData.CARE_REMARK + " NVARCHAR(500))"
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
		db.execSQL("DROP TABLE IF EXISTS " + DBData.SHARE_TABLENAME);
		db.execSQL("DROP TABLE IF EXISTS " + DBData.CARE_TABLENAME);
	}

}
