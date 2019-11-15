package com.xslong.xslonglib.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁用ViewPager左右滑动事件
 */
public class DismissScrollViewPager extends ViewPager {

    private boolean dismissScroll = false;
    
    public DismissScrollViewPager(Context context) {
        super(context);
    }

    public DismissScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDismissScroll(boolean dismissScroll) {
        this.dismissScroll = dismissScroll;
    }



    @Override
    public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {  
        /* return false;//super.onTouchEvent(arg0); */
        if (dismissScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (dismissScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
