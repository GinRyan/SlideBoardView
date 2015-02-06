package org.gryan.slideboardview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * SlideBoardView
 * 
 * @author Liangzheng
 *
 */
public class SlideBoardView extends LinearLayout {

	private ViewDragHelper mDragHelper = null;
	private View mDragView;
	private int flagViewWidth = 0;
	float mInitialMotionX = 0f;
	float mInitialMotionY = 0f;
	private int mRealTopOffset;
	public static final int DIRECTION_LEFT = 0x21;
	public static final int DIRECTION_RIGHT = 0x12;
	private int currentDirection = DIRECTION_LEFT;

	public SlideBoardView(Context context) {
		super(context);
		init();
	}

	public SlideBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View curv = getChildAt(i);
			if (curv instanceof SlideInnerLayout) {
				mDragView = curv;
				flagViewWidth = ((SlideInnerLayout) mDragView).getHandViewWidth();
			}
		}
	}

	private boolean smoothSlideTo(int toOffset) {
		boolean smoothSlideViewTo = mDragHelper.smoothSlideViewTo(mDragView, toOffset, mRealTopOffset);
		if (smoothSlideViewTo) {
			ViewCompat.postInvalidateOnAnimation(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = MotionEventCompat.getActionMasked(ev);
		if ((action != MotionEvent.ACTION_DOWN)) {
			mDragHelper.cancel();
			return super.onInterceptTouchEvent(ev);
		}
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mDragHelper.cancel();
		}
		boolean canInterceptTap = false;
		float x = ev.getX();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mInitialMotionX = x;
			if (mDragView instanceof SlideInnerLayout) {
				((SlideInnerLayout) mDragView).getChildAt(0);
				canInterceptTap = mDragHelper.isViewUnder(((SlideInnerLayout) mDragView).getChildAt(0), (int) x, (int) y);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float absDx = Math.abs(x - mInitialMotionX);
			int slop = mDragHelper.getTouchSlop();
			if (absDx > slop) {
				mDragHelper.cancel();
				return false;
			}
			break;
		}
		return mDragHelper.shouldInterceptTouchEvent(ev) || canInterceptTap;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mDragHelper.processTouchEvent(ev);
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		boolean isDragViewUnder = mDragHelper.isViewUnder(mDragView, (int) x, (int) y);
		switch (action & MotionEventCompat.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mInitialMotionX = x;
			break;
		case MotionEvent.ACTION_UP:
			float dx = x - mInitialMotionX;
			int slop = mDragHelper.getTouchSlop();
			if (isDragViewUnder && Math.abs(dx) > slop) {
				if (dx > 0) {
					slideToRight();
				} else if (dx < 0) {
					slideToLeft();
				}
			}
			break;
		}
		return true;
	}

	public void slideToRight() {
		currentDirection = DIRECTION_RIGHT;
		int finalLeft = getWidth() - getPaddingLeft() - flagViewWidth;
		smoothSlideTo(finalLeft);
		if (onCollapseBoardListener != null) {
			onCollapseBoardListener.onCollapse(false);
		}
	}

	public void slideToLeft() {
		currentDirection = DIRECTION_LEFT;
		int finalLeft = 0;
		smoothSlideTo(finalLeft);
		if (onCollapseBoardListener != null) {
			onCollapseBoardListener.onCollapse(true);
		}
	}

	public void toggleBoard() {
		switch (currentDirection) {
		case DIRECTION_LEFT:
			slideToRight();
			break;
		case DIRECTION_RIGHT:
			slideToLeft();
			break;
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mRealTopOffset = mDragView.getTop() - getPaddingTop();
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	public class DragHelperCallback extends Callback {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child == mDragView;
		}

		/**
		 * Restrict the bounds to prevent slide out the left bounds.
		 */
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// Log.d("DragLayout", "clampViewPositionHorizontal " + left + "," +
			// dx);
			int leftBound = getPaddingLeft();
			int newLeft = Math.max(left, leftBound);
			return newLeft;
		}

		/**
		 * Restrict the view can only slide horizontally and keep current y
		 * axis.
		 */
		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			return mRealTopOffset;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), mRealTopOffset);
			invalidate();
		}

	}

	OnCollapseBoardListener onCollapseBoardListener;

	public void setOnCollapseBoardListener(OnCollapseBoardListener onCollapseBoardListener) {
		this.onCollapseBoardListener = onCollapseBoardListener;
	}

	public interface OnCollapseBoardListener {
		/**
		 * When BoardView is expanded or collapsed will called this method.
		 * 
		 * @param isExpanded
		 */
		public void onCollapse(boolean isExpanded);
	}
}
