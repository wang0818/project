package com.bxnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.bxnote.bean.Note;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.Consumer;
import com.bxnote.view.LargerPhotoLayout;

public class LargerPhotoActivity extends BaseActivity implements OnClickListener {
	private Note mNote;
	private LargerPhotoLayout mLargerPhotoLayout;
	private Consumer mConsumer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mNote = BundleUtils.getNote(bundle);
		mConsumer = new Consumer();
		initView();
		mLargerPhotoLayout.setOnClickListener(this);
	}

	@Override
	public void initView() {
		mLargerPhotoLayout = new LargerPhotoLayout(this, mHeight, mWidth,
				mConsumer, mNote);
		setContentView(mLargerPhotoLayout);
	}

	@Override
	public void onClick(View v) {
		finish();
		this.overridePendingTransition(
				R.anim.larger, R.anim.larger);
	}
}
