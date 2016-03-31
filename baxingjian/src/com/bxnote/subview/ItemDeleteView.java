package com.bxnote.subview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Utils;

public class ItemDeleteView extends BaseLinearLyaout {
	public RelativeLayout mContainTopLayout;
	public ImageView mTopIV;
	public ImageView mBottomIV;
	public TextView  mTopTV;
	// layoutParams
	private LayoutParams mTopParams;
	private LayoutParams mBottomParams;

	public ItemDeleteView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ItemDeleteView(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addTop();
		addView();
		initData();
	}

	private void addTop() {
		mTopTV.setVisibility(View.GONE);
		mTopTV.setGravity(Gravity.CENTER);
		mTopTV.setTextSize(TextSizeConstant.TEXT_8);
		mTopTV.setTextColor(Color.RED);
		mContainTopLayout.addView(mTopTV, mTopParams);
		mContainTopLayout.addView(mTopIV, mTopParams);
	}

	private void initData() {
		mTopIV.setImageResource(R.drawable.send_to);
		mBottomIV.setImageResource(R.drawable.short_line);
	}

	private void addView() {
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		addView(mContainTopLayout, mTopParams);
		addView(mBottomIV, mBottomParams);
	}

	public ItemDeleteView(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTopIV = new ImageView(getContext());
		mBottomIV = new ImageView(getContext());
		mContainTopLayout = new RelativeLayout(getContext());
		mTopTV = new TextView(getContext());
	}

	@Override
	protected void initParams() {
		mTopParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight));
		mBottomParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.HOME_ITEM_BOTTOM_LINE_HEIGHT,
						mHeight));
		mBottomParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN10, mWidth);
	}

}
