package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class WriteChooseLayout extends BaseRelativelayout {
	public ImageView mLeftIV;
	public ImageView mCenterIV;
	public ImageView mRightIV;
	// params
	private LayoutParams mLeftParams;
	private LayoutParams mCenterParams;
	private LayoutParams mRightParams;

	public WriteChooseLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WriteChooseLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addView();
		initData();
	}

	private void initData() {
		mLeftIV.setImageResource(R.drawable.to_left_touch);
		mRightIV.setImageResource(R.drawable.to_right_touch);
		mCenterIV.setImageResource(R.drawable.choose_letter);
	}

	private void addView() {
		addView(mLeftIV, mLeftParams);
		addView(mCenterIV, mCenterParams);
		addView(mRightIV, mRightParams);
	}

	public WriteChooseLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mLeftIV = new ImageView(getContext());
		mCenterIV = new ImageView(getContext());
		mRightIV = new ImageView(getContext());
	}
	

	@Override
	protected void initParams() {
		mLeftParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_CHOOSE_LEFT_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_CHOOSE_LEFT_HEIGHT, mHeight));
		mLeftParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN30, mWidth);
		mCenterParams =new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_CHOOSE_LEFT_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_CHOOSE_LEFT_HEIGHT, mHeight));
		mCenterParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mRightParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_CHOOSE_LEFT_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_CHOOSE_LEFT_HEIGHT, mHeight));
		mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mRightParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN30, mWidth);
	}

}
