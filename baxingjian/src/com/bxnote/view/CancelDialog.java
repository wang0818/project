package com.bxnote.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

@SuppressLint("NewApi")
public class CancelDialog extends Dialog {
	// view
	private LinearLayout mContainLayout;
	private LinearLayout mContainConfirmAndCancelLayout;
	public TextView mConfirmBT;
	public TextView mCanCel;
	private TextView mTitle;
	// params
	private LayoutParams mContainParams;
	private LayoutParams mContainConfirmAndCancelParams;
	private LayoutParams mContainConfirmParams;
	private LayoutParams mContainCancelParams;
	private LayoutParams mTitleParams;
	// private conta
	private int DISTANCE = 32;

	public CancelDialog(Context context) {
		super(context);
	}

	public CancelDialog(Context context, int theme) {
		super(context, theme);
		initView();
		initParams();
		addConfirmAndCancel();
		addLayout();
		mContainLayout.setBackgroundColor(ColorConstant.RED);
		mTitle.setText("谨慎注销，用户注销后不可恢复。是否继续注销");// 快捷注册
		mTitle.setTextColor(Color.WHITE);
	}

	private void addConfirmAndCancel() {
		mContainConfirmAndCancelLayout.setOrientation(LinearLayout.HORIZONTAL);
		mContainConfirmAndCancelLayout.addView(mConfirmBT, mContainConfirmParams);
		mContainConfirmAndCancelLayout.addView(mCanCel, mContainCancelParams);
	}

	private void addLayout() {
		mContainLayout.setOrientation(LinearLayout.VERTICAL);
		mContainLayout.addView(mTitle, mTitleParams);
		mContainLayout.addView(mContainConfirmAndCancelLayout,
				mContainConfirmAndCancelParams);
	}

	@SuppressLint("InlinedApi")
	private void initParams() {
		mContainParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.CANCEL_WIDTH, Constant.sScreenWdith),
				LayoutParams.WRAP_CONTENT);
		mContainConfirmAndCancelParams = new LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.BUTTON_HEIGHT,
						Constant.sScreenHeihgt));
		mTitleParams = new LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mTitleParams.leftMargin = Utils.getWidth(DISTANCE,
				Constant.sScreenWdith);
		mTitleParams.rightMargin = Utils.getWidth(DISTANCE,
				Constant.sScreenWdith);
		mTitleParams.topMargin = Utils.getHeight(DISTANCE,
				Constant.sScreenWdith);
		mTitleParams.bottomMargin = Utils.getHeight(DISTANCE,
				Constant.sScreenWdith);
		mContainConfirmParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		mContainConfirmParams.weight = Constant.DEFAULE_ONE;
		mContainConfirmParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN10, Constant.sScreenWdith);
		mContainCancelParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		mContainCancelParams.weight = Constant.DEFAULE_ONE;

	}

	private void initView() {
		mContainLayout = new LinearLayout(getContext());
		mContainConfirmAndCancelLayout = new LinearLayout(getContext());
		mConfirmBT = new TextView(getContext());
		mConfirmBT.setGravity(Gravity.CENTER);
		mConfirmBT.setBackgroundColor(ColorConstant.WHITE);
		mConfirmBT.setText("确定");
		mConfirmBT.setTextColor(ColorConstant.RED);
		mConfirmBT.setTextSize(TextSizeConstant.TEXT_13);
		mCanCel = new TextView(getContext());
		mCanCel.setGravity(Gravity.CENTER);
		// mContainConfirmLayout.setBackgroundResource(R.drawable.button_cancel);
		mCanCel.setTextSize(TextSizeConstant.TEXT_13);
		mCanCel.setBackgroundColor(Color.WHITE);
		mCanCel.setText("取消");
		mCanCel.setTextColor(ColorConstant.RED);
		mTitle = new TextView(getContext());
		mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,
				TextSizeConstant.TEXT_13);
		mTitle.setTextColor(ColorConstant.WHITE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mContainLayout, mContainParams);
	}

	public void cancelText(String text) {
		mCanCel.setText(text);
	};

	public void confirmText(String text) {
		mConfirmBT.setText(text);
	}

	public void setCancelBackGround(int id) {
		mCanCel.setBackgroundResource(id);
	}

	public void setConfirmBackGround(int id) {
		mConfirmBT.setBackgroundResource(id);
	}

	public void setTextTitle(String title) {
		mTitle.setText(title);
	}

}
