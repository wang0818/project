package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Utils;

public class ShortcutRegisterLayout extends LinearLayout {
	private int mWidth;
	private int mHeight;
	private TextView mShortcutRegisterTV;
	public ImageView mWxIV;
	public ImageView mSinaIV;
	// params
	private LayoutParams mShortCutParams;
	private LayoutParams mWxParams;
	private LayoutParams mSinaParams;

	public ShortcutRegisterLayout(Context context, int mHeight, int mWidth) {
		super(context);
		this.mWidth = mWidth;
		this.mHeight = mHeight;
		initParams();
		initView();
		setOrientation(LinearLayout.HORIZONTAL);
		addView(mShortcutRegisterTV, mShortCutParams);
		addView(mSinaIV, mSinaParams);
		addView(mWxIV, mWxParams);
		mShortcutRegisterTV.setText("快速注册:");
		mShortcutRegisterTV.setTextColor(ColorConstant.GRAY);
		mShortcutRegisterTV.setTextSize(TextSizeConstant.TEXT_18);
		mSinaIV.setImageResource(R.drawable.sina);
		mWxIV.setImageResource(R.drawable.qq);
	}

	public ShortcutRegisterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void initView() {
		mShortcutRegisterTV = new TextView(getContext());
		mWxIV = new ImageView(getContext());
		mSinaIV = new ImageView(getContext());
	}

	protected void initParams() {
		mShortCutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mWxParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WX_AND_SINA_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WX_AND_SINA_HEIGHT, mHeight));
		mWxParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN55, mWidth);
		mSinaParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WX_AND_SINA_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WX_AND_SINA_HEIGHT, mHeight));
		mSinaParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN55, mWidth);
	}

}
