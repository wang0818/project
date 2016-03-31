package com.bxnote.view;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.HandWriteChooseLayout;
import com.bxnote.subview.ShareLayout;
import com.bxnote.subview.Title;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class HomeLayout extends BaseRelativelayout {
	public Title mTitle;
	public ImageView mMonthIV;
	public ImageView mTopLine;
	public ListView mBXNoteLV;
	public TextView mGaryBackTV;
	public ShareLayout mShareLayout;
//	public HandWriteChooseLayout mHandWriteChooseLayout;
	public ImageView mHandIV;
	public ImageView mInputIV;

	public ImageView mGrayIV;
	// layout
	private LayoutParams mTitleParams;
	private LayoutParams mMonthParams;
	private LayoutParams mTopParams;
	private LayoutParams mBXNoteParams;
	private LayoutParams mShareParams;
	private LayoutParams mHandWriteChooseParams;
	private LayoutParams mInputParams;
	private LayoutParams mHandParams;
	private LayoutParams mGrayParams;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> mMonthMap = new HashMap<Integer, Integer>();

	public HomeLayout(Context context) {
		super(context);
	}

	public HomeLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		Utils.isChangeTheme(this, getContext());
		initView();
		initParams();
		addMonth();
		addView();
		initData();
	}

	private void addMonth() {
		mMonthMap.put(1, R.drawable.month1);
		mMonthMap.put(2, R.drawable.month2);
		mMonthMap.put(3, R.drawable.month3);
		mMonthMap.put(4, R.drawable.month4);
		mMonthMap.put(5, R.drawable.month5);
		mMonthMap.put(6, R.drawable.month6);
		mMonthMap.put(7, R.drawable.month7);
		mMonthMap.put(8, R.drawable.month8);
		mMonthMap.put(9, R.drawable.month9);
		mMonthMap.put(10, R.drawable.month10);
		mMonthMap.put(11, R.drawable.month11);
		mMonthMap.put(12, R.drawable.month12);

	}

	private void initData() {
		mMonthIV.setImageResource(R.drawable.month1);
		mTopLine.setImageResource(R.drawable.upper_line);
		mBXNoteLV.setDividerHeight(Constant.DEFAULE_ZERO);
		mHandIV.setImageResource(R.drawable.write);
		mInputIV.setBackgroundResource(R.drawable.input);
	}

	private void addView() {
		addView(mTitle, mTitleParams);
		addView(mBXNoteLV, mBXNoteParams);
		addView(mTopLine, mTopParams);
		addView(mMonthIV, mMonthParams);
		addView(mGrayIV, mGrayParams);
		addView(mHandIV, mHandParams);
		addView(mInputIV, mInputParams);
		addView(mShareLayout, mShareParams);
//		addView(mHandWriteChooseLayout, mHandWriteChooseParams);
		mGrayIV.setBackgroundColor(ColorConstant.HALF_TRANS);
	}

	@Override
	protected void initView() {
		mTitle = new Title(getContext(), mHeight, mWidth);
		mMonthIV = new ImageView(getContext());
		mTopLine = new ImageView(getContext());
		mBXNoteLV = new ListView(getContext());
		mGaryBackTV = new TextView(getContext());
		mShareLayout = new ShareLayout(getContext(), mHeight, mWidth);
//		mHandWriteChooseLayout = new HandWriteChooseLayout(getContext(),
//				mHeight, mWidth);
		mHandIV = new ImageView(getContext());
		mInputIV = new ImageView(getContext());
		mGrayIV = new ImageView(getContext());
		mGrayIV.setVisibility(View.GONE);
		mShareLayout.setVisibility(View.GONE);
		mHandIV.setVisibility(View.GONE);
		mInputIV.setVisibility(View.GONE);
//		mHandWriteChooseLayout.setVisibility(View.GONE);
	}

	@Override
	protected void initParams() {
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HOME_TITLE_HEIGHT, mHeight));
		mMonthParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MONTH_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MONTH_ICON_HEIGHT, mHeight));
		mMonthParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN80, mWidth);
		mMonthParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN125,
				mHeight);
		mTopParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_TOP_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_TOP_LINE_HEIGHT, mHeight));
		mTopParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN155, mWidth);
		mTopParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN170, mHeight);
		mBXNoteParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mBXNoteParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN183,
				mHeight);
		mShareParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.SHARE_HEIGHT, mHeight));
		mHandWriteChooseParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_HAND_WRITE_CHOOSE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mHandWriteChooseParams.topMargin = Utils.getHeight(
				ConfigLayout.MARGIN183, mHeight);
		mHandWriteChooseParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mGrayParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mInputParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight));
		mInputParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mInputParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIIN34,
				mWidth);
		mInputParams.topMargin = Utils
				.getHeight(
						(ConfigLayout.HOME_TITLE_HEIGHT - ConfigLayout.HOME_MENU_ICON_HEIGHT)
								/ 2
								+ ConfigLayout.HOME_MENU_ICON_HEIGHT
								+ ConfigLayout.MARGIN20, mHeight);
		mHandParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight));
		mHandParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mHandParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIIN34
				+ ConfigLayout.HOME_MENU_ICON_WIDTH + ConfigLayout.MARGIN20,
				mWidth);
		mHandParams.topMargin = Utils
				.getHeight(
						(ConfigLayout.HOME_TITLE_HEIGHT - ConfigLayout.HOME_MENU_ICON_HEIGHT) / 2,
						mHeight);
		// mContainParams = new LayoutParams(Utils.getWidth(
		// ConfigLayout.HOME_MENU_ICON_WIDTH, mWidth), Utils.getWidth(
		// ConfigLayout.HOME_MENU_ICON_HEIGHT, mHeight))
	}

	public void setResourceID(int month) {
		if (month <= Constant.DEFAULE_ZERO) {
			return;
		}
		mMonthIV.setImageResource(mMonthMap.get(month));
	}

	public void settingTheme() {
		Utils.isChangeTheme(this, getContext());
	}
}
