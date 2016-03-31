package com.bxnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.UserKeep;
import com.bxnote.utils.Utils;
import com.bxnote.view.LoginLayout;

public class LoginActivity extends BaseActivity {
	private LoginLayout mLoginLayout;
	private EditText mUserName;
	private EditText mUserPassword;
	private ImageView mLoginIV;
	private String mPassword;
	private String mEmail;
	private CreateUserDateDao mCreateUserDao;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		Intent intent = getIntent();
		Bundle bundle =intent.getExtras();
		mCreateUserDao = new CreateUserDateDao(this);
		if (!Utils.isObject(bundle)) {
			mPassword = BundleUtils.getPassword(bundle);
			mEmail = BundleUtils.getEmail(bundle);
		}
		initView();
	}

	@Override
	public void initView() {
		mLoginLayout = new LoginLayout(this, mHeight, mWidth);
		mUserName = mLoginLayout.mUserLayout.mInputET;
		mUserPassword = mLoginLayout.mPasswordLayout.mInputET;
		if (!StringUtils.isEmpty(mEmail)) {
			mUserName.setText(mEmail);
		}
		if (!StringUtils.isEmpty(mPassword)) {
			mUserPassword.setText(mPassword);
		}
		mLoginIV = mLoginLayout.mLoginIV;
		mLoginIV.setOnClickListener(mLoginClickListener);
		setContentView(mLoginLayout);
	}

	@Override
	public void initData() {

	};

	OnClickListener mLoginClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			String userName = mUserName.getText().toString();
			String passWord = mUserPassword.getText().toString();
			if (Utils.isUserName(getApplicationContext(), userName)
					&& Utils.isUserName(getApplicationContext(), passWord)) {
				if (mCreateUserDao.isHaveUser(userName,passWord)) {
					UserKeep.keepUserData(userName, LoginActivity.this);//保存用户的数据
					ActivitySkip homeActivity = new ActivitySkip(LoginActivity.this, HomeActivity.class, LoginActivity.this);
					homeActivity.startActivity(userName);
				}else{
					ToastUtils.showShortToast(getApplicationContext(), "用户名或密码输入错误");
				}
			}
		}
	};
}
