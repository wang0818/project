package com.bxnote.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static void showShortToast(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
}
