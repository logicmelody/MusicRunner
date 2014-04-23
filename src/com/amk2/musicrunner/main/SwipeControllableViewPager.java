package com.amk2.musicrunner.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeControllableViewPager extends ViewPager {

	private boolean mIsSwipeable = true;

	public SwipeControllableViewPager(Context context) {
		super(context);
	}

	public SwipeControllableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if(mIsSwipeable) {
			return super.onInterceptTouchEvent(event);
		} else {
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(mIsSwipeable) {
			return super.onTouchEvent(event);
		} else {
			return false;
		}
	}

	public void setSwipeable(boolean isSwipeable) {
		mIsSwipeable = isSwipeable;
	}
}
