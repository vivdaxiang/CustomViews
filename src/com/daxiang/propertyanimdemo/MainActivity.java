package com.daxiang.propertyanimdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.daxiang.propertyanimdemo.animation.CustomEvaluator;
import com.daxiang.propertyanimdemo.animation.FlowLayoutActivity;
import com.daxiang.propertyanimdemo.animation.RotationActivity;
import com.daxiang.propertyanimdemo.animation.StickyAnimActivity;
import com.daxiang.propertyanimdemo.animation.SystemEvaluator;
import com.daxiang.propertyanimdemo.animation.TranslateActivity;
import com.daxiang.propertyanimdemo.animation.WaveViewActivity;

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

		}
	}

}
