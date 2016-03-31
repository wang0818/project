package com.bxnote.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserOpenHelper extends SQLiteOpenHelper {

	private static Context mContext;
	private static String DEAFINE_DATABASE_NAME = "user.db";
	private static int mVersion = 1;

	public UserOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DEAFINE_DATABASE_NAME, null, mVersion);
	}

	public UserOpenHelper() {
		super(mContext, DEAFINE_DATABASE_NAME, null, mVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table user_tab(_id integer PRIMARY KEY AUTOINCREMENT,username text , password text,email text)";
		String content = "create table user_content(_id integer PRIMARY KEY AUTOINCREMENT , username varchar(255) ,user_content text , image_location varchar(255),year integer , month integer , day integer , hour integer , minute integer ,second integer,current_insert_time text,chapeaut text , end_lan text , greetings text , signature_name text , is_hand_write integer,one_content text , two_content text ,three_content text ,four_content text , five_content text,text_type varchar(255) , background varchar(255))";
		db.execSQL(content);
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		String sql = "drop table if exists user_tab";
//		db.execSQL(sql);
	}

}
