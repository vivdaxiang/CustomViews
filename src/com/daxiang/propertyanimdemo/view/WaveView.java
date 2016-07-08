package com.daxiang.propertyanimdemo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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

	private List<Point> wavePoints = new ArrayList<WaveView.Point>();

	public WaveView(Context context) {
		super(context);
	}

	public WaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWaveWidth = getMeasuredWidth() / visibleWaveCount;
		mWaveHeight = getMeasuredWidth() / 8;

		for (int i = 0; i < 4 * visibleWaveCount + 1 + 4; i++) {

		}
	}

	private class Point {
		private float x;
		private float y;

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
