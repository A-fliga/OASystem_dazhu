package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.LoginBean;
import org.oasystem_dazhu.mvp.view.LoginDelegate;
import org.oasystem_dazhu.simplecache.ACache;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.SharedPreferencesUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import static org.oasystem_dazhu.constants.Constants.LOGIN_INFO;


/**
 * Created by www on 2018/12/29.
 */

public class LoginActivity extends ActivityPresenter {
    private EditText unEt, pwdEt;

    @Override
    public Class getDelegateClass() {
        return LoginDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unEt = viewDelegate.get(R.id.login_username);
        pwdEt = viewDelegate.get(R.id.login_password);
        viewDelegate.setOnClickListener(onClickListener, R.id.login_btn, R.id.can_not_login, R.id.forget_pwd);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.login_btn:
                    if (unEt.getText().toString().replaceAll(" ", "").isEmpty() || pwdEt.getText().toString().replaceAll(" ", "").isEmpty()) {
                        ToastUtil.s("输入不能为空");
                    } else {
                        if (!AppUtil.isFastDoubleClick(1000)) {
                            toLogin();
                        }
                    }
                    break;

                case R.id.can_not_login:
                    ToastUtil.s("暂不支持外部修改，请联系管理员");
                    break;

                case R.id.forget_pwd:
                    ToastUtil.s("暂不支持外部修改，请联系管理员");
                    break;
            }
        }
    };

    private void toLogin() {
        PublicModel.getInstance().login(new MSubscribe<BaseEntity<LoginBean>>() {
            @Override
            public void onNext(BaseEntity<LoginBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    SharedPreferencesUtil.saveUserName(unEt.getText().toString().replaceAll(" ", ""));
                    ACache aCache = ACache.get(MyApplication.getContext());
                    aCache.put(LOGIN_INFO, bean.getData());
                    startMyActivityWithFinish(MainActivity.class);
                }
            }
        }, unEt.getText().toString().replaceAll(" ", ""), pwdEt.getText().toString().replaceAll(" ", ""));
    }
}
