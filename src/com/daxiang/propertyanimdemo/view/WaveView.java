package com.daxiang.propertyanimdemo.view;

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

	private float mWaveWidth;
	private float mWaveHeight;
	private int visibleWaveCount = 2;

	private float mLevel;

	private Paint mPaint;
	private Path mPath;

	private List<Point> wavePoints;

	private float mLeftSide;
	private float mViewHeight;

	private float speedVertical = 2;
	private float speedHorizontal = 2;

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
		mPaint.setColor(0xff3F85E4);

		mPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWaveWidth = getMeasuredWidth() / visibleWaveCount;
		mWaveHeight = getMeasuredWidth() / 8;
		mLevel = mViewHeight = getMeasuredHeight();
		mLeftSide = -mWaveWidth;
		float x;
		float y;
		Point point;
		for (int i = 0; i < 4 * visibleWaveCount + 1 + 4; i++) {
			x = i * (mWaveWidth / 4) - mWaveWidth;
			y = 0;
			switch (i % 4) {
			case 0:
				y = mLevel;
				break;

			case 1:
				y = mLevel - mWaveHeight;
				break;
			case 2:
				y = mLevel;
				break;
			case 3:
				y = mLevel + mWaveHeight;
				break;
			}
			point = new Point(x, y);
			wavePoints.add(point);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPath.moveTo(wavePoints.get(0).getX(), wavePoints.get(0).getY());
		int i = 0;
		for (; i < wavePoints.size() - 2; i = i + 2) {
			mPath.quadTo(wavePoints.get(i + 1).getX(), wavePoints.get(i + 1)
					.getY(), wavePoints.get(i + 2).getX(), wavePoints
					.get(i + 2).getY());

		}
		mPath.lineTo(wavePoints.get(i).getX(), mViewHeight);
		mPath.lineTo(mLeftSide, mViewHeight);
		mPath.close();
		canvas.drawPath(mPath, mPaint);
		mHandler.sendEmptyMessageDelayed(MOVE_MSG, 10);

	}

	private static final int MOVE_MSG = 11;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MOVE_MSG:
				// 重新计算每个点的位置；
				mLevel -= speedVertical;
				for (int i = 0; i < wavePoints.size(); i++) {
					wavePoints.get(i).setX(
							wavePoints.get(i).getX() + speedHorizontal);
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

				invalidate();
				break;

			}
		};
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
