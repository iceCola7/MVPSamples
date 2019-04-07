package com.cxz.sample.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxz.baselibs.base.BaseMvpActivity;
import com.cxz.baselibs.imageloader.ImageLoader;
import com.cxz.sample.R;
import com.cxz.sample.event.MessageEvent;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.bean.BannerListBean;
import com.cxz.sample.mvp.model.bean.CollectListBean;
import com.cxz.sample.mvp.model.bean.WeatherInfo;
import com.cxz.sample.mvp.presenter.SamplePresenter;
import com.cxz.sample.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class SampleActivity extends BaseMvpActivity<SamplePresenter> implements SampleContract.View {

    private ProgressDialog mProgressDialog;

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_result2)
    TextView tvResult2;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.imageView)
    ImageView imageView;

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
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_sample;
    }

    @Override
    protected void initView() {
        super.initView();
        mProgressDialog = DialogUtil.getWaitDialog(this, "正在加载");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
    }

    @OnClick({R.id.btn_get_weather, R.id.btn_get_banner, R.id.btn_login, R.id.btn_collect,
            R.id.btn_permission, R.id.btn_logout})
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_weather:
                sendMessage();
                tvResult2.setText("");
                mPresenter.getWeatherInfo(getCityId());
                break;
            case R.id.btn_get_banner:
                tvResult.setText("");
                imageView.setVisibility(View.VISIBLE);
                mPresenter.getBannerList();
                break;
            case R.id.btn_login:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    showDefaultMsg("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showDefaultMsg("密码不能为空");
                    return;
                }
                mPresenter.login(username, password);
                break;
            case R.id.btn_collect:
                tvResult.setText("");
                imageView.setVisibility(View.GONE);
                mPresenter.getCollectList(0);
                break;
            case R.id.btn_permission:
                mPresenter.addDispose(getRxPermissions().request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    // TODO 允许
                                    showDefaultMsg("已允许");
                                } else {
                                    // TODO 未允许
                                    showDefaultMsg("未允许");
                                }
                            }
                        }));
                break;
            case R.id.btn_logout:
                mPresenter.logout();
                break;
        }
    }

    public void sendMessage() {
        EventBus.getDefault().post(new MessageEvent("message"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(MessageEvent event) {
        tvResult2.setText(event.getMessage());
    }

    @Override
    public void showWeatherInfo(WeatherInfo weatherInfo) {
        String s = weatherInfo.getWeatherinfo().getCity() + ","
                + weatherInfo.getWeatherinfo().getTemp() + ","
                + weatherInfo.getWeatherinfo().getWD();
        tvResult2.setText(s);
    }

    @Override
    public void loginSuccess() {
        showDefaultMsg("登录成功");
    }

    @Override
    public void showBannerList(List<BannerListBean.Banner> bannerList) {
        if (bannerList.size() > 0) {
            tvResult.setText(bannerList.get(0).getTitle());
            ImageLoader.getInstance()
                    .loadImageByDefault(this, bannerList.get(0).getImagePath(), imageView);
        }
    }

    @Override
    public void showCollectList(CollectListBean.Collect collect) {
        if (collect.getDatas().size() > 0) {
            tvResult.setText(collect.getDatas().get(0).getTitle());
        }
    }

    @Override
    public void logoutSuccess() {
        showDefaultMsg("已退出登录");
    }

}
