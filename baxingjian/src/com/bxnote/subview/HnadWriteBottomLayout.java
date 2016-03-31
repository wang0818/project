package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.callback.HandWriteBitmapCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;
import com.bxnote.view.HandWriteView;

public class HnadWriteBottomLayout extends BaseRelativelayout {
	public LinearLayout mOtherFunctionLayout;
	public ImageView mDeleteIV;
	public ImageView mEnterIV;
	public ImageView mBlankIV;
	public HandWriteView mHandWriteView;
	//
	private LayoutParams mHandParams;
	private LayoutParams mOtherFunctionParams;
	private android.widget.LinearLayout.LayoutParams mDeleteParams;
	private android.widget.LinearLayout.LayoutParams mEnterParams;
	private android.widget.LinearLayout.LayoutParams mBlankParams;
	//
	private HandWriteBitmapCallback mHandWriteBitmapCallback;

	public HnadWriteBottomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HnadWriteBottomLayout(Context context, int mHeight, int mWidth,
			HandWriteBitmapCallback mHandWriteCallback) {
		super(context, mHeight, mWidth);
		mHandWriteBitmapCallback = mHandWriteCallback;
		initView();
		initParams();
		addOtherFunction();
		addView(mHandWriteView, mHandParams);
		addView(mOtherFunctionLayout, mOtherFunctionParams);
		initData();
	}

	private void initData() {
		this.setBackgroundResource(R.drawable.hand_edit);
		mDeleteIV.setImageResource(R.drawable.delete_hand);
		mEnterIV.setImageResource(R.drawable.enter);
		mBlankIV.setImageResource(R.drawable.kongge);
	}

	private void addOtherFunction() {
		mOtherFunctionLayout.setOrientation(LinearLayout.VERTICAL);
		mOtherFunctionLayout.addView(mDeleteIV, mDeleteParams);
		mOtherFunctionLayout.addView(mEnterIV, mEnterParams);
		mOtherFunctionLayout.addView(mBlankIV, mBlankParams);
	}

	public HnadWriteBottomLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mHandWriteView = new HandWriteView(getContext(),
				mHandWriteBitmapCallback,mHeight,mWidth);
		mOtherFunctionLayout = new LinearLayout(getContext());
		mDeleteIV = new ImageView(getContext());
		mEnterIV = new ImageView(getContext());
		mBlankIV = new ImageView(getContext());
	}

	@Override
	protected void initParams() {
		mHandParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HAND_WRITE_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HAND_WRITE_ICON_HEIGT, mHeight));
		mHandParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mOtherFunctionParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		mOtherFunctionParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mOtherFunctionParams.rightMargin = Utils.getWidth(
				ConfigLayout.MARGIN103, mWidth);
		mDeleteParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.HAND_WRITE_DELETE_ICON_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.HAND_WRITE_DELETE_ICON_HEIGT, mHeight));
		mDeleteParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN33,
				mHeight);
		mEnterParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.HAND_WRITE_DELETE_ICON_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.HAND_WRITE_DELETE_ICON_HEIGT, mHeight));
		mEnterParams.topMargin = Utils
				.getHeight(ConfigLayout.MARGIN33, mHeight);
		mBlankParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.HAND_WRITE_DELETE_ICON_WIDTH,
						mWidth), Utils.getHeight(
						ConfigLayout.HAND_WRITE_DELETE_ICON_HEIGT, mHeight));
		mBlankParams.topMargin = Utils
				.getHeight(ConfigLayout.MARGIN33, mHeight);
	}

}
