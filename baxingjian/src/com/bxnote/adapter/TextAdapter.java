package com.bxnote.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bxnote.subview.FontLayout;

public class TextAdapter extends BaseAdapter {
	private List<String> mList;
	private int mHeight;
	private int mWidth;
	private Context mContext;
	public TextAdapter(List<String> mList, int mHeight, int mWidth,
			Context mContext) {
		super();
		this.mList = mList;
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FontLayout font = new FontLayout(mContext, mHeight, mWidth,mList.get(position));
		return font;
	}

}
