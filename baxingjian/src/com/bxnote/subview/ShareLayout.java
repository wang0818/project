package com.bxnote.subview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.bxnote.activity.R;
import com.bxnote.baseview.BaseLinearLyaout;
import com.bxnote.utils.Constant;

public class ShareLayout extends BaseLinearLyaout {
	public ShareButton mWxIV;
	public ShareButton mWxFriendsIV;
	public ShareButton mSinaIV;
	public ShareButton mQQQoneIV;
	public ShareButton mShareEmailIV;
	// prams
	private LayoutParams mWxParams;
	private LayoutParams mWxFriendsParams;
	private LayoutParams mSinaParams;
	private LayoutParams mQQQoneParams;
	private LayoutParams mShareEmailParams;

	// mShareParams.leftMargin = Utils.getWidth(ConfigLayout.MARGIN33, mWidth);
	// mShareParams.rightMargin = Utils
	// .getWidth(ConfigLayout.MARGIN33, mWidth);
	public ShareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ShareLayout(Context context, int mHeight, int mWidth) {
		super(context, mHeight, mWidth);
		initView();
		initParams();
		addView();
		initData();
//		this.setPadding(Utils.getWidth(ConfigLayout.MARGIN33, mWidth), 0,
//				Utils.getWidth(ConfigLayout.MARGIN33, mWidth), 0);
	}

	private void initData() {
		this.setBackgroundResource(R.drawable.share_bg_new);
		this.setGravity(Gravity.CENTER_VERTICAL);
		mWxIV.mTopIV.setImageResource(R.drawable.wxbg);
		mWxIV.mContentTV.setText("微信好友");
		mWxFriendsIV.mTopIV.setImageResource(R.drawable.wxpengyouquan);
		mWxFriendsIV.mContentTV.setText("朋友圈");
		mSinaIV.mTopIV.setImageResource(R.drawable.weibo);
		mSinaIV.mContentTV.setText("微博");
		mQQQoneIV.mTopIV.setImageResource(R.drawable.qqbg);
		mQQQoneIV.mContentTV.setText("QQ空间");
		mShareEmailIV.mTopIV.setImageResource(R.drawable.email);
		mShareEmailIV.mContentTV.setText("邮箱");
	}

	private void addView() {
		setOrientation(LinearLayout.HORIZONTAL);
		addView(mWxIV, mWxParams);
		addView(mWxFriendsIV, mWxFriendsParams);
		addView(mSinaIV, mSinaParams);
		addView(mQQQoneIV, mQQQoneParams);
		addView(mShareEmailIV, mShareEmailParams);
	}

	public ShareLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		mWxIV = new ShareButton(getContext(), mHeight, mWidth);
		mWxFriendsIV = new ShareButton(getContext(), mHeight, mWidth);
		mSinaIV = new ShareButton(getContext(), mHeight, mWidth);
		mQQQoneIV = new ShareButton(getContext(), mHeight, mWidth);
		mShareEmailIV = new ShareButton(getContext(), mHeight, mWidth);
	}

	@Override
	protected void initParams() {
		mWxParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mWxParams.weight = Constant.DEFAULE_ONE;
		mWxFriendsParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mWxFriendsParams.weight = Constant.DEFAULE_ONE;
		mSinaParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mSinaParams.weight = Constant.DEFAULE_ONE;
		mQQQoneParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mQQQoneParams.weight = Constant.DEFAULE_ONE;
		mShareEmailParams =  new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mShareEmailParams.weight = Constant.DEFAULE_ONE;

	}

}
