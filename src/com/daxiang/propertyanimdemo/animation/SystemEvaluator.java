package com.daxiang.propertyanimdemo.animation;

import com.daxiang.propertyanimdemo.R;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SystemEvaluator extends Activity {

	private TextView color_value;
	private RelativeLayout container;
	private View floating_view;
	private static final int RED = 0xFFF11B0A;
	private static final int ORANGE = 0xFFF1B30A;
	private static final int YELLOW = 0xFFF6F318;
	private static final int GREEN = 0xFF0BEB25;
	private static final int BLUE = 0xFF1871CC;
	private static final int PURPLE = 0xFF9836DC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_system_evaluator);

		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				initFloatingView();
			}
		}, 1000);

	}

	private void initFloatingView() {
		float parentWidth = container.getMeasuredWidth();
		float parentHeight = container.getMeasuredHeight();
		float width = floating_view.getMeasuredWidth();
		float height = floating_view.getMeasuredHeight();
		float[] xPosi = new float[] { 0, parentWidth - width, 0, parentWidth / 2, parentWidth - width, 0,
				parentWidth - width };
		float[] yPosi = new float[] { 0, parentHeight / 4, parentHeight * 3 / 4, parentHeight - height,
				parentHeight * 3 / 4, parentHeight / 4, 0 };

		ObjectAnimator xAnim = ObjectAnimator.ofFloat(floating_view, "x", xPosi);
		xAnim.setDuration(20000);
		xAnim.setRepeatCount(ValueAnimator.INFINITE);
		xAnim.setRepeatMode(ValueAnimator.REVERSE);

		ObjectAnimator yAnim = ObjectAnimator.ofFloat(floating_view, "y", yPosi);
		yAnim.setDuration(20000);
		yAnim.setRepeatCount(ValueAnimator.INFINITE);
		yAnim.setRepeatMode(ValueAnimator.REVERSE);

		ObjectAnimator argbAnimator = ObjectAnimator.ofInt(floating_view, "backgroundColor",
				new int[] { 0x996D07C4, 0x990B29E0, 0x99D7A50D, 0x99E26659, 0x9911EC2A, 0x99F6EA14 });
		argbAnimator.setEvaluator(new ArgbEvaluator());
		argbAnimator.setDuration(20000);
		argbAnimator.setRepeatCount(ValueAnimator.INFINITE);
		argbAnimator.setRepeatMode(ValueAnimator.REVERSE);
		argbAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
			}

		});

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(xAnim).with(yAnim).with(argbAnimator);
		animatorSet.start();
	}

	private void initViews() {
		color_value = (TextView) findViewById(R.id.color_value);
		container = (RelativeLayout) findViewById(R.id.container);
		floating_view = findViewById(R.id.floating_view);

		ObjectAnimator argbAnimator = ObjectAnimator.ofInt(container, "backgroundColor", RED, ORANGE, YELLOW, GREEN,
				BLUE, PURPLE);
		argbAnimator.setEvaluator(new ArgbEvaluator());
		argbAnimator.setDuration(10000);
		argbAnimator.setRepeatCount(ValueAnimator.INFINITE);
		argbAnimator.setRepeatMode(ValueAnimator.REVERSE);
		argbAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				String colorValue = Integer.toHexString(((Integer) animation.getAnimatedValue()).intValue());
				color_value.setText("colorValue : 0x" + colorValue);
			}

		});
		argbAnimator.start();
	}
}
