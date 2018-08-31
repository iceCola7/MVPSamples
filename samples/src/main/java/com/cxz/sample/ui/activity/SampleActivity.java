package com.cxz.sample.ui.activity;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxz.baselibs.base.BaseMvpActivity;
import com.cxz.sample.R;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.bean.WeatherInfo;
import com.cxz.sample.mvp.presenter.SamplePresenter;
import com.cxz.sample.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SampleActivity extends BaseMvpActivity<SamplePresenter> implements SampleContract.View {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    private ProgressDialog mDialog;

    @Override
    protected SamplePresenter createPresenter() {
        return new SamplePresenter();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sample;
    }

    @Override
    protected void initView() {
        mDialog = DialogUtil.getWaitDialog(this, "正在加载");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_send)
    public void doClick(View view){
        Log.e("tag","-----------");
        mPresenter.getWeatherInfo("101010100");
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
    public void showMessage(String message) {
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherInfo(WeatherInfo weatherInfo) {
        textView.setText(weatherInfo.toString());
    }
}
