package com.bxnote.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bxnote.callback.BitmapCallback;

public class MapImageTask implements Task {
	private BitmapCallback mBitmapCallback;
	private String imageUrl;
	private Context mContext;
	private boolean isCancle;
	public MapImageTask(Context mContext, String imageUrl,
			BitmapCallback callback, Object object) {
		mBitmapCallback = callback;
		this.imageUrl = imageUrl;
		this.mContext = mContext;
		
	}

	@Override
	public void runTask() {
		if (isCancel()) {
			return;
		}
		String imagePath = BitmapLoader.getImageDir(mContext).concat(imageUrl);
		Bitmap bitmap = BitmapLoader.getImageFromLocal(imagePath, isCancel());
		if (!Utils.isObject(mBitmapCallback)) {
			mBitmapCallback.shareCallback(bitmap);
		}
	}

	@Override
	public void cacelTask() {
		isCancle = true;
	}

	@Override
	public boolean isCancel() {
		return isCancle;
	}

}
