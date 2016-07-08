package com.daxiang.propertyanimdemo.animation;

import com.daxiang.propertyanimdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

public class RotationActivity extends Activity implements OnClickListener {

	private Button animatingButton;
	private ViewPropertyAnimator animator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_rotation);

		initViews();
	}

	private void initViews() {
		findViewById(R.id.rotation).setOnClickListener(this);
		findViewById(R.id.rotationBy).setOnClickListener(this);
		findViewById(R.id.rotationX).setOnClickListener(this);
		findViewById(R.id.rotationXBy).setOnClickListener(this);
		findViewById(R.id.rotationY).setOnClickListener(this);
		findViewById(R.id.rotationYBy).setOnClickListener(this);

		animatingButton = (Button) findViewById(R.id.animatingObject);

	}

	@Override
	public void onClick(View v) {
		if (animator == null) {
			animator = animatingButton.animate().setDuration(2000);
			animatingButton.setRotation(0);
			animatingButton.setRotationX(0);
			animatingButton.setRotationY(0);
		}
		switch (v.getId()) {
		case R.id.rotation:
			animator.rotation(360);
			break;

		case R.id.rotationBy:
			animator.rotationBy(360);
			break;

		case R.id.rotationX:
			animator.rotationX(360);
			break;

		case R.id.rotationXBy:
			animator.rotationXBy(360);
			break;

		case R.id.rotationY:
			animator.rotationY(360);
			break;

		case R.id.rotationYBy:
			animator.rotationYBy(360);
			break;
		}
	}
}
