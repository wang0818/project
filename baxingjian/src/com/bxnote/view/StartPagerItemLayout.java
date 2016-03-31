package com.bxnote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.bean.Start;
import com.bxnote.callback.AnimationCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class StartPagerItemLayout extends BaseRelativelayout {
	public Start mStart;
	public LinearLayout mStartPageLayout;
	public LinearLayout mOterPageLayout;
	public ImageView mTitleIV;
	public ImageView mContentIV;
	public ImageView mTextIV;
	public LinearLayout mBottomLayout;
	public ImageView mBottomDotIV;
	// layoutparams
	public LayoutParams mStartPageParams;
	public LayoutParams mOterPageParams;
	public android.widget.LinearLayout.LayoutParams mTitleParams;
	public android.widget.LinearLayout.LayoutParams mContentParams;
	public android.widget.LinearLayout.LayoutParams mTextParams;
	public LayoutParams mBottomParams;
	private android.widget.LinearLayout.LayoutParams mBottomDotParams;
	public boolean isFirst;
	public boolean isExecAnimation;
	public AnimationCallback mAnimationCallback;
	public int position;

	public StartPagerItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StartPagerItemLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
	}

	public StartPagerItemLayout(Context context) {
		super(context);
	}

	public StartPagerItemLayout(Start start, Context context, int mHeight,
			int mWidth, int position, AnimationCallback animationCallback) {
		super(context, mHeight, mWidth);
		mStart = start;
		this.mAnimationCallback = animationCallback;
		this.position = position;
		initView();
		initParams();
		addOtherLayout();
		addBottomDot();
		addView(mStartPageLayout, mStartPageParams);
		addView(mOterPageLayout, mOterPageParams);
		addView(mBottomLayout, mBottomParams);
	}

	private void addOtherLayout() {
		mOterPageLayout.setOrientation(LinearLayout.VERTICAL);
		mOterPageLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		mOterPageLayout.addView(mTitleIV, mTitleParams);
		mOterPageLayout.addView(mContentIV, mContentParams);
		mOterPageLayout.addView(mTextIV, mTextParams);
	}

	private void addBottomDot() {
		mBottomLayout.setGravity(Gravity.CENTER);
		mBottomLayout.setOrientation(LinearLayout.HORIZONTAL);
		mBottomLayout.addView(mBottomDotIV, mBottomDotParams);
	}

	public void initData() {
		if (!Utils.isObject(mStart)) {
			if (mStart.isExecAnimation) {
				mOterPageLayout.setVisibility(View.VISIBLE);
				mStartPageLayout.setVisibility(View.GONE);
				mTitleIV.setImageResource(mStart.title);
				mContentIV.setImageResource(mStart.content);
				mContentIV.setVisibility(View.INVISIBLE);
				mTextIV.setImageResource(mStart.text);
				mTextIV.setVisibility(View.INVISIBLE);
			} else {
				mOterPageLayout.setVisibility(View.GONE);
				mStartPageLayout.setBackgroundResource(mStart.start);
			}
			mBottomDotIV.setImageResource(mStart.bottom);
		}
	}

	public void startAnimation() {
		mContentIV.setVisibility(View.VISIBLE);
		mTextIV.setVisibility(View.VISIBLE);
		mTextIV.startAnimation(getTextAnimation());
		mContentIV.startAnimation(getPhotoAnimation());
	}

	public void cancelAnimation() {
		if (!Utils.isObject(mTextIV.getAnimation())) {
			mTextIV.getAnimation().cancel();
		}
		if (!Utils.isObject(mContentIV.getAnimation())) {
			mContentIV.getAnimation().cancel();
		}
	}

	@Override
	protected void initView() {
		mStartPageLayout = new LinearLayout(getContext());
		mOterPageLayout = new LinearLayout(getContext());
		mTitleIV = new ImageView(getContext());
		mContentIV = new ImageView(getContext());
		mTextIV = new ImageView(getContext());
		mBottomLayout = new LinearLayout(getContext());
		mBottomDotIV = new ImageView(getContext());
	}

	@Override
	protected void initParams() {
		mStartPageParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.START_FIRST_HEIGHT, mHeight));
		mStartPageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mOterPageParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mOterPageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mTitleParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.START_TITLE_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.START_TITLE_HEIGHT, mHeight));
		mContentParams = new android.widget.LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, Utils.getHeight(
						ConfigLayout.START_CONTENT_HEIGHT, mHeight));
		mTextParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.START_TITLE_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.START_TEXT_PAGET_HEIGHT, mHeight));
		mBottomParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mBottomParams.bottomMargin = Utils.getHeight(ConfigLayout.MARGIN150,
				mHeight);
		mBottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mBottomDotParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.START_BOTTOM_PAGE_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.START_BOTTOM_PAGE_HEIGHT, mHeight));
	}

	public TranslateAnimation getTextAnimation() {
		final TranslateAnimation transLateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		transLateAnimation.setDuration(500);
		return transLateAnimation;
	}

	public TranslateAnimation getPhotoAnimation() {
		final TranslateAnimation transLateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		transLateAnimation.setDuration(500);
		transLateAnimation.setAnimationListener(mAnimationListener);
		return transLateAnimation;
	}

	private AnimationListener mAnimationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			if (!Utils.isObject(mAnimationCallback)) {
				mAnimationCallback.finish(position);
			}
		}
	};
}
