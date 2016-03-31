package com.bxnote.utils;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bxnote.callback.BaseUIListener;
import com.bxnote.callback.QQCallback;
import com.bxnote.callback.QQUserCallback;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQWeiboUtils implements IUiListener {
	private Tencent mTencent;
	private Activity mActivity;
	private Context mContext;
	private QQCallback mQqCallback;
	private QQUserCallback mQqUserCallback;
	private QQAuth mQQAuth;
    private QQShare mQQShare = null;
    private UserInfo mInfo;
	public QQWeiboUtils(Context context, Activity activity,
			QQCallback qqCallback, QQUserCallback qqUserCallback) {
		mQQAuth = QQAuth.createInstance(Constant.APP_ID, context);
		mTencent = Tencent.createInstance(Constant.APP_ID, activity);
		  mQQShare = new QQShare(context, mQQAuth.getQQToken());
		mActivity = activity;
		mContext = context;
		mQqCallback = qqCallback;
		mQqUserCallback = qqUserCallback;
	}

	public void login() {
		if (!Utils.isObject(mQQAuth)) {
			if (!mQQAuth.isSessionValid()) {
				mTencent.loginWithOEM(mActivity, "all", this,"10000144","10000144","xxxx");
			}else{
				mQQAuth.logout(mContext);
				mTencent.loginWithOEM(mActivity, "all", this,"10000144","10000144","xxxx");
			}
//			if (!mTencent.isSessionValid()) {
//				mTencent.login(mActivity, Constant.SCOPE, this);
//			}
		}
	}

	public void loginOut() {
		if (!Utils.isObject(mTencent)) {
			if (mTencent.isSessionValid()) {
				mTencent.logout(mActivity);
			}
		}
		UserKeep.clearQQuser(mContext);
	}
	public Tencent getTencent() {
		return mTencent;
	}
	public Intent getIntentSharePhotoAndText(String photoUri,
			String share_text, String imageName) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		File file = new File(photoUri + imageName);
		shareIntent.putExtra(Intent.EXTRA_TEXT, share_text);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.setType("image/png");
		return shareIntent;
	}
	public void qqShare(int shareType,int extarFlag,String imageUrl,String appName){
		Log.e("tag", "imageURl"+imageUrl);
		 final Bundle params = new Bundle();
	     params.putString(shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE ? QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL 
	     		: QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
	     params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
	     params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
	     params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, extarFlag);
	     doShareTo(params,shareType);
	     return;
	}
	 private void doShareTo(final Bundle params,final int shareType) {
	        new Thread(new Runnable() {
	            
	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	                mQQShare.shareToQQ(mActivity, params, new IUiListener() {

	                    @Override
	                    public void onCancel() {
	                    	if(shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE){
	                    	}
	                    }

	                    @Override
	                    public void onComplete(Object response) {
	                        // TODO Auto-generated method stub
	                    	Log.e("tag", "response"+response);
//	                        Util.toastMessage(activity, "onComplete: " + response.toString());
	                    }

	                    @Override
	                    public void onError(UiError e) {
	                        // TODO Auto-generated method stub
	                    }

	                });
	            }
	        }).start();
	    }

	@Override
	public void onComplete(Object arg0) {
		if (!Utils.isObject(mQqCallback)) {
			mQqCallback.shareCallback((JSONObject)arg0);
		}
	}

	@Override
	public void onCancel() {
		
	}

	@Override
	public void onError(UiError arg0) {
		
	}

	public void onClickUserInfo() {
        mInfo = new UserInfo(mActivity, mQQAuth.getQQToken());
        if (ready(mActivity)) {
        	mInfo.getUserInfo(new BaseUIListener(){
        		@Override
        		public void onComplete(Object response) {
        			mQqUserCallback.doComplete((JSONObject)response, 0);
        		}
        	});
        }
	}
	public  boolean ready(Context context) {
		if (mQQAuth == null) {
			return false;
		}
		boolean ready = mQQAuth.isSessionValid()
				&& mQQAuth.getQQToken().getOpenId() != null;
		if (!ready)
			Toast.makeText(context, "login and get openId first, please!",
					Toast.LENGTH_SHORT).show();
		return ready;
	}
}
