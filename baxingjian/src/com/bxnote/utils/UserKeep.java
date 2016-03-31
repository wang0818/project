package com.bxnote.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.bean.User;
import com.bxnote.config.ParamsConfig;

public class UserKeep {
	private static final String FILE_NAME = "user";
	private static final String QQ_USER = "qqUser";
	private static final String USER = "user_data";

	// 保存用户信息
	public static void keepUser(Context context, SinaUser user) {
		SharedPreferences spref = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND);
		Editor editor = spref.edit();
		editor.putLong("id", user.expiresIn);
		editor.putString("name", user.name);
		editor.putString("sinaToken", user.accessToken);
		editor.putString("gender", user.gender);
		editor.commit();
	}

	// 读取user对象
	public static SinaUser readUser(Context context) {
		SinaUser user = new SinaUser();
		SharedPreferences spref = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND);
		user.expiresIn = spref.getLong("id", Constant.USERLOGOUT);
		user.name = spref.getString("name", "");
		user.accessToken = spref.getString("sinaToken", "");
		user.gender = spref.getString("gender", "");
		return user;
	}

	public static void keepQqUser(Context context, QQUser qqUser) {
		SharedPreferences spref = context.getSharedPreferences(QQ_USER,
				Context.MODE_APPEND);
		Editor editor = spref.edit();
		editor.putLong(ParamsConfig.EXPIRES_IN, qqUser.expiresIn);
		editor.putString(ParamsConfig.QQ_NICK_NAME, qqUser.qqNickName);
		editor.putString(ParamsConfig.GENDER, qqUser.gender);
		editor.putString(ParamsConfig.ACCESS_TOKEN, qqUser.accessToken);
		editor.commit();
	}

	public static void keepUserData(String userID, Context context) {
		SharedPreferences spref = context.getSharedPreferences(USER,
				Context.MODE_APPEND);
		Editor editor = spref.edit();
		editor.putString(ParamsConfig.USER_EMAIL, userID);
		editor.commit();
	}

	public static User getUserData(Context context) {
		User user = new User();
		SharedPreferences spref = context.getSharedPreferences(USER,
				Context.MODE_APPEND);
		user.email = spref.getString(ParamsConfig.USER_EMAIL, "");
		return user;
	}

	public static QQUser getQQUser(Context context) {
		QQUser qqUser = new QQUser();
		SharedPreferences spref = context.getSharedPreferences(QQ_USER,
				Context.MODE_APPEND);
		qqUser.expiresIn = spref.getLong(ParamsConfig.EXPIRES_IN,
				Constant.USERLOGOUT);
		qqUser.qqNickName = spref.getString(ParamsConfig.QQ_NICK_NAME, "");
		qqUser.gender = spref.getString(ParamsConfig.GENDER, "");
		qqUser.accessToken = spref.getString(ParamsConfig.ACCESS_TOKEN, "");
		return qqUser;

	}

	// 清空
	public static void clearSinauser(Context context) {
		SharedPreferences pref = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	public static void clearQQuser(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QQ_USER,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();

	}

	public static void clearUserData(Context context) {
		SharedPreferences pref = context.getSharedPreferences(USER,
				Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();

	}
}
