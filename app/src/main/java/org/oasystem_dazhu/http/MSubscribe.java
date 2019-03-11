package org.oasystem_dazhu.http;

import android.content.Intent;

import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.presenter.activity.ActivityPresenter;
import org.oasystem_dazhu.mvp.presenter.activity.LoginActivity;
import org.oasystem_dazhu.simplecache.ACache;
import org.oasystem_dazhu.utils.NetUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by www on 2018/12/28.
 */

public abstract class MSubscribe<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        HttpClient.finishRequest();
        ProgressDialogUtil.instance().stopLoad();
    }

    @Override
    public void onError(Throwable e) {
        HttpClient.finishRequest();
        if (HttpClient.getTaskNum() == 0)
            ProgressDialogUtil.instance().stopLoad();
        ToastUtil.l(e.toString());

        //出现错误
        if (e instanceof HttpException) {
            ACache.get(MyApplication.getContext()).clear();
            Intent intent = new Intent(ActivityPresenter.getTopActivity(), LoginActivity.class);
            ActivityPresenter.getTopActivity().startActivity(intent);
            ActivityPresenter.getTopActivity().finish();
            ActivityPresenter.finishAllActivity();
        }
    }

    @Override
    public void onNext(T bean) {
        if (bean instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) bean;
            if (entity.getCode() != 0) {
                ToastUtil.s(entity.getMsg());
                ACache.get(MyApplication.getContext()).clear();
                Intent intent = new Intent(ActivityPresenter.getTopActivity(), LoginActivity.class);
                ActivityPresenter.getTopActivity().startActivity(intent);
                ActivityPresenter.finishAllActivity();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (HttpClient.getTaskNum() == 1)
            ProgressDialogUtil.instance().startLoad("加载中");
        if (!NetUtil.isConnect()) {
            this.unsubscribe();
            HttpClient.finishRequest();
            ProgressDialogUtil.instance().stopLoad();
        }
    }
}
