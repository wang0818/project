package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Utils;

public class FontLayout extends BaseLinearLyaout {
	private TextView mContentTV;
	private LayoutParams mContentParams;
	private String path;

	public FontLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FontLayout(Context context, int mHeight, int mWidth, String path) {
		super(context, mHeight, mWidth);
		this.path = path;
		initView();
		initParams();
		addView(mContentTV, mContentParams);
		initData();
	}

	private void initData() {
		mContentTV.setTextSize(TextSizeConstant.TEXT_SIZE);
		mContentTV.setText("八行箋");
		Utils.setOtherType(getContext(), mContentTV, path);
	}

	public FontLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mContentTV = new TextView(getContext());
	}

	@Override
	protected void initParams() {
		mContentParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.FONT_WIDTH, mWidth), LayoutParams.MATCH_PARENT);
	}
}
