package com.bxnote.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.bxnote.activity.R;
import com.bxnote.adapter.StartViewPager;
import com.bxnote.bean.Start;
import com.bxnote.bean.User;
import com.bxnote.callback.AnimationCallback;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AnimationUtils;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.UserKeep;
import com.bxnote.utils.Utils;
import com.bxnote.view.BeginView;

public class StartActivity extends BaseActivity implements
		OnPageChangeListener, AnimationCallback {
	private BeginView mBeginView;
	private ViewPager mViewPager;
	private ImageView mAppIV;
	private StartViewPager mViewPagerAdapter;
	private AlphaAnimation mAlpahAnimation;
	private int mCurrentSelect;
	private List<Start> start;
	private User mUser;
	private boolean isScroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		mUser = UserKeep.getUserData(this);
		initView();
		initData();
		if (Utils.getIsFirst(this)) {
			mAppIV.setVisibility(View.GONE);
			mViewPager.setVisibility(View.VISIBLE);
			Utils.setIsFirst(this, false);
		} else {
			mAppIV.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.INVISIBLE);
			mAlpahAnimation = new AlphaAnimation(
					com.bxnote.utils.AnimationUtils.DEFAULE_ONE,
					AnimationUtils.DEFAULE_ZERO);
			mAlpahAnimation.setDuration(AnimationUtils.DEFAULE_TIME);
			mAlpahAnimation.setAnimationListener(mAppAnimationListener);
			mAlpahAnimation.setFillAfter(true);
			mAppIV.setAnimation(mAlpahAnimation);
		}
	}

	@Override
	public void initView() {
		mBeginView = new BeginView(this, mHeight, mWidth);
		mViewPager = mBeginView.mViewPager;
		mAppIV = mBeginView.mAppIV;
		setContentView(mBeginView);
	}

	@Override
	public void initData() {
		mViewPagerAdapter = new StartViewPager(this, initStartData(), mHeight,
				mWidth, this);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private List<Start> initStartData() {
		start = new ArrayList<Start>();
		Start start1 = new Start();
		start1.isExecAnimation = false;
		start1.start = R.drawable.text1;
		start1.bottom = R.drawable.fivedot;
		Start start2 = new Start();
		start2.isExecAnimation = true;
		start2.bottom = R.drawable.fivedot2;
		start2.content = R.drawable.page2;
		start2.title = R.drawable.bahangjian;
		start2.text = R.drawable.text2;
		Start start3 = new Start();
		start3.isExecAnimation = true;
		start3.bottom = R.drawable.fivedot3;
		start3.content = R.drawable.page3;
		start3.title = R.drawable.bahangjian;
		start3.text = R.drawable.text3;
		Start start4 = new Start();
		start4.isExecAnimation = true;
		start4.bottom = R.drawable.fivedot4;
		start4.content = R.drawable.page4;
		start4.title = R.drawable.bahangjian;
		start4.text = R.drawable.text4;
		Start start5 = new Start();
		start5.isExecAnimation = true;
		start5.bottom = R.drawable.fivedot5;
		start5.content = R.drawable.page5;
		start5.title = R.drawable.bahangjian;
		start5.text = R.drawable.text5;
		start.add(start1);
		start.add(start2);
		start.add(start3);
		start.add(start4);
		start.add(start5);
		return start;
	}

	@Override
	public void successData() {
	}

	AnimationListener mAppAnimationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			redirectTo();
		}
	};

	private void redirectTo() {
		if ("".equals(mUser.email)) {
			ActivitySkip activitySkip = new ActivitySkip(this,
					LoginAndRegisterActivity.class, StartActivity.this);
			activitySkip.startActivity();
		} else {
			ActivitySkip activitySkip = new ActivitySkip(this,
					HomeActivity.class, StartActivity.this);
			activitySkip.startActivity(mUser.email);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		case 0:
			if (mCurrentSelect == start.size() - 1) {
				if (isScroll) {
					redirectTo();
				} else {
					isScroll = true;
				}
			} else {
				isScroll = false;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.e("tag", "onPageScrolled -- > " + mCurrentSelect);

	}

	@Override
	public void onPageSelected(int arg0) {
		mCurrentSelect = arg0;
		Log.e("tag", "mCurrentSelect -- > " + mCurrentSelect);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!Utils.isObject(mBeginView)) {
			mBeginView.removeAllViews();
		}
	}

	@Override
	public void finish(int current) {

	}
}
