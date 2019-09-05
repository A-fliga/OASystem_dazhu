package org.oasystem_dazhu.mvp.presenter.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.mvp.presenter.activity.ActivityPresenter;
import org.oasystem_dazhu.mvp.presenter.activity.ChangePassWordActivity;
import org.oasystem_dazhu.mvp.presenter.activity.LoginActivity;
import org.oasystem_dazhu.mvp.presenter.activity.MySealActivity;
import org.oasystem_dazhu.mvp.view.UserCenterDelegate;
import org.oasystem_dazhu.simplecache.ACache;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.FileUtil;
import org.oasystem_dazhu.utils.ToastUtil;

/**
 * Created by www on 2018/12/29.
 */

public class UserCenterFragment extends FragmentPresenter {
    @Override
    public Class getDelegateClass() {
        return UserCenterDelegate.class;
    }

    @Override
    protected void onFragmentVisible() {

    }

    @Override
    protected void onFragmentHidden() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewDelegate.setOnClickListener(mOnClickListener, R.id.logout, R.id.mySeal, R.id.changePwd, R.id.clear_cache);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.logout:
                    showDialog();
                    break;
                case R.id.mySeal:
                    startMyActivity(MySealActivity.class, null);
                    break;

                case R.id.changePwd:
                    startMyActivity(ChangePassWordActivity.class, null);
                    break;

                case R.id.clear_cache:
                    FileUtil.clearCache();
                    ToastUtil.s("清理完成");
                    break;
            }
        }
    };


    private void showDialog() {
        DialogUtil.showDialog(getActivity(), "您确定要退出帐号吗？", "确定", "取消", onClickListener);
    }

    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                //退出帐号，退出极光推送
                //// TODO: 2018/12/30
                ACache.get(MyApplication.getContext()).clear();
                startMyActivityWithFinish(LoginActivity.class, null);
                ActivityPresenter.finishAllActivity();
            }
            dialogInterface.dismiss();
        }
    };
}
