package com.daxiang.propertyanimationdemo.animation;

import com.daxiang.propertyanimationdemo.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomEvaluator extends Activity implements OnClickListener {

	private LinearLayout container;
	private Button btn_run;
	private XYPosition xyPosi;
	private AnimView animView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_evaluator);
		initViews();
	}

	private void initViews() {
		container = (LinearLayout) findViewById(R.id.container);
		btn_run = (Button) findViewById(R.id.run);
		btn_run.setOnClickListener(this);
		animView = new AnimView(this);
		animView.setImageResource(R.drawable.ic_launcher);
		container.addView(animView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.run:
			startAnimation();
			break;

		}
	}

	private void startAnimation() {
		XYPosition startValue = new XYPosition();
		startValue.setX(0f);
		startValue.setY(btn_run.getHeight());

		XYPosition endValue = new XYPosition();
		endValue.setX(container.getWidth() - animView.getWidth());
		endValue.setY(container.getHeight() - animView.getHeight());

		ValueAnimator xyAnimator = ObjectAnimator.ofObject(new XYEvaluator(), startValue, endValue);
		// xyAnimator.setDuration(3000);
		xyAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				xyPosi = (XYPosition) animation.getAnimatedValue();
				animView.invalidate();
			}
		});
		// xyAnimator.start();

		ValueAnimator scaleXAnimator = ObjectAnimator.ofFloat(animView, "scaleX", 1, 2, 1);
		ValueAnimator scaleYAnimator = ObjectAnimator.ofFloat(animView, "scaleY", 1, 2, 1);
		ValueAnimator rotationAnimator = ObjectAnimator.ofFloat(animView, "rotation", 0, 360);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(xyAnimator).with(scaleXAnimator).with(scaleYAnimator).with(rotationAnimator);
		animatorSet.setDuration(3000);
		animatorSet.setInterpolator(new BounceInterpolator());
		animatorSet.start();
	}

	public class AnimView extends ImageView {

		public AnimView(Context context) {
			super(context);
		}

		public AnimView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			if (xyPosi != null) {
				setX(xyPosi.getX());
				setY(xyPosi.getY());
			}
			super.onDraw(canvas);
		}
	}

	public class XYPosition {
		private float x;
		private float y;

		public XYPosition() {
		};

		public XYPosition(float x, float y) {
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

	public class XYEvaluator implements TypeEvaluator<XYPosition> {

		@Override
		public XYPosition evaluate(float fraction, XYPosition startValue, XYPosition endValue) {
			float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
			float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
			return new XYPosition(x, y);
		}

	}

}
