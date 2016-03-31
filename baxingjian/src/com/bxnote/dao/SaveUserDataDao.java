package com.bxnote.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bxnote.bean.Note;
import com.bxnote.utils.Utils;

public class SaveUserDataDao {

	private UserOpenHelper mDefineTypeDBHelper;

	public SaveUserDataDao(Context context) {
		super();
		mDefineTypeDBHelper = new UserOpenHelper(context, null, null, 0);
	}

	public void insertTable(Note note) {
		SQLiteDatabase database = mDefineTypeDBHelper.getWritableDatabase(); // 写入数据库
		try {
			String sql = "insert into user_content(username,user_content,image_location,year,month,day,hour,minute,second,current_insert_time,chapeaut,end_lan,greetings,signature_name,is_hand_write,one_content,two_content ,three_content ,four_content ,five_content,text_type,background)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] userNum = new Object[] { note.userName, note.userContent,
					note.imageLocation, note.year, note.month, note.day,
					note.hour, note.minute, note.second, note.currInsertTime,
					note.mChapeaut, note.mEndLan, note.mGreetings,
					note.mSignatureName, note.isHandWrite, note.mOneContent,
					note.mTwoContent, note.mThreeContent, note.mFourContent,
					note.mFiveContent ,note.mTextType,note.mBackground};
			database.execSQL(sql, userNum);
		} catch (Exception e) {
			Log.e("tag", "e" + e.getMessage());
		}
	}

	public void updateTable(SQLiteDatabase db) {
		String sql = "";
		db.execSQL(sql);
	}

	public void deleteTable(SQLiteDatabase db) {
		String sql = "";
		db.execSQL(sql);
	}

	public List<Note> queryNotes(String userId) {
		SQLiteDatabase database = mDefineTypeDBHelper.getReadableDatabase();
		List<Note> notes = new ArrayList<Note>();
		Cursor cursor = null;
		try {
			String sql = "select * from user_content where username = ? order by current_insert_time desc";
			cursor = database.rawQuery(sql, new String[] { "" + userId });
			while (cursor.moveToNext()) {
				Note note = new Note();
				note.userName = cursor.getString(cursor
						.getColumnIndex("username"));
				note.userContent = cursor.getString(cursor
						.getColumnIndex("user_content"));
				note.imageLocation = cursor.getString(cursor
						.getColumnIndex("image_location"));
				note.year = cursor.getInt(cursor.getColumnIndex("year"));
				note.month = cursor.getInt(cursor.getColumnIndex("month"));
				note.day = cursor.getInt(cursor.getColumnIndex("day"));
				note.hour = cursor.getInt(cursor.getColumnIndex("hour"));
				note.minute = cursor.getInt(cursor.getColumnIndex("minute"));
				note.second = cursor.getInt(cursor.getColumnIndex("second"));
				note.currInsertTime = cursor.getString(cursor
						.getColumnIndex("current_insert_time"));
				note.mChapeaut = cursor.getString(cursor
						.getColumnIndex("chapeaut"));
				note.mEndLan = cursor.getString(cursor
						.getColumnIndex("end_lan"));
				note.mGreetings = cursor.getString(cursor
						.getColumnIndex("greetings"));
				note.mSignatureName = cursor.getString(cursor
						.getColumnIndex("signature_name"));
				note.isHandWrite = cursor.getInt(cursor
						.getColumnIndex("is_hand_write"));
				note.mOneContent = cursor.getString(cursor
						.getColumnIndex("one_content"));
				note.mTwoContent = cursor.getString(cursor
						.getColumnIndex("two_content"));
				note.mThreeContent = cursor.getString(cursor
						.getColumnIndex("three_content"));
				note.mFourContent = cursor.getString(cursor
						.getColumnIndex("four_content"));
				note.mFiveContent = cursor.getString(cursor
						.getColumnIndex("five_content"));
				note.mTextType = cursor.getString(cursor
						.getColumnIndex("text_type"));
				note.mBackground = cursor.getString(cursor
						.getColumnIndex("background"));
				notes.add(note);
			}
		} catch (Exception e) {
		} finally {
			closeCursor(cursor);
		}
		return notes;
	}

	public boolean isHaveObject(String currenttime) {
		SQLiteDatabase database = mDefineTypeDBHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String sql = "select * from user_content where current_insert_time = ?";
			cursor = database.rawQuery(sql, new String[] { "" + currenttime });
			while (cursor.moveToNext()) {
				return true;
			}
		} catch (Exception e) {
		} finally {
			closeCursor(cursor);
		}
		return false;
	}

	public void updateUserDataItem(String currentInsertTime, Note note) {
		SQLiteDatabase database = mDefineTypeDBHelper.getWritableDatabase(); // 写入数据库
		try {
			String sql = "update user_content set text_type = ? , background = ? , chapeaut=?,end_lan=?,greetings=?,signature_name=?,is_hand_write=?,one_content = ? ,two_content= ?,three_content = ? ,four_content = ? ,five_content =?, user_content = ? ,image_location = ? ,year = ? , month = ? , day= ? , hour = ? , minute = ? , second = ? where current_insert_time = ? ";
			Object[] objec = {note.mTextType,note.mBackground,note.mChapeaut, note.mEndLan, note.mGreetings,
					note.mSignatureName, note.isHandWrite, note.mOneContent,
					note.mTwoContent, note.mThreeContent, note.mFourContent,
					note.mFiveContent, note.userContent, note.imageLocation,
					note.year, note.month, note.day, note.hour, note.minute,
					note.second, currentInsertTime };
			database.execSQL(sql, objec);
		} catch (Exception e) {
			Log.e("tag", "message -- > " + e.getMessage());
		}
	}

	public void deleteIndexCurrentTime(String currentTime) {
		try {
			SQLiteDatabase database = mDefineTypeDBHelper.getWritableDatabase();
			String sql = "delete from user_content where current_insert_time = ? ";
			Object[] objec = { currentTime };
			database.execSQL(sql, objec);
		} catch (Exception e) {
			Log.e("tag", "message -- > " + e.getMessage());
		}

	}
	public void deleteTab() {
		try {
			SQLiteDatabase database = mDefineTypeDBHelper.getWritableDatabase();
			String sql = "delete from user_content";
			database.execSQL(sql);
		} catch (Exception e) {
			Log.e("tag", "message -- > " + e.getMessage());
		}

	}
	public void closeCursor(Cursor cursor) {
		if (!Utils.isObject(cursor)) {
			cursor.close();
		}
	}

}
