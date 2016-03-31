package com.bxnote.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {
	public int mWidth;
	public int mHeight;
	protected SlidingMenu mSlidingMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Constant.sScreenWdith = Utils.getScreentWidth(this);
		Constant.sScreenHeihgt = Utils.getScreenHeihgt(this);
		mHeight = Utils.getScreenHeihgt(this);
		mWidth = Utils.getScreentWidth(this);
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.menu_frame);
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay()
				.getWidth() / 40);
		mSlidingMenu.setBehindOffset(0);// mWidth
		mSlidingMenu.setFadeEnabled(true);
		mSlidingMenu.setFadeDegree(0.4f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// TOUCHMODE_FULLSCREEN
		mSlidingMenu.setBehindScrollScale(0.99f);
	}

	public  void initView(){};

	public  void initData(){};

	public void successData() {
	}
}
