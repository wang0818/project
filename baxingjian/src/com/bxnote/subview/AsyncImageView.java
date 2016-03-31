package com.bxnote.subview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.utils.Utils;

public class AsyncImageView extends BaseLinearLyaout {
	private ImageView mIV;
	// layoutparams
	private LayoutParams mParams;
	private String imageUrl;
	private Bitmap mBitmap;
	private boolean isCrap;
	@Override
	protected void initView() {
		mIV = new ImageView(getContext());
	}

	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AsyncImageView(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
	}
	public AsyncImageView(Context context) {
		super(context);
		initView();
		initParams();
		addView(mIV, mParams);
	}
	public AsyncImageView(Context context,boolean isCrap) {
		super(context);
		this.isCrap = isCrap;
		initView();
		initParams();
		addView(mIV, mParams);
	}

	@Override
	protected void initParams() {
		mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		if (!Utils.isObject(bitmap)) {
			if (isCrap) {
				mIV.setImageBitmap(bitmap);
			}else{
				mIV.setImageBitmap(bitmap);
				mIV.setScaleType(ScaleType.FIT_XY);	
			}

		}
	}
	public void restore() {
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mIV.setImageBitmap(null);
			mBitmap.recycle();
			mBitmap = null;
		}

	}

}
