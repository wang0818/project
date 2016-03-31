package com.bxnote.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class BaseRelativelayout extends RelativeLayout {
	protected int mHeight;
	protected int mWidth;

	public BaseRelativelayout(Context context, int mHeight, int mWidth) {
		super(context);
		this.mHeight = mHeight;
		this.mWidth = mWidth;
	}

	public BaseRelativelayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseRelativelayout(Context context) {
		super(context);
	}

	protected abstract void initView();

	protected abstract void initParams();
}
