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
    private EditText mSourcePassword, mNewPassword, mNewPasswordConfirm;

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
        mViewDelegate.setOnClickListener(onClickListener, R.id.sure_btn);
    }

    private void initView() {
        mSourcePassword = mViewDelegate.get(R.id.source_password);
        mNewPassword = mViewDelegate.get(R.id.new_password);
        mNewPasswordConfirm = mViewDelegate.get(R.id.new_password_two);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sure_btn:
                    if (isEmpty(mSourcePassword)) {
                        ToastUtil.l("原密码不能为空");
                        break;
                    }

                    if (isEmpty(mNewPassword) || isEmpty(mNewPasswordConfirm)) {
                        ToastUtil.l("新密码不能为空");
                        break;
                    }

                    if (!mNewPassword.getText().toString().replaceAll(" ", "")
                            .equals(mNewPasswordConfirm.getText().toString().replaceAll(" ", ""))) {
                        ToastUtil.l("两次密码输入不一致");
                        break;
                    }

                    if (mNewPassword.getText().toString().replaceAll(" ", "").length() < 6 ||
                            mNewPasswordConfirm.getText().toString().replaceAll(" ", "").length() < 6) {
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
        }, mSourcePassword.getText().toString().replaceAll(" ", ""), mNewPassword.getText().toString().replaceAll(" ", ""));
    }

    private Boolean isEmpty(EditText editText) {
        return editText.getText().toString().replaceAll(" ", "").isEmpty();
    }
}
