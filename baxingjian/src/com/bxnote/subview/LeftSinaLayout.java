package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class LeftSinaLayout extends BaseRelativelayout {
	public TextView mTextTV;
	public ImageView mBackChangeIV;
	public ImageView mSinaIV;
	// layoutparams
	private LayoutParams mTextParams;
	private LayoutParams mBackChangeParams;
	private LayoutParams mSinaParams;
	//
	private boolean isSinaOrQq;

	public LeftSinaLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LeftSinaLayout(Context context, int mHeight, int mWidth,
			boolean isSina) {
		super(context, mHeight, mWidth);
		this.isSinaOrQq = isSina;
		initView();
		initParams();
		addView(mTextTV, mTextParams);
		addView(mBackChangeIV, mBackChangeParams);
		addView(mSinaIV, mSinaParams);
		initData();
	}

	private void initData() {
		if (isSinaOrQq) {
			mSinaIV.setVisibility(View.VISIBLE);
			mBackChangeIV.setVisibility(View.GONE);
			mSinaIV.setImageResource(R.drawable.selected_btn);
		} else {
			mSinaIV.setVisibility(View.GONE);
			mBackChangeIV.setVisibility(View.VISIBLE);
			mBackChangeIV.setImageResource(R.drawable.change_theme1_btn);
		}
	}

	public LeftSinaLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTextTV = new TextView(getContext());
		mBackChangeIV = new ImageView(getContext());
		mSinaIV = new ImageView(getContext());
		Utils.setType(getContext(), mTextTV);// 设置字体样式
		setText("设置文本");
	}

	@Override
	protected void initParams() {
		mTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mTextParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN110, mWidth);
		mBackChangeParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.MENU_BACK_CHANGE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.MENU_BACK_CHANGE_HEIGHT, mHeight));
		mBackChangeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mBackChangeParams.addRule(RelativeLayout.CENTER_VERTICAL);
		mBackChangeParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN270,
				mWidth);
		mSinaParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.MENU_BACK_SINA_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.MENU_BACK_SINA_HEIGHT, mHeight));
		mSinaParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mSinaParams.addRule(RelativeLayout.CENTER_VERTICAL);
		mSinaParams.rightMargin = Utils
				.getWidth(ConfigLayout.MARGIN270, mWidth);

	}

	public void setText(String content) {
		mTextTV.setText(content);
	}

	public void setTextColor(int color) {
		mTextTV.setTextColor(color);
	}

}
