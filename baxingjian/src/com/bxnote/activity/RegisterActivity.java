package com.bxnote.activity;

import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import com.bxnote.bean.AccessToken;
import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.bean.User;
import com.bxnote.callback.QQCallback;
import com.bxnote.callback.QQUserCallback;
import com.bxnote.callback.TokenCallback;
import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.Constant;
import com.bxnote.utils.QQWeiboUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.UserKeep;
import com.bxnote.utils.Utils;
import com.bxnote.utils.WeiboUtil;
import com.bxnote.view.RegisterLayout;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;

public class RegisterActivity extends BaseActivity implements OnClickListener,
		TokenCallback, QQCallback, QQUserCallback {
	private EditText mName, mEmail, mPassword;
	private ImageView mRegister;
	private ImageView mQQRegsiterIV;
	private ImageView mSinaRegisterIV;
	private String name, email, password;
	private RegisterLayout mRegisterLayout;
	private CreateUserDateDao mCreateUserDao;
	private WeiboUtil mWeiboUtils;
	private QQWeiboUtils mQQUtils;
	private JSONObject mTokenJson;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.QQ_USER_LOGIN:
				if (msg.obj instanceof QQUser) {
					ActivitySkip activity = new ActivitySkip(
							RegisterActivity.this, HomeActivity.class,
							RegisterActivity.this);
					activity.startActivity(((QQUser) msg.obj).expiresIn + "");
				}
				break;
			case Constant.SIAN_USER_LOGIN:
				if (msg.obj instanceof SinaUser) {
					ActivitySkip activity = new ActivitySkip(
							RegisterActivity.this, HomeActivity.class,
							RegisterActivity.this);
					activity.startActivity(((SinaUser) msg.obj).expiresIn + "");
				}
			case Constant.SINA_ID_SUCCCESS:
				break;
			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		mCreateUserDao = new CreateUserDateDao(this);
		mWeiboUtils = new WeiboUtil(this, this);
		mQQUtils = new QQWeiboUtils(getApplicationContext(), this, this, this);
		initView();
	}

	@Override
	public void initView() {
		mRegisterLayout = new RegisterLayout(this, Constant.sScreenHeihgt,
				Constant.sScreenWdith);
		mName = mRegisterLayout.mNickNameRegisterLayout.mInputET;
		mEmail = mRegisterLayout.mUserregisterLayout.mInputET;
		mPassword = mRegisterLayout.mPassWordRegisterLayout.mInputET;
		mRegister = mRegisterLayout.mRegisterIV;
		mSinaRegisterIV = mRegisterLayout.mShortCutLayout.mSinaIV;
		mSinaRegisterIV.setOnClickListener(mSinaListener);
		mQQRegsiterIV = mRegisterLayout.mShortCutLayout.mWxIV;
		mQQRegsiterIV.setOnClickListener(mQQListener);
		mRegister.setOnClickListener(this);
		setContentView(mRegisterLayout);
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View arg0) {
		password = mPassword.getText().toString();
		name = mName.getText().toString();
		email = mEmail.getText().toString();
		if (Utils.isUserEmail(this, email) && Utils.isUserName(this, name)
				&& Utils.isPassword(this, password)) {
			if (mCreateUserDao.isHaveUser(email)) {
				ToastUtils.showShortToast(this, "邮箱已经被注册");
				return;
			}
			User user = new User();
			user.name = name;
			user.email = email;
			user.password = password;
			mCreateUserDao.insertTable(user);
			ActivitySkip loginActivity = new ActivitySkip(this,
					LoginActivity.class, this);
			loginActivity.startActivity(email, password);
		}
	}

	private OnClickListener mQQListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mQQUtils.login();
		}
	};
	private OnClickListener mSinaListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mWeiboUtils.createToken(RegisterActivity.this);
		}
	};

	@Override
	public void callback(final AccessToken accessToken) {
	
		if (!Utils.isObject(accessToken)) {
//			Message msg = mHandler.obtainMessage();
//			msg.obj = accessToken;
//			msg.what = Constant.SINA_ID_SUCCCESS;
			new SinaUserThread(accessToken).run();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (mWeiboUtils.getSsoHandler() != null) {
			  mWeiboUtils.getSsoHandler().authorizeCallBack(requestCode, resultCode, data);
	        }
	}

	@Override
	public void shareCallback(JSONObject jsonObject) {
		mTokenJson = jsonObject;
		mQQUtils.onClickUserInfo();
	}

	@Override
	public void doComplete(JSONObject response, Object state) {
		Log.e("tag", ""+response);
		UserKeep.clearQQuser(this);
		QQUser qqUser = new QQUser();
		try {
			qqUser.expiresIn = mTokenJson.getLong("expires_in");
			qqUser.accessToken = mTokenJson.getString("access_token");
			qqUser.gender = response.getString("gender");
			qqUser.qqNickName = response.getString("nickname");
			if (qqUser.expiresIn != 0) {
				UserKeep.keepQqUser(this, qqUser);
				UserKeep.keepUserData("" + qqUser.expiresIn,
						RegisterActivity.this);
				mCreateUserDao.insertTable(Utils.getUser(qqUser));
				Message message = mHandler.obtainMessage();
				message.obj = qqUser;
				message.what = Constant.QQ_USER_LOGIN;
				mHandler.sendMessage(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
									UserKeep.keepUser(RegisterActivity.this,
											sinaUser);
									UserKeep.keepUserData(sinaUser.expiresIn
											+ "", RegisterActivity.this);
									mCreateUserDao.insertTable(Utils
											.getUser(sinaUser));
									Message message = mHandler.obtainMessage();
									message.obj = sinaUser;
									message.what = Constant.SIAN_USER_LOGIN;
									mHandler.sendMessage(message);
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
