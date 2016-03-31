package com.bxnote.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.bxnote.activity.R;
import com.bxnote.bean.AccessToken;
import com.bxnote.bean.Note;
import com.bxnote.bean.SinaUser;
import com.bxnote.bean.User;
import com.bxnote.callback.BitmapCallback;
import com.bxnote.callback.TokenCallback;
import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.subview.Title;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.MapImageTask;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.UserKeep;
import com.bxnote.utils.Utils;
import com.bxnote.utils.WeiboUtil;
import com.bxnote.view.SinaShareLayout;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;

public class SinaShareActivity extends BaseActivity implements TokenCallback,
		IWeiboHandler.Response {
	private SinaShareLayout mSinaShareLayout;
	private Note mNote;
	private String mEmail;
	private User mUser;
	private CreateUserDateDao mCreateUserDao;
	private Title mTitle;
	private ImageView mBackIV;
	private ImageView mShareIV;
	private WeiboUtil mWeiboUtils;
	private Oauth2AccessToken mAccessToken;
	private String content;
	private SendPhotoAsyncImageTask mSendPhoto;
	private Consumer mConsmer;
	private Handler mShareHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constant.SHARELOADING:
				shareWeibo((AccessToken) msg.obj);
				break;
			case Constant.SHAREWEIBO:
				content = mSinaShareLayout.mContentET.getText().toString();
				mAccessToken = mWeiboUtils.getAccessToken().accessToken;// 获取token
				mSendPhoto = new SendPhotoAsyncImageTask(content, true);
				mSendPhoto.execute("");// 分享微博的功能
				break;
			case Constant.SHAREWEIBOFAIL:
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (!Utils.isObject(bundle)) {
			mNote = BundleUtils.getNote(bundle);
			mEmail = BundleUtils.getEmail(bundle);
		}
		mConsmer = new Consumer();
		mCreateUserDao = new CreateUserDateDao(this);
		mUser = mCreateUserDao.queryUser(mEmail);
		mWeiboUtils = new WeiboUtil(this, this);
		initView();
		initLisener();
		if (!Utils.isObject(mUser)) {
			mTitle.mContentTV.setText(mUser.name);
		}
		successData();
	}

	private void initLisener() {
		mBackIV.setOnClickListener(mBackClickListener);
		mShareIV.setOnClickListener(mUploadClickListener);
	}

	@Override
	public void initData() {
	}

	@Override
	public void initView() {
		mSinaShareLayout = new SinaShareLayout(this, mHeight, mWidth, mNote,
				mConsmer);
		mTitle = mSinaShareLayout.mTitle;
		mBackIV = mTitle.mMenuIV;
		mShareIV = mTitle.mEditIV;
		setContentView(mSinaShareLayout);
	}

	@Override
	public void successData() {
		if (!Utils.isObject(mUser)) {
			mTitle.mContentTV.setText(mUser.name);
		}
	}

	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	private OnClickListener mUploadClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ToastUtils.showShortToast(getApplicationContext(), "分享微博");
			if (mWeiboUtils.getAccessToken().accessToken != null) {
				mAccessToken = mWeiboUtils.getAccessToken().accessToken;
				/*
				 * 如果微博分享没有过期的话，直接分享 否则登陆分享
				 */
				if (mAccessToken.isSessionValid()) {// 如果token没有过期
					final String content = mSinaShareLayout.mContentET
							.getText().toString();
					mSendPhoto = new SendPhotoAsyncImageTask(content, true);
					mSendPhoto.execute("");
				} else {
					mWeiboUtils.createToken(SinaShareActivity.this);// 创建token
				}
			} else {
				mWeiboUtils.createToken(SinaShareActivity.this);// 创建token
			}
		}
	};

	public void shareWeibo() {
		try {
			// 检查微博客户端环境是否正常，如果未安装微博，弹出对话框询问用户下载微博客户端
			if (mWeiboUtils.getWeiboShareApi().checkEnvironment(true)) {
				content = mSinaShareLayout.mContentET.getText().toString();
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_launcher);
				mWeiboUtils.shareContentAndPhoto(bitmap, content);
			}
		} catch (WeiboShareException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callback(AccessToken accessToken) {
		Message message = mShareHandler.obtainMessage();
		message.obj = accessToken;
		message.what = Constant.SHARELOADING;
		mShareHandler.sendMessage(message);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			mWeiboUtils.getSsoHandler().authorizeCallBack(requestCode,
					resultCode, data);
		}

	}

	@SuppressLint("NewApi")
	private class SendPhotoAsyncImageTask extends
			AsyncTask<String, Integer, Boolean> {
		private String content;
		boolean isSuccess = false;

		public SendPhotoAsyncImageTask(String content, boolean isShowLoading) {
			super();
			this.content = content;
		}

		/*
		 * 执行后台的任务
		 */
		@Override
		protected Boolean doInBackground(String... params) {
			new MapImageTask(SinaShareActivity.this, mNote.imageLocation,
					new BitmapCallback() {

						@Override
						public void shareCallback(Bitmap bitmap) {
							shareImage(bitmap, content);

						}
					}, null).runTask();
			return isSuccess;
		}

		/*
		 * 显示ui
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/*
		 * 任务执行完成调用
		 */
		@Override
		protected void onPostExecute(Boolean isSuccess) {
			ToastUtils.showShortToast(getApplicationContext(), "分享成功");
			finish();
			super.onPostExecute(isSuccess);
		}

	}

	public void shareWeibo(final AccessToken accessToken) {
		mAccessToken = accessToken.accessToken;
		new SinaUserThread(accessToken).run();
	}

	public void shareImage(Bitmap bitmap, String content) {
		// 获取图片的对象
		if (StringUtils.isEmpty(content)) {
			content = "八行笺";
		}
		StatusesAPI statusesApi = new StatusesAPI(mAccessToken);
		String respon = statusesApi.uploadSync(content, bitmap, "0.0", "0.0");
		Log.e("tag", "respon" + respon);
	}

	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this, "分享失败 " + "Error Message: " + baseResp.errMsg,
					Toast.LENGTH_LONG).show();
			break;
		}

	}

	private class SinaUserThread extends Thread {
		public AccessToken mAccessToken;

		public SinaUserThread(AccessToken mAccessToken) {
			super();
			this.mAccessToken = mAccessToken;
		}

		@Override
		public void run() {
			UsersAPI userAPi = new UsersAPI(mAccessToken.accessToken);
			userAPi.show(Long.parseLong(mAccessToken.uid),
					new RequestListener() {
						@Override
						public void onComplete(String arg0) {
							try {
								JSONObject jsonObject = new JSONObject(arg0);
								String name = jsonObject.getString("name");
								long id = jsonObject.getLong("id");
								String gender = jsonObject.getString("gender");
								SinaUser sinaUser = new SinaUser();
								sinaUser.name = name;
								sinaUser.accessToken = mAccessToken.accessToken
										.toString();
								sinaUser.expiresIn = id;
								sinaUser.gender = gender;
								if (sinaUser.expiresIn != 0) {
									UserKeep.keepUser(SinaShareActivity.this,
											sinaUser);
									mCreateUserDao.insertTable(Utils
											.getUser(sinaUser));
									Message message = mShareHandler
											.obtainMessage();
									message.obj = sinaUser;
									message.what = Constant.SHAREWEIBO;
									mShareHandler.sendMessage(message);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onWeiboException(WeiboException arg0) {

						}
					});
		}
	}
}
