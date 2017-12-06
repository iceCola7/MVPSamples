package com.cxz.samples.module.other;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxz.samples.R;
import com.cxz.samples.app.App;
import com.cxz.samples.base.BaseFragment;
import com.cxz.samples.di.component.DaggerOtherFragmentComponent;
import com.cxz.samples.di.module.OtherFragmentModule;
import com.cxz.samples.event.MessageEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxz on 2017/12/3.
 */

public class OtherFragment extends BaseFragment<OtherPresenter> implements OtherContract.View {

    @BindView(R.id.textView3)
    TextView textView;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initInject() {
        DaggerOtherFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .otherFragmentModule(new OtherFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @OnClick(R.id.button2)
    public void doClick(View view) {
        mPresenter.sendMessage();
    }

    @Override
    public void setMessage(MessageEvent messageEvent) {
        textView.setText(messageEvent.getMessage());
    }
}
