package com.bxnote.view;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.OtherTitle;
import com.bxnote.subview.RegisterUserLayout;
import com.bxnote.utils.Utils;

public class LoginLayout extends BaseRelativelayout {
	public OtherTitle mtitle;
	public LinearLayout mContainLayout;
	public RegisterUserLayout mUserLayout;
	public RegisterUserLayout mPasswordLayout;
	public ImageView mLoginIV;
	// params
	private LinearLayout.LayoutParams mUserParams;
	private LinearLayout.LayoutParams mPassParams;
	private LinearLayout.LayoutParams mLoginParams;
	private LayoutParams mContainParams;
	private LayoutParams mTitleParams;

	public LoginLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initParams();
		initView();
		addView(mContainLayout, mContainParams);
		addView(mtitle, mTitleParams);
		mContainLayout.setGravity(Gravity.CENTER_VERTICAL);
		mContainLayout.setOrientation(LinearLayout.VERTICAL);
		mContainLayout.addView(mUserLayout, mUserParams);
		mContainLayout.addView(mPasswordLayout, mPassParams);
		mContainLayout.addView(mLoginIV, mLoginParams);
		Utils.isChangeTheme(this, getContext());
	}

	@Override
	protected void initView() {
		mUserLayout = new RegisterUserLayout(getContext(), mWidth, mHeight);
		mUserLayout.setText("邮\b\b\b\b箱:");
		mUserLayout.setHint("输入邮箱");
		mPasswordLayout = new RegisterUserLayout(getContext(), mWidth, mHeight);
		mPasswordLayout.setText("密\b\b\b\b码:");
		mPasswordLayout.setHint("输入密码");
		mPasswordLayout.mInputET
				.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
		mLoginIV = new ImageView(getContext());
		mLoginIV.setImageResource(R.drawable.loginsubmit);
		mContainLayout = new LinearLayout(getContext());
		mtitle = new OtherTitle(getContext(), mHeight, mWidth);
	}

	@Override
	protected void initParams() {
		mUserParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mUserParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN110, mWidth);
		mUserParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN88, mWidth);
		mPassParams = new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPassParams.topMargin = Utils.getWidth(ConfigLayout.MARGIN75, mWidth);
		mPassParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN110, mWidth);
		mPassParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN88, mWidth);
		mLoginParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getWidth(ConfigLayout.REGITER_HEIGHT, mWidth));
		mLoginParams.topMargin = Utils.getWidth(ConfigLayout.MARGIN210, mWidth);
		mLoginParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN83, mWidth);
		mLoginParams.rightMargin = Utils
				.getWidth(ConfigLayout.MARGIN88, mWidth);
		mContainParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

}
