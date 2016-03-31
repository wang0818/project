package com.bxnote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bxnote.subview.AsyncImageView;

@SuppressLint("HandlerLeak")
public class AsyncImageTask implements Task {
	private String url;
	// private ImageView mImage;
	private AsyncImageView mAsyncLayout;
	private Context mContext;
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

	public AsyncImageTask(Context context, AsyncImageView asyncLayout) {
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
		String imagePath = BitmapLoader.getImageDir(mContext).concat(url);
		Bitmap bitmap = BitmapLoader.getImageFromLocal(imagePath, isCancel());
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
