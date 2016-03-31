package com.bxnote.test;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.bxnote.activity.BaseActivity;

public class Test extends BaseActivity {
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TextView text = new TextView(this);
		text.setText("测试");
		final TranslateAnimation transLateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF,0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		transLateAnimation.setDuration(5000);
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				text.startAnimation(transLateAnimation);
			}
		});
		setContentView(text);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	};
}
