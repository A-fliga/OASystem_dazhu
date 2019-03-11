package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.view.ChangePwdDelegate;
import org.oasystem_dazhu.simplecache.ACache;
import org.oasystem_dazhu.utils.ToastUtil;

/**
 * Created by www on 2019/3/11.
 */

public class ChangePassWordActivity extends ActivityPresenter<ChangePwdDelegate> {
    private EditText source_password, new_password, new_password_two;

    @Override
    public Class<ChangePwdDelegate> getDelegateClass() {
        return ChangePwdDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        viewDelegate.setOnClickListener(onClickListener, R.id.sure_btn);
    }

    private void initView() {
        source_password = viewDelegate.get(R.id.source_password);
        new_password = viewDelegate.get(R.id.new_password);
        new_password_two = viewDelegate.get(R.id.new_password_two);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sure_btn:
                    if (isEmpty(source_password)) {
                        ToastUtil.l("原密码不能为空");
                        break;
                    }

                    if (isEmpty(new_password) || isEmpty(new_password_two)) {
                        ToastUtil.l("新密码不能为空");
                        break;
                    }

                    if (!new_password.getText().toString().replaceAll(" ", "")
                            .equals(new_password_two.getText().toString().replaceAll(" ", ""))) {
                        ToastUtil.l("两次密码输入不一致");
                        break;
                    }

                    if (new_password.getText().toString().replaceAll(" ", "").length() < 6 ||
                            new_password_two.getText().toString().replaceAll(" ", "").length() < 6) {
                        ToastUtil.l("新密码不能少于6位");
                        break;
                    }
                    toChangePwd();
                    break;
            }
        }
    };

    private void toChangePwd() {
        PublicModel.getInstance().updatePwd(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                if (bean.getCode() != 0) {
                    ToastUtil.l(bean.getMsg());
                } else {
                    ToastUtil.l("修改成功，请重新登录");
                    ACache.get(MyApplication.getContext()).clear();
                    startMyActivityWithFinish(LoginActivity.class, null);
                    ActivityPresenter.finishAllActivity();
                }
            }
        }, source_password.getText().toString().replaceAll(" ", ""), new_password.getText().toString().replaceAll(" ", ""));
    }

    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().replaceAll(" ", "").isEmpty();
    }
}
