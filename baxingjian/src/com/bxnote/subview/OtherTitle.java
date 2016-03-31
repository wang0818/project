package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class OtherTitle extends BaseLinearLyaout {
	private ImageView mTitleIV;
	private LayoutParams mTitleParams;

	public OtherTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OtherTitle(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addView(mTitleIV, mTitleParams);
		this.setBackgroundResource(R.drawable.title);
		mTitleIV.setImageResource(R.drawable.bahangjian);
	}

	public OtherTitle(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTitleIV = new ImageView(getContext());
	}

	@Override
	protected void initParams() {
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.TITLE_HEIGHT, mHeight));
	}

}
