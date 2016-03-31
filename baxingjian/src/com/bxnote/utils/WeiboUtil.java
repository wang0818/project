package com.bxnote.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.bxnote.bean.AccessToken;
import com.bxnote.callback.TokenCallback;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

@SuppressLint("HandlerLeak")
public class WeiboUtil {
	private Context mContext;
	private WeiboAuth mWeibo;
	private Oauth2AccessToken mOauthAccessToken;
	private Activity mActivity;
	private AccessToken mAccessToken;
	/**
	 * SsoHandler 仅当sdk支持sso时有效，
	 */
	private SsoHandler mSsoHandler;
	private TokenCallback mTokenCallback;
	private IWeiboShareAPI mWeiboShareAPI = null;

	public WeiboUtil(Activity activity, Context mContext) {
		super();
		this.mContext = mContext;
		this.mActivity = activity;
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity,
				Constant.APP_KEY);
		// 如果未安装微博客户端，设置下载微博对应的回调
		if (!mWeiboShareAPI.isWeiboAppInstalled()) {
			mWeiboShareAPI
					.registerWeiboDownloadListener(new IWeiboDownloadListener() {
						@Override
						public void onCancel() {
							Toast.makeText(
									mActivity,
									"取消下载微博",
									Toast.LENGTH_SHORT).show();
						}
					});
		}

		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
		init();
	}

	public IWeiboShareAPI getWeiboShareApi() {
		return mWeiboShareAPI;
	}

	public void init() {
		mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
		mOauthAccessToken = mAccessToken.accessToken;
	}

	/**
	 * 
	 // 从 Bundle 中解析 Token mAccessToken =
	 * Oauth2AccessToken.parseAccessToken(values); if
	 * (mAccessToken.isSessionValid()) { // 显示 Token updateTokenView(false);
	 * 
	 * // 保存 Token 到 SharedPreferences
	 * AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);
	 * Toast.makeText(WBAuthActivity.this,
	 * R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show(); }
	 * else { // 以下几种情况，您会收到 Code： // 1. 当您未在平台上注册的应用程序的包名与签名时； // 2.
	 * 当您注册的应用程序包名与签名不正确时； // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。 String code
	 * = values.getString("code"); String message =
	 * getString(R.string.weibosdk_demo_toast_auth_failed); if
	 * (!TextUtils.isEmpty(code)) { message = message + "\nObtained the code: "
	 * + code; } Toast.makeText(WBAuthActivity.this, message,
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * @author Administrator
	 * 
	 */
	private class AuthDialogListener implements WeiboAuthListener {

		// @Override
		// public void onComplete(Bundle values) {
		// // 从 Bundle 中解析 Token
		// mOauthAccessToken = Oauth2AccessToken.parseAccessToken(values);
		// if (mOauthAccessToken.isSessionValid()) {
		// // 显示 Token
		// // updateTokenView(false);
		//
		// // 保存 Token 到 SharedPreferences
		// AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
		// Toast.makeText(mContext,
		// "成功", Toast.LENGTH_SHORT).show();
		// } else {
		// // 以下几种情况，您会收到 Code：
		// // 1. 当您未在平台上注册的应用程序的包名与签名时；
		// // 2. 当您注册的应用程序包名与签名不正确时；
		// // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
		// String code = values.getString("code");
		// String message = "失败";
		// if (!TextUtils.isEmpty(code)) {
		// message = message + "\nObtained the code: " + code;
		// }
		// Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
		// }
		// }
		//
		// @Override
		// public void onCancel() {
		// }
		//
		// @Override
		// public void onWeiboException(WeiboException e) {
		// Toast.makeText(mContext,
		// "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		// }

		@Override
		public void onCancel() {
		}

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires = values.getString("expires_in");
			String uid = values.getString("uid");
			String userName = values.getString("user_name");
			mOauthAccessToken = new Oauth2AccessToken(token, expires);
			if (mOauthAccessToken.isSessionValid()) {
				AccessTokenKeeper.keepAccessToken(mContext, mOauthAccessToken,
						uid);
				AccessToken at = new AccessToken();
				at.accessToken = mOauthAccessToken;
				at.uid = uid;
				mTokenCallback.callback(at);
			}
		}

		@Override
		public void onWeiboException(WeiboException arg0) {

		}
	}

	public boolean isSessionValid(Oauth2AccessToken oa) {
		if (oa.isSessionValid()) {
			return true;
		}
		return false;
	}

	/*
	 * 直接读取token对象
	 */
	public AccessToken getAccessToken() {
		mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
		return mAccessToken;
	}

	public void createToken(TokenCallback token) {
		mTokenCallback = token;
		deleteImpower();// 清楚用户的数据
		mWeibo = new WeiboAuth(mActivity, Constant.APP_KEY,
				Constant.REDIRECT_URL, Constant.SCOPE);
		 mSsoHandler = new SsoHandler(mActivity, mWeibo);
		 mSsoHandler.authorize(new AuthDialogListener());
//		mWeibo.anthorize(new AuthDialogListener());
	}

	public void deleteImpower() {
		AccessTokenKeeper.clear(mContext);
		UserKeep.clearSinauser(mContext);
	}

	public SsoHandler getSsoHandler() {
		return mSsoHandler;
	}

	public void shareContentAndPhoto(Bitmap bitmap, String content) {
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		weiboMessage.textObject = getTextObj(content);
		weiboMessage.imageObject = getImageObj(bitmap);
		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;
		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(request);
	}

	private TextObject getTextObj(String content) {
		TextObject textObject = new TextObject();
		textObject.text = content;
		return textObject;
	}

	private ImageObject getImageObj(Bitmap bitmap) {
		ImageObject imageObject = new ImageObject();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		imageObject.setImageObject(bitmapDrawable.getBitmap());
		return imageObject;
	}

}
