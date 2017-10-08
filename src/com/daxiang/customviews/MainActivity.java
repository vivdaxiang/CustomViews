package com.daxiang.customviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.daxiang.customviews.activity.BubbleImageViewActivity;
import com.daxiang.customviews.activity.CustomEvaluator;
import com.daxiang.customviews.activity.FlowLayoutActivity;
import com.daxiang.customviews.activity.RotationActivity;
import com.daxiang.customviews.activity.StickyAnimActivity;
import com.daxiang.customviews.activity.SystemEvaluator;
import com.daxiang.customviews.activity.TranslateActivity;
import com.daxiang.customviews.activity.WaveViewActivity;

public class MainActivity extends Activity implements OnClickListener {

	private Button rotation;
	private Button translate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
	}

	private void initViews() {
		rotation = (Button) findViewById(R.id.rotation);
		rotation.setOnClickListener(this);
		translate = (Button) findViewById(R.id.translate);
		translate.setOnClickListener(this);
		findViewById(R.id.systemEvaluator).setOnClickListener(this);
		findViewById(R.id.customEvaluator).setOnClickListener(this);
		findViewById(R.id.sticky).setOnClickListener(this);
		findViewById(R.id.wave).setOnClickListener(this);
		findViewById(R.id.flow_layout).setOnClickListener(this);
		findViewById(R.id.bubble_image_view).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rotation:
			startActivity(new Intent(MainActivity.this, RotationActivity.class));
			break;

		case R.id.translate:
			startActivity(new Intent(MainActivity.this, TranslateActivity.class));
			break;

		case R.id.systemEvaluator:
			startActivity(new Intent(MainActivity.this, SystemEvaluator.class));
			break;

		case R.id.customEvaluator:
			startActivity(new Intent(MainActivity.this, CustomEvaluator.class));
			break;

		case R.id.sticky:
			startActivity(new Intent(MainActivity.this,
					StickyAnimActivity.class));
			break;

		case R.id.wave:
			startActivity(new Intent(MainActivity.this, WaveViewActivity.class));
			break;

		case R.id.flow_layout:
			startActivity(new Intent(MainActivity.this,
					FlowLayoutActivity.class));
			break;

		case R.id.bubble_image_view:
			startActivity(new Intent(MainActivity.this,
					BubbleImageViewActivity.class));
			break;

		}
	}

}
