package com.bxnote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bxnote.subview.AsyncImageView;

@SuppressLint("HandlerLeak")
public class AsyncImageLocalTask implements Task {
	private String mLocalPath;
	private AsyncImageView mAsyncLayout;
	private boolean cancel = false;
	@SuppressLint("HandlerLeak")
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				Bitmap bitmap = (Bitmap) msg.obj;
				mAsyncLayout.setBitmap(bitmap);
			}
		}
	};

	public AsyncImageLocalTask(Context context, AsyncImageView asyncLayout,
			long destId) {
		super();
		this.mAsyncLayout = asyncLayout;
		this.mLocalPath = asyncLayout.getImageUrl();
	}

	@Override
	public void runTask() {
		if (isCancel()) {
			return;
		}
		Message msg = mHandle.obtainMessage();
		if (mLocalPath == null) {
			return;
		}
		// String imagePath =
		// NotEncryptImageLoader.getImageDir(mContext,mDestId).concat(
		// mLocalPath);
		Bitmap bitmap = BitmapLoader.getImageFromLocal(mLocalPath, isCancel());
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
