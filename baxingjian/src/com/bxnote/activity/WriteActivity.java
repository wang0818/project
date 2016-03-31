package com.bxnote.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bxnote.adapter.NoteBackgroundAdapter;
import com.bxnote.adapter.TextAdapter;
import com.bxnote.bean.Note;
import com.bxnote.bean.User;
import com.bxnote.config.ConfigLayout;
import com.bxnote.config.TextSizeConstant;
import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.dao.SaveUserDataDao;
import com.bxnote.subview.OtherLetterLayout;
import com.bxnote.subview.Title;
import com.bxnote.subview.WriteSubView;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.BitmapLoader;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.Utils;
import com.bxnote.view.WriteLayout;

@SuppressWarnings("deprecation")
public class WriteActivity extends BaseActivity {
	private WriteLayout mWriteLayout;
	private Title mTitle;
	private WriteSubView mWriteSubView;
	private ImageView mLeftIV;
	private ImageView mCenterIV;
	private ImageView mRightIV;
	private ImageView mShowIntruduceIV;
	private OtherLetterLayout mChapeauLayout;
	private OtherLetterLayout mGreetingsLayout;
	private OtherLetterLayout mEndLanLayout;
	private OtherLetterLayout mSignatureNameLayout;
	private Gallery mFontGallery;
	private Gallery mBackGroundPhotoGallery;
	private ImageView mBackIV;
	private ImageView mSaveIV;
	private List<String> mFontType;
	private List<String> mBackGrounds;
	private TextAdapter mFontAdapter;
	private NoteBackgroundAdapter mNoteBackGroundAdapter;
	private EditText mOneET;
	private EditText mTwoET;
	private EditText mThreeET;
	private EditText mFourET;
	private EditText mFiveET;
	private boolean isShowLetter;
	private Consumer mConsumer;
	private ImageView mGrayIV;
	private HashMap<String, Integer> backsMap = new HashMap<String, Integer>();
	private SaveUserDataDao mSaveUserDataDao;
	private String mEmail = "userID";
	private Note mNote;
	private User mUser;
	private CreateUserDateDao mCreateUserDao;
	private long dataTime;
	private String mTextType;
	private String mBackGround;
	private boolean isCheckLoing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		Intent intent = getIntent();
		dataTime = System.currentTimeMillis();
		if (!Utils.isObject(intent)) {
			Bundle bundle = intent.getExtras();
			mEmail = BundleUtils.getEmail(bundle);
			mNote = BundleUtils.getNote(bundle);
		}
		if (!Utils.isObject(mNote)) {
			mBackGround = mNote.mBackground;
			mTextType = mNote.mTextType;
		}
		mConsumer = new Consumer();
		mCreateUserDao = new CreateUserDateDao(this);
		mSaveUserDataDao = new SaveUserDataDao(this);
		mUser = mCreateUserDao.queryUser(mEmail);
		initData();
		initMap();
		initView();
		initListener();
		successData();
	}

	private void initMap() {
		backsMap.put("smallChooseLetter0", R.drawable.letter0);// ("smallChooseLetter0.png");
		backsMap.put("smallChooseLetter1", R.drawable.letter1);
		backsMap.put("smallChooseLetter2", R.drawable.letter2);
		backsMap.put("smallChooseLetter3", R.drawable.letter3);
		backsMap.put("smallChooseLetter4", R.drawable.letter4);
		backsMap.put("smallChooseLetter5", R.drawable.letter5);
		backsMap.put("smallChooseLetter6", R.drawable.letter6);
		backsMap.put("smallChooseLetter7", R.drawable.letter7);
		backsMap.put("smallChooseLetter8", R.drawable.letter8);
		backsMap.put("smallChooseLetter9", R.drawable.letter9);
		backsMap.put("smallChooseLetter10", R.drawable.letter10);
		backsMap.put("smallChooseLetter11", R.drawable.letter11);
		backsMap.put("smallChooseLetter12", R.drawable.letter12);
	}

	private void initListener() {
		mBackIV.setOnClickListener(mBackClickListener);
		mSaveIV.setOnClickListener(mSaveClickListener);
		mLeftIV.setOnClickListener(mLeftClickListener);
		mRightIV.setOnClickListener(mRightClickListener);
		mBackGroundPhotoGallery.setOnItemClickListener(mGalleryItemListener);
		mFontGallery.setOnItemClickListener(mFontGalleryItemListener);
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
		mChapeauLayout.mEditET.setOnFocusChangeListener(mChangeFocuseListener);
		mGreetingsLayout.mEditET
				.setOnFocusChangeListener(mGreetingChangeFocuseListener);
		mEndLanLayout.mEditET
				.setOnFocusChangeListener(mEndLanChangeFocuseListener);
		mSignatureNameLayout.mEditET
				.setOnFocusChangeListener(mSignChangeFocuseListener);
	}

	private OnFocusChangeListener mChangeFocuseListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			focuChanged(v, hasFocus, mChapeauLayout);
		}
	};
	private OnFocusChangeListener mGreetingChangeFocuseListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			focuChanged(v, hasFocus, mGreetingsLayout);
		}
	};
	private OnFocusChangeListener mEndLanChangeFocuseListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			focuChanged(v, hasFocus, mEndLanLayout);
		}
	};
	private OnFocusChangeListener mSignChangeFocuseListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			focuChanged(v, hasFocus, mSignatureNameLayout);
		}
	};

	public void focuChanged(View v, boolean hasFocus,
			OtherLetterLayout otherView) {
		if (!hasFocus) {
			otherView.mEditET.setVisibility(View.GONE);
			String chape = otherView.mEditET.getText().toString();
			otherView.mIntroduceTV.setBackgroundColor(Color.WHITE);
			otherView.mIntroduceTV.setTextColor(Color.BLACK);
			if (!StringUtils.isEmpty(chape)) {
				otherView.mIntroduceTV.setVisibility(View.VISIBLE);
				otherView.mIntroduceTV.setText(chape);
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
			}
			otherView.mIntroduceTV.setBackgroundResource(R.drawable.tips);
		}
	}

	@Override
	public void initView() {
		mWriteLayout = new WriteLayout(this, mHeight, mWidth, mNote, backsMap);
		mGrayIV = mWriteLayout.mGrayIV;
		mTitle = mWriteLayout.mTitle;
		mShowIntruduceIV = mWriteLayout.mShowIntruduceIV;
		mBackIV = mTitle.mMenuIV;
		mSaveIV = mTitle.mEditIV;
		mLeftIV = mWriteLayout.mWriteChooseLayout.mLeftIV;
		mCenterIV = mWriteLayout.mWriteChooseLayout.mCenterIV;
		mRightIV = mWriteLayout.mWriteChooseLayout.mRightIV;
		mChapeauLayout = mWriteLayout.mWriteView.mChapeauLayout;
		mGreetingsLayout = mWriteLayout.mWriteView.mGreetingsLayout;
		mEndLanLayout = mWriteLayout.mWriteView.mEndLanLayout;
		mSignatureNameLayout = mWriteLayout.mWriteView.mSignatureNameLayout;
		mWriteSubView = mWriteLayout.mWriteView;
		mOneET = mWriteLayout.mWriteView.mOneLineLayout.mEditText;
		mOneET.setVisibility(View.VISIBLE);
		mTwoET = mWriteLayout.mWriteView.mTwoLineLayout.mEditText;
		mTwoET.setVisibility(View.VISIBLE);
		mThreeET = mWriteLayout.mWriteView.mThreeLineLayout.mEditText;
		mThreeET.setVisibility(View.VISIBLE);
		mFourET = mWriteLayout.mWriteView.mFourLineLayout.mEditText;
		mFourET.setVisibility(View.VISIBLE);
		mFiveET = mWriteLayout.mWriteView.mFiveLineLayout.mEditText;
		mFiveET.setVisibility(View.VISIBLE);
		mFontGallery = mWriteLayout.mFontAndChooseLayout.mFontGallery;
		mBackGroundPhotoGallery = mWriteLayout.mFontAndChooseLayout.mLetterGallery;
		setContentView(mWriteLayout);
	}

	@Override
	public void initData() {
		mFontType = new ArrayList<String>();
		mFontType.add("biaokaiti.ttf");
		mFontType.add("fangzhegnqingkebenyuesongjianti.ttf");
		mFontType.add("fangzhengbeiweikaishufanti.ttf");
		mFontType.add("fangzhenghuangcao.ttf");
		mFontType.add("fangzhengqingkebenyuesongjianti.ttf");
		mFontType.add("mititi.ttf");
		mFontType.add("yeyougenhangshufan.ttf");
		mBackGrounds = new ArrayList<String>();
		mBackGrounds.add("smallChooseLetter0.png");
		mBackGrounds.add("smallChooseLetter1.png");
		mBackGrounds.add("smallChooseLetter2.png");
		mBackGrounds.add("smallChooseLetter3.png");
		mBackGrounds.add("smallChooseLetter4.png");
		mBackGrounds.add("smallChooseLetter5.png");
		mBackGrounds.add("smallChooseLetter6.png");
		mBackGrounds.add("smallChooseLetter7.png");
		mBackGrounds.add("smallChooseLetter8.png");
		mBackGrounds.add("smallChooseLetter9.png");
		mBackGrounds.add("smallChooseLetter10.png");
		mBackGrounds.add("smallChooseLetter11.png");
		mBackGrounds.add("smallChooseLetter12.png");
	}

	@Override
	public void successData() {
		if (!Utils.isObject(mUser)) {
			mTitle.mContentTV.setText(mUser.name);
		}
		mFontAdapter = new TextAdapter(mFontType, mHeight, mWidth, this);
		mFontGallery.setAdapter(mFontAdapter);
		mFontGallery.setAlpha(256);
		mFontGallery.setSelection(3);
		// mFontGallery.s
		mNoteBackGroundAdapter = new NoteBackgroundAdapter(mBackGrounds,
				mHeight, mWidth, this, mConsumer);
		mBackGroundPhotoGallery.setAdapter(mNoteBackGroundAdapter);
		mBackGroundPhotoGallery.setSelection(3);
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
			ToastUtils.showShortToast(WriteActivity.this, "正在保存，请稍后");
			saveImageBitmap();
			ActivitySkip activitySkip = new ActivitySkip(WriteActivity.this,
					HomeActivity.class, WriteActivity.this);
			activitySkip.startActivity(mEmail);
			ToastUtils.showShortToast(WriteActivity.this, "保存成功");
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		/**
		 * 关闭及时保存信息
		 */
//		if (!StringUtils.isEmpty(mOneET.getText().toString())) {
//			saveImageBitmap();
//		}
	};

	private void saveImageBitmap() {
		String photoName = null;
		if (Utils.isObject(mNote)) {
			photoName = dataTime + Constant.BITMAP_SUFFIX;
		} else {
			photoName = mNote.imageLocation;
		}
		Bitmap saveBitmap = Utils.getSaveBitmap(mWriteSubView);
		BitmapLoader.saveImage(BitmapLoader.getImageDir(WriteActivity.this)
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

	private OnClickListener mLeftClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			isShowLetter();
		}
	};
	private OnClickListener mRightClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			isShowLetter();
		}
	};

	private void isShowLetter() {
		if (isShowLetter) {
			showLetter();
		} else {
			showFont();
		}
	}

	private void showLetter() {
		mFontGallery.setVisibility(View.GONE);
		mBackGroundPhotoGallery.setVisibility(View.VISIBLE);
		mCenterIV.setImageResource(R.drawable.choose_letter);
		isShowLetter = false;
	}

	private void showFont() {
		mFontGallery.setVisibility(View.VISIBLE);
		mBackGroundPhotoGallery.setVisibility(View.GONE);
		mCenterIV.setImageResource(R.drawable.choose_font);
		isShowLetter = true;
	}

	private OnItemClickListener mGalleryItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// 设置背景色
			String backGround = mBackGrounds.get(arg2);
			String getBackKey = Utils.getBackKey(backGround);
			if (!StringUtils.isEmpty(getBackKey)) {
				mBackGround = getBackKey;
				int resId = backsMap.get(getBackKey);
				mWriteSubView.setBackgroundResource(resId);
			}
		}
	};
	private OnItemClickListener mFontGalleryItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			setTextType(mFontType.get(arg2));
		}
	};

	/**
	 * 改变字体
	 * 
	 * @param fontType
	 */
	private void setTextType(String fontType) {
		mTextType = fontType;
		Utils.setOtherType(this, mOneET, fontType);
		Utils.setOtherType(this, mTwoET, fontType);
		Utils.setOtherType(this, mThreeET, fontType);
		Utils.setOtherType(this, mFourET, fontType);
		Utils.setOtherType(this, mFiveET, fontType);
		Utils.setOtherType(this, mChapeauLayout.mEditET, fontType);
		Utils.setOtherType(this, mGreetingsLayout.mEditET, fontType);
		Utils.setOtherType(this, mEndLanLayout.mEditET, fontType);
		Utils.setOtherType(this, mSignatureNameLayout.mEditET, fontType);
		Utils.setOtherType(this, mChapeauLayout.mIntroduceTV, fontType);
		Utils.setOtherType(this, mGreetingsLayout.mIntroduceTV, fontType);
		Utils.setOtherType(this, mEndLanLayout.mIntroduceTV, fontType);
		Utils.setOtherType(this, mSignatureNameLayout.mIntroduceTV, fontType);

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
			String text = mOneET.getText().toString();
			if (StringUtils.isEmpty(text)) {
				return;
			}
			chanText(text,mOneET,mTwoET);
		}
	};
	public void chanText(String text,EditText mCurrentET,EditText mNextET){
		Paint paint = new Paint();
		float fl = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				TextSizeConstant.TEXT_SIZE,
				Utils.getScale(WriteActivity.this));
		paint.setTextSize(fl);
		if (Utils.isChangeLine(paint, text, mHeight)) {
			if (!isCheckLoing) {
				isCheckLoing = true;
				// 获取输入的数组
				String[] datas = Utils.getText(paint, text, mHeight);
				mCurrentET.setText(datas[0]);
				mNextET.setVisibility(View.VISIBLE);
				mNextET.setText(datas[1]);
				setSelection(mNextET, datas);
				mNextET.requestFocus();
				isCheckLoing = false;
			}
		}
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
			String text = mTwoET.getText().toString();
			if (StringUtils.isEmpty(text)) {
				mOneET.requestFocus();
				return;
			}
			chanText(text,mTwoET,mThreeET);
