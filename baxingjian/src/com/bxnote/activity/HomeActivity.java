package com.bxnote.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.bxnote.adapter.HomeNoteAdapter;
import com.bxnote.bean.AccessToken;
import com.bxnote.bean.Note;
import com.bxnote.bean.QQUser;
import com.bxnote.bean.SinaUser;
import com.bxnote.bean.User;
import com.bxnote.callback.CancellationCallback;
import com.bxnote.callback.QQCallback;
import com.bxnote.callback.QQUserCallback;
import com.bxnote.callback.ShareCallback;
import com.bxnote.callback.TokenCallback;
import com.bxnote.callback.TouchCallback;
import com.bxnote.dao.CreateUserDateDao;
import com.bxnote.dao.SaveUserDataDao;
import com.bxnote.subview.ShareButton;
import com.bxnote.subview.ShareLayout;
import com.bxnote.subview.Title;
import com.bxnote.utils.ActivitySkip;
import com.bxnote.utils.AppManager;
import com.bxnote.utils.BitmapLoader;
import com.bxnote.utils.BundleUtils;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Consumer;
import com.bxnote.utils.FileUtils;
import com.bxnote.utils.MyAnimations;
import com.bxnote.utils.QQWeiboUtils;
import com.bxnote.utils.ShareThread;
import com.bxnote.utils.StringUtils;
import com.bxnote.utils.ToastUtils;
import com.bxnote.utils.UserKeep;
import com.bxnote.utils.Utils;
import com.bxnote.utils.WeiboUtil;
import com.bxnote.view.CancelDialog;
import com.bxnote.view.HomeLayout;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.slidingmenu.lib.SlidingMenu;
import com.tencent.connect.share.QQShare;

