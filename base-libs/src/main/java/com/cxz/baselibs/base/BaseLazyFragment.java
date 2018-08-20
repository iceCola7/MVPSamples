package com.cxz.baselibs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

/**
 * @author chenxz
 * @date 2018/8/20
 * @desc BaseLazyFragment
 */
public abstract class BaseLazyFragment extends BaseMvpFragment {

    /**
     * Fragment 处理懒加载，为了防止 setUserVisibleHint 进入多次导致数据重复加载
     */
    protected boolean isUIVisible = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !isUIVisible) {
            isUIVisible = true;
            lazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !isUIVisible) {
            isUIVisible = true;
            lazyLoad();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUIVisible = false;
    }

    @UiThread
    protected abstract void lazyLoad();

}