//			Paint paint = new Paint();
//			float fl = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//					TextSizeConstant.TEXT_SIZE,
//					Utils.getScale(WriteActivity.this));
//			paint.setTextSize(fl);
//			if (Utils.isChangeLine(paint, text, mHeight)) {
//				// 获取输入的数组
//				String[] datas = Utils.getText(paint, text, mHeight);
//				mTwoET.setText(datas[0]);
//				mThreeET.setVisibility(View.VISIBLE);
//				mThreeET.setText(datas[1]);
//				setSelection(mThreeET, datas);
//				mThreeET.requestFocus();
//			}
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
			String text = mThreeET.getText().toString();
			if (StringUtils.isEmpty(text)) {
				mTwoET.requestFocus();
				return;
			}
			chanText(text,mThreeET,mFourET);

//			Paint paint = new Paint();
//			float fl = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//					TextSizeConstant.TEXT_SIZE,
//					Utils.getScale(WriteActivity.this));
//			paint.setTextSize(fl);
//			if (Utils.isChangeLine(paint, text, mHeight)) {
//				// 获取输入的数组
//				String[] datas = Utils.getText(paint, text, mHeight);
//				mThreeET.setText(datas[0]);
//				mFourET.setVisibility(View.VISIBLE);
//				mFourET.setText(datas[1]);
//				setSelection(mFourET, datas);
//				mFourET.requestFocus();
//			}
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
			String text = mFourET.getText().toString();
			if (StringUtils.isEmpty(text)) {
				mThreeET.requestFocus();
				return;
			}
			chanText(text,mFourET,mFiveET);
