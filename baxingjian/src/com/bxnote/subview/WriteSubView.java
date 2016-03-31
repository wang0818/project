package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Utils;

public class WriteSubView extends BaseLinearLyaout {
	public LinearLayout mLinearLyaout;
	public OtherLetterLayout mChapeauLayout; // 起首
	public OtherLetterLayout mGreetingsLayout;// 问候语
	public LineHandLayout mOneLineLayout;
	public LineHandLayout mTwoLineLayout;
	public LineHandLayout mThreeLineLayout;
	public LineHandLayout mFourLineLayout;
	public LineHandLayout mFiveLineLayout;
	public LinearLayout mEndLayout;
	public OtherLetterLayout mEndLanLayout;// 結語
	public RelativeLayout mSignatureLayout;
	public OtherLetterLayout mSignatureNameLayout;// 署名
	//
	private LayoutParams mLinearParams;
	private LayoutParams mChapeauParams;
	private LayoutParams mGreetingsParams;
	private LayoutParams mOneLineParams;
	private LayoutParams mTwoLineParams;
	private LayoutParams mThreeLineParams;
	private LayoutParams mFourLineParams;
	private LayoutParams mFiveLineParams;
	private LayoutParams mEndParams;
	private LayoutParams mEndLanguageParams;
	private LayoutParams mSignatureParams;
	private android.widget.RelativeLayout.LayoutParams mSignatureNameParams;

	public WriteSubView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WriteSubView(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initParams();
		initView();
		addChapeView();
		addEndView();
		addSingnatureView();
		addSubView();
	}

	private void addSubView() {
		addView(mSignatureLayout, mSignatureParams);
		addView(mEndLayout, mEndParams);
		addView(mFiveLineLayout, mFiveLineParams);
		addView(mFourLineLayout, mFourLineParams);
		addView(mThreeLineLayout, mThreeLineParams);
		addView(mTwoLineLayout, mTwoLineParams);
		addView(mOneLineLayout, mOneLineParams);
		addView(mLinearLyaout, mLinearParams);
		mSignatureLayout.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	private void addSingnatureView() {
		mSignatureLayout.addView(mSignatureNameLayout, mSignatureNameParams);
	}

	private void addEndView() {
		mEndLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		mEndLayout.addView(mEndLanLayout, mEndLanguageParams);
	}

	private void addChapeView() {
		mLinearLyaout.setGravity(Gravity.CENTER_HORIZONTAL);
		mLinearLyaout.setOrientation(LinearLayout.VERTICAL);
		mLinearLyaout.addView(mChapeauLayout, mChapeauParams);
		mLinearLyaout.addView(mGreetingsLayout, mGreetingsParams);
	}

	public WriteSubView(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		mLinearLyaout = new LinearLayout(getContext());
		mChapeauLayout = new OtherLetterLayout(getContext(), mHeight, mWidth);
		mGreetingsLayout = new OtherLetterLayout(getContext(), mHeight, mWidth);// 问候语
		mOneLineLayout = new LineHandLayout(getContext(), mHeight, mWidth);
		mTwoLineLayout = new LineHandLayout(getContext(), mHeight, mWidth);
		mThreeLineLayout = new LineHandLayout(getContext(), mHeight, mWidth);
		mFourLineLayout = new LineHandLayout(getContext(), mHeight, mWidth);
		mFiveLineLayout = new LineHandLayout(getContext(), mHeight, mWidth);
		mEndLayout = new LinearLayout(getContext());
		mEndLanLayout = new OtherLetterLayout(getContext(), mHeight, mWidth);
		mSignatureLayout = new RelativeLayout(getContext());
		mSignatureNameLayout = new OtherLetterLayout(getContext(), mHeight,
				mWidth);
	}

	@Override
	protected void initParams() {
		mLinearParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mChapeauParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);// Utils.getHeight(
		// ConfigLayout.WRITE_LINE_OTHER_HEIGHT, mHeight)
		mChapeauParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN55,
				mHeight);
		mGreetingsParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mGreetingsParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN45,
				mHeight);
		mOneLineParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mTwoLineParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mThreeLineParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mFourLineParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mFiveLineParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mEndParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth), Utils.getHeight(
				ConfigLayout.WRITE_LINE_HEIGHT, mHeight));
		mEndLanguageParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mEndLanguageParams.topMargin = Utils.getHeight(ConfigLayout.MARGIN55,
				mHeight);
		mSignatureParams = new LayoutParams(Utils.getWidth(
				ConfigLayout.WRITE_LINE_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mSignatureNameParams = new android.widget.RelativeLayout.LayoutParams(
				Utils.getWidth(ConfigLayout.WRITE_LINE_OTHER_WIDTH, mWidth),
				LayoutParams.WRAP_CONTENT);
		mSignatureNameParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mSignatureNameParams.bottomMargin = Utils.getHeight(
				ConfigLayout.MARGIN55, mHeight);

	}

	public void setTextType(String type) {
		mChapeauLayout.setTextType(type); // 起首
		mGreetingsLayout.setTextType(type);// 问候语
		mOneLineLayout.setTextType(type);
		mTwoLineLayout.setTextType(type);
		mThreeLineLayout.setTextType(type);
		mFourLineLayout.setTextType(type);
		mFiveLineLayout.setTextType(type);
		mEndLanLayout.setTextType(type);// 結語
		mSignatureNameLayout.setTextType(type);// 署名
	}
}
