package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.JPushRegisterBean;
import org.oasystem_dazhu.mvp.model.bean.LoginBean;
import org.oasystem_dazhu.mvp.view.LoginDelegate;
import org.oasystem_dazhu.simplecache.ACache;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.SharedPreferencesUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.jpush.android.api.JPushInterface;

import static org.oasystem_dazhu.constants.Constants.LOGIN_INFO;


/**
 * Created by www on 2018/12/29.
 */

public class LoginActivity extends ActivityPresenter {
    private EditText unEt, pwdEt;
    private boolean isRequestSuccess = false;

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
        addTextChangeListener(unEt);
        pwdEt = viewDelegate.get(R.id.login_password);
        viewDelegate.setOnClickListener(onClickListener, R.id.login_btn, R.id.can_not_login, R.id.forget_pwd);
        EventBus.getDefault().register(this);
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[/\\:*?<>|\"\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    private void addTextChangeListener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String editable = et.getText().toString();
                String str = stringFilter(editable); //过滤特殊字符
                if (!editable.equals(str)) {
                    et.setText(str);
                }
                et.setSelection(et.length());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                    if (UserManager.getInstance().isDazhu()) {
                        startMyActivityWithFinish(MainActivity.class);
                    } else {
                        if (!TextUtils.isEmpty(JPushInterface.getRegistrationID(LoginActivity.this))) {
                            startMyActivityWithFinish(MainActivity.class);
                        } else {
                            isRequestSuccess = true;
                            ProgressDialogUtil.instance().startLoad("初始化中");
                        }
                    }
                }
            }
        }, unEt.getText().toString().replaceAll(" ", ""), pwdEt.getText().toString().replaceAll(" ", ""));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //极光注册成功的监听
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void jPushRegister(JPushRegisterBean bean) {
        if (bean != null) {
            ProgressDialogUtil.instance().stopLoad();
            if (isRequestSuccess) {
                startMyActivityWithFinish(MainActivity.class);
            }
        }
    }

}
