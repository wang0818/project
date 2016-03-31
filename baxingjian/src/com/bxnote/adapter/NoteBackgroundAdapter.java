package com.bxnote.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bxnote.subview.LetterLayout;
import com.bxnote.utils.Consumer;

public class NoteBackgroundAdapter extends BaseAdapter {
	private List<String> mList;
	private int mHeight;
	private int mWidth;
	private Context mContext;
	private Consumer mConuser;

	public NoteBackgroundAdapter(List<String> mList, int mHeight, int mWidth,
			Context mContext, Consumer consumer) {
		super();
		this.mList = mList;
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		this.mContext = mContext;
		this.mConuser = consumer;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LetterLayout letterLayout = new LetterLayout(mContext, mHeight, mWidth, mList.get(arg0), mConuser);
		return letterLayout;
	}
}
