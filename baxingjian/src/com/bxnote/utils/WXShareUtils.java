package com.bxnote.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.bxnote.activity.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

public class WXShareUtils {
	private IWXAPI mIwApi;
	private Context mContext;
	private static final int THUMB_SIZE = 100;

	public WXShareUtils(Context mContext) {
		super();
		this.mContext = mContext;
		mIwApi = WXAPIFactory.createWXAPI(mContext, Constant.WXAPP_ID, true);
		mIwApi.registerApp(Constant.WXAPP_ID);
	}

	public void sendToImage(boolean isWXMoments, Bitmap bitmap, String title) {
		title = "八行笺";
		if (!mIwApi.isWXAppInstalled()) {
			Toast.makeText(mContext, "请安装微信", Toast.LENGTH_LONG).show();
			return;
		}
		Bitmap newBitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.icon80);
		WXImageObject wxImage = null;
		Bitmap thumbBmp = null;
		WXMediaMessage msg = new WXMediaMessage();
		wxImage = new WXImageObject(bitmap);
		msg.mediaObject = wxImage;
		thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE,
				true);
		msg.title = title;
		bitmap.recycle();
		msg.thumbData = bmpToByteArray(thumbBmp, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = isWXMoments ? SendMessageToWX.Req.WXSceneTimeline
				: SendMessageToWX.Req.WXSceneSession;
		mIwApi.sendReq(req);
		Toast.makeText(mContext, "分享", Toast.LENGTH_LONG).show();
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
