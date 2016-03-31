package com.bxnote.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bxnote.bean.Note;
import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.bean.User;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.ParamsConfig;

public class Utils {
	public static int getScreentWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	public static DisplayMetrics getScale(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); // 获取手机的密度
		return dm;
	}

	public static int getScreenHeihgt(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	public static float getDesity(Activity activity) {

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		return dm.density;

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getWidth(int width, int screenWidth) {
		if (screenWidth == Constant.DEFAULE_ZERO) {
			return width;
		}
		float widthF = width;
		return Math.round(screenWidth * widthF / Constant.DESIGN_DEFAULE_WIDTH);
	}

	public static int getHeight(int height, int screenheight) {
		if (screenheight == Constant.DEFAULE_ZERO) {
			return height;
		}
		float heightF = height;
		return Math.round(screenheight * heightF
				/ Constant.DESIGN_DEFAULE_HEIGHT);
	}

	public static boolean isObject(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isCollectionEmpty(Object object) {
		if (object == null) {

			return true;
		}
		if (object instanceof List) {
			if (((List) object).size() <= Constant.DEFAULE_ZERO) {
				return true;
			}
		}
		return false;
	}

	public static int getTitleWidth(int width, int menuWdith) {
		return Constant.DESIGN_DEFAULE_WIDTH - menuWdith * 2;
	}

	public static boolean getIsFirst(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(ParamsConfig.IS_FIRST, true);
	}

	public static boolean getIsWriteData(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(ParamsConfig.IS_WRITE, true);
	}

	public static void setIsFirst(Context context, boolean isFirst) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(ParamsConfig.IS_FIRST, isFirst).commit();

	}

	public static void setIsWriteData(Context context, boolean isFirst) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(ParamsConfig.IS_WRITE, isFirst).commit();

	}

	public static boolean getIsDefaultTheme(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(ParamsConfig.DEFAULE_THEME, true);

	}

	public static void setIsDefaultTheme(Context context, boolean isDefaultTheme) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(ParamsConfig.DEFAULE_THEME, isDefaultTheme)
				.commit();

	}

	public static boolean isUserName(Context applicationContext, String content) {
		if (StringUtils.isEmpty(content)) {
			ToastUtils.showShortToast(applicationContext, "用户名不能为空");
			return false;
		}
		return true;
	}

	public static boolean isUserEmail(Context applicationContext, String content) {
		if (StringUtils.isEmpty(content)) {
			ToastUtils.showShortToast(applicationContext, "用户名不能为空");
			return false;
		}
		if (!StringUtils.isEmail(content)) {
			ToastUtils.showShortToast(applicationContext, "输入正确的邮箱");
			return false;
		}
		return true;
	}

	public static boolean isPassword(Context applicationContext, String content) {
		if (StringUtils.isEmpty(content)) {
			ToastUtils.showShortToast(applicationContext, "密码不能为空");
			return false;
		}
		return true;
	}

	public static void setType(Context context, TextView textview) {
		Typeface typeface = Typeface.createFromAsset(context.getAssets(),
				"font/fangzhengqingkebenyuesongjianti.ttf");
		textview.setTypeface(typeface);
	}

	public static void setOtherType(Context context, TextView textview,
			String fontType) {
		Typeface typeface = Typeface.createFromAsset(context.getAssets(),
				"font/" + fontType);
		textview.setTypeface(typeface);
	}

	public static void getCurrentTime() {
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentSavePhotoTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static boolean isChangeLine(List<String> bitmapAddresses) {
		StringBuffer stringBuffer = new StringBuffer();
		// 判斷顯示的長度是否應該
		for (int i = 0; i < bitmapAddresses.size(); i++) {
			stringBuffer.append(bitmapAddresses.get(i));
		}
		Log.e("tag", stringBuffer.toString());
		return false;
	}

	public static Bitmap getSaveBitmap(View itemView) {
		itemView.destroyDrawingCache();
		itemView.destroyDrawingCache();
		itemView.setDrawingCacheEnabled(true);
		Bitmap bm = itemView.getDrawingCache();// 获取bitmap的对象
		return bm;
	}

	public static String getLetterPath() {
		return "letter";
	}

	public static String getBackKey(String imageUrl) {
		imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("."));
		return imageUrl;
	}

	public static int getTextWidth(Paint paint, String str) {
		int iRet = 0; // 计算字符串的长度
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len]; // 文字数组
			paint.getTextWidths(str, widths);// 每个文字的宽度
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);// 获取每个文字的宽度
			}
		}
		return iRet;
	}

	public static boolean isChangeLine(Paint paint, String str, int height) {
		int iRet = 0; // 计算字符串的长度
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len]; // 文字数组
			paint.getTextWidths(str, widths);// 每个文字的宽度
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);// 获取每个文字的宽度
			}
		}
		Log.e("",
				"utils"
						+ iRet
						+ " get "
						+ Utils.getHeight(ConfigLayout.HAND_WRITE_EDIT_HEIGHT,
								height));
		if (iRet > Utils.getHeight(ConfigLayout.HAND_WRITE_EDIT_HEIGHT, height)) {
			return true;
		}
		return false;
	}

	public static boolean isChangeLine(List<SpannableString> spannables,
			int height) {
		int size = spannables.size();
		size = size + Constant.DEFAULE_ONE;
		Log.e("tag",
				"Utils.getHeight((int)(size*Constant.HAND_WRITE_HEIHT),height)"
						+ Utils.getHeight(
								(int) (size * Constant.HAND_WRITE_HEIHT),
								height));
		if (Utils
				.getHeight(
						(int) (size * Constant.HAND_WRITE_HEIHT + Constant.DEFAULE_ONE),
						height) >= Utils.getHeight(
				ConfigLayout.HAND_WRITE_EDIT_HEIGHT, height)) {
			return true;
		} else {
			return false;
		}
	}

	public static String[] getText(Paint paint, String str, int height) {
		int iRet = 0; // 计算字符串的长度
		int recodeIndex = 0;
		String[] datas = new String[2];
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len]; // 文字数组
			paint.getTextWidths(str, widths);// 每个文字的宽度
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);// 获取每个文字的宽度
				if (iRet > Utils.getHeight(ConfigLayout.HAND_WRITE_EDIT_HEIGHT,
						height)) {
					recodeIndex = j;
					break;
				}
			}
			datas[0] = str.substring(Constant.DEFAULE_ZERO, recodeIndex);
			datas[0] = datas[0] + ";";
			datas[1] = str.substring(recodeIndex, str.length());
		}
		return datas;
	}

	public static Note getNote(Note note) {
		Calendar calendar = Calendar.getInstance();
		note.year = calendar.get(Calendar.YEAR);
		/*
		 * 在获取月份的时候少一个月，是从0-11开始的，获取月份的时候加+1
		 */
		note.month = calendar.get(Calendar.MONTH) + 1;
		note.day = calendar.get(Calendar.DAY_OF_MONTH);
		note.hour = calendar.get(Calendar.HOUR_OF_DAY);
		note.minute = calendar.get(Calendar.MINUTE);
		note.second = calendar.get(Calendar.SECOND);
		return note;
	}

	public static User getUser(QQUser qqUser) {
		User user = new User();
		user.email = "" + qqUser.expiresIn;
		user.name = qqUser.qqNickName;
		user.password = qqUser.accessToken;
		return user;
	}

	public static User getUser(SinaUser sinaUser) {
		User user = new User();
		user.email = "" + sinaUser.expiresIn;
		user.name = sinaUser.name;
		user.password = sinaUser.accessToken;
		return user;
	}

	public static String getHour(int hour) {
		switch (hour) {
		case 1:
		case 2:
			return "丑时";
		case 3:
		case 4:
			return "寅时";
		case 5:
		case 6:
			return "卯时";
		case 7:
		case 8:
			return "辰时";
		case 9:
		case 10:
			return "巳时";
		case 11:
		case 12:
			return "午时";
		case 13:
		case 14:
			return "未时";
		case 15:
		case 16:
			return "申时";
		case 17:
		case 18:
			return "酉时";
		case 19:
		case 20:
			return "戍时";
		case 21:
		case 22:
			return "亥时";
		case 23:
		case 0:
			return "子时";
		default:
			break;
		}
		return "";
	}

	public static String getDay(int day) {
		return Lunar.getChinaDayString(day);
	}

	public static boolean isProcessInternetOperate(Context applicationContext) {
		return false;
	}

	public static void settingInputHide(EditText mOneET) {
		try {
			Class<EditText> cls = EditText.class;
			Method setSoftInputShownOnFocus;
			setSoftInputShownOnFocus = cls.getMethod(
					"setSoftInputShownOnFocus", boolean.class);
			setSoftInputShownOnFocus.setAccessible(true);
			setSoftInputShownOnFocus.invoke(mOneET, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getSpannableIndex(List<SpannableString> spannables,
			CharSequence spannable) {
		if (Utils.isCollectionEmpty(spannables)) {
			return 0;
		}
		for (int i = 0, size = spannables.size(); i < size; i++) {
			SpannableString spannableString = spannables.get(i);
			String str = SpannableString.valueOf(spannable).toString();
			if (str.equals(spannableString.toString())) {
				return i;
			}
		}
		return -1;
	}

	public static boolean getId(int id) {
		if (id == Constant.TWO || id == Constant.THREE || id == Constant.FOUR) {
			return true;
		} else {
			return false;
		}
	}

	public static void isChangeTheme(View view, Context context) {
		if (Utils.getIsDefaultTheme(context)) {
			view.setBackgroundColor(ColorConstant.ORIANGE);
		} else {
			view.setBackgroundColor(ColorConstant.BLACK);
		}
	}

	public static void sendEmail(String path, Activity activity) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + path));
		intent.putExtra(Intent.EXTRA_SUBJECT, "八行笺");
		intent.setType("image/*");
		intent.setType("message/rfc882");
		activity.startActivity(Intent.createChooser(intent,
				"Choose Email Client"));
	}
}
