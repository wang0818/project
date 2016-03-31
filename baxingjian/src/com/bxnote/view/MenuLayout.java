package com.bxnote.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.callback.TouchCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.LeftSinaLayout;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class MenuLayout extends BaseLinearLyaout {
	public LeftSinaLayout mTemaLayout;
	public LeftSinaLayout mQqEncuadernacionLayout;
	public LeftSinaLayout mSinaEncuadernacionLayout;
	public TextView mCancellationTV;
	//
	public LayoutParams mTemaParams;
	public LayoutParams mCancellationParams;
	public LayoutParams mQqEnacuadernacionParams;
	public LayoutParams mSinaEnacuaderaciontParams;
	// toch
	private TouchCallback mTouchCallback;
	private SinaUser mSinaUser;
	private QQUser mQQUser;

	public MenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MenuLayout(Context context, int mHeight, int mWidth,
			TouchCallback touchCallback, SinaUser sinaUser, QQUser qqUser) {
		super(context, mHeight, mWidth);
		mSinaUser = sinaUser;
		mQQUser = qqUser;
		mTouchCallback = touchCallback;
		initView();
		initParams();
		setOrientation(LinearLayout.VERTICAL);
		addView(mTemaLayout, mTemaParams);
		addView(mQqEncuadernacionLayout, mQqEnacuadernacionParams);
		addView(mSinaEncuadernacionLayout, mSinaEnacuaderaciontParams);
		addView(mCancellationTV, mCancellationParams);
		initData();
	}

	private void initData() {
		if (Utils.getIsDefaultTheme(getContext())) {
			this.setBackgroundResource(R.drawable.setting_bg);
			this.setColorType(Color.BLACK);
		} else {
			this.setColorType(Color.WHITE);
			this.setBackgroundResource(R.drawable.new_setting_bg);
		}
		if (Utils.isObject(mSinaUser.expiresIn)) {
			this.mSinaEncuadernacionLayout.mSinaIV
			.setImageResource(R.drawable.selected_btn);
		}else{
			if (mSinaUser.expiresIn == Constant.USERLOGOUT) {
				this.mSinaEncuadernacionLayout.mSinaIV
						.setImageResource(R.drawable.unselected_btn);
			} else {
				this.mSinaEncuadernacionLayout.mSinaIV
						.setImageResource(R.drawable.selected_btn);
			}
		}
		if (Utils.isObject(mQQUser)) {
			this.mQqEncuadernacionLayout.mSinaIV
			.setImageResource(R.drawable.selected_btn);
		}else{
			if (mQQUser.expiresIn == Constant.USERLOGOUT) {
				this.mQqEncuadernacionLayout.mSinaIV
						.setImageResource(R.drawable.unselected_btn);
			} else {
				this.mQqEncuadernacionLayout.mSinaIV
						.setImageResource(R.drawable.selected_btn);
			}
		}
		
	}

	public void setImageRes(int resId) {
		this.setBackgroundResource(resId);
	}
	public void setColorType(int color){
		mTemaLayout.setTextColor(color);
		mQqEncuadernacionLayout.setTextColor(color);
		mSinaEncuadernacionLayout.setTextColor(color);
		mCancellationTV.setTextColor(color);
	}
	public MenuLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTemaLayout = new LeftSinaLayout(getContext(), mHeight, mWidth, false);
		mTemaLayout.setText("主题选择");
		mQqEncuadernacionLayout = new LeftSinaLayout(getContext(), mHeight,
				mWidth, true);
		mQqEncuadernacionLayout.setText("腾讯绑定");
		mSinaEncuadernacionLayout = new LeftSinaLayout(getContext(), mHeight,
				mWidth, true);
		mSinaEncuadernacionLayout.setText("微博绑定");
		mCancellationTV = new TextView(getContext());
		Utils.setType(getContext(), mCancellationTV);
		mCancellationTV.setText("注销");
	}

	@Override
	protected void initParams() {
		mTemaParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mTemaParams.topMargin = Utils
				.getHeight(ConfigLayout.MARGIN350, mHeight);
		mCancellationParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mCancellationParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN45,
				mHeight);
		mCancellationParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN110,
				mWidth);
		mQqEnacuadernacionParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mQqEnacuadernacionParams.topMargin = Utils.getHeight(
				ConfigLayout.MARGIN45, mHeight);
		mSinaEnacuaderaciontParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mSinaEnacuaderaciontParams.topMargin = Utils.getHeight(
				ConfigLayout.MARGIN45, mHeight);

	}

	int x = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getX() - x < 10) {
				if (!Utils.isObject(mTouchCallback)) {
					mTouchCallback.leftCallback();
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

}
