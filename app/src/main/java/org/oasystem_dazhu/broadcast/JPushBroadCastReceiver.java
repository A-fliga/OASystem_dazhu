package org.oasystem_dazhu.broadcast;

import android.content.Context;
import android.content.DialogInterface;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.bean.JPushRegisterBean;
import org.oasystem_dazhu.mvp.presenter.activity.ActivityPresenter;
import org.oasystem_dazhu.utils.DialogUtil;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by www on 2019/8/11.
 */

public class JPushBroadCastReceiver extends JPushMessageReceiver {
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        JPushRegisterBean bean = new JPushRegisterBean();
        bean.setRegistrationId(registrationId);
        EventBus.getDefault().post(bean);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        DialogUtil.showDialog(ActivityPresenter.getTopActivity(), "您有新的审批", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
    }
}
