package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.bean.Note;
import com.bxnote.callback.ShareCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class ItemRightLayout extends BaseLinearLyaout {
	public ItemDeleteView mDateIDV;
	public ItemDeleteView mDatetimeIDV;
	public ItemDeleteView mShareIDV;
	public ItemDeleteView mWiriteIDV;
	public ItemDeleteView mDeleteIDV;
	// params
	private LayoutParams mDateParams;
	private LayoutParams mDateTimeParams;

	private LayoutParams mShareParams;

	private LayoutParams mWriteParams;

	private LayoutParams mDeleteParams;
	//
	private Note mNote;
	private ShareCallback<Note> mShareCallback;

	public ItemRightLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ItemRightLayout(Context context, int mHeight, int mWidth, Note note,
			ShareCallback<Note> shareCallback) {
		super(context, mHeight, mWidth);
		mNote = note;
		mShareCallback = shareCallback;
		initView();
		initParams();
		initListener();
		addView();
		initData();
	}

	private void initData() {
		mWiriteIDV.mTopIV.setImageResource(R.drawable.edit_letter);
		mShareIDV.mTopIV.setImageResource(R.drawable.share_letter);
		mDeleteIDV.mTopIV.setImageResource(R.drawable.delete_letter);
		mDateIDV.mTopTV.setText(Utils.getHour(mNote.hour));
		mDatetimeIDV.mTopTV.setText(Utils.getDay(mNote.day));
	}

	private void addView() {
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.BOTTOM);
		addView(mDateIDV, mDateParams);
		mDateIDV.mTopIV.setVisibility(View.GONE);
		mDateIDV.mTopTV.setVisibility(View.VISIBLE);
		addView(mDatetimeIDV, mDateTimeParams);
		mDatetimeIDV.mTopIV.setVisibility(View.GONE);
		mDatetimeIDV.mTopTV.setVisibility(View.VISIBLE);
		addView(mShareIDV, mShareParams);
		addView(mWiriteIDV, mWriteParams);
		addView(mDeleteIDV, mDeleteParams);
		initListener();
	}

	private void initListener() {
		mShareIDV.setOnClickListener(mShareClickListener);
		mDeleteIDV.setOnClickListener(mDeleteClickListener);
		mWiriteIDV.setOnClickListener(mWriteClickListener);
	}

	public ItemRightLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mDateIDV = new ItemDeleteView(getContext(), mHeight, mWidth);
		mDatetimeIDV = new ItemDeleteView(getContext(), mHeight, mWidth);
		mShareIDV = new ItemDeleteView(getContext(), mHeight, mWidth);

		mWiriteIDV = new ItemDeleteView(getContext(), mHeight, mWidth);

		mDeleteIDV = new ItemDeleteView(getContext(), mHeight, mWidth);

	}

	@Override
	protected void initParams() {
		mDateParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);// Utils.getHeight(ConfigLayout.HOME_ITEM_RIGHT_ITEM_HEIGHT,
		// mHeight)
		mDateTimeParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mShareParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mWriteParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mDeleteParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
	}

	private OnClickListener mShareClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (!Utils.isObject(mShareCallback)) {
				mShareCallback.shareCallback(mNote);
			}
		}
	};
	private OnClickListener mDeleteClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (!Utils.isObject(mShareCallback)) {
				mShareCallback.deleteCallback(mNote);
			}
		}
	};
	private OnClickListener mWriteClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (!Utils.isObject(mShareCallback)) {
				mShareCallback.writeCallback(mNote);
			}
		}
	};
}
