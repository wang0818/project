package com.bxnote.subview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class HandWriteChooseLayout extends BaseLinearLyaout {
	public TextView mHandTV;
	public TextView mInputTV;
	//params
	public LayoutParams mHandParams;
	public LayoutParams mInputParams;
	public HandWriteChooseLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HandWriteChooseLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		setOrientation(LinearLayout.VERTICAL);
		addView(mHandTV, mHandParams);
		addView(mInputTV, mInputParams);
		this.setBackgroundColor(Color.WHITE);
	}

	public HandWriteChooseLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mHandTV = new TextView(getContext());
		mHandTV.setGravity(Gravity.CENTER);
		mHandTV.setText("手写写入");
		mInputTV = new TextView(getContext());
		mInputTV.setGravity(Gravity.CENTER);
		mInputTV.setText("输入法输入");
	}

	@Override
	protected void initParams() {
		mHandParams = new LayoutParams(LayoutParams.MATCH_PARENT, Utils.getHeight(ConfigLayout.HAND_WRITE_CHOOSE_HEIGHT, mHeight));
		mInputParams = new LayoutParams(LayoutParams.MATCH_PARENT,  Utils.getHeight(ConfigLayout.HAND_WRITE_CHOOSE_HEIGHT, mHeight));
	}

}
