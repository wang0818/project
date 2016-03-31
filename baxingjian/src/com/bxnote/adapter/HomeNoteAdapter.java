package com.bxnote.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bxnote.bean.Note;
import com.bxnote.callback.ShareCallback;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.Utils;
import com.bxnote.view.HomeItemLayout;

public class HomeNoteAdapter extends BaseAdapter {
	public int mHeight;
	public int mWidth;
	public Context mContext;
	public List<Note> mNotes;
	private ShareCallback<Note> mShareCallback;
	private Consumer mConsumer;
	private HashMap<Integer, HomeItemLayout> mHomeItemMap = new HashMap<Integer, HomeItemLayout>();
	public HomeNoteAdapter(Context context, int mHeight, int mWidth,
			List<Note> notes, ShareCallback<Note> shareCallback,Consumer consumer) {
		super();
		this.mShareCallback = shareCallback;
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		this.mContext = context;
		this.mNotes = notes;
		this.mConsumer = consumer;

	}

	@Override
	public int getCount() {
		return mNotes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mNotes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HomeItemLayout homeItemLayout = null;
		if (Utils.isObject(mHomeItemMap.get(arg0))) {
			homeItemLayout= new HomeItemLayout(mContext, mHeight,
					mWidth, mNotes.get(arg0), mShareCallback,mConsumer);
			mHomeItemMap.put(arg0, homeItemLayout);
		}else{
			homeItemLayout = mHomeItemMap.get(arg0);
		}
		return homeItemLayout;
	}
	public void cancelBitmap(){
		Set<Integer> set = mHomeItemMap.keySet();
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()) {
			int next = iterator.next();
			HomeItemLayout homeItemLayout = mHomeItemMap.get(next);
			if (!Utils.isObject(homeItemLayout)) {
				homeItemLayout.cancel();
			}
		}
		mHomeItemMap.clear();
	}
	public void clearMap(){
		mHomeItemMap.clear();
	}
}