//			Paint paint = new Paint();
//			float fl = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//					TextSizeConstant.TEXT_SIZE,
//					Utils.getScale(WriteActivity.this));
//			paint.setTextSize(fl);
//			if (Utils.isChangeLine(paint, text, mHeight)) {
//				// 获取输入的数组
//				String[] datas = Utils.getText(paint, text, mHeight);
//				mFourET.setText(datas[0]);
//				mFiveET.setVisibility(View.VISIBLE);
//				mFiveET.setText(datas[1]);
//				setSelection(mFiveET, datas);
//				mFiveET.requestFocus();
//			}
		}
	};

	private void setSelection(EditText edit, String[] datas) {
		edit.setSelection(datas[1].length());
	}

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
			String text = mFiveET.getText().toString();
			if (StringUtils.isEmpty(text)) {
				mFourET.requestFocus();
				return;
			}
			Paint paint = new Paint();
			float fl = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
					TextSizeConstant.TEXT_SIZE,
					Utils.getScale(WriteActivity.this));
			paint.setTextSize(fl);
			if (Utils.isChangeLine(paint, text, mHeight)) {
				// 获取输入的数组
				ToastUtils.showShortToast(WriteActivity.this, "不能再输入文字");
				String[] datas = Utils.getText(paint, text, mHeight);
				mFiveET.setText(datas[0]);
			}
			paint.setTextSize(fl);
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

	private void showEdit(OtherLetterLayout oterView) {
		oterView.mEditET.setVisibility(View.VISIBLE);
		oterView.mEditET.requestFocus();
		oterView.mIntroduceTV.setVisibility(View.GONE);
	}

	private void showIntroduceIV(int resId, OtherLetterLayout oterView) {
		oterView.mEditET.setVisibility(View.GONE);
		oterView.mIntroduceTV.setVisibility(View.VISIBLE);
		mGrayIV.setVisibility(View.VISIBLE);
		mShowIntruduceIV.setVisibility(View.VISIBLE);
		mShowIntruduceIV.setBackgroundResource(resId);
		animation(mShowIntruduceIV);
	}

	private OnClickListener mGrayListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mShowIntruduceIV.setVisibility(View.GONE);
			mGrayIV.setVisibility(View.GONE);
			if (!Utils.isObject(mShowIntruduceIV.getAnimation())) {
				mShowIntruduceIV.getAnimation().cancel();
			}
		}
	};

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
		note.mTextType = mTextType;
		note.mBackground = mBackGround;
		note.userName = mEmail;
		note.mChapeaut = mChapeauLayout.mEditET.getText().toString();
		note.mGreetings = mGreetingsLayout.mEditET.getText().toString();
		note.mEndLan = mEndLanLayout.mEditET.getText().toString();
		note.mSignatureName = mSignatureNameLayout.mEditET.getText().toString();
		note.isHandWrite = Constant.DEFAULE_ZERO;
		return Utils.getNote(note);
	}

	private void animation(View view) {
		ScaleAnimation scale = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		view.startAnimation(scale);
	}
}
