package com.daxiang.customviews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.daxiang.customviews.R;
import com.daxiang.customviews.view.BubbleImageView;

public class BubbleImageViewActivity extends Activity {

	private BubbleImageView bubbleImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bubble_image_view);

		bubbleImageView = (BubbleImageView) findViewById(R.id.bubbleImageView);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bubbleImageView.setImageResource(R.drawable.bubble_image_view2);
			}
		}, 3000);
	}
}
