package com.bxnote.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bxnote.bean.Start;
import com.bxnote.callback.AnimationCallback;
import com.bxnote.utils.Utils;
import com.bxnote.view.StartPagerItemLayout;

public class StartViewPager extends PagerAdapter {
	private List<Start> mList;
	private Context mContext;
	private int mHeight;
	private int mWidth;
	private HashMap<Integer, View> mMap = new HashMap<Integer, View>();
	private AnimationCallback mAnimationCallback;
	public StartViewPager(Context mContext, List<Start> mList, int height,
			int width, AnimationCallback animationCallback) {
		super();
		this.mAnimationCallback = animationCallback;
		this.mContext = mContext;
		this.mList = mList;
		this.mHeight = height;
		this.mWidth = width;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = mMap.get(position);
		if (!Utils.isObject(view)) {
			container.removeView(view);
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		StartPagerItemLayout startPagerItemLayout = new StartPagerItemLayout(
				mList.get(position), mContext, mHeight, mWidth, position,
				mAnimationCallback);
		startPagerItemLayout.initData();
		container.addView(startPagerItemLayout);
		mMap.put(position, startPagerItemLayout);
		return startPagerItemLayout;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		View view = mMap.get(position);
		if (!Utils.isObject(view)) {
			if (view instanceof StartPagerItemLayout) {
				StartPagerItemLayout startPagerItemLayyout = (StartPagerItemLayout) view;
				if (!startPagerItemLayyout.isExecAnimation) {
					startPagerItemLayyout.isExecAnimation = true;
					startPagerItemLayyout.startAnimation();
				}
			}
		}
	}

	public void cancelAnimation(int current) {
		if (current == 0) {
			return;
		}
		Set<Integer> ite = mMap.keySet();
		@SuppressWarnings("rawtypes")
		Iterator iterator = ite.iterator();
		while (iterator.hasNext()) {
			int position = (Integer) iterator.next();
			if (position < current) {
				View view = mMap.get(position);
				if (!Utils.isObject(view)) {
					if (view instanceof StartPagerItemLayout) {
						StartPagerItemLayout startPagerItemLayyout = (StartPagerItemLayout) view;
						if (!startPagerItemLayyout.isExecAnimation) {
							startPagerItemLayyout.isExecAnimation = false;
							startPagerItemLayyout.cancelAnimation();
						}
					}
				}
			}
		}
	}
}
