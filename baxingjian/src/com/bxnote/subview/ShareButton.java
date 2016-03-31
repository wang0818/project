package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Utils;

public class ShareButton extends BaseLinearLyaout {
	public ImageView mTopIV;
	public TextView mContentTV;
	private LayoutParams mTopParams;
	private LayoutParams mBottomParams;

	public ShareButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShareButton(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		this.setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(LinearLayout.VERTICAL);
		addView(mTopIV, mTopParams);
		addView(mContentTV, mBottomParams);
		initData();
	}

	private void initData() {
		mContentTV.setText("微信好友");
		mContentTV.setTextSize(TextSizeConstant.TEXT_13);
		mContentTV.setTextColor(ColorConstant.RED);
	}

	public ShareButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		mTopIV = new ImageView(getContext());
		mContentTV = new TextView(getContext());
	}

	@Override
	protected void initParams() {
		mTopParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.SHARE_ICON_WIDTH, mWidth), Utils.getWidth(
				ConfigLayout.SHARE_ICON_HEIGHT, mHeight));
		mBottomParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}
}
