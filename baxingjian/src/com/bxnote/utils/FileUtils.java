package com.bxnote.utils;

import android.content.Context;
import android.util.Log;

public class FileUtils {
	public static void deleteFile(String imageName,Context context){
		Log.e("tag", ""+BitmapLoader.getImageDir(context)+"/"+imageName);
		
	}
}
