package com.bxnote.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ColorConstant;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class BeginView extends BaseRelativelayout {
	public ImageView mAppIV;
	public ViewPager mViewPager;
	// layoutparams
	public LayoutParams mAppParams;
	public LayoutParams mViewParams;

	public BeginView(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		Utils.isChangeTheme(this, getContext());
		initView();
		initParams();
		addView(mAppIV, mAppParams);
		addView(mViewPager, mViewParams);
		mViewPager.setBackgroundColor(ColorConstant.RED);
		mAppIV.setImageResource(R.drawable.opening);
	}

	@Override
	protected void initView() {
		mAppIV = new ImageView(getContext());
		mViewPager = new ViewPager(getContext());
	}

	@Override
	protected void initParams() {
		mAppParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mViewParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}
}
