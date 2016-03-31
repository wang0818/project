package com.bxnote.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class BaseLinearLyaout extends LinearLayout {
	protected int mHeight;
	protected int mWidth;

	public BaseLinearLyaout(Context context, int mHeight, int mWidth) {
		super(context);
		this.mHeight = mHeight;
		this.mWidth = mWidth;
	}

	public BaseLinearLyaout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseLinearLyaout(Context context) {
		super(context);
	}

	protected abstract void initView();

	protected abstract void initParams();
}
