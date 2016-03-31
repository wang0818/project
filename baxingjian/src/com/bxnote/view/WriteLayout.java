package com.bxnote.view;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseRelativelayout;
import com.bxnote.bean.Note;
import com.bxnote.config.ConfigLayout;
import com.bxnote.subview.FontAndLetterLayout;
import com.bxnote.subview.Title;
import com.bxnote.subview.WriteChooseLayout;
import com.bxnote.subview.WriteSubView;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.Utils;

public class WriteLayout extends BaseRelativelayout {
	public Title mTitle;
	public WriteSubView mWriteView;
	public WriteChooseLayout mWriteChooseLayout;
	public ImageView mHandIV;
	public ImageView mInputIV;
	public FontAndLetterLayout mFontAndChooseLayout;
	public ImageView mGrayIV;
	public ImageView mShowIntruduceIV;
	// params
	private LayoutParams mTitleParams;
	private LayoutParams mWriteParams;
	private LayoutParams mWriteChooseParams;
	private LayoutParams mFontAndLetterParams;
	private LayoutParams mGrayParams;
	private LayoutParams mIntroduceParams;
	private LayoutParams mInputParams;
	private LayoutParams mHandWriteParams;
	//
	private Note mNote;
	private HashMap<String, Integer> mBackGround = new HashMap<String, Integer>();

	public WriteLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WriteLayout(Context context, int mHeight, int mWidth, Note mNote,
			HashMap<String, Integer> hashMap) {
		super(context, mHeight, mWidth);
		mBackGround = hashMap;
		this.mNote = mNote;
		Utils.isChangeTheme(this, getContext());
		initView();
		initParams();
		addView();
		initData();
	}

	private void initData() {
		mWriteView.setBackgroundResource(R.drawable.letter1);
		mTitle.mEditIV.setImageResource(R.drawable.finish_touch);
		mTitle.mMenuIV.setImageResource(R.drawable.back_touched);
		mTitle.setBackgroundResource(R.drawable.title);
		mWriteView.mChapeauLayout.setText("起\n首");
		mWriteView.mGreetingsLayout.setText("问\n候\n语");
		mWriteView.mEndLanLayout.setText("结\n语");
		mWriteView.mSignatureNameLayout.setText("署\n名");
		if (!Utils.isObject(mNote)) {
			if (!StringUtils.isEmpty(mNote.mBackground)) {
				mWriteView.setBackgroundResource(mBackGround
						.get(mNote.mBackground));
			}
			mWriteView.setTextType(mNote.mTextType);
			// 起首
			mWriteView.mChapeauLayout.mEditET.setVisibility(View.GONE);
			mWriteView.mChapeauLayout.mIntroduceTV.setVisibility(View.VISIBLE);
			// 问候语
			mWriteView.mGreetingsLayout.mEditET.setVisibility(View.GONE);
			mWriteView.mGreetingsLayout.mIntroduceTV
					.setVisibility(View.VISIBLE);
			// 结语
			mWriteView.mEndLanLayout.mEditET.setVisibility(View.GONE);
			mWriteView.mEndLanLayout.mIntroduceTV.setVisibility(View.VISIBLE);
			// 署名
			mWriteView.mSignatureNameLayout.mEditET.setVisibility(View.GONE);
			mWriteView.mSignatureNameLayout.mIntroduceTV
					.setVisibility(View.VISIBLE);
			// 添加内容
			mWriteView.mChapeauLayout.mIntroduceTV
					.setBackgroundResource(R.drawable.tips);
			mWriteView.mChapeauLayout.mIntroduceTV.setTextColor(Color.BLACK);
			if (!StringUtils.isEmpty(mNote.mChapeaut)) {
				mWriteView.mChapeauLayout.mEditET.setText(mNote.mChapeaut);
				mWriteView.mChapeauLayout.mIntroduceTV.setText(mNote.mChapeaut);
			} else {
				mWriteView.mChapeauLayout.mIntroduceTV.setText("起首");
			}
			mWriteView.mGreetingsLayout.mIntroduceTV.setTextColor(Color.BLACK);
			mWriteView.mGreetingsLayout.mIntroduceTV
					.setBackgroundResource(R.drawable.black);
			if (!StringUtils.isEmpty(mNote.mGreetings)) {
				mWriteView.mGreetingsLayout.mEditET.setText(mNote.mGreetings);
				mWriteView.mGreetingsLayout.mIntroduceTV
						.setText(mNote.mGreetings);
			} else {
				mWriteView.mGreetingsLayout.mIntroduceTV.setText("问候语");
			}
			mWriteView.mEndLanLayout.mIntroduceTV.setTextColor(Color.BLACK);
			mWriteView.mEndLanLayout.mIntroduceTV
					.setBackgroundResource(R.drawable.black);
			if (!StringUtils.isEmpty(mNote.mEndLan)) {
				mWriteView.mEndLanLayout.mEditET.setText(mNote.mEndLan);
				mWriteView.mEndLanLayout.mIntroduceTV.setText(mNote.mEndLan);
			} else {
				mWriteView.mEndLanLayout.mIntroduceTV.setText("结语");
			}
			mWriteView.mSignatureNameLayout.mIntroduceTV
					.setTextColor(Color.BLACK);
			mWriteView.mSignatureNameLayout.mIntroduceTV
					.setBackgroundResource(R.drawable.black);
			if (!StringUtils.isEmpty(mNote.mSignatureName)) {
				mWriteView.mSignatureNameLayout.mEditET
						.setText(mNote.mSignatureName);
				mWriteView.mSignatureNameLayout.mIntroduceTV
						.setText(mNote.mSignatureName);
			} else {
				mWriteView.mSignatureNameLayout.mIntroduceTV.setText("署名");
			}
			// 对数据进行分组显示
			if (!StringUtils.isEmpty(mNote.mOneContent)) {
				mWriteView.mOneLineLayout.mEditText.setVisibility(View.VISIBLE);
				mWriteView.mOneLineLayout.mEditText.setText(mNote.mOneContent);
			}
			if (!StringUtils.isEmpty(mNote.mTwoContent)) {
				mWriteView.mTwoLineLayout.mEditText.setVisibility(View.VISIBLE);
				mWriteView.mTwoLineLayout.mEditText.setText(mNote.mTwoContent);
			}
			if (!StringUtils.isEmpty(mNote.mThreeContent)) {
				mWriteView.mThreeLineLayout.mEditText
						.setVisibility(View.VISIBLE);
				mWriteView.mThreeLineLayout.mEditText
						.setText(mNote.mThreeContent);
			}
			if (!StringUtils.isEmpty(mNote.mFourContent)) {
				mWriteView.mFourLineLayout.mEditText
						.setVisibility(View.VISIBLE);
				mWriteView.mFourLineLayout.mEditText
						.setText(mNote.mFourContent);
			}
			if (!StringUtils.isEmpty(mNote.mFiveContent)) {
				mWriteView.mFiveLineLayout.mEditText
						.setVisibility(View.VISIBLE);
				mWriteView.mFiveLineLayout.mEditText
						.setText(mNote.mFiveContent);
			}
		}
	}

