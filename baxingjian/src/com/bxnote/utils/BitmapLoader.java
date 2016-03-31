package com.bxnote.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class BitmapLoader {
	// 文件保存的路径
	public static final String SDCARD = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	// 文件保存的路径
	public static final String SDCARD_CACHE_IMG_PATH = SDCARD
			+ "/bahangjian/image/";
	public static final String SDCARD_IMAGE_DOWNLOAD = SDCARD
			+ "/bahangjian/saveimage/";

	/**
	 * 保存图片
	 */
	public static void saveImage(String imagePath, Bitmap bm) {
		FileOutputStream fos;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // 将图片转化为输出流
		byte[] buffer = baos.toByteArray(); // 转化为字节数组
		try {
			baos.close(); // 转化byte的后关闭流文件
		} catch (IOException e1) {
			e1.printStackTrace();
		}// 最后关闭输出流
		File f = new File(imagePath);
		if (f.exists()) {
			f.delete();
		}
		File parentFile = f.getParentFile().getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		File sub_parentFile = f.getParentFile();
		if (!sub_parentFile.exists()) {
			sub_parentFile.mkdirs();
		}
		try {
			f.createNewFile();
			fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveImage(String imagePath, InputStream inputStrem) {
		File file = new File(imagePath);
		if (file.exists()) {
			return;
		} else {
			File parentFile = file.getParentFile().getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			File sub_parentFile = file.getParentFile();
			if (!sub_parentFile.exists()) {
				sub_parentFile.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(imagePath);
			bos = new BufferedOutputStream(fos);
			StreamUtils.copy(inputStrem, bos);
			bos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 从SD卡加载图片
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getImageFromLocal(String imagePath, boolean isCancel) {
		if (imagePath != null && imagePath.length() > 0) {
			File file = new File(imagePath);
			if (file.exists()) {
				BitmapFactory.Options bfOptions = new BitmapFactory.Options();
				FileInputStream fs = null;
				try {
					fs = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Bitmap bitmap = null;
				if (fs != null)
					try {
						bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(),
								null, bfOptions);
						if (isCancel) {
							if (bitmap != null && !bitmap.isRecycled()) {
								bitmap.recycle();
								bitmap = null;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				return bitmap;
			}
		}
		return null;
	}

	public static boolean isLocalFile(String imagePath) {
		if (imagePath != null && imagePath.length() > 0) {
			File file = new File(imagePath);
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件的存储路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getImageDir(Context context) {
		if (isSDcardExist()) {
			return getImageDirBySDCard();
		} else {
			return getImageDirByData(context);
		}
	}

	public static String getCityImage(Context context) {
		if (isSDcardExist()) {
			return getImageDirCitySDCARD();
		} else {
			return getImageDirByData(context);
		}
	}

	public static String md5(String paramString) {
		String returnStr;
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			if (paramString != null) {
				localMessageDigest.update(paramString.getBytes());
			}
			returnStr = byteToHexString(localMessageDigest.digest());
			return returnStr;
		} catch (Exception e) {
			return paramString;
		}

	}

	/**
	 * 将指定byte数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * 判断sd卡是否存在
	 */
	public static boolean isSDcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取安装在用户手机上的sdcard的image
	 */
	public static String getImageDirBySDCard() {
		return SDCARD_CACHE_IMG_PATH;
	}

	public static String getImageDirCitySDCARD() {
		return SDCARD_IMAGE_DOWNLOAD;
	}

	// 获取安装在用户手机上的内存下的image目录
	public static String getImageDirByData(Context context) {
		return context.getFilesDir().getAbsolutePath() + "image";
	}

	public static Bitmap getImageFromAssetsFile(String fileName,
			Context mContext) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		AssetManager am = mContext.getResources().getAssets();
		Bitmap image = null;
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

}
