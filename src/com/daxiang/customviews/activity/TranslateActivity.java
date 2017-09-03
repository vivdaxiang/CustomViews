package com.daxiang.customviews.activity;

import com.daxiang.customviews.R;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.TextView;

/**
 * Translate动画；
 *
 * @author daxiang
 * @date 2016年6月28日
 * @time 下午5:01:02
 */
public class TranslateActivity extends Activity implements OnClickListener {

	private Button animatingButton;
	private ViewPropertyAnimator animator;

	private float translationX;
	private float translationY;
	// private float translationZ;

	private TextView tv_value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_translate);
		initViews();
	}

	private void initViews() {
		findViewById(R.id.x).setOnClickListener(this);
		findViewById(R.id.xBy).setOnClickListener(this);
		findViewById(R.id.y).setOnClickListener(this);
		findViewById(R.id.yBy).setOnClickListener(this);

		findViewById(R.id.translationX).setOnClickListener(this);
		findViewById(R.id.translationXBy).setOnClickListener(this);
		findViewById(R.id.translationY).setOnClickListener(this);
		findViewById(R.id.translationYBy).setOnClickListener(this);
		findViewById(R.id.translationZ).setOnClickListener(this);
		findViewById(R.id.translationZBy).setOnClickListener(this);
		findViewById(R.id.goBack).setOnClickListener(this);

		tv_value = (TextView) findViewById(R.id.tv_value);

		animatingButton = (Button) findViewById(R.id.animatingObject);
		translationX = animatingButton.getTranslationX();
		translationY = animatingButton.getTranslationY();
		// translationZ = animatingButton.getTranslationZ();
	}

	@Override
	public void onClick(View v) {
		if (animator == null) {
			animator = animatingButton.animate().setDuration(2000).setListener(new Animator.AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {

				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {
					tv_value.setText("x = " + animatingButton.getX() + " y = " + animatingButton.getY()
							+ "\n translationX=" + animatingButton.getTranslationX() + " translationY="
							+ animatingButton.getTranslationY());
				}

				@Override
				public void onAnimationCancel(Animator animation) {

				}
			});
		}

		switch (v.getId()) {

		case R.id.x:
			animator.x(0);// 相对于父控件的x坐标；
			break;

		case R.id.xBy:
			animator.xBy(300);
			break;
		case R.id.y:
			animator.y(200);
			break;
		case R.id.yBy:
			animator.yBy(200);
			break;
		case R.id.translationX:
			animator.translationX(300); // 相对于自己左边缘的坐标；
			break;

		case R.id.translationXBy:
			animator.translationXBy(-300);
			break;
		case R.id.translationY:
			animator.translationY(200);// 相对于自己顶部的坐标；

			break;
		case R.id.translationYBy:
			animator.translationYBy(200);

			break;
		// case R.id.translationZ:
		// animator.translationZ(50);
		// break;
		// case R.id.translationZBy:
		// animator.translationZBy(50);
		// break;
		case R.id.goBack:
			animator.translationX(translationX).translationY(translationY);// .translationZ(translationZ);
			break;
		}
	}

}
