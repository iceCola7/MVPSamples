package com.cxz.baselibs.utils;

import android.view.View;

/**
 * @author chenxz
 * @date 2018/10/29
 * @desc 连续点击事件
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    private int mThrottleFirstTime = 1000;
    private long mLastClickTime = 0;

    public OnNoDoubleClickListener() {
    }

    public OnNoDoubleClickListener(int throttleFirstTime) {
        mThrottleFirstTime = throttleFirstTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);

}
