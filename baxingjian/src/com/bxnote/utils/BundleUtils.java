package com.bxnote.utils;

import com.bxnote.bean.Note;
import com.bxnote.config.ParamsConfig;

import android.os.Bundle;

public class BundleUtils {
	public static void setEmail(Bundle mBundle, String name) {
		mBundle.putString(ParamsConfig.EMAIL, name);
	}
	public static String getEmail(Bundle mBundle) {
		return mBundle.getString(ParamsConfig.EMAIL);
	}
	public static void setPassword(Bundle mBundle, String password) {
		mBundle.putString(ParamsConfig.PASSWORD,password);
	}
	public static  String  getPassword(Bundle mBundle){
		return mBundle.getString(ParamsConfig.PASSWORD);
	}
	public static void setNote(Bundle mBundle, Note note) {
		mBundle.putSerializable("note",note);
	}
	public static Note getNote(Bundle mBundle ) {
		return (Note)mBundle.getSerializable("note");//("note",note);
	}
}
