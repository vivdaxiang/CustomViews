package com.daxiang.customviews.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * 波浪效果的view；
 * 
 * @author daxiang
 * @date 2016年7月8日
 * @time 下午6:18:39
 */
public class WaveView extends View {
	private static final String TAG = WaveView.class.getSimpleName();

	/**
	 * 波长；
	 */
	private float mWaveWidth;
	/**
	 * 波峰的高度
	 */
	private float mWaveHeight;
	/**
	 * 完整的可见波形的总数
	 */
	private int visibleWaveCount = 1;

	/**
	 * 水平面；
	 */
	private float mLevel;

	private Paint mPaint;
	private Path mPathOne;
	private Path mPathTwo;

	/**
	 * 绘制贝塞尔曲线要求的起点、终点、控制点等
	 */
	private List<Point> wavePoints;

	private float mLeftSide;
	private float mViewHeight;
	private float mViewWidth;

	/**
	 * 垂直方向移动的速度；
	 */
	private float speedVertical = 0.5f;
	/**
	 * 水平方向移动的速度；
	 */
	private float speedHorizontal = 14;

	private boolean isMeasured;

	public WaveView(Context context) {
		super(context);
		init();
	}

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		wavePoints = new ArrayList<WaveView.Point>();

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);

		mPathOne = new Path();
		mPathTwo = new Path();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e(TAG, "---------onMeasure");
		// during draw/layout operations 会测量多次，onMeasure多次被调用；
		if (!isMeasured) {
			isMeasured = true;
			mViewWidth = getMeasuredWidth();
			mWaveWidth = mViewWidth / visibleWaveCount;
			mWaveHeight = mWaveWidth / 12;
			mLevel = mViewHeight = getMeasuredHeight();
			mLeftSide = -mWaveWidth;
			float x;
			float y;
			Point point;
			// 初始化每个点的位置
			for (int i = 0; i < 4 * visibleWaveCount + 1 + 4; i++) {
				x = i * (mWaveWidth / 4) - mWaveWidth;
				y = 0;
				switch (i % 4) {
				case 0:
					y = mLevel;
					break;
				case 1:
					y = mLevel - mWaveHeight;// 上波峰；
					break;
				case 2:
					y = mLevel;
					break;
				case 3:
					y = mLevel + mWaveHeight;// 下波峰；
					break;
				}
				point = new Point(x, y);
				wavePoints.add(point);
			}
		}

	}

	private boolean firstDraw = true;

	@Override
	protected void onDraw(Canvas canvas) {
		if (firstDraw) {
			firstDraw = false;
			super.onDraw(canvas);
		}

		Log.e(TAG, "---------onDraw");
		// 再次绘制前，清除之前的轮廓；
		mPathOne.reset();
		mPathTwo.reset();

		mPathOne.moveTo(wavePoints.get(0).getX(), wavePoints.get(0).getY());
		mPathTwo.moveTo(wavePoints.get(0).getX(), wavePoints.get(0).getY());

		int i = 0;
		for (; i < wavePoints.size() - 2; i = i + 2) {

			mPathOne.quadTo(wavePoints.get(i + 1).getX(), wavePoints.get(i + 1).getY(), wavePoints.get(i + 2).getX(),
					wavePoints.get(i + 2).getY());

			switch ((i + 1) % 4) {
			case 1:
				mPathTwo.quadTo(wavePoints.get(i + 1).getX(), wavePoints.get(i + 1).getY() + mWaveHeight * 2,
						wavePoints.get(i + 2).getX(), wavePoints.get(i + 2).getY());
				break;

			case 3:
				mPathTwo.quadTo(wavePoints.get(i + 1).getX(), wavePoints.get(i + 1).getY() - mWaveHeight * 2,
						wavePoints.get(i + 2).getX(), wavePoints.get(i + 2).getY());
				break;
			}

		}

		mPathOne.lineTo(wavePoints.get(i).getX(), mViewHeight);
		mPathOne.lineTo(mLeftSide, mViewHeight);
		mPathOne.close();

		mPathTwo.lineTo(wavePoints.get(i).getX(), mViewHeight);
		mPathTwo.lineTo(mLeftSide, mViewHeight);
		mPathTwo.close();

		mPaint.setColor(0x8836BDE1);
		canvas.drawPath(mPathOne, mPaint);

		mPaint.setColor(0x885AC9E6);
		canvas.drawPath(mPathTwo, mPaint);

		mHandler.sendEmptyMessageDelayed(MOVE_MSG, 10);

	}

	private static final int MOVE_MSG = 11;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MOVE_MSG:
				if (wavePoints.get(0).getX() >= 0) {
					resetXValue();
				}
				// 重点：当水平线到达一定的位置后，保持不变。因为绘制的区域太大，对手机性能要求太高，会导致动画不流畅；
				if (mLevel >= mViewHeight * 6 / 7) {
					mLevel -= speedVertical;
				}

				// 重新计算每个点的位置；
				for (int i = 0; i < wavePoints.size(); i++) {
					wavePoints.get(i).setX(wavePoints.get(i).getX() + speedHorizontal);
					switch (i % 4) {
					case 0:
						wavePoints.get(i).setY(mLevel);
						break;
					case 1:
						wavePoints.get(i).setY(mLevel - mWaveHeight);
						break;
					case 2:
						wavePoints.get(i).setY(mLevel);
						break;
					case 3:
						wavePoints.get(i).setY(mLevel + mWaveHeight);
						break;
					}
				}

				Log.e(TAG, "---------handleMessage");

				invalidate();
				break;

			}
		}

	};

	private void resetXValue() {
		for (int i = 0; i < wavePoints.size(); i++) {
			wavePoints.get(i).setX(i * (mWaveWidth / 4) - mWaveWidth);
		}
	};

	private class Point {
		private float x;
		private float y;

		public Point(float x, float y) {
			super();
			this.x = x;
			this.y = y;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

	}

}
