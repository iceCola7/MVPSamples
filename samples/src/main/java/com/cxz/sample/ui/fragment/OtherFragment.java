package com.cxz.sample.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.cxz.baselibs.base.BaseFragment;
import com.cxz.sample.R;
import com.cxz.sample.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public class OtherFragment extends BaseFragment {

    @BindView(R.id.tv_result)
    TextView tv_result;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.btn_send)
    public void doClick(View view) {
        sendMessage();
    }

    private void sendMessage() {
        EventBus.getDefault().post(new MessageEvent("message_fragment"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(MessageEvent event) {
        tv_result.setText(event.getMessage());
    }

}
