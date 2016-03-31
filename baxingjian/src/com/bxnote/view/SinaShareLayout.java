package com.bxnote.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.bean.Note;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.AsyncImageView;
import com.bxnote.subview.Title;
import com.bxnote.utils.AsyncImageTask;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.Utils;

public class SinaShareLayout extends BaseRelativelayout implements TextWatcher {
	public LinearLayout mContainLayout;
	public Title mTitle;
	public LinearLayout mSinaContainLayout;
	public RelativeLayout mContainContentLayout;
	public TextView mContentTV;
	public EditText mContentET;
	public AsyncImageView mLetter;
	private Consumer mConsumer;
	private Note mNote;
	// params
	private LayoutParams mContainParams;
	private LayoutParams mTitleParams;
	private android.widget.LinearLayout.LayoutParams mSinaContainParams;
	private LayoutParams mContentParams;
	private LayoutParams mContentEtParams;
	private LayoutParams mContentTVParams;
	private android.widget.LinearLayout.LayoutParams mLetterParams;
	// number
	private int NUMBER = 90;

	public SinaShareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SinaShareLayout(Context context, int mHeight, int mWidth, Note note,
			Consumer mConsumer) {
		super(context, mHeight, mWidth);
		mNote = note;
		this.mConsumer = mConsumer;
		initView();
		initParams();
		addContentView();
		addContainLayout();
		addAllView();
		addView(mContainLayout, mContainParams);
		initData();
		Utils.isChangeTheme(this, getContext());
	}

	private void initData() {
		mTitle.mMenuIV.setImageResource(R.drawable.back_touched);
		mTitle.mEditIV.setImageResource(R.drawable.finish_touch);
		mTitle.setBackgroundResource(R.drawable.title);
		mSinaContainLayout.setBackgroundColor(Color.WHITE);
		if (!Utils.isObject(mNote)) {
			mLetter.setImageUrl(mNote.imageLocation);
			mConsumer.add(new AsyncImageTask(getContext(), mLetter));
		}
	}

	private void addAllView() {
		mContainLayout.setOrientation(LinearLayout.VERTICAL);
		mContainLayout.addView(mTitle, mTitleParams);
		mContainLayout.addView(mSinaContainLayout, mSinaContainParams);
	}

	private void addContainLayout() {
		mSinaContainLayout.setGravity(Gravity.RIGHT);
		mSinaContainLayout.setOrientation(LinearLayout.VERTICAL);
		mSinaContainLayout.addView(mContainContentLayout, mContentParams);
		mSinaContainLayout.addView(mLetter, mLetterParams);
		mLetter.setBackgroundColor(Color.RED);
	}

	private void addContentView() {
		mContainContentLayout.setGravity(Gravity.RIGHT);
		mContainContentLayout.addView(mContentET, mContentEtParams);
		mContainContentLayout.addView(mContentTV, mContentTVParams);
		mContentET.addTextChangedListener(this);
		mContentET.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				NUMBER) });
		mContentET.setGravity(Gravity.TOP);
		mContentET.setHint("发表一下评论");
		mContentET.setBackgroundColor(Color.TRANSPARENT);
		mContentTV.setGravity(Gravity.RIGHT);
		mContentTV.setText("90");
	}

	public SinaShareLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTitle = new Title(getContext(), mHeight, mWidth);
		mContentET = new EditText(getContext());
		mSinaContainLayout = new LinearLayout(getContext());
		mLetter = new AsyncImageView(getContext());
		mContainContentLayout = new RelativeLayout(getContext());
		mContentTV = new TextView(getContext());
		mContainLayout = new LinearLayout(getContext());
	}

	@Override
	protected void initParams() {
		mContainParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HOME_TITLE_HEIGHT, mHeight));
		mSinaContainParams = new android.widget.LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		mSinaContainParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN20,
				mWidth);
		mSinaContainParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN20,
				mHeight);
		mSinaContainParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN20,
				mWidth);
		mSinaContainParams.bottomMargin = Utils.getHeight(
				ConfigLayout.MARGIN20, mHeight);
		mContentParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.SHARE_CONTENT_HEIGHT, mHeight));
		mContentTVParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mContentTVParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mContentTVParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN20,
				mWidth);
		mContentEtParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mLetterParams = new android.widget.LinearLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.ZOOM_IN_PHOTO_WIDTH, mWidth),
				Utils.getHeight(ConfigLayout.ZOOM_IN_PHOTO_HEIGHT, mHeight));
		mLetterParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN20,
				mHeight);
		mContentTVParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN20,
				mWidth);

	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (StringUtils.isEmpty(mContentET.getText().toString())) {
			return;
		}
		int size = mContentET.getText().toString().length();
		int surplus = NUMBER - size;
		mContentTV.setText("" + surplus);

	}
}
