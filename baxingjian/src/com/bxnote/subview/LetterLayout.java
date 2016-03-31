package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.AsynImgaeAssetsTask;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.Utils;

public class LetterLayout extends BaseLinearLyaout {
	private Consumer mConsumer;
	private AsyncImageView mAsyncIV;
	private String path;
	private LayoutParams mBitmapParams;

	public LetterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LetterLayout(Context context, int mHeight, int mWidth, String path,
			Consumer consumer) {
		super(context, mHeight, mWidth);
		this.path = path;
		mConsumer = consumer;
		initView();
		initParams();
		addView(mAsyncIV, mBitmapParams);
		initData();
	}

	private void initData() {
		mAsyncIV.setImageUrl(path);
		mConsumer.add(new AsynImgaeAssetsTask(getContext(), mAsyncIV));
	}

	public LetterLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mAsyncIV = new AsyncImageView(getContext());
	}

	@Override
	protected void initParams() {
		mBitmapParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.LETTER_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.LETTER_HEIGHT, mHeight));
		mBitmapParams.leftMargin = Utils
				.getWidth(ConfigLayout.MARGIN30, mWidth);
		mBitmapParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN30,
				mWidth);
	}
}
