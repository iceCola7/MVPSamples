package com.cxz.baselibs.widget;

import android.view.View;

/**
 * @author chenxz
 * @date 2018/11/23
 * @desc 防止连续点击
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    private long mThrottleFirstTime = 1000;
    private long mLastClickTime = 0;

    public OnNoDoubleClickListener() {
    }

    public OnNoDoubleClickListener(long mThrottleFirstTime) {
        this.mThrottleFirstTime = mThrottleFirstTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    abstract void onNoDoubleClick(View v);

}