	private void addView() {
		addView(mTitle, mTitleParams);
		addView(mWriteView, mWriteParams);
		addView(mWriteChooseLayout, mWriteChooseParams);
		addView(mFontAndChooseLayout, mFontAndLetterParams);
		addView(mGrayIV, mGrayParams);
		addView(mShowIntruduceIV, mIntroduceParams);
		mGrayIV.setBackgroundColor(0x7f000000);
		mGrayIV.setVisibility(View.GONE);
		mShowIntruduceIV.setVisibility(View.GONE);
	}

	public WriteLayout(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mTitle = new Title(getContext(), mHeight, mWidth);
		mWriteView = new WriteSubView(getContext(), mHeight, mWidth);
		mWriteChooseLayout = new WriteChooseLayout(getContext(), mHeight,
				mWidth);
		mFontAndChooseLayout = new FontAndLetterLayout(getContext(), mHeight,
				mWidth);
		mHandIV = new ImageView(getContext());
		mInputIV = new ImageView(getContext());
		mGrayIV = new ImageView(getContext());
		mShowIntruduceIV = new ImageView(getContext());
	}

	@Override
	protected void initParams() {
		mTitleParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.HOME_TITLE_HEIGHT, mHeight));
		mWriteParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mWriteParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN138,
				mHeight);
		mWriteParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN80, mWidth);
		mWriteParams.rightMargin = Utils
				.getWidth(ConfigLayout.MARGIN80, mWidth);
		mWriteChooseParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mWriteChooseParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mWriteChooseParams.bottomMargin = Utils.getWidth(
				ConfigLayout.MARGIN238, mWidth);
		mFontAndLetterParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.WRITE_BOTTOM_HEIGHT, mHeight));
		mFontAndLetterParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mGrayParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mIntroduceParams = new LayoutParams(Utils.getWidth(
		// ConfigLayout.WRITE_INTRUDUCE_WIDTH, mWidth), Utils.getHeight(
		// ConfigLayout.WRITE_INTRUDUCE_HEIGHT, mHeight));
		// mIntroduceParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		mIntroduceParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getHeight(ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mIntroduceParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN138,
				mHeight);
		mIntroduceParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN80,
				mWidth);
		mIntroduceParams.rightMargin = Utils.getWidth(ConfigLayout.MARGIN80,
				mWidth);
		mInputParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}
}
