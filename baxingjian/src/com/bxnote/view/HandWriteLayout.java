package com.bxnote.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.callback.HandWriteBitmapCallback;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.HnadWriteBottomLayout;
import com.bxnote.subview.Title;
import com.bxnote.subview.WriteSubView;
import com.bxnote.utils.Utils;

public class HandWriteLayout extends BaseRelativelayout {
	public Title mTitle;
	public WriteSubView mWriteView;
	public HnadWriteBottomLayout mHandWriteLayout;
	public ImageView mGrayIV;
	public ImageView mShowIntruduceIV;
	public ImageView mChangeBackGroundIV;
	// params
	private LayoutParams mTitleParams;
	private LayoutParams mWriteParams;
	private LayoutParams mHandWriteBottomParams;
	private LayoutParams mGrayParams;
	private LayoutParams mIntroduceParams;
	private LayoutParams mChangeBackParams;
	//
	private HandWriteBitmapCallback mHandWriteBitmapCallback;

	public HandWriteLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HandWriteLayout(Context context, int mHeight, int mWidth,
			HandWriteBitmapCallback mHandWriteCallback) {
		super(context, mHeight, mWidth);
		this.mHandWriteBitmapCallback = mHandWriteCallback;
		Utils.isChangeTheme(this, getContext());
		initView();
		initParams();
		addView();
		initData();
	}

	private void initData() {
		mTitle.mEditIV.setImageResource(R.drawable.finish_touch);
		mTitle.mMenuIV.setImageResource(R.drawable.back_touched);
		mTitle.setBackgroundResource(R.drawable.title);
		mWriteView.mChapeauLayout.setText("起\n首");
		mWriteView.mGreetingsLayout.setText("问\n候\n语");
		mWriteView.mEndLanLayout.setText("结\n语");
		mWriteView.mSignatureNameLayout.setText("署\n名");
		mChangeBackGroundIV.setBackgroundResource(R.drawable.paper_tri);

	}

	private void addView() {
		mWriteView.setBackgroundResource(R.drawable.letter1);
		addView(mTitle, mTitleParams);
		addView(mWriteView, mWriteParams);
		addView(mHandWriteLayout, mHandWriteBottomParams);
		addView(mChangeBackGroundIV, mChangeBackParams);
		addView(mGrayIV, mGrayParams);
		addView(mShowIntruduceIV, mIntroduceParams);
		mGrayIV.setBackgroundColor(0x7f000000);
		mGrayIV.setVisibility(View.GONE);
		mShowIntruduceIV.setVisibility(View.GONE);
	}

	public HandWriteLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTitle = new Title(getContext(), mHeight, mWidth);
		mWriteView = new WriteSubView(getContext(), mHeight, mWidth);
		mHandWriteLayout = new HnadWriteBottomLayout(getContext(), mHeight,
				mWidth, mHandWriteBitmapCallback);
		mGrayIV = new ImageView(getContext());
		mShowIntruduceIV = new ImageView(getContext());
		mChangeBackGroundIV = new ImageView(getContext());
	}

	@Override
	protected void initParams() {
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HOME_TITLE_HEIGHT, mHeight));
		mWriteParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mWriteParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN138,
				mHeight);
		mWriteParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN80, mWidth);
		mWriteParams.rightMargin = Utils
				.getWidth(ConfigLayout.MARGIN80, mWidth);
		mHandWriteBottomParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HAND_WRITE_BOTTOM_HEIGHT, mHeight));
		mHandWriteBottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mGrayParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mIntroduceParams = new LayoutParams(Utils.getWidth(
		// ConfigLayout.WRITE_INTRUDUCE_WIDTH, mWidth), Utils.getHeight(
		// ConfigLayout.WRITE_INTRUDUCE_HEIGHT, mHeight));
		// mIntroduceParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mIntroduceParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mIntroduceParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN138,
				mHeight);
		mIntroduceParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN80,
				mWidth);
		mIntroduceParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN80,
				mWidth);
		mChangeBackParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HAND_CHANGE_BACKGROUND_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.HAND_CHANGE_BACKGROUND_HEIGHT,
						mHeight));
		mChangeBackParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mChangeBackParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN138
				+ ConfigLayout.WRITE_LINE_HEIGHT
				- ConfigLayout.HAND_CHANGE_BACKGROUND_HEIGHT, mHeight);
		mChangeBackParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN80,
				mWidth);
	}
}
