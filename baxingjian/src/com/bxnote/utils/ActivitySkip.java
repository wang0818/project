package com.bxnote.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bxnote.activity.R;
import com.bxnote.bean.Note;

public class ActivitySkip {
	private Intent mIntent;
	private Bundle mBundle;
	private Context mContext;
	private Activity mActivity;

	public ActivitySkip(Context context, Class<?> cls, Activity activity) {
		super();
		this.mContext = context;
		this.mIntent = new Intent(context, cls);
		this.mBundle = new Bundle();
		this.mActivity = activity;
	}
	public void startActivity(String name ,String password) {
		BundleUtils.setEmail(mBundle,name);
		BundleUtils.setPassword(mBundle,password);
		startActivity();
	}
	public void startActivity(String name) {
		BundleUtils.setEmail(mBundle,name);
		startActivity();
	}
	public void startActivity(String name,Note note) {
		BundleUtils.setEmail(mBundle,name);
		BundleUtils.setNote(mBundle,note);
		startActivity();
	}
	public void startActivity() {
		mIntent.putExtras(mBundle);
		mContext.startActivity(mIntent);
	}
	public void startActivity(Note note) {
		BundleUtils.setNote(mBundle,note);
		showDialog();
	}
	private void showDialog() {
		mIntent.putExtras(mBundle);
		mContext.startActivity(mIntent);
		mActivity.overridePendingTransition(R.anim.larger, R.anim.larger);
	}
}
