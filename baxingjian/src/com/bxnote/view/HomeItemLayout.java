package com.bxnote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.bean.Note;
import com.bxnote.callback.ShareCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.AsyncImageView;
import com.bxnote.subview.ItemRightLayout;
import com.bxnote.utils.AsyncImageTask;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.Utils;

public class HomeItemLayout extends BaseLinearLyaout {
	private LinearLayout mAllLayout;
	private RelativeLayout mBackGroundLayout;// 设置line和dot
	private ImageView mDotIV;
	private ImageView mBackGroundIV;
	private RelativeLayout mNoteLayout;// 设置背景图和保存的图片
	private ImageView mLeftIV;
	private AsyncImageView mPhotoIV;
	// 右侧的按钮
	private ItemRightLayout mItemRightLayout;
	// LayoutParams
	private LayoutParams mAllParams;
	private LayoutParams mBackGroundParams;
	private android.widget.RelativeLayout.LayoutParams mDotParams;
	private android.widget.RelativeLayout.LayoutParams mBackGroundIVParams;
	private LayoutParams mNoteParams;
	private android.widget.RelativeLayout.LayoutParams mLeftParams;
	private android.widget.RelativeLayout.LayoutParams mPhotoParams;
	private LayoutParams mItemRightParams;
	//
	private Note mNote;
	private ShareCallback<Note> mShareCallback;
	private Consumer mConsumer;

	public HomeItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HomeItemLayout(Context context, int mHeight, int mWidth, Note note,
			ShareCallback<Note> shareCallback, Consumer consumer) {
		super(context, mHeight, mWidth);
		this.mShareCallback = shareCallback;
		this.mNote = note;
		this.mConsumer = consumer;
		initParams();
		initView();
		addleftView();
		addCenterView();
		addView();
		addView(mAllLayout, mAllParams);
		initData();
		mPhotoIV.setOnClickListener(mShwoLargerOnClickListener);
	}

	private void initData() {
		mLeftIV.setBackgroundResource(R.drawable.small_letter);
		mBackGroundIV.setImageResource(R.drawable.left_line);
		mDotIV.setImageResource(R.drawable.dot);
		if (!Utils.isObject(mNote)) {
			mPhotoIV.setImageUrl(mNote.imageLocation);
			mConsumer.add(new AsyncImageTask(getContext(), mPhotoIV));
		}
	}

	private void addCenterView() {
		mNoteLayout.addView(mLeftIV, mLeftParams);
		mNoteLayout.addView(mPhotoIV, mPhotoParams);
	}

	private void addleftView() {
		mBackGroundLayout.addView(mBackGroundIV, mBackGroundIVParams);
		mBackGroundLayout.addView(mDotIV, mDotParams);
	}

	private void addView() {
		mAllLayout.setOrientation(LinearLayout.HORIZONTAL);
		mAllLayout.addView(mBackGroundLayout, mBackGroundParams);
		mAllLayout.addView(mNoteLayout, mNoteParams);
		mAllLayout.addView(mItemRightLayout, mItemRightParams);
	}

	public HomeItemLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mAllLayout = new LinearLayout(getContext());
		mBackGroundLayout = new RelativeLayout(getContext());
		mDotIV = new ImageView(getContext());
		mBackGroundIV = new ImageView(getContext());
		mNoteLayout = new RelativeLayout(getContext());
		mLeftIV = new ImageView(getContext());
		mPhotoIV = new AsyncImageView(getContext());
		mItemRightLayout = new ItemRightLayout(getContext(), mHeight, mWidth,
				mNote, mShareCallback);
	}

	@Override
	protected void initParams() {
		mAllParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HOME_ITEM_HEIGHT, mHeight));
		mAllParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN120, mWidth);
		mBackGroundParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_LEFT_DOT_WIDTH, mWidth),
				LayoutParams.MATCH_PARENT);
		mDotParams = new android.widget.RelativeLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.HOME_ITEM_LEFT_DOT_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.HOME_ITEM_LEFT_DOT_HEIHGT, mWidth));
		mDotParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN120, mHeight);
		mBackGroundIVParams = new android.widget.RelativeLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.HOME_ITEM_LEFT_DOT_WIDTH, mWidth),
				LayoutParams.MATCH_PARENT);
		mNoteParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_CENTER_NOTE_BACKGROUND_WIDHT, mWidth),
				Utils.getHeight(
						ConfigLayout.HOME_CENTER_NOTE_BACKGROUND_HEIGHT,
						mHeight));
		mNoteParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN60, mWidth);
		mNoteParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN88, mHeight);
		mNoteParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN48, mWidth);
		mLeftParams = new android.widget.RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mPhotoParams = new android.widget.RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mPhotoParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN20, mWidth);
		mItemRightParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.HOME_ITEM_BOTTOM_LINE_WIDTH, mWidth),
				LayoutParams.MATCH_PARENT);
	}
	public void cancel(){
		mPhotoIV.restore();
	}
	OnClickListener mShwoLargerOnClickListener =  new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (!Utils.isObject(mShareCallback)) {
				mShareCallback.showLargerPhoto(mNote);
			}
		}
	};
}
