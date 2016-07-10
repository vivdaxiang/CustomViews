package com.daxiang.propertyanimdemo.animation;

import android.app.Activity;
import android.os.Bundle;

import com.daxiang.propertyanimdemo.R;
import com.daxiang.propertyanimdemo.view.WaveView;

/**
 * 
 * @author daxiang
 * @date 2016-7-10
 */
public class WaveViewActivity extends Activity {

	private WaveView waveView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wave_view);

		waveView = (WaveView) findViewById(R.id.waveView);
	}
}
