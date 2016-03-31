package com.bxnote.activity;

import com.bxnote.subview.OtherTitle;

import android.os.Bundle;

public class TestActivityy extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		OtherTitle other = new OtherTitle(this,mHeight,mWidth);
		setContentView(other);
	}
}
