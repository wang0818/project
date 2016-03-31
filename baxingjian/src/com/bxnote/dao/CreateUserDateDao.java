package com.bxnote.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bxnote.bean.User;
import com.bxnote.utils.Utils;

public class CreateUserDateDao {
	 UserOpenHelper mDefineTypeDBHelper;

	public CreateUserDateDao(Context context) {
		super();
		mDefineTypeDBHelper = new UserOpenHelper(context, null, null, 0);
	}

	public void insertTable(User user) {
		SQLiteDatabase database = mDefineTypeDBHelper.getWritableDatabase(); // 写入数据库
		String sql = "insert into user_tab(username,email,password)values(?,?,?)";
		Object[] userNum = new Object[] { user.name, user.email, user.password };
		database.execSQL(sql, userNum);
	}

	public void updateTable(SQLiteDatabase db) {
		String sql = "";
		db.execSQL(sql);
	}
	public boolean isHaveUser(String userEmail,String userPassword) {
		SQLiteDatabase database = mDefineTypeDBHelper.getReadableDatabase();
		Cursor cursor = null;
		String sql = "select * from user_tab where email = ? and password = ?";
		cursor = database.rawQuery(sql, new String[] { "" + userEmail,
				"" + userPassword });
		while (cursor.moveToNext()) {
			return true;
		}
		return false;
	}
	public boolean isHaveUser(String userEmail) {
		SQLiteDatabase database = mDefineTypeDBHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select * from user_tab where email = ?";
			cursor = database.rawQuery(sql, new String[] { "" + userEmail });
			while (cursor.moveToNext()) {
				return true;
			}
		} catch (Exception e) {
		}finally{
			closeCursor(cursor);
		}
		
		return false;
	}
	public User queryUser(String email) {
		SQLiteDatabase database = mDefineTypeDBHelper.getReadableDatabase();
		Cursor cursor = null;
		User user = new User();
		try {
			String sql = "select * from user_tab where email = ? ";
			cursor = database.rawQuery(sql, new String[] { "" + email });
			while (cursor.moveToNext()) {
				user.email = cursor.getString(cursor.getColumnIndex("email"));
				user.password = cursor.getString(cursor.getColumnIndex("password"));
				user.name = cursor.getString(cursor.getColumnIndex("username"));
				return user;
			}
		} catch (Exception e) {
		}finally{
			closeCursor(cursor);
		}
		return null;
	}
	public void closeCursor(Cursor cursor){
		if (!Utils.isObject(cursor)) {
			cursor.close();
		}
	}
}
