package com.bxnote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.bean.Note;
import com.bxnote.config.ColorConstant;
import com.bxnote.subview.AsyncImageView;
import com.bxnote.utils.AsyncImageTask;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.Utils;

public class LargerPhotoLayout extends BaseRelativelayout {
	private AsyncImageView mLargerIV;
	private LayoutParams mLargerParams;
	private Consumer mConsumer;
	private Note mNote;
	public LargerPhotoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LargerPhotoLayout(Context context, int mHeight, int mWidth,Consumer consumer,Note note) {
		super(context, mHeight, mWidth);
		mNote = note;
		mConsumer = consumer;
		this.setBackgroundColor(ColorConstant.RED);
		initView();
		initParams();
		addView(mLargerIV, mLargerParams);
		initData();
	}

	private void initData() {
		if (!Utils.isObject(mNote)) {
			mLargerIV.setImageUrl(mNote.imageLocation);
			mConsumer.add(new AsyncImageTask(getContext(), mLargerIV));
		}
	}

	public LargerPhotoLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mLargerIV = new AsyncImageView(getContext(),true);
	}

	@Override
	protected void initParams() {
		mLargerParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

}
