package com.bxnote.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.callback.CancellationCallback;
import com.bxnote.callback.TouchCallback;
import com.bxnote.utils.Utils;
import com.bxnote.view.MenuLayout;

@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {
	private MenuLayout mMenuLayout;
	private int mHeight;
	private int mWidth;
	private TouchCallback mTouchCallback;
	private ImageView mBackChangeIV;
	private SinaUser mSinaUser;
	private QQUser mQqUser;
	private ImageView mSinaIV;
	private ImageView mQQIV;
	private TextView mCancellationTV;
	private CancellationCallback mCancellationCallback;

	public MenuFragment() {
		super();
	}

	public MenuFragment(int mHeight, int mWidth, TouchCallback touchCallback,
			SinaUser sinaUser, QQUser qqUser,
			CancellationCallback cancellationCallback) {
		super();
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		mTouchCallback = touchCallback;
		this.mSinaUser = sinaUser;
		this.mQqUser = qqUser;
		this.mCancellationCallback = cancellationCallback;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mMenuLayout = new MenuLayout(getActivity(), mHeight, mWidth,
				mTouchCallback, mSinaUser, mQqUser);
		mBackChangeIV = mMenuLayout.mTemaLayout.mBackChangeIV;
		mSinaIV = mMenuLayout.mSinaEncuadernacionLayout.mSinaIV;
		mQQIV = mMenuLayout.mQqEncuadernacionLayout.mSinaIV;
		mCancellationTV = mMenuLayout.mCancellationTV;
		mBackChangeIV.setOnClickListener(mOnBackChangeListener);
		mCancellationTV.setOnClickListener(mCancellationListener);
		mMenuLayout.mSinaEncuadernacionLayout.setOnClickListener(mSinaListener);
		mMenuLayout.mQqEncuadernacionLayout
				.setOnClickListener(mQQChangeListener);
		return mMenuLayout;
	}

	OnClickListener mOnBackChangeListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (Utils.getIsDefaultTheme(getActivity())) {
				Utils.setIsDefaultTheme(getActivity(), false);
				mMenuLayout.setImageRes(R.drawable.new_setting_bg);
				mBackChangeIV.setImageResource(R.drawable.change_theme2_btn);
				mMenuLayout.setColorType(Color.WHITE);
			} else {
				mMenuLayout.setImageRes(R.drawable.setting_bg);
				mBackChangeIV.setImageResource(R.drawable.change_theme1_btn);
				mMenuLayout.setColorType(Color.BLACK);
				Utils.setIsDefaultTheme(getActivity(), true);
			}
			if (!Utils.isObject(mCancellationCallback)) {
				mCancellationCallback.settingTheme();
			}
		}
	};

	public void setSinaLoginOrLoginOut(int resId) {
		mSinaIV.setImageResource(resId);
	}

	public void setQQLoginOrLoginOut(int resId) {
		mQQIV.setImageResource(resId);
	}

	OnClickListener mSinaListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCancellationCallback.registerSina();
		}
	};
	OnClickListener mQQChangeListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCancellationCallback.registerQQ();
		}
	};
	/*
	 * 注销用户
	 */
	private OnClickListener mCancellationListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mCancellationCallback.cancelUser();
		}
	};
}
