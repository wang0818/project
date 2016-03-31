package com.bxnote.subview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.Utils;

public class LineHandLayout extends BaseLinearLyaout {
	public EditText mEditText;
	private LayoutParams mEditParams;

	public LineHandLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineHandLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initParams();
		initView();
		setGravity(Gravity.CENTER);
		addView(mEditText, mEditParams);
	}

	public LineHandLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mEditText = new EditText(getContext());
		mEditText.setTextSize(TextSizeConstant.TEXT_14);
		mEditText.setGravity(Gravity.TOP);
		mEditText.setBackgroundColor(Color.TRANSPARENT);
		mEditText.setVisibility(View.GONE);
		mEditText.setPadding(Utils.getWidth(ConfigLayout.MARGIN20, mWidth), 0,
				Utils.getWidth(ConfigLayout.MARGIN20, mWidth), 0);
	}

	@Override
	protected void initParams() {
		mEditParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HAND_WRITE_EDIT_HEIGHT, mHeight));
	}
	public void setTextType(String type){
		if (!StringUtils.isEmpty(type)) {
			Utils.setOtherType(getContext(), mEditText, type);
		}
	}
}
