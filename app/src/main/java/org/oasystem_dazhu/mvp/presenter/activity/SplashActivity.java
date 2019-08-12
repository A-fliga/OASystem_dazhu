package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.bean.JPushRegisterBean;
import org.oasystem_dazhu.mvp.view.SplashDelegate;
import org.oasystem_dazhu.utils.ProgressDialogUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by www on 2018/12/29.
 */

public class SplashActivity extends ActivityPresenter {
    private Handler handler;
    private boolean canStart2Main = false;

    @Override
    public Class getDelegateClass() {
        return SplashDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        handler.postDelayed(mRun, 1500);
        EventBus.getDefault().register(this);
    }


    Runnable mRun = new Runnable() {
        @Override
        public void run() {
            if (UserManager.getInstance().isLogin()) {
                if (UserManager.getInstance().isDazhu()) {
                    startMyActivityWithFinish(MainActivity.class);
                } else {
                    if (!TextUtils.isEmpty(JPushInterface.getRegistrationID(SplashActivity.this))) {
                        startMyActivityWithFinish(MainActivity.class);
                    } else {
                        canStart2Main = true;
                        ProgressDialogUtil.instance().startLoad("初始化中");
                    }
                }
            } else {
                startMyActivityWithFinish(LoginActivity.class);
            }
        }
    };

    //极光注册成功的监听
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void jPushRegister(JPushRegisterBean bean) {
        if (bean != null) {
            if (canStart2Main) {
                ProgressDialogUtil.instance().stopLoad();
                startMyActivityWithFinish(MainActivity.class);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mRun);
        EventBus.getDefault().unregister(this);
    }
}
