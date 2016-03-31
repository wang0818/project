package com.bxnote.subview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Gallery;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;

@SuppressWarnings("deprecation")
public class FontAndLetterLayout extends BaseRelativelayout {
	public Gallery mFontGallery;
	public Gallery mLetterGallery;
	// layout
	private LayoutParams mFontParams;
	private LayoutParams mletterParams;

	public FontAndLetterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FontAndLetterLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addView();
		initData();
	}

	private void initData() {
		this.setBackgroundResource(R.drawable.menu);
	}

	@SuppressLint("NewApi")
	private void addView() {
		addView(mFontGallery, mFontParams);
		addView(mLetterGallery, mletterParams);
		mFontGallery.setVisibility(View.GONE);
		mLetterGallery.setAlpha(111);
	}

	public FontAndLetterLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mFontGallery = new Gallery(getContext());
		mLetterGallery = new Gallery(getContext());
	}

	@Override
	protected void initParams() {
		mFontParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mletterParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

}
