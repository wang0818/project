package com.bxnote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import com.bxnote.callback.BitmapCallback;

public class ShareThread extends Thread {
	private Context mContext;
	private boolean isMoments;
	private Consumer mConsumer;
	private String imageUrl;
	private String mTitle;
	@SuppressLint("HandlerLeak")
	Handler mShareHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				WXShareUtils wxmoments = new WXShareUtils(mContext);
				wxmoments.sendToImage(isMoments, (Bitmap) msg.obj, mTitle);
			}
		};
	};
	public ShareThread(Consumer consume, Context mContext,
			String imageUrl,boolean isWxMoment) {
		super();
		this.mConsumer = consume;
		this.imageUrl = imageUrl;
		this.mContext = mContext;
		this.isMoments = isWxMoment;
	}

	@Override
	public void run() {
		mConsumer.add(new MapImageTask(mContext, imageUrl,
				new BitmapCallback() {
					
					@Override
					public void shareCallback(Bitmap bitmap) {
//						int width = bitmap.getWidth();
//						int height = bitmap.getHeight();
//						Log.e("tag", "width "+width);
//						Log.e("tag", "height "+height );
//
//						Matrix matrix = new Matrix();
//						float scaleWidht = ((float) Utils.getWidth(
//								Constant.HAND_WRITE_WIDTH,
//								Constant.sScreenWdith) / width);
//						float scaleHeight = ((float) Utils.getHeight(
//								Constant.HAND_WRITE_HEIHT,
//								Constant.sScreenHeihgt) / height);
//						matrix.postScale(scaleWidht, scaleHeight);
//						Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0,
//								width, height, matrix, true);
						shareWxAndWxChat(bitmap);
					}
				},null));
	}
	public void shareWxAndWxChat(Bitmap bitmap) {
		Message message = mShareHandler.obtainMessage();
		if (bitmap != null) {
			message.obj = bitmap;
		} else {
			message.obj = null;
		}
		mShareHandler.sendMessage(message);

	}
}
