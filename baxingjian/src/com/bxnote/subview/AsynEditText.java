package com.bxnote.subview;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class AsynEditText {
	private TextView mIV;
	private String imageUrl;
	private List<SpannableString> mSpannablees;
	private boolean isNeedAdd;
	public AsynEditText() {
		super();
	}

	public AsynEditText(Context context, TextView mIV, String imageUrl,
			List<SpannableString> mSpannablees,boolean isNeedAdd) {
		super();
		this.mSpannablees = mSpannablees;
		this.mIV = mIV;
		this.imageUrl = imageUrl;
		this.isNeedAdd = isNeedAdd;
	}

	@SuppressLint("NewApi")
	public void setBitmap(Bitmap bitmap) {
		if (Utils.isObject(bitmap)) {
			return;
		}
		@SuppressWarnings("deprecation")
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		drawable.setBounds(0, 0, Utils.getWidth(Constant.HAND_WRITE_WIDTH,
				Constant.sScreenWdith), Utils.getHeight(
				Constant.HAND_WRITE_HEIHT, Constant.sScreenHeihgt));
		ImageSpan imageSpan = new ImageSpan(drawable);// 添加换行符
		SpannableString spannableString = new SpannableString( imageUrl
				+ ";");// 直接定义图片的名字
		spannableString.setSpan(imageSpan, 0, spannableString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mIV.append(spannableString);
		if (isNeedAdd) {
			mSpannablees.add(spannableString);
		}
	}

	public String getImageUrl() {
		return imageUrl;
	}

}