public class HomeActivity extends BaseActivity implements TouchCallback,
		ShareCallback<Note>, CancellationCallback, TokenCallback, QQCallback,
		QQUserCallback, OnScrollListener {
	private boolean isExit;
	private HomeLayout mHomeLayout;
	private ShareLayout mShareLayout;
	private CancelDialog mCancelDialog;
	// private TextView mHandWirteTV;
	// private TextView mInputWriteTV;
	private ImageView mWirteIV;
	private ImageView mMenuIV;
	private ImageView mGrayIV;
	private ShareButton mWxIV;
	private ShareButton mWxFriendsIV;
	private ShareButton mSinaIV;
	private ShareButton mQQQoneIV;
	private ShareButton mEmailIV;
	// private HandWriteChooseLayout mHandWriteChooseLayout;
	private ImageView mInputIV;
	private ImageView mHandIV;
	private ListView mNoteLV;
	private Title mTitle;
	private HomeNoteAdapter mHomeNoteAdapter;
	private String mEmail = "userID";
	private com.bxnote.fragment.MenuFragment mMenuFragMent;
	private User mUser;
	private CreateUserDateDao mCreateUserDao;
	private SaveUserDataDao mSaveUserDataDao;
	private List<Note> mNotes = new ArrayList<Note>();
	private boolean isShowChoose = true;
	private boolean isShare = true;
	private WeiboUtil mWeiboUtils;
	private QQWeiboUtils mQQWeiboUtils;
	private JSONObject mTokenJson;
	private Note mNote;
	// data
	private SinaUser mSinaUser;
	private QQUser mQQUser;
	private Consumer mConsumer;
	private boolean isShareQQ;
	private int mLastFirstVisibleItem = -1;
	private int mExtarFlag = 0x00;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.QQ_USER_LOGIN:
				if (msg.obj instanceof QQUser) {
					mQQUser = (QQUser) msg.obj;
					mMenuFragMent.setQQLoginOrLoginOut(R.drawable.selected_btn);
				}
				if (isShareQQ) {
					// mQQWeiboUtils.qqShare(, extarFlag, title, targetUrl,
					// summary, imageUrl, appName);
					isShareQQ = false;
				}
				break;
			case Constant.SIAN_USER_LOGIN:
				if (msg.obj instanceof SinaUser) {
					mSinaUser = (SinaUser) msg.obj;
					mMenuFragMent
							.setSinaLoginOrLoginOut(R.drawable.selected_btn);
				}
				break;
			case Constant.SUCCESS_DATA:
				successData();
				break;
			case Constant.EXIT_APP:
				isExit = false;
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().finishAllActivity();// 退出以前的activity
		AppManager.getAppManager().addActivity(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (!Utils.isObject(bundle)) {
			mEmail = BundleUtils.getEmail(bundle);
		}
		if (StringUtils.isEmpty(mEmail)) {
			mEmail = "userID";
		}
		mConsumer = new Consumer();
		mCreateUserDao = new CreateUserDateDao(this);
		mSaveUserDataDao = new SaveUserDataDao(this);
		mSinaUser = UserKeep.readUser(this);
		mQQUser = UserKeep.getQQUser(this);
		mWeiboUtils = new WeiboUtil(this, this);
		mQQWeiboUtils = new QQWeiboUtils(this, this, this, this);
		mCancelDialog = new CancelDialog(this, R.style.dialog);
		mCancelDialog.setCancelable(false);
		mCancelDialog.mCanCel.setOnClickListener(mCancelistener);
		mCancelDialog.mConfirmBT.setOnClickListener(mConfirmListener);
		/**
		 * 判断这个地方是否是空的，如果是空的不获取数据
		 */
		if (!StringUtils.isEmpty(mEmail)) {
			mUser = mCreateUserDao.queryUser(mEmail);
		}
		initView();
		initData();
		initListener();
		if (!Utils.isObject(mUser)) {
			mTitle.mContentTV.setText(mUser.name);
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mSinaUser = UserKeep.readUser(this);
		if (!(mSinaUser.expiresIn == Constant.USERLOGOUT)) {
			mMenuFragMent.setSinaLoginOrLoginOut(R.drawable.selected_btn);
		}
		initData();
	}

	private void initListener() {
		mWxIV.mTopIV.setOnClickListener(mOnShareWx);
		mWxFriendsIV.mTopIV.setOnClickListener(mOnShareCircleFriendsListener);
		mQQQoneIV.mTopIV.setOnClickListener(mOnZoneListener);
		mSinaIV.mTopIV.setOnClickListener(mOnShareSinaListner);
		mEmailIV.mTopIV.setOnClickListener(mOnShareEmailListener);
		mMenuIV.setOnClickListener(mMenuListener);// 对侧滑进行监听
	}

	@Override
	public void initView() {
		mHomeLayout = new HomeLayout(this, mHeight, mWidth);
		// mHandWriteChooseLayout = mHomeLayout.mHandWriteChooseLayout;
		mWxIV = mHomeLayout.mShareLayout.mWxIV;
		mWxFriendsIV = mHomeLayout.mShareLayout.mWxFriendsIV;
		mSinaIV = mHomeLayout.mShareLayout.mSinaIV;
		mQQQoneIV = mHomeLayout.mShareLayout.mQQQoneIV;
		mEmailIV = mHomeLayout.mShareLayout.mShareEmailIV;
		mGrayIV = mHomeLayout.mGrayIV;
		mShareLayout = mHomeLayout.mShareLayout;
		mHandIV = mHomeLayout.mHandIV;
		mInputIV = mHomeLayout.mInputIV;
		mHandIV.setOnClickListener(mHandWriteListener);
		mInputIV.setOnClickListener(mInputWriteListner);
		mGrayIV.setOnClickListener(mOnGrayListener);
		mNoteLV = mHomeLayout.mBXNoteLV;
		mNoteLV.setOnScrollListener(this);
		LayoutParams layoutparames = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
		mTitle = mHomeLayout.mTitle;
		mMenuIV = mTitle.mMenuIV;
		mWirteIV = mTitle.mEditIV;
		mWirteIV.setOnClickListener(mOnChooseWriteListener);
		addFragMent();// 添加侧滑
		setContentView(mHomeLayout, layoutparames);
	}

	private void addFragMent() {
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		mMenuFragMent = new com.bxnote.fragment.MenuFragment(mHeight, mWidth,
				this, mSinaUser, mQQUser, this);// 设置左侧的手势
		t.replace(R.id.menu_frame, mMenuFragMent);
		t.commit();
	}

	@Override
	public void initData() {
		new Thread() {
			@Override
			public void run() {
				if (Utils.getIsWriteData(HomeActivity.this)) {
					long oneDataTime = System.currentTimeMillis();
					String onePhotoName = oneDataTime + Constant.BITMAP_SUFFIX;
					Bitmap bitmap1 = BitmapFactory.decodeResource(
							getResources(), R.drawable.model_letter3);
					BitmapLoader.saveImage(
							BitmapLoader.getImageDir(HomeActivity.this)
									+ onePhotoName, bitmap1);
					mSaveUserDataDao.insertTable(initNote(oneDataTime,
							onePhotoName));
					long twoDataTime = System.currentTimeMillis();
					String twoPhotoName = twoDataTime + Constant.BITMAP_SUFFIX;
					Bitmap bitmap2 = BitmapFactory.decodeResource(
							getResources(), R.drawable.model_letter2);
					BitmapLoader.saveImage(
							BitmapLoader.getImageDir(HomeActivity.this)
									+ twoPhotoName, bitmap2);
					mSaveUserDataDao.insertTable(initTwoNote(twoDataTime,
							twoPhotoName));
					long threeDataTime = System.currentTimeMillis();
					String threePhotoName = threeDataTime
							+ Constant.BITMAP_SUFFIX;
					Bitmap bitmap3 = BitmapFactory.decodeResource(
							getResources(), R.drawable.model_letter1);
					BitmapLoader.saveImage(
							BitmapLoader.getImageDir(HomeActivity.this)
									+ threePhotoName, bitmap3);
					mSaveUserDataDao.insertTable(initThreeNote(threeDataTime,
							threePhotoName));
					Utils.setIsWriteData(HomeActivity.this, false);
				}
				mNotes = mSaveUserDataDao.queryNotes(mEmail);
				if (!Utils.isCollectionEmpty(mNotes)) {
					Message msg = mHandler.obtainMessage();
					msg.what = Constant.SUCCESS_DATA;
					mHandler.sendMessage(msg);
				}
			}
		}.start();
	}

	@Override
	public void successData() {
		mHomeNoteAdapter = new HomeNoteAdapter(this, mHeight, mWidth, mNotes,
				this, mConsumer);
		mNoteLV.setAdapter(mHomeNoteAdapter);
	}

	@Override
	public void leftCallback() {
		mSlidingMenu.toggle(true);
	}

	OnClickListener mHandWriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ActivitySkip activitySkip = new ActivitySkip(HomeActivity.this,
					HandWriteActivity.class, HomeActivity.this);
			hideChooseLayout();
			if (!Utils.isObject(mUser)) {
				activitySkip.startActivity(mEmail);
			} else {
				activitySkip.startActivity(mEmail);
			}
		}
	};
	OnClickListener mInputWriteListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ActivitySkip activitySkip = new ActivitySkip(HomeActivity.this,
					WriteActivity.class, HomeActivity.this);
			hideChooseLayout();
			if (!Utils.isObject(mUser)) {
				activitySkip.startActivity(mEmail);
			} else {
				activitySkip.startActivity(mEmail);
			}
		}
	};
	OnClickListener mOnChooseWriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isShowChoose) {
				showChooseLayout();
			} else {
				hideChooseLayout();
			}
		}

	};
	OnClickListener mOnGrayListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			hideAllLayout();
		}
	};
	OnClickListener mOnShareWx = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!Utils.isObject(mNote)) {
				new ShareThread(mConsumer, HomeActivity.this,
						mNote.imageLocation, false).start();
			}
		}
	};
	OnClickListener mOnShareCircleFriendsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!Utils.isObject(mNote)) {
				new ShareThread(mConsumer, HomeActivity.this,
						mNote.imageLocation, true).run();
			}
		}
	};
	OnClickListener mOnShareSinaListner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!Utils.isObject(mNote)) {
				ActivitySkip activitySkip = new ActivitySkip(HomeActivity.this,
						SinaShareActivity.class, HomeActivity.this);
				activitySkip.startActivity(mEmail, mNote);
			}
		}
	};
	OnClickListener mOnShareEmailListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Utils.sendEmail(BitmapLoader.getImageDir(getApplicationContext())
					+ mNote.imageLocation, HomeActivity.this);
		}
	};
	OnClickListener mOnZoneListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mQQWeiboUtils.qqShare(QQShare.SHARE_TO_QQ_TYPE_IMAGE, mExtarFlag,
					BitmapLoader.getImageDir(getApplicationContext())
							+ mNote.imageLocation, "八行箋");
		}
	};

	private void hideAllLayout() {
		hideChooseLayout();
		hideShareLayout();
	}

	private void showChooseLayout() {
		mHandIV.setVisibility(View.VISIBLE);
		mInputIV.setVisibility(View.VISIBLE);
		MyAnimations.startAnimationsIn(mHandIV, 100);
		MyAnimations.startAnimationsIn(mInputIV, 100);
		// mHandWriteChooseLayout.setVisibility(View.VISIBLE);
		mGrayIV.setVisibility(View.VISIBLE);
		isShowChoose = false;
	}

	private void hideChooseLayout() {
		mGrayIV.setVisibility(View.GONE);
		mHandIV.setVisibility(View.GONE);
		mInputIV.setVisibility(View.GONE);
		// mHandWriteChooseLayout.setVisibility(View.GONE);
		isShowChoose = true;
	}

	@Override
	public void shareCallback(Note t) {
		mNote = t;
		if (isShare) {
			showShareLayout();
		} else {
			hideShareLayout();
		}
	}

	private void showShareLayout() {
		mShareLayout.setVisibility(View.VISIBLE);
		mGrayIV.setVisibility(View.VISIBLE);
		isShare = false;
	}

	private void hideShareLayout() {
		mShareLayout.setVisibility(View.GONE);
		mGrayIV.setVisibility(View.GONE);
		isShare = true;
	}

	private OnClickListener mMenuListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mSlidingMenu.showMenu();
		}
	};

	@Override
	public void cancelUser() {
		mCancelDialog.show();
	}

	@Override
	public void registerQQ() {
		mQQUser = UserKeep.getQQUser(this);// (this);
		if (mQQUser.expiresIn == Constant.USERLOGOUT) {
			mQQWeiboUtils.login();
		} else {
			if (mEmail.equals("" + mQQUser.expiresIn)) {
				ToastUtils.showShortToast(this, "绑定qq登录，无法解除绑定");
				return;
			}
			mMenuFragMent.setQQLoginOrLoginOut(R.drawable.unselected_btn);
			mQQWeiboUtils.loginOut();
		}
	}

	@Override
	public void registerSina() {
		mSinaUser = UserKeep.readUser(this);// (this);//(this);
		if (mSinaUser.expiresIn == Constant.USERLOGOUT) {
			mWeiboUtils.createToken(this);
		} else {
			if (mEmail.equals("" + mSinaUser.expiresIn)) {
				ToastUtils.showShortToast(this, "绑定qq登录，无法解除绑定");
				return;
			}
			mMenuFragMent.setSinaLoginOrLoginOut(R.drawable.unselected_btn);
			mWeiboUtils.deleteImpower();
		}
	}

	@Override
	public void doComplete(JSONObject response, Object state) {
		UserKeep.clearQQuser(this);
		QQUser qqUser = new QQUser();
		try {
			qqUser.expiresIn = mTokenJson.getLong("expires_in");
			qqUser.accessToken = mTokenJson.getString("access_token");
			qqUser.gender = response.getString("gender");
			qqUser.qqNickName = response.getString("nickname");
			if (qqUser.expiresIn != 0) {
				UserKeep.keepQqUser(this, qqUser);
				Message message = mHandler.obtainMessage();
				message.obj = qqUser;
				message.what = Constant.QQ_USER_LOGIN;
				mHandler.sendMessage(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void shareCallback(JSONObject jsonObject) {
		mTokenJson = jsonObject;
		mQQWeiboUtils.onClickUserInfo();

	}

	@Override
	public void callback(AccessToken accessToken) {
		new SinaUserThread(accessToken).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!Utils.isObject(mWeiboUtils.getSsoHandler())) {
			mWeiboUtils.getSsoHandler().authorizeCallBack(requestCode,
					resultCode, data);
			return;
		}
	}

	private class SinaUserThread extends Thread {
		public AccessToken mAccessToken;

		public SinaUserThread(AccessToken mAccessToken) {
			super();
			this.mAccessToken = mAccessToken;
		}

		@Override
		public void run() {
			UsersAPI userAPi = new UsersAPI(mAccessToken.accessToken);
			userAPi.show(Long.parseLong(mAccessToken.uid),
					new RequestListener() {

						@Override
						public void onComplete(String arg0) {
							try {
								JSONObject jsonObject = new JSONObject(arg0);
								String name = jsonObject.getString("name");
								long id = jsonObject.getLong("id");
								String gender = jsonObject.getString("gender");
								SinaUser sinaUser = new SinaUser();
								sinaUser.name = name;
								sinaUser.accessToken = mAccessToken.accessToken
										.toString();
								sinaUser.expiresIn = id;
								sinaUser.gender = gender;
								if (sinaUser.expiresIn != 0) {
									UserKeep.keepUser(HomeActivity.this,
											sinaUser);
									Message message = mHandler.obtainMessage();
									message.obj = sinaUser;
									message.what = Constant.SIAN_USER_LOGIN;
									mHandler.sendMessage(message);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onWeiboException(WeiboException arg0) {

						}
					});
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (Utils.isCollectionEmpty(mNotes)) {
			return;
		}
		Note note = mNotes.get(firstVisibleItem);
		if (Utils.isObject(note)) {
			return;
		}
		if (firstVisibleItem != mLastFirstVisibleItem) {
			mHomeLayout.setResourceID(note.month);
		}
		mLastFirstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void deleteCallback(Note t) {
		mNotes.remove(t);
		mHomeNoteAdapter.clearMap();
		mSaveUserDataDao.deleteIndexCurrentTime(t.currInsertTime);
		mHomeNoteAdapter.notifyDataSetChanged();// 更新数据
		FileUtils.deleteFile(t.imageLocation, this);
	}

	@Override
	public void writeCallback(Note t) {
		if (t.isHandWrite == Constant.DEFAULE_ZERO) {
			ActivitySkip activitySkip = new ActivitySkip(this,
					WriteActivity.class, this);
			activitySkip.startActivity(mEmail, t);

		} else {
			ActivitySkip activitySkip = new ActivitySkip(this,
					HandWriteActivity.class, this);
			activitySkip.startActivity(mEmail, t);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			ToastUtils.showShortToast(this, "再按一次返回键退出应用程序");
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			AppManager.getAppManager().AppExit(this);
		}
	}

	public Note initNote(long dataTime, String photoName) {
		Note note = new Note();
		String currentTime = "" + dataTime;
		note.mOneContent = "余在金陵与阮叔相聚二十五日，二十日等舟还皖，体中尚适。";
		note.mTwoContent = "余与阮叔蒙恩晋封侯伯，门户大盛，深为袛惧。";
		note.mThreeContent = "尔在省以谦、敬二字为主，事事请问意臣、芝生两姻叔，断不可送条子，致腾物议。";
		note.mFourContent = "十六日出闱，十七八拜客，十九日即可回家。九月初在家听榜信后，再启程来署可也。";
		note.mFiveContent = "择交是第一要事，须择志趣远大者。";
		note.currInsertTime = currentTime;
		note.imageLocation = photoName;
		note.userName = mEmail;
		note.mChapeaut = "字谕纪鸿";
		note.mGreetings = "自尔还乡启行后，久未接尔来禀，殊不放心。今日天气奇热，尔在途次平安否？";
		note.mEndLan = "此嘱。";
		note.mSignatureName = "涤生手示";
		note.isHandWrite = Constant.DEFAULE_ZERO;
		note.mBackground = "smallChooseLetter11";
		note.mTextType = "yeyougenhangshufan.ttf";
		return Utils.getNote(note);
	}

	public Note initTwoNote(long dataTime, String photoName) {
		Note note = new Note();
		String currentTime = "" + dataTime;
		note.mOneContent = "既接光仪，又获手示，诲谕勤勤，";
		note.mTwoContent = "感且不荆德芳返平，托致微物，";
		note.mThreeContent = "尚祈哂纳。世局多故，至希为国自珍。";
		note.currInsertTime = currentTime;
		note.imageLocation = photoName;
		note.userName = mEmail;
		note.mChapeaut = "澄宇先生夫子道席";
		note.mGreetings = "";
		note.mEndLan = "肃此。敬颂 。教安。不具";
		note.mSignatureName = "受业毛泽东 ";
		note.isHandWrite = Constant.DEFAULE_ZERO;
		// note.mBackground = "smallChooseLetter9.png";
		// note.mTextType = "fangzhengqingkebenyuesongjianti.ttf";
		return Utils.getNote(note);
	}

	public Note initThreeNote(long dataTime, String photoName) {
		Note note = new Note();
		String currentTime = "" + dataTime;
		note.mOneContent = "日前寄上海婴照片一张，想已收到。小包一个，今天收到了。";
		note.mTwoContent = "酱鸭、酱肉，昨起白花，蒸过之后，味仍不坏；只有鸡腰是全不能喫了。其余的东西，都好的。";
		note.mThreeContent = "下午已分了一份给老三去。但其中的一种粉，无人认识，亦不知喫法，下次信中，乞示知。";
		note.mFourContent = "上海一向很暖，昨天发风，才冷了起来，但房中亦尚有五十余度";
		note.mFiveContent = "寓内大小俱安，请勿念为要。海婴有几句话，写在另一纸上，今附呈。 ";
		note.currInsertTime = currentTime;
		note.imageLocation = photoName;
		note.userName = mEmail;
		note.mChapeaut = "母亲大人膝下";
		note.mGreetings = "敬禀者";
		note.mEndLan = "专此布达，恭请金安。";
		note.mSignatureName = "男树叩上广平及海婴同叩";
		note.isHandWrite = Constant.DEFAULE_ZERO;
		note.mBackground = "smallChooseLetter9";
		note.mTextType = "fangzhengqingkebenyuesongjianti.ttf";
		return Utils.getNote(note);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!Utils.isObject(mHomeNoteAdapter)) {
			mHomeNoteAdapter.cancelBitmap();
		}
	}

	@Override
	public void settingTheme() {
		mHomeLayout.settingTheme();
		mTitle.setTextColor();
	}

	@Override
	public void showLargerPhoto(Note t) {
		mNote = t;
		ActivitySkip mLargerPhoto = new ActivitySkip(this,
				LargerPhotoActivity.class, this);
		mLargerPhoto.startActivity(mNote);
	}

	private OnClickListener mConfirmListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mCancelDialog.dismiss();
			removeUser();
		}
	};

	private void removeUser() {
		UserKeep.clearUserData(this);// 清空用户的数据
		Utils.setIsWriteData(HomeActivity.this, true);
		Utils.setIsDefaultTheme(this, true);
		mSaveUserDataDao.deleteTab();
		ActivitySkip activity = new ActivitySkip(this,
				LoginAndRegisterActivity.class, this);
		activity.startActivity();
	}

	private OnClickListener mCancelistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mCancelDialog.dismiss();
		}
	};
}
