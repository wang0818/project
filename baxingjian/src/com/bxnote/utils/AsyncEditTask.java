package com.bxnote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bxnote.subview.AsynEditText;

@SuppressLint("HandlerLeak")
public class AsyncEditTask implements Task {
	private String url;
	// private ImageView mImage;
	private AsynEditText mAsyncLayout;
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

	public AsyncEditTask(Context context, AsynEditText asyncLayout) {
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
		url = StringUtils.subString(url);
		Message msg = mHandle.obtainMessage();
		String imagePath = BitmapLoader.getImageDir(mContext).concat(url);
		Bitmap bitmap = BitmapLoader.getImageFromLocal(imagePath, isCancel());
		if (bitmap != null) {
//			Matrix matrix = new Matrix();
//			float scaleWidht = ((float) Utils.getWidth(
//					Constant.HAND_WRITE_WIDTH, Constant.sScreenWdith) / bitmap
//					.getWidth());
//			float scaleHeight = ((float) Utils.getHeight(
//					Constant.HAND_WRITE_HEIHT, Constant.sScreenHeihgt) / bitmap
//					.getHeight());
//			matrix.postScale(scaleWidht, scaleHeight);
//			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0,
//					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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
