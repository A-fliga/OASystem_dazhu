package org.oasystem_dazhu.utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.presenter.activity.ActivityPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by www on 2017/6/14.
 */
public class ProgressDialogUtil {

    private static final int START_DIALOG = 0;//开始对话框
    private static final int UPDATE_DIALOG = 1;//更新对话框
    private static final int STOP_DIALOG = 2;//销毁对话框
    private static final int START_EMPTY_DIALOG = 3;//开始无文本对话框
    private static volatile ProgressDialogUtil utils;
    private AlertDialog dialog = null;
    private TextView title = null;
    private WeakReference<Context> context = null;
    private boolean canceledOnTouchOutside = false, cancelable = true;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            String message = "";
            switch (msg.what) {
                case START_DIALOG:// 启动加载框
                    message = (String) msg.obj;
                    if (dialog != null) {
                        updateMsg(message);
                    } else
                        init(message);
                    break;
                case START_EMPTY_DIALOG:
                    if (dialog != null) {
                        updateMsg("");
                    } else
                        init(message);
                case UPDATE_DIALOG:// 更新加载框
                    message = (String) msg.obj;
                    if (title.getVisibility() == View.VISIBLE) {
                        if (TextUtils.isEmpty(message)) {
                            title.setVisibility(View.GONE);
                        } else {
                            title.setText(message);
                        }
                    } else {
                        if (!TextUtils.isEmpty(message)) {
                            title.setText(message);
                            title.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case STOP_DIALOG:// 停止加载框
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog.cancel();
                        dialog = null;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private ProgressDialogUtil() {
    }

    public static ProgressDialogUtil instance() {
        if (utils == null) {
            synchronized (ProgressDialogUtil.class) {
                if (utils == null) {
                    utils = new ProgressDialogUtil();
                }
            }
        }
        return utils;
    }

    /**
     * @方法说明:加载控件与布局
     * @方法名称:init
     * @返回值:void
     */
    private void init(String msg) {
        if (context.get() != null) {
            if (isBackground(context.get())) {
                // 如果程序在后台，则不加载
                return;
            }
        }
        if (context.get() != null) {
            LayoutInflater flat = LayoutInflater.from(context.get());
            View v = flat.inflate(R.layout.prograss_dialog, null);
            // v.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            // 创建对话
            dialog = new AlertDialog.Builder(context.get(), R.style.MyProgressDialogStyle).create();
            // 设置返回键点击消失对话框
            dialog.setCancelable(cancelable);
            // 设置点击返回框外边不消失
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            // 给该对话框增加系统权限
//             dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            // 显示对话
            dialog.show();
            // 加载控件
            title = (TextView) v.findViewById(R.id.loading_msg);

            if (TextUtils.isEmpty(msg)) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title.setText(msg);
            }

            // 必须放到显示对话框下面，否则显示不出效果
            Window window = dialog.getWindow();
            // window.getAttributes().x = 0;
            // window.getAttributes().y = 0;//设置y坐标
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            // params.alpha = 0.6f;
            window.setAttributes(params); // 加载布局组件
            dialog.getWindow().setContentView(v);
        }
    }


    /**
     * @param
     * @方法说明:启动对话框
     * @方法名称:startLoad
     * @返回值:void
     */
    //无文本消息的对话框
    public void startLoad() {
        context = new WeakReference<Context>(ActivityPresenter.getTopActivity());/// 获取当前的activity的上下文
        if (context.get() == null) {
            return;
        }
        if (isBackground(context.get())) {// 如果程序在后台，则不加载
            return;
        }
        final Message message = new Message();
        message.what = START_EMPTY_DIALOG;
        handler.sendMessage(message);
    }

    //有文本消息的对话框
    public void startLoad(String msg) {
        context = new WeakReference<Context>(ActivityPresenter.getTopActivity());// 获取当前的activity的上下文
        if (context.get() == null) {
            return;
        }
        if (isBackground(context.get())) {// 如果程序在后台，则不加载
            return;
        }
        final Message message = new Message();
        message.what = START_DIALOG;
        message.obj = msg;
        handler.sendMessage(message);
    }


    /**
     * @param msg
     * @方法说明:更新显示的内容
     * @方法名称:UpdateMsg
     * @返回值:void
     */
    private void updateMsg(String msg) {
        Message message = new Message();
        message.what = UPDATE_DIALOG;
        message.obj = msg;
        handler.sendMessage(message);
    }

    /**
     * @param flag
     * @方法说明:允许加载条转动的时候去点击系统返回键
     * @方法名称:openCancelable
     * @返回值:void
     */
    public ProgressDialogUtil cancelable(boolean flag) {
        this.cancelable = flag;
        return this;
    }

    /**
     * @param
     * @方法说明:允许点击对话框触摸消失
     * @方法名称:isTouchDismiss
     * @返回值:void
     */
    public ProgressDialogUtil canceledOnTouchOutside(boolean isDismiss) {
        this.canceledOnTouchOutside = isDismiss;
        return this;
    }

    /**
     * @方法说明:让警告框消失
     * @方法名称:dismiss
     * @返回值:void
     */
    public void stopLoad() {
        handler.sendEmptyMessage(STOP_DIALOG);
    }

    public boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 后台运行
                    return true;
                } else {
                    // 前台运行
                    return false;
                }
            }
        }
        return false;
    }
}