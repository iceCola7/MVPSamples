package com.cxz.sample.ui.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.TextView;

import com.cxz.baselibs.base.BaseMvpActivity;
import com.cxz.sample.R;
import com.cxz.sample.event.MessageEvent;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.bean.WeatherInfo;
import com.cxz.sample.mvp.presenter.SamplePresenter;
import com.cxz.sample.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class SampleActivity extends BaseMvpActivity<SamplePresenter> implements SampleContract.View {

    @BindView(R.id.tv_result)
    TextView tv_result;
    @BindView(R.id.tv_result2)
    TextView tv_result2;
    private ProgressDialog mDialog;

    @Override
    public String getCityId() {
        return "101010100";
    }

    @Override
    protected SamplePresenter createPresenter() {
        return new SamplePresenter();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sample;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = DialogUtil.getWaitDialog(this, "正在加载");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_send)
    public void doClick(View view) {
        sendMessage();
        mPresenter.getWeatherInfo(getCityId());
    }

    public void sendMessage() {
        EventBus.getDefault().post(new MessageEvent("message"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(MessageEvent event) {
        tv_result2.setText(event.getMessage());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
    }

    @Override
    public void showWeatherInfo(WeatherInfo weatherInfo) {
        tv_result.setText(weatherInfo.toString());
    }
}
