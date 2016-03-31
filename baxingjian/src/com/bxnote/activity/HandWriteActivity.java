package com.bxnote.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bxnote.bean.Note;
import com.bxnote.bean.User;
import com.bxnote.callback.HandWriteBitmapCallback;
import com.bxnote.config.ColorConstant;
import com.bxnote.config.ConfigLayout;
import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.dao.SaveUserDataDao;
import com.bxnote.subview.AsynEditText;
import com.bxnote.subview.OtherLetterLayout;
import com.bxnote.subview.Title;
import com.bxnote.subview.WriteSubView;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.AsyncEditTask;
import com.bxnote.utils.BitmapLoader;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.Utils;
import com.bxnote.view.HandWriteLayout;

public class HandWriteActivity extends BaseActivity implements
		HandWriteBitmapCallback {
	private HandWriteLayout mHandWriteLayout;
	private EditText mOneET;
	private EditText mTwoET;
	private EditText mThreeET;
	private EditText mFourET;
	private EditText mFiveET;
	private EditText mChapeauET;
	private EditText mGreetingsET;
	private EditText mEndLanET;
	private EditText mSignatureNameET;
	private OtherLetterLayout mChapeauLayout;
	private OtherLetterLayout mGreetingsLayout;
	private OtherLetterLayout mEndLanLayout;
	private OtherLetterLayout mSignatureNameLayout;
	private ImageView mBackIV;
	private ImageView mSaveIV;
	private ImageView mGrayIV;
	private ImageView mShowIntruduceIV;
	private Title mTitle;
	private WriteSubView mWriteSubView;
	private ImageView mChangeBackGroundIV;
	private String mEmail = "userID";
	private SaveUserDataDao mSaveUserDataDao;
	private ImageView mDeleteIV;
	private ImageView mEnterIV;
	private ImageView mBlankIV;
	private int currentFocusId = Constant.ONE;
	private int endInput;
	private Note mNote;
	private CreateUserDateDao mCreateUserDao;
	private User mUser;
	private List<SpannableString> mOneDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mTwoDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mThreeDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mFourDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mFiveDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mSixDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mSevenDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mEightDatas = new ArrayList<SpannableString>();
	private List<SpannableString> mNineDatas = new ArrayList<SpannableString>();
	private Consumer mConsumer;
	private int count;
	private boolean isStart = true;
	private int changeCount;
	private long dataTime;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> mBacksMap = new HashMap<Integer, Integer>();
	private InputMethodManager mImm;
	private String mBackGround;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		Intent intent = getIntent();
		dataTime = System.currentTimeMillis();
		if (!Utils.isObject(intent)) {
			mEmail = BundleUtils.getEmail(intent.getExtras());
			mNote = BundleUtils.getNote(intent.getExtras());
		}
		if (!Utils.isObject(mNote)) {
			mBackGround = mNote.mBackground;
		}
		mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mConsumer = new Consumer();
		mSaveUserDataDao = new SaveUserDataDao(this);
		mCreateUserDao = new CreateUserDateDao(this);
		mUser = mCreateUserDao.queryUser(mEmail);
		initMap();
		initView();
		successData();
		initListener();
	}

	private void initListener() {
		mBackIV.setOnClickListener(mBackClickListener);
		mSaveIV.setOnClickListener(mSaveClickListener);
		mOneET.addTextChangedListener(mTextWatcher);
		mTwoET.addTextChangedListener(mTwoWatcher);
		mThreeET.addTextChangedListener(mThreeWatcher);
		mFourET.addTextChangedListener(mFourWatcher);
		mFiveET.addTextChangedListener(mFiveWatcher);
		mChapeauLayout.mIntroduceTV.setOnClickListener(mChapeClickListener);
		mChapeauLayout.mIntroduceTV
				.setOnLongClickListener(mChapeauLongClickListeneer);
		mGreetingsLayout.mIntroduceTV
				.setOnClickListener(mGreetingsClickListener);
		mGreetingsLayout.mIntroduceTV
				.setOnLongClickListener(mGreetingsLongClickListeneer);
		mEndLanLayout.mIntroduceTV.setOnClickListener(mEndLanClickListener);
		mEndLanLayout.mIntroduceTV
				.setOnLongClickListener(mEndLanLongClickListeneer);
		mSignatureNameLayout.mIntroduceTV
				.setOnClickListener(mSignatureNameClickListener);
		mSignatureNameLayout.mIntroduceTV
				.setOnLongClickListener(mSignatureNameLongClickListeneer);
		mGrayIV.setOnClickListener(mGrayListener);
		mDeleteIV.setOnClickListener(mDeleteListener);
		mEnterIV.setOnClickListener(mEneterListener);
		mBlankIV.setOnClickListener(mBankListener);
		mChangeBackGroundIV.setOnClickListener(onChangeLister);
		// 请求焦点
		Utils.settingInputHide(mOneET);
		Utils.settingInputHide(mTwoET);
		Utils.settingInputHide(mThreeET);
		Utils.settingInputHide(mFourET);
		Utils.settingInputHide(mFiveET);
		Utils.settingInputHide(mChapeauET);
		Utils.settingInputHide(mGreetingsET);
		Utils.settingInputHide(mEndLanET);
		Utils.settingInputHide(mSignatureNameET);
		mOneET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mTwoET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mThreeET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mFourET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mFiveET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mChapeauET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mGreetingsET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mEndLanET.setOnFocusChangeListener(mOneFocuseChangeListener);
		mSignatureNameET.setOnFocusChangeListener(mOneFocuseChangeListener);
	}

	@Override
	public void initView() {
		mHandWriteLayout = new HandWriteLayout(this, mHeight, mWidth, this);
		mTitle = mHandWriteLayout.mTitle;
		mOneET = mHandWriteLayout.mWriteView.mOneLineLayout.mEditText;
		mOneET.setVisibility(View.VISIBLE);
		mOneET.setId(Constant.ONE);
		mTwoET = mHandWriteLayout.mWriteView.mTwoLineLayout.mEditText;
		mTwoET.setVisibility(View.VISIBLE);
		mTwoET.setId(Constant.TWO);
		mThreeET = mHandWriteLayout.mWriteView.mThreeLineLayout.mEditText;
		mThreeET.setVisibility(View.VISIBLE);
		mThreeET.setId(Constant.THREE);
		mFourET = mHandWriteLayout.mWriteView.mFourLineLayout.mEditText;
		mFourET.setVisibility(View.VISIBLE);
		mFourET.setId(Constant.FOUR);
		mFiveET = mHandWriteLayout.mWriteView.mFiveLineLayout.mEditText;
		mFiveET.setVisibility(View.VISIBLE);
		mFiveET.setId(Constant.FIVE);
		mChapeauLayout = mHandWriteLayout.mWriteView.mChapeauLayout;
		mGreetingsLayout = mHandWriteLayout.mWriteView.mGreetingsLayout;
		mEndLanLayout = mHandWriteLayout.mWriteView.mEndLanLayout;
		mSignatureNameLayout = mHandWriteLayout.mWriteView.mSignatureNameLayout;
		mChapeauET = mChapeauLayout.mEditET;
		mChapeauET.setId(Constant.SIX);
		mGreetingsET = mGreetingsLayout.mEditET;
		mGreetingsET.setId(Constant.SEVEN);
		mEndLanET = mEndLanLayout.mEditET;
		mEndLanET.setId(Constant.EIGHT);
		mSignatureNameET = mSignatureNameLayout.mEditET;
		mSignatureNameET.setId(Constant.NINE);
		mWriteSubView = mHandWriteLayout.mWriteView;
		mBackIV = mTitle.mMenuIV;
		mSaveIV = mTitle.mEditIV;
		mGrayIV = mHandWriteLayout.mGrayIV;
		mShowIntruduceIV = mHandWriteLayout.mShowIntruduceIV;
		mDeleteIV = mHandWriteLayout.mHandWriteLayout.mDeleteIV;
		mEnterIV = mHandWriteLayout.mHandWriteLayout.mEnterIV;
		mBlankIV = mHandWriteLayout.mHandWriteLayout.mBlankIV;
		mChangeBackGroundIV = mHandWriteLayout.mChangeBackGroundIV;
		setContentView(mHandWriteLayout);
	}

	@Override
	public void initData() {

	}

	@Override
	public void successData() {
		if (!Utils.isObject(mUser)) {
			mTitle.mContentTV.setText(mUser.name);
		}
		if (!Utils.isObject(mNote)) {
			if (!StringUtils.isEmpty(mNote.mBackground)) {
				mHandWriteLayout.mWriteView.setBackgroundResource(mBacksMap
						.get(Integer.parseInt(mNote.mBackground)));
			}
			if (!StringUtils.isEmpty(mNote.mOneContent)) {
				String[] datas = StringUtils.getDatas(mNote.mOneContent);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mOneET, datas[i], mOneDatas, true)));
				}
			}
			if (!StringUtils.isEmpty(mNote.mTwoContent)) {
				String[] datas = StringUtils.getDatas(mNote.mTwoContent);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mTwoET, datas[i], mTwoDatas, true)));
				}
			}
			if (!StringUtils.isEmpty(mNote.mThreeContent)) {

				String[] datas = StringUtils.getDatas(mNote.mThreeContent);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mThreeET, datas[i], mThreeDatas, true)));
				}

			}
			if (!StringUtils.isEmpty(mNote.mFourContent)) {
				String[] datas = StringUtils.getDatas(mNote.mFourContent);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mFourET, datas[i], mFourDatas, true)));
				}
			}
			if (!StringUtils.isEmpty(mNote.mFiveContent)) {

				String[] datas = StringUtils.getDatas(mNote.mFiveContent);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mFiveET, datas[i], mFiveDatas, true)));
				}
			}
			eidtVisible(mChapeauLayout);
			if (!StringUtils.isEmpty(mNote.mChapeaut)) {
				String[] datas = StringUtils.getDatas(mNote.mChapeaut);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mChapeauET, datas[i], mSixDatas, true)));
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mChapeauLayout.mIntroduceTV, datas[i],
							mSixDatas, false)));
				}
			} else {
				editText(mChapeauLayout, "起首");
			}
			eidtVisible(mGreetingsLayout);
			if (!StringUtils.isEmpty(mNote.mGreetings)) {
				String[] datas = StringUtils.getDatas(mNote.mGreetings);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mGreetingsET, datas[i], mSevenDatas, true)));
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mGreetingsLayout.mIntroduceTV, datas[i],
							mSevenDatas, false)));
				}
			} else {
				editText(mGreetingsLayout, "问候语");

			}
			eidtVisible(mEndLanLayout);
			if (!StringUtils.isEmpty(mNote.mEndLan)) {
				String[] datas = StringUtils.getDatas(mNote.mEndLan);
				for (int i = 0; i < datas.length; i++) {
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mEndLanET, datas[i], mEightDatas, true)));
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mEndLanLayout.mIntroduceTV, datas[i],
							mEightDatas, false)));
				}
			} else {
				editText(mEndLanLayout, "结语");
			}
			eidtVisible(mSignatureNameLayout);
			if (!StringUtils.isEmpty(mNote.mSignatureName)) {
				String[] datas = StringUtils.getDatas(mNote.mSignatureName);
				for (int i = 0; i < datas.length; i++) {
					mConsumer
							.add(new AsyncEditTask(this, new AsynEditText(this,
									mSignatureNameET, datas[i], mNineDatas,
									true)));
					mConsumer.add(new AsyncEditTask(this, new AsynEditText(
							this, mSignatureNameLayout.mIntroduceTV, datas[i],
							mNineDatas, false)));
				}
			} else {
				editText(mSignatureNameLayout, "署名");

			}
		}
	}

	@Override
	public void handWriteCallback(List<Bitmap> mBitmaps, String name) {
		if (endInput == Constant.END_INPUT) {
			return;
		}
		isStart = true;
		Bitmap bitmap = mBitmaps.get(count);
		ImageSpan imageSpan = new ImageSpan(this, bitmap);// 添加换行符
		SpannableString spannableString = new SpannableString("[" + name + ";");// 直接定义图片的名字
		spannableString.setSpan(imageSpan, 0, spannableString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		switch (currentFocusId) {
		case Constant.ONE:
			addSpannadble(spannableString, mOneET, mOneDatas);
			break;
		case Constant.TWO:
			addSpannadble(spannableString, mTwoET, mTwoDatas);
			break;
		case Constant.THREE:
			addSpannadble(spannableString, mThreeET, mThreeDatas);

			break;
		case Constant.FOUR:
			addSpannadble(spannableString, mFourET, mFourDatas);

			break;
		case Constant.FIVE:
			addSpannadble(spannableString, mFiveET, mFiveDatas);

			break;
		case Constant.SIX:
			addSpannadble(spannableString, mChapeauET, mSixDatas);
			break;
		case Constant.SEVEN:
			addSpannadble(spannableString, mGreetingsET, mSevenDatas);
			break;
		case Constant.EIGHT:
			addSpannadble(spannableString, mEndLanET, mEightDatas);
			break;
		case Constant.NINE:
			addSpannadble(spannableString, mSignatureNameET, mNineDatas);
			break;
		}
		count++;

	}

	public void addSpannadble(SpannableString spannableString,
			EditText editText, List<SpannableString> spannables) {
		// 光标的为止
		editText.append(spannableString);
		spannables.add(spannableString);
	}

	public void addSpannadble(TextView editText,
			List<SpannableString> spannables) {
		for (int i = 0, size = spannables.size(); i < size; i++) {
			SpannableString spnanable = spannables.get(i);
			editText.append(spnanable);
		}
	}

	private TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (Utils.isCollectionEmpty(mOneDatas)) {
				return;
			}
			if (Utils.isChangeLine(mOneDatas, mHeight)) {
				// 获取输入的数组
				requestFocus(mTwoET);
			}
		}
	};

	public int getSelectStart(EditText edittext) {
		int selectionStar = edittext.getSelectionStart();
		if (selectionStar > 0) {
			String sub = edittext.getText().toString()
					.substring(Constant.DEFAULE_ZERO, selectionStar);
			String[] datas = StringUtils.getDatas(sub);
			return datas.length;
		}
		return Constant.DEFAULE_ZERO;
	}

	private TextWatcher mTwoWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (Utils.isCollectionEmpty(mTwoDatas)) {
				// requestFocus(mOneET);
				return;
			}
			if (Utils.isChangeLine(mTwoDatas, mHeight)) {
				// 获取输入的数组
				requestFocus(mThreeET);
			}
		}
	};
	private TextWatcher mThreeWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (Utils.isCollectionEmpty(mThreeDatas)) {
				// requestFocus(mTwoET);
				return;
			}
			if (Utils.isChangeLine(mThreeDatas, mHeight)) {
				// 获取输入的数组
				requestFocus(mFourET);
			}
		}
	};

	private TextWatcher mFourWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (Utils.isCollectionEmpty(mFourDatas)) {
				// requestFocus(mThreeET);
				return;
			}
			if (Utils.isChangeLine(mFourDatas, mHeight)) {
				// 获取输入的数组
				requestFocus(mFiveET);
			}
		}
	};
	private TextWatcher mFiveWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (Utils.isCollectionEmpty(mFiveDatas)) {
				// requestFocus(mThreeET);
				return;
			}
			if (Utils.isChangeLine(mFiveDatas, mHeight)) {
				// 获取输入的数组
				ToastUtils.showShortToast(HandWriteActivity.this, "不能再输入文字");
				endInput = Constant.END_INPUT;
			}
		}
	};
	private OnClickListener mChapeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			showEdit(mChapeauLayout);
		}
	};
	private OnLongClickListener mChapeauLongClickListeneer = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			showIntroduceIV(R.drawable.qi_shou, mChapeauLayout);
			return true;
		}
	};
	private OnClickListener mGreetingsClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			showEdit(mGreetingsLayout);
		}
	};
	private OnLongClickListener mGreetingsLongClickListeneer = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			showIntroduceIV(R.drawable.wen_hou_yu, mGreetingsLayout);
			return true;
		}
	};
	private OnClickListener mEndLanClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			showEdit(mEndLanLayout);
		}
	};
	private OnLongClickListener mEndLanLongClickListeneer = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			showIntroduceIV(R.drawable.jie_yu, mEndLanLayout);
			return true;
		}
	};
	private OnClickListener mSignatureNameClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			showEdit(mSignatureNameLayout);
		}
	};
	private OnLongClickListener mSignatureNameLongClickListeneer = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			showIntroduceIV(R.drawable.shu_ming, mSignatureNameLayout);
			return true;
		}
	};

	private void eidtVisible(OtherLetterLayout oterView) {
		oterView.mEditET.setVisibility(View.GONE);
		oterView.mIntroduceTV.setVisibility(View.VISIBLE);
		oterView.mIntroduceTV.setText("");
		oterView.mIntroduceTV.setBackgroundResource(R.drawable.tips);
	}

	private void editText(OtherLetterLayout oterView, String text) {
		oterView.mIntroduceTV.setText(text);
		oterView.mIntroduceTV.setTextColor(ColorConstant.BLACK);
	}

	private void showEdit(OtherLetterLayout oterView) {
		oterView.mEditET.setVisibility(View.VISIBLE);
		oterView.mEditET.requestFocus();
		oterView.mIntroduceTV.setVisibility(View.GONE);
		currentFocusId = oterView.mEditET.getId();
	}

	private void showIntroduceIV(int resId, OtherLetterLayout oterView) {
		oterView.mEditET.setVisibility(View.GONE);
		oterView.mIntroduceTV.setVisibility(View.VISIBLE);
		mGrayIV.setVisibility(View.VISIBLE);
		mShowIntruduceIV.setVisibility(View.VISIBLE);
		mShowIntruduceIV.setBackgroundResource(resId);
		animation(mShowIntruduceIV);
	}

	private void animation(View view) {
		ScaleAnimation scale = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		view.startAnimation(scale);
	}

	private OnClickListener mGrayListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mShowIntruduceIV.setVisibility(View.GONE);
			mGrayIV.setVisibility(View.GONE);
		}
	};
	private OnFocusChangeListener mOneFocuseChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			if (hasFocus) {
				getFocus(v);
			} else {
				showText(v);
			}

		}
	};

	public void showText(View view) {
		switch (view.getId()) {
		case Constant.SIX:
			focuChanged(view, mChapeauLayout, mSixDatas,"起首");
			break;
		case Constant.SEVEN:
			focuChanged(view, mGreetingsLayout, mSevenDatas,"问候语");
			break;
		case Constant.EIGHT:
			focuChanged(view, mEndLanLayout, mEightDatas,"结语");
			break;
		case Constant.NINE:
			focuChanged(view, mSignatureNameLayout, mNineDatas,"署名");
			break;
		}
	}

	public void focuChanged(View v, OtherLetterLayout otherView,
			List<SpannableString> spannablees,String text) {
		otherView.mEditET.setVisibility(View.GONE);
		String chape = otherView.mEditET.getText().toString();
		otherView.mIntroduceTV.setBackgroundColor(Color.WHITE);
		otherView.mIntroduceTV.setTextColor(Color.BLACK);
		if (!StringUtils.isEmpty(chape)) {
			otherView.mIntroduceTV.setVisibility(View.VISIBLE);
			otherView.mIntroduceTV.setText("");// 设置内容为空
			addSpannadble(otherView.mIntroduceTV, spannablees);
			RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			otherView.mIntroduceTV.setLayoutParams(layout);
		} else {
			otherView.mIntroduceTV.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, Utils.getHeight(
							ConfigLayout.WRITE_LINE_OTHER_HEIGHT, mHeight));
			otherView.mIntroduceTV.setLayoutParams(layout);
			otherView.mIntroduceTV.setText(text);
		}
		otherView.mIntroduceTV.setBackgroundResource(R.drawable.tips);
	}

	public void getFocus(View mEdit) {
		// if (mEdit.getVisibility() != View.VISIBLE) {
		// mEdit.setVisibility(View.VISIBLE);
		// }
		currentFocusId = mEdit.getId();
		// mEdit.requestFocus();
	}

	private OnClickListener mBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	private OnClickListener mSaveClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ToastUtils.showShortToast(HandWriteActivity.this, "正在保存，请稍后");
			ActivitySkip activitySkip = new ActivitySkip(
					HandWriteActivity.this, HomeActivity.class,
					HandWriteActivity.this);
			saveImageBitmap();
			activitySkip.startActivity(mEmail);
			ToastUtils.showShortToast(HandWriteActivity.this, "保存成功");
		}
	};

	private void saveImageBitmap() {
		String photoName = null;
		if (Utils.isObject(mNote)) {
			photoName = dataTime + Constant.BITMAP_SUFFIX;
		} else {
			photoName = mNote.imageLocation;
		}
		long dataTime = System.currentTimeMillis();
		Bitmap saveBitmap = Utils.getSaveBitmap(mWriteSubView);
		BitmapLoader.saveImage(BitmapLoader.getImageDir(HandWriteActivity.this)
				+ photoName, saveBitmap);
		if (mSaveUserDataDao.isHaveObject(dataTime + "")) {
			mSaveUserDataDao.updateUserDataItem("" + dataTime,
					initNote(dataTime, photoName));
		} else {
			if (Utils.isObject(mNote)) {
				mSaveUserDataDao.insertTable(initNote(dataTime, photoName));
			} else {
				mSaveUserDataDao.updateUserDataItem(mNote.currInsertTime,
						initNote(dataTime, photoName));
			}
		}
	}

	private OnClickListener mDeleteListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (currentFocusId) {
			case Constant.ONE:
				deleteText(mOneET, mOneDatas);// 删除当前的对象
				break;
			case Constant.TWO:
				deleteText(mTwoET, mTwoDatas);// 删除当前的对象
				break;
			case Constant.THREE:
				deleteText(mThreeET, mThreeDatas);// 删除当前的对象
				break;
			case Constant.FOUR:
				deleteText(mFourET, mFourDatas);// 删除当前的对象
				break;
			case Constant.FIVE:
				deleteText(mFiveET, mFiveDatas);// 删除当前的对象
				break;
			case Constant.SIX:
				deleteText(mChapeauET, mSixDatas);// 删除当前的对象
				break;
			case Constant.SEVEN:
				deleteText(mGreetingsET, mSevenDatas);// 删除当前的对象
				break;
			case Constant.EIGHT:
				deleteText(mEndLanET, mEightDatas);// 删除当前的对象
				break;
			case Constant.NINE:
				deleteText(mSignatureNameET, mNineDatas);// 删除当前的对象
				break;
			}
		}
	};
	private OnClickListener mEneterListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (currentFocusId) {
			case Constant.ONE:
				requestFocus(mTwoET);
				break;
			case Constant.TWO:
				requestFocus(mThreeET);
				break;
			case Constant.THREE:
				requestFocus(mFourET);
				break;
			case Constant.FOUR:
				requestFocus(mFiveET);// 删除当前的对象
				break;
			case Constant.FIVE:
				ToastUtils.showShortToast(HandWriteActivity.this, "已经是最后一行了");
				break;
			case Constant.SIX:
			case Constant.SEVEN:
			case Constant.EIGHT:
			case Constant.NINE:
				ToastUtils.showShortToast(HandWriteActivity.this,
						"在起首、问候语、结语、署名、操作的时候禁止换行");
				break;
			}
		}
	};
	/**
	 * 生成一张空格的图片
	 */
	private OnClickListener mBankListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			mHandWriteLayout.mHandWriteLayout.mHandWriteView.genderPhoto(100,
					isStart);
		}
	};

	/**
	 * 删除某个文本
	 * 
	 * @param mEditText
	 */
	private int deleteText(EditText mEditText, List<SpannableString> datas) {
		int selectionStart = mEditText.getSelectionStart();// 获取光标的位置
		if (selectionStart > 0) {
			String body = mEditText.getText().toString();
			if (!StringUtils.isEmpty(body)) {
				String tempStr = body.substring(0, selectionStart);
				int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
				if (i != -1) {
					CharSequence cs = tempStr.subSequence(i, selectionStart);
					// 判断是否是一个表情
					mEditText.getEditableText().delete(i, selectionStart);
					int index = Utils.getSpannableIndex(datas, cs);
					Log.e("tag", "index -- > " + index);
					datas.remove(index);
					return index;
				}
			}
			mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1,
					getEditTextCursorIndex(mEditText));
		}
		if (StringUtils.isEmpty(mEditText.getText().toString())) {
			switch (mEditText.getId()) {
			case Constant.TWO:
				requestFocus(mOneET);
				break;
			case Constant.THREE:
				requestFocus(mTwoET);
				break;
			case Constant.FOUR:
				requestFocus(mThreeET);// 删除当前的对象
				break;
			}
		}
		return 0;
	}

	private int getEditTextCursorIndex(EditText mEditText) {
		return mEditText.getSelectionStart();
	}

	private void requestFocus(EditText editText) {
		if (editText.getVisibility() != View.VISIBLE) {
			editText.setVisibility(View.VISIBLE);
		}
		currentFocusId = editText.getId();
		editText.requestFocus();
	}

	public Note initNote(long dataTime, String photoName) {
		Note note = new Note();
		String currentTime = "" + dataTime;
		note.mOneContent = mOneET.getText().toString();
		note.mTwoContent = mTwoET.getText().toString();
		note.mThreeContent = mThreeET.getText().toString();
		note.mFourContent = mFourET.getText().toString();
		note.mFiveContent = mFiveET.getText().toString();
		note.currInsertTime = currentTime;
		note.imageLocation = photoName;
		note.userName = mEmail;
		note.mChapeaut = mChapeauLayout.mEditET.getText().toString();
		note.mGreetings = mGreetingsLayout.mEditET.getText().toString();
		note.mEndLan = mEndLanLayout.mEditET.getText().toString();
		note.mSignatureName = mSignatureNameLayout.mEditET.getText().toString();
		note.isHandWrite = Constant.DEFAULE_ONE;
		note.mBackground = mBackGround;
		return Utils.getNote(note);
	}

	private OnClickListener onChangeLister = new OnClickListener() {

		@Override
		public void onClick(View v) {
			changeCount++;
			int remainder = changeCount % 13;
			mBackGround = "" + remainder;
			mHandWriteLayout.mWriteView.setBackgroundResource(mBacksMap
					.get(remainder));
		}
	};

	private void initMap() {
		mBacksMap.put(0, R.drawable.letter0);
		mBacksMap.put(1, R.drawable.letter1);
		mBacksMap.put(2, R.drawable.letter2);
		mBacksMap.put(3, R.drawable.letter3);
		mBacksMap.put(4, R.drawable.letter4);
		mBacksMap.put(5, R.drawable.letter5);
		mBacksMap.put(6, R.drawable.letter6);
		mBacksMap.put(7, R.drawable.letter7);
		mBacksMap.put(8, R.drawable.letter8);
		mBacksMap.put(9, R.drawable.letter9);
		mBacksMap.put(10, R.drawable.letter10);
		mBacksMap.put(11, R.drawable.letter11);
		mBacksMap.put(12, R.drawable.letter12);
	}
}
