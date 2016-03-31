package com.bxnote.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bxnote.callback.HandWriteBitmapCallback;
import com.bxnote.utils.BitmapLoader;
import com.bxnote.utils.Constant;
import com.bxnote.utils.Utils;

public class HandWriteView extends View {

	private Paint mPaint;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	private long mEndTime;
	private List<Bitmap> mBitmaps = new ArrayList<Bitmap>();
	private boolean isStart = false;
	private Handler mHandler;
	private HandWriteBitmapCallback mHandWriteCallback;
	private String dateString;
	private int count = 0;
	private String saveName;
	private int height;
	private int width;

	@SuppressLint("SimpleDateFormat")
	public HandWriteView(Context context,
			HandWriteBitmapCallback mHandWriteCallback, int height, int width) {
		super(context);
		this.mHandWriteCallback = mHandWriteCallback;
		dateString = "" + System.currentTimeMillis();
		this.height = height;
		this.width = width;
		initPaint();
		init(context);
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF000000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(Constant.STROKE_WIDTH);
	}

	public HandWriteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("HandlerLeak")
	public void init(Context context) {
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
						mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
				mCanvas.setBitmap(mBitmap);
				invalidate();
				if (!Utils.isObject(mHandWriteCallback)) {
					mHandWriteCallback.handWriteCallback(mBitmaps,
							(String) msg.obj);
				}
			}
		};
	}

	public HandWriteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setPaint(Paint paint) {
		this.mPaint = paint;
	}

	public void setBitmapMap(List<Bitmap> bitmaps) {
		mBitmaps = bitmaps;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0x00000000);

		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		canvas.drawPath(mPath, mPaint);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	/**
	 * 使用手势绘制文字
	 * 
	 * @param x
	 * @param y
	 */
	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	/*
	 * touch_up
	 */
	private void touch_up() {
		mPath.lineTo(mX, mY);
		mCanvas.drawPath(mPath, mPaint);
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mEndTime = System.currentTimeMillis();
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			mEndTime = System.currentTimeMillis();
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			mEndTime = System.currentTimeMillis();
			touch_up();
			invalidate();
			if (!isStart) {
				isStart = true;
				genderPhoto(1000, isStart);
			}
			break;
		}
		return true;
	}

	public void genderPhoto(final int distacetime, final boolean start) {
		isStart = start;
		new Thread() {
			public void run() {
				while (isStart) {
					long time = System.currentTimeMillis();
					if (time >= mEndTime + distacetime) {
						if (mBitmaps != null) {
							int width = mBitmap.getWidth();
							int height = mBitmap.getHeight();
							Matrix matrix = new Matrix();
							float scaleWidht = ((float) Utils.getWidth(
									Constant.HAND_WRITE_WIDTH,
									HandWriteView.this.width) / width);
							float scaleHeight = ((float) Utils.getHeight(
									Constant.HAND_WRITE_HEIHT,
									HandWriteView.this.height) / height);
							matrix.postScale(scaleWidht, scaleHeight);
							Bitmap newbmp = Bitmap.createBitmap(mBitmap, 0, 0,
									width, height, matrix, true);
							saveName = dateString + "/" + dateString + count
									+ ".png.tile";
							// 將對象保存到本地
							BitmapLoader.saveImage(
									BitmapLoader.getImageDir(getContext())
											+ saveName, newbmp);
							count++;
							// 天剑bitmap对象
							mBitmaps.add(newbmp);
						}
						Message msg = mHandler.obtainMessage();
						msg.obj = saveName;// 当前的排行
						mHandler.sendMessage(msg);
						HandWriteView.this.isStart = false;
					}
				}
			};
		}.start();
	}
}
