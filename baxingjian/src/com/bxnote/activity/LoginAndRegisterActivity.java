package com.bxnote.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bxnote.baseview.BaseAbsView;
import com.bxnote.callback.DropCallback;
import com.bxnote.callback.RegisterAndLoginCallback;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;
import com.bxnote.view.LoginAndRegisterLayout;

public class LoginAndRegisterActivity extends BaseActivity implements
		DropCallback, RegisterAndLoginCallback {
	private LoginAndRegisterLayout mLoginAndRegisterLayout;
	private int clickPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//退出以前的activity
		AppManager.getAppManager().finishAllActivity();
		AppManager.getAppManager().addActivity(this);
		initView();
	}

	@Override
	public void initView() {
		mLoginAndRegisterLayout = new LoginAndRegisterLayout(this, mHeight,
				mWidth, this, this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setContentView(mLoginAndRegisterLayout, layoutParams);
	}

	@Override
	public void initData() {

	}

	@Override
	public void dropPosition(int position, View view) {
		BaseAbsView mAbsolute = mLoginAndRegisterLayout.mAbsoluteLayout;
		if (mAbsolute.getChildAt(position) instanceof ImageView) {
			ImageView addCity = (ImageView) mAbsolute.getChildAt(position);
			if (addCity != null) {
				mAbsolute.setOnLongClick(view, position);
			}
		}
	}

	@Override
	public void callBack(int position) {
		clickPosition = position;
		ImageView animationIV = mLoginAndRegisterLayout.mAnimationIV;
		hideOther();
		execAnimation(animationIV);
	}

	private void hideOther() {
		if (clickPosition == Constant.DEFAULT_TWO) {
			mLoginAndRegisterLayout.mRegisterIV.setVisibility(View.INVISIBLE);
		} else {
			mLoginAndRegisterLayout.mLoginIV.setVisibility(View.INVISIBLE);
		}
		mLoginAndRegisterLayout.mDrawIV.setVisibility(View.INVISIBLE);
		mLoginAndRegisterLayout.mLeftArrowLayout.setVisibility(View.INVISIBLE);
		mLoginAndRegisterLayout.mRightArrowLayout.setVisibility(View.INVISIBLE);
		mLoginAndRegisterLayout.mDrawIV.setVisibility(View.INVISIBLE);
	}

	private void execAnimation(final ImageView v) {
		v.setVisibility(View.VISIBLE);
		ScaleAnimation scale = new ScaleAnimation(0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		scale.setAnimationListener(mAnimationListener);
		v.startAnimation(scale);
	}

	AnimationListener mAnimationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			redectTo();
		}

	};

	private void redectTo() {
		if (clickPosition == Constant.DEFAULT_THREE) {//注册
			ActivitySkip registerActivity = new ActivitySkip(this, RegisterActivity.class, this);
			registerActivity.startActivity();
			return ;
		}
		if (clickPosition == Constant.DEFAULT_TWO) {//登录
			ActivitySkip loginActivity = new ActivitySkip(this,
					LoginActivity.class, this);
			loginActivity.startActivity();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		if (!Utils.isObject(mLoginAndRegisterLayout)) {
			mLoginAndRegisterLayout.removeAllViews();
			mLoginAndRegisterLayout = null;
		}
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		if (Utils.isObject(mLoginAndRegisterLayout)) {
			initView();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().AppExit(getApplicationContext());
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
