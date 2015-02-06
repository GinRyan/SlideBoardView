package org.gryan.slideboardview.demo;

import org.gryan.slideboardview.R;
import org.gryan.slideboardview.SlideBoardView;
import org.gryan.slideboardview.R.id;
import org.gryan.slideboardview.R.layout;
import org.gryan.slideboardview.SlideBoardView.OnCollapseBoardListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * sample
 * 
 * @author Liangzheng
 */
public class MainActivity extends Activity {
	SlideBoardView slideBoardView1 = null;
	View flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flag = findViewById(R.id.flag);
		slideBoardView1 = (SlideBoardView) findViewById(R.id.slideBoardView1);

		slideBoardView1.setOnCollapseBoardListener(new OnCollapseBoardListener() {

			@Override
			public void onCollapse(boolean isExpanded) {
				if (isExpanded) {
					flag.setBackgroundColor(Color.parseColor("#FFBB33"));
				} else {
					flag.setBackgroundColor(Color.parseColor("#FF8800"));
				}
			}
		});

	}
}
