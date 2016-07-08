package com.daxiang.propertyanimationdemo.animation;

import com.daxiang.propertyanimationdemo.R;
import com.daxiang.propertyanimationdemo.view.StickyView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class StickyAnimActivity extends Activity {

	private LinearLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticky_anim);

		initViews();
	}

	private void initViews() {
		container = (LinearLayout) findViewById(R.id.container);

		StickyView stickyView = new StickyView(this);
		container.addView(stickyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
}
