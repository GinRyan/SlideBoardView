package org.gryan.slideboardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Inner layout.
 * 
 * @author Liangzheng
 */
public class SlideInnerLayout extends LinearLayout {

	private int view0Width;
	private View handView;

	public SlideInnerLayout(Context context) {
		super(context);
	}

	public SlideInnerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		for (int i = 0; i < getChildCount(); i++) {
			handView = getChildAt(i);
			if (handView instanceof SlideHandView) {
				view0Width = handView.getWidth();
			}
		}
	}

	public View getHandView() {
		return handView;
	}
	
	public int getHandViewWidth() {
		return view0Width;
	}
}
