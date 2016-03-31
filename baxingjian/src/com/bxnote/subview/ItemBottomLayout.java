package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bxnote.baseview.BaseLinearLyaout;

@SuppressWarnings("deprecation")
public class ItemBottomLayout extends BaseLinearLyaout {
	public RelativeLayout mChooseLayout;
	public ImageView mLeftIV;
	public ImageView mCenterIV;
	public ImageView mRightIV;
	public RelativeLayout mChooseItemLayout;
	public Gallery mTextGallery;
	public Gallery mNoteGallery;
	public ItemBottomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ItemBottomLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
	}

	public ItemBottomLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initParams() {
		// TODO Auto-generated method stub
		
	}

}
