package com.bxnote.utils;

import com.bxnote.subview.AsyncImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;


public class AsynImgaeAssetsTask implements Task {
	private String url;
	// private ImageView mImage;
	private AsyncImageView mAsyncLayout;
	// 文件保存的路径
	public static final String SDCARD = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	private Context mContext;
	private boolean cancel = false;
	@SuppressLint("HandlerLeak")
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				Bitmap bitmap = (Bitmap) msg.obj;
				mAsyncLayout.setBitmap(bitmap);
			} else {

			}
		}
	};

	public AsynImgaeAssetsTask(Context context, AsyncImageView asyncLayout) {
		super();
		this.mAsyncLayout = asyncLayout;
		this.url = asyncLayout.getImageUrl();
		this.mContext = context;
	}

	@Override
	public void runTask() {
		if (isCancel()) {
			return;
		}
		Message msg = mHandle.obtainMessage();
		Bitmap bitmap = null;
		bitmap = BitmapLoader.getImageFromAssetsFile(Utils.getLetterPath() + "/"
				+ url, mContext);// 从当前目录查找
		if (bitmap != null) {
			msg.obj = bitmap;
			mHandle.sendMessage(msg);
			return;
		}
	}

	@Override
	public void cacelTask() {
		cancel = true;
	}

	@Override
	public boolean isCancel() {
		return cancel;
	}
}
