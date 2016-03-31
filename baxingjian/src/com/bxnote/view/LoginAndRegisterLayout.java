package com.bxnote.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseAbsView;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.callback.DropCallback;
import com.bxnote.callback.RegisterAndLoginCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

@SuppressWarnings("deprecation")
public class LoginAndRegisterLayout extends BaseRelativelayout {
	public BaseAbsView mAbsoluteLayout;
	public ImageView mLoginIV;
	public ImageView mRegisterIV;
	public LinearLayout mRightArrowLayout;
	public ImageView mRightArrowIV;
	public ImageView mDrawIV;
	public LinearLayout mLeftArrowLayout;
	public ImageView mLeftArrowIV;
	public ImageView mAnimationIV;
	// params
	public LayoutParams mAbsLouteParams;
	public android.widget.AbsoluteLayout.LayoutParams mLoginParams;
	public android.widget.AbsoluteLayout.LayoutParams mRegisterParams;
	public android.widget.LinearLayout.LayoutParams mRightArrowParams;
	public android.widget.AbsoluteLayout.LayoutParams mDrawParams;
	public android.widget.AbsoluteLayout.LayoutParams mLeftArrowParams;
	public android.widget.LinearLayout.LayoutParams mLeftIVParams;
	public android.widget.AbsoluteLayout.LayoutParams mRightArrowLayoutParams;
	public LayoutParams mAnimationParams;

	public LoginAndRegisterLayout(Context context, int mHeight, int mWidth,
			DropCallback dropCallback, RegisterAndLoginCallback registerCallback) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		initData();
		Utils.isChangeTheme(this,getContext());
		mAbsoluteLayout.setDropCallback(dropCallback);
		mAbsoluteLayout.setRegisterAndLoginCallback(registerCallback);
		addView(mAbsoluteLayout, mAbsLouteParams);
		addLeftArrow();
		addRightArrow();
		addAbsView();
	}

	private void addRightArrow() {
		mRightArrowLayout.setGravity(Gravity.CENTER_VERTICAL);
		mRightArrowLayout.addView(mRightArrowIV, mRightArrowParams);
	}

	private void addLeftArrow() {
		mLeftArrowLayout.setGravity(Gravity.CENTER_VERTICAL);
		mLeftArrowLayout.addView(mLeftArrowIV, mLeftIVParams);
	}

	private void addAbsView() {
		mAbsoluteLayout.addView(mAnimationIV, mAnimationParams);
		mAbsoluteLayout.addView(mDrawIV, mDrawParams);
		mAbsoluteLayout.addView(mLoginIV, mLoginParams);
		mAbsoluteLayout.addView(mRegisterIV, mRegisterParams);
		mAbsoluteLayout.addView(mLeftArrowLayout, mLeftArrowParams);
		mAbsoluteLayout.addView(mRightArrowLayout, mRightArrowLayoutParams);
	}

	public void initData() {
		mLoginIV.setImageResource(R.drawable.login);
		mRegisterIV.setImageResource(R.drawable.register);
		mLeftArrowIV.setImageResource(R.drawable.left_arrow);
		mRightArrowIV.setImageResource(R.drawable.right_arrow);
		mDrawIV.setBackgroundResource(R.drawable.gray_btn);
		mAnimationIV.setBackgroundResource(R.drawable.bg_red);
		mAnimationIV.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void initView() {
		mAnimationIV = new ImageView(getContext());
		mAbsoluteLayout = new BaseAbsView(getContext(), mHeight, mWidth);
		mLoginIV = new ImageView(getContext());
		mRegisterIV = new ImageView(getContext());
		mRightArrowIV = new ImageView(getContext());
		mDrawIV = new ImageView(getContext());
		mLeftArrowIV = new ImageView(getContext());
		mLeftArrowLayout = new LinearLayout(getContext());
		mRightArrowLayout = new LinearLayout(getContext());
	}

	@Override
	protected void initParams() {
		mAnimationParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mAbsLouteParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mLoginParams = new android.widget.AbsoluteLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_LOGIN_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight),
				Utils.getWidth(ConfigLayout.MARGIN92, mWidth), Utils.getHeight(
						ConfigLayout.MARGIN565, mHeight));
		mRegisterParams = new android.widget.AbsoluteLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_LOGIN_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight),
				Utils.getWidth(ConfigLayout.MARGIN524, mWidth),
				Utils.getHeight(ConfigLayout.MARGIN565, mHeight));
		mLeftArrowParams = new android.widget.AbsoluteLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_ARROW_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight),
				Utils.getWidth(ConfigLayout.MARGIN214, mWidth),
				Utils.getHeight(ConfigLayout.MARGIN689, mHeight));
		mLeftIVParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_ARROW_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_ARROW_HEIGHT, mHeight));
		mRightArrowParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_ARROW_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_ARROW_HEIGHT, mHeight));
		mRightArrowLayoutParams = new android.widget.AbsoluteLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_ARROW_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight),
				Utils.getWidth(ConfigLayout.MARGIN432, mWidth),
				Utils.getHeight(ConfigLayout.MARGIN565, mHeight));
		mDrawParams = new android.widget.AbsoluteLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.LOGIN_AND_REGISTER_MIDDLE_WIDTH,
						mWidth),
				Utils.getHeight(ConfigLayout.LOGIN_AND_REGISTER_MIDDLE_HEIGHT,
						mHeight),
				Utils.getWidth(ConfigLayout.MARGIN309, mWidth),
				Constant.DEFAULE_ZERO);
	}
}
