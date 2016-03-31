package com.bxnote.baseview;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import com.bxnote.activity.R;
import com.bxnote.callback.DropCallback;
import com.bxnote.callback.RegisterAndLoginCallback;
import com.bxnote.config.ConfigLayout;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

@SuppressWarnings("deprecation")
public class BaseAbsView extends AbsoluteLayout {
	public static final int INVALID_POSITION = -1;
	private Rect mTouchFrame;
	public int mFirstPosition;// 第一个出现的位置
	protected int mWidth;
	protected int mHeight;
	private Timer mTimer;
	private boolean isCancelTimer;
	private boolean isGestureExc;
	private int mRecodeTimerX;
	private int mRecodeTimerY;
	private int mLastX;
	private int mLastY;
	private DropCallback mDropCallback;
	private ImageView dragImageView = null;
	private View dragItemView = null;// 当前的对象
	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowParams = null;
	private int dragPosition;
	private boolean isCountXY;
	private RegisterAndLoginCallback mRegisterAndLoginCallback;
	private View mDragView;
	private int position;

	public BaseAbsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BaseAbsView(Context context) {
		super(context);
	}

	public BaseAbsView(Context context, int mHeight, int mWidth) {
		super(context);
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		mTimer = new Timer();
	}

	public BaseAbsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mRecodeTimerX = (int) ev.getX();
		mRecodeTimerY = (int) ev.getY();
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mLastX = (int) ev.getX();
			mLastY = (int) ev.getY();
			isGestureExc = true;
			int position = getPointPosition(mLastX, mLastY);
			if (position != INVALID_POSITION && mDropCallback != null) {
				View view = (View) getChildAt(position);
				this.position = position;
				mDragView = view;
				if (!Utils.isObject(view)) {
					isCancelTimer = false;
					Log.e("tag", "" + position);
					setttingDownBack(view, position);
					if (!Utils.isObject(mTimer)) {
						mTimer.schedule(new OnClickTimerTask(position),
								Constant.START_TIMER);

					}
				}
			}
		}
		onTouch(ev);
		if (Utils.isObject(dragImageView)) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				isGestureExc = false;
				if (dragImageView == null) {
					cancelTimer();
					settingUpBack(mDragView, position);
				} else {
					stopDrag();
					onDrop((int) ev.getX(), (int) ev.getY());
				}
			}

		}
		return true;
	}

	@SuppressLint("NewApi")
	public int getPointPosition(int x, int y) {
		if (getChildCount() == Constant.DEFAULE_ZERO) {
			return INVALID_POSITION;
		}
		Rect frame = mTouchFrame;

		if (frame == null) {
			mTouchFrame = new Rect();
			frame = mTouchFrame;
		}
		final int count = getChildCount();
		for (int i = count - 1; i >= 0; i--) {
			final View child = getChildAt(i);
			if (isContainLayout(child, x, y)) {
				return mFirstPosition + i;
			}
		}
		return INVALID_POSITION;
	}

	@SuppressLint("NewApi")
	public boolean isContainLayout(View child, int x, int y) {
		float leftX = child.getX();
		float topY = child.getY();
		if (x >= leftX
				&& x <= (leftX + Utils.getWidth(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_WIDTH, mWidth))
				&& y >= topY
				&& y <= (topY + Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight))) {
			return true;
		}
		return false;
	}

	private class OnClickTimerTask extends TimerTask {
		int position;

		public OnClickTimerTask(int position) {
			super();
			this.position = position;
		}

		@Override
		public void run() {
			if (Math.abs(mRecodeTimerX - mLastX) < 10
					&& Math.abs(mRecodeTimerY - mLastY) < 10 && !isCancelTimer) {
				Message msg = mHandler.obtainMessage();
				msg.what = position;
				mHandler.sendMessage(msg);
			}
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mDropCallback.dropPosition(msg.what, null);
		};
	};

	public void cancelTimer() {
		isCancelTimer = true;
	}

	private void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	private void onDrop(int x, int y) {
		if (!Utils.isObject(dragItemView)) {
			dragItemView.setVisibility(View.VISIBLE);
		}
	}

	public boolean onTouch(MotionEvent ev) {
		if (dragImageView != null && dragPosition != Constant.INVALID_POSITION) {
			// 获取滑动的站点的位置
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (!isCountXY) {
					isCountXY = true;
				}
				onDrag(x, y);
				return false;
			case MotionEvent.ACTION_UP:
				settingUpBack(mDragView, position);
				if (isCanRegiterAndLogin(x, y)) {
					settingLocation();
					if (!Utils.isObject(mRegisterAndLoginCallback)) {
						mRegisterAndLoginCallback.callBack(dragPosition);
					}
				} else {
					onDrop(x, y);
				}
				stopDrag();

				break;
			default:
				break;
			}
		}
		return true;
	}

	@SuppressLint("NewApi")
	private void settingLocation() {
		if (!Utils.isObject(dragItemView)) {
			if (dragItemView.getLayoutParams() instanceof AbsoluteLayout.LayoutParams) {
				AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) dragItemView
						.getLayoutParams();
				layoutParams.x = Utils.getWidth(ConfigLayout.MARGIN309, mWidth);
				layoutParams.y = Utils.getHeight(ConfigLayout.MARGIN565,
						mHeight);
				dragItemView.setLayoutParams(layoutParams);
				dragItemView.setVisibility(View.VISIBLE);
			}
		}

	}

	private boolean isCanRegiterAndLogin(int x, int y) {
		float leftX = Utils.getWidth(ConfigLayout.MARGIN309, mWidth);
		float topY = Utils.getHeight(ConfigLayout.MARGIN565, mHeight);
		if (x >= leftX
				&& x <= (leftX + Utils.getWidth(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_WIDTH, mWidth))
				&& y >= topY
				&& y <= (topY + Utils.getHeight(
						ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight))) {
			return true;
		}
		return false;
	}

	@SuppressLint("NewApi")
	private void onDrag(int x, int y) {// 设置移动的位置
		if (dragImageView != null) {
			windowParams.alpha = 0.9f;
			windowParams.x = (int) (x - (mLastX - dragItemView.getX() - getScrollX()));
			windowParams.y = getTopMoveY(y);// 需要当前的view的位置减去滚动的位置
			windowManager.updateViewLayout(dragImageView, windowParams);
		}
	}

	@SuppressLint("NewApi")
	private int getTopMoveY(int y) {
		return y - (mLastY - (int) dragItemView.getY());
	}

	public void setOnLongClick(View view, int index) {

		if (this != null) {
			dragPosition = index;
		}
		View itemView = getChildAt(dragPosition);
		if (!isCountXY) {
			isCountXY = true;
		}

		if (isGestureExc) {
			itemView.destroyDrawingCache();
			dragItemView = (View) itemView;
			itemView.destroyDrawingCache();
			itemView.setDrawingCacheEnabled(true);
			Bitmap bm = itemView.getDrawingCache();
			startDrag(bm);
			hideDropItem();
			itemView.setVisibility(View.INVISIBLE);// 获取当前的item对象，让当前的item进行隐藏
		}
	}

	@SuppressLint("NewApi")
	private void startDrag(Bitmap bm) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();// 设置布局参数
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;// 设置布局的位置
		windowParams.x = (int) dragItemView.getX() - getScrollX();// 减去移动的位置
		windowParams.y = (int) dragItemView.getY() - getScrollY();
		windowParams.height = Utils.getHeight(
				ConfigLayout.LOGIN_AND_REGISTER_LOGIN_HEIGHT, mHeight);// 设置宽
		// WindowManager.LayoutParams.WRAP_CONTENT
		windowParams.width = Utils.getWidth(
				ConfigLayout.LOGIN_AND_REGISTER_LOGIN_WIDTH, mWidth);// WindowManager.LayoutParams.WRAP_CONTENT;//
		// 设置高
		windowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
		windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		windowParams.alpha = 0.9f;// 设置透明值
		ImageView iv = new ImageView(getContext());
		iv.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);// 获取WindowManager对象
		windowManager.addView(iv, windowParams);
		dragImageView = iv;// 将ImageView设置过去了
	}

	private void hideDropItem() {
		// if (mBaseScrollCallback != null) {
		// mBaseScrollCallback.showDropItem(false);
		// }
	}

	public void setDropCallback(DropCallback dropCallback) {
		mDropCallback = dropCallback;
	}

	public void setRegisterAndLoginCallback(
			RegisterAndLoginCallback registerCallback) {
		mRegisterAndLoginCallback = registerCallback;
	}

	private void setttingDownBack(View view, int position) {
		if (position == Constant.DEFAULT_THREE) {// 注册
			if (view instanceof ImageView) {
				((ImageView) view)
						.setImageResource(R.drawable.signup_btn_touched);
			}
		}
		if (position == Constant.DEFAULT_TWO) {// 登录
			if (view instanceof ImageView) {
				((ImageView) view)
						.setImageResource(R.drawable.login_btn_touched);
			}
		}
	}

	private void settingUpBack(View view, int position) {
		if (Utils.isObject(mDragView)) {
			return;
		}
		if (position == Constant.DEFAULT_THREE) {// 注册
			if (view instanceof ImageView) {
				((ImageView) view).setImageResource(R.drawable.signup_btn);
			}
		}
		if (position == Constant.DEFAULT_TWO) {// 登录
			if (view instanceof ImageView) {
				((ImageView) view).setImageResource(R.drawable.login_btn);
			}
		}
	}
}
