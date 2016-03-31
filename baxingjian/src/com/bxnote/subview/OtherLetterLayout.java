package com.bxnote.subview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.Utils;

public class OtherLetterLayout extends BaseRelativelayout {
	public TextView mIntroduceTV;
	public EditText mEditET;
	// layout params
	private LayoutParams mIntroduceParams;
	private LayoutParams mEditParams;

	public OtherLetterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OtherLetterLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addView(mEditET, mEditParams);
		addView(mIntroduceTV, mIntroduceParams);
	}

	public OtherLetterLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mIntroduceTV = new TextView(getContext());
		mEditET = new EditText(getContext());
//		if (Utils.getWidth(ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth)>(TextSizeConstant.TEXT_10*1.5)*2) {
//			int textsize = (int)(Utils.getWidth(ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth)*1.5)/2;
//			Log.e("tag","textsize"+textsize);
////			mIntroduceTV.setTextSize(textsize);
//		}else{
			mIntroduceTV.setTextSize(TextSizeConstant.TEXT_14);
//		}
		mIntroduceTV.setBackgroundColor(ColorConstant.GRAY);
		mIntroduceTV.setTextColor(ColorConstant.WHITE);
		mIntroduceTV.setGravity(Gravity.CENTER);
		mIntroduceTV.setText("署名");
		mEditET.setVisibility(View.GONE);
		mEditET.setTextSize(TextSizeConstant.TEXT_14);
		mEditET.setGravity(Gravity.TOP);
		mEditET.setGravity(Gravity.CENTER_HORIZONTAL);
		mEditET.setPadding(10, 0, 0, 10);
		// mEditET.setFilters(new InputFilter[]{new
		// InputFilter.LengthFilter(3)});
		mEditET.setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	protected void initParams() {
		mIntroduceParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mEditParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	public void setText(String content) {
		mIntroduceTV.setText(content);
	}
	public void setTextType(String type){
		if (!StringUtils.isEmpty(type)) {
			Utils.setOtherType(getContext(), mIntroduceTV, type);
			Utils.setOtherType(getContext(), mEditET, type);
		}
	}

}
