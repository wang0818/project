package com.bxnote.subview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Utils;

public class RegisterUserLayout extends BaseLinearLyaout {
	private TextView mNoteTV;
	public EditText mInputET;
	private LayoutParams mNoteParams;
	private LayoutParams mInputParams;

	public RegisterUserLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initParams();
		initView();
		setOrientation(LinearLayout.HORIZONTAL);
		addView(mNoteTV, mNoteParams);
		addView(mInputET, mInputParams);
	}

	public RegisterUserLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RegisterUserLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mNoteTV = new TextView(getContext());
		mNoteTV.setTextSize(TextSizeConstant.TEXT_18);
		mNoteTV.setTextColor(ColorConstant.GRAY);
		mInputET = new EditText(getContext());
		mInputET.setBackgroundColor(Color.WHITE);
		mInputET.setTextColor(ColorConstant.GRAY);
		mInputET.setTextSize(TextSizeConstant.TEXT_12);
	}

	@Override
	protected void initParams() {
		mNoteParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mInputParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mInputParams.leftMargin = Utils.getWidth(ConfigLayout.MARIGN30, mWidth);
	}

	public void setText(String content) {
		mNoteTV.setText(content);
	}

	public void setHint(String conent) {
		mInputET.setHint(conent);
	}

}
