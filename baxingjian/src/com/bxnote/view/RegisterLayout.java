package com.bxnote.view;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.OtherTitle;
import com.bxnote.subview.RegisterUserLayout;
import com.bxnote.subview.ShortcutRegisterLayout;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class RegisterLayout extends BaseRelativelayout {
	public LinearLayout mContainLayout;
	public ShortcutRegisterLayout mShortCutLayout;
	public RegisterUserLayout mNickNameRegisterLayout;
	public RegisterUserLayout mUserregisterLayout;
	public RegisterUserLayout mPassWordRegisterLayout;
	public ImageView mRegisterIV;
	public OtherTitle mTitle;
	// params
	private LinearLayout.LayoutParams mShortCutParams;
	private LinearLayout.LayoutParams mNickNameparams;
	private LinearLayout.LayoutParams mUserRegisterParams;
	private LinearLayout.LayoutParams mPassWordParams;
	private LinearLayout.LayoutParams mRegisterParams;
	private LayoutParams mContainParams;
	private LayoutParams mTitleParams;

	public RegisterLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		Utils.isChangeTheme(this, getContext());
		initParams();
		initView();
		mContainLayout.setOrientation(LinearLayout.VERTICAL);
		addView(mContainLayout, mContainParams);
		addView(mTitle, mTitleParams);
		mContainLayout.setGravity(Gravity.CENTER_VERTICAL);
		mContainLayout.setPadding(Utils.getWidth(ConfigLayout.MARGIN96, mWidth),
				Constant.DEFAULE_ZERO, ConfigLayout.MARGIN88,
				Constant.DEFAULE_ZERO);
		mContainLayout.addView(mShortCutLayout, mShortCutParams);
		mContainLayout.addView(mNickNameRegisterLayout, mNickNameparams);
		mContainLayout.addView(mUserregisterLayout, mUserRegisterParams);
		mContainLayout.addView(mPassWordRegisterLayout, mPassWordParams);
		mContainLayout.addView(mRegisterIV, mRegisterParams);

	}

	public RegisterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
		mContainLayout = new LinearLayout(getContext());
		mShortCutLayout = new ShortcutRegisterLayout(getContext(), mHeight,
				mWidth);
		mNickNameRegisterLayout = new RegisterUserLayout(getContext(), mHeight,
				mWidth);
		mNickNameRegisterLayout.setText("昵\b\b称:");
		mNickNameRegisterLayout.setHint("设置昵称");
		mUserregisterLayout = new RegisterUserLayout(getContext(), mHeight,
				mWidth);
		mUserregisterLayout.setText("邮\b\b箱:");
		mUserregisterLayout.setHint("输入邮箱");
		mPassWordRegisterLayout = new RegisterUserLayout(getContext(), mHeight,
				mWidth);
		mPassWordRegisterLayout.setText("密\b\b码:");
		mPassWordRegisterLayout.setHint("设置密码");
		mPassWordRegisterLayout.mInputET
				.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
		mRegisterIV = new ImageView(getContext());
		mRegisterIV.setImageResource(R.drawable.regiter_btn);
		mTitle = new OtherTitle(getContext(), mHeight, mWidth);
	}

	@Override
	protected void initParams() {
		mShortCutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mNickNameparams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mNickNameparams.topMargin = Utils.getWidth(ConfigLayout.MARGIN75,
				mWidth);
		mUserRegisterParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mUserRegisterParams.topMargin = Utils.getWidth(ConfigLayout.MARGIN75,
				mWidth);
		mPassWordParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPassWordParams.topMargin = Utils.getWidth(ConfigLayout.MARGIN75,
				mWidth);
		mRegisterParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, Utils.getWidth(
						ConfigLayout.REGITER_HEIGHT, mWidth));
		mRegisterParams.topMargin = Utils.getWidth(ConfigLayout.MARGIN75,
				mWidth);
		mContainParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

	}

}
