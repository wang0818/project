package com.bxnote.subview;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class Title extends BaseLinearLyaout {
	public ImageView mMenuIV;
	public ImageView mEditIV;
	public TextView mContentTV;
	//
	private LayoutParams mMenuParams;
	private LayoutParams mEditIVParams;
	private LayoutParams mContentParams;

	public Title(Context context) {
		super(context);
	}

	public Title(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setOrientation(LinearLayout.HORIZONTAL);
		addView(mMenuIV, mMenuParams);
		addView(mContentTV, mContentParams);
		addView(mEditIV, mEditIVParams);
		this.setBackgroundColor(ColorConstant.RED);
		initData();
		setTextColor();
	}

	private void initData() {
		mMenuIV.setImageResource(R.drawable.menu_touched);
		mEditIV.setImageResource(R.drawable.write_touched);
		mContentTV.setGravity(Gravity.CENTER);
		mContentTV.setText("用户");
	}

	@Override
	protected void initView() {
		mMenuIV = new ImageView(getContext());
		mEditIV = new ImageView(getContext());
		mContentTV = new TextView(getContext());

	}

	@Override
	protected void initParams() {
		mMenuParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight));
		mMenuParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIIN34, mWidth);
		mMenuParams.rightMargin = Utils
				.getWidth(ConfigLayout.MARGIIN34, mWidth);
		mEditIVParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight));
		mEditIVParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIIN34,
				mWidth);
//		mEditIVParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIIN34,
//				mWidth);
		mContentParams = new LayoutParams(Utils.getWidth(
				Utils.getTitleWidth(mWidth, ConfigLayout.HOME_MENU_ICON_TOTAL),
				mWidth), LayoutParams.MATCH_PARENT);
	}

	public void setTextColor() {
		if (Utils.getIsDefaultTheme(getContext())) {
			mContentTV.setTextColor(ColorConstant.BLACK);
		} else {
			mContentTV.setTextColor(ColorConstant.WHITE);
		}
	}
}
