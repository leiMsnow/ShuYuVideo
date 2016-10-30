package com.shuyu.video.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Azure on 2016/10/30.
 */

public class MyViewPager extends ViewPager {

    private boolean canScroll = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (canScroll)
            super.scrollTo(x, y);
    }
}
