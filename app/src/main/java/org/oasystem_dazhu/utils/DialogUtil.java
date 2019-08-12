package org.oasystem_dazhu.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.oasystem_dazhu.R;

import me.jessyan.autosize.AutoSize;


/**
 * Created by www on 2017/11/16.
 */

public class DialogUtil {
    /**
     * 这是兼容的 AlertDialog
     */
    public static void showDialog(Activity activity, String message, String sure,
                                  String cancel, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("温馨提示").setCancelable(false).setPositiveButton(sure, listener).
                setNegativeButton(cancel, listener).setMessage(message);
        AutoSize.cancelAdapt(activity);
        builder.show();
    }

    /**
     * 复选对话框
     */
    public static void showChoiceDialog(Activity activity, String title, String sure,
                                        String cancel, String[] data, boolean[] booleanList,
                                        DialogInterface.OnClickListener listener,
                                        DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setCancelable(false).setPositiveButton(sure, listener).
                setNegativeButton(cancel, listener).setMultiChoiceItems(data, booleanList, multiChoiceClickListener);
        AutoSize.cancelAdapt(activity);
        builder.show();
    }

    /**
     * 单选对话框
     */
    public static void showSingleChoiceDialog(Activity activity, String title, String sure,
                                              String cancel, String[] data,
                                              DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setCancelable(false).setPositiveButton(sure, listener).
                setNegativeButton(cancel, listener).setSingleChoiceItems(data, -1, listener);
        AutoSize.cancelAdapt(activity);
        builder.show();
    }

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("正在更新，请勿关闭！");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.setProgress(0);
        return pd;
    }

    public static AlertDialog createAlertDialog(Context context, View view) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).create();
        dialog.setView(view);
        return dialog;
    }

    public static View getDialogView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView;
        dialogView = inflater.inflate(R.layout.personal_icon_dialog, null);
        return dialogView;
    }


    /**
     * 显示定位权限被拒绝对话框
     */
    public static void showLocIgnoredDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.mipmap.mlogo);
        builder.setTitle("手机已关闭位置权限");
        builder.setMessage("请在权限管理里打开");

        //监听下方button点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                }
                activity.startActivity(localIntent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        AutoSize.cancelAdapt(activity);
        dialog.show();
    }


    /**
     * 这是兼容的 AlertDialog
     */
    public static void showDialog(Activity context, String message, String sure, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示").setCancelable(true).setPositiveButton(sure, listener).
                setMessage(message);
        AutoSize.cancelAdapt(context);
        builder.show();
    }

    /**
     * 这是兼容的 AlertDialog
     */
    public static AlertDialog showDialog2(Context context, String message, String sure, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示").setCancelable(false).setPositiveButton(sure, listener).
                setMessage(message);
        return builder.show();
    }


    /**
     * 显示定位服务未开启确认对话框
     */
    public static void showLocServiceDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("手机未开启位置服务")
                .setMessage("请在权限管理里打开")
                .setNegativeButton("取消", null)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            activity.startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            intent.setAction(Settings.ACTION_SETTINGS);
                            try {
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
        ;
        AutoSize.cancelAdapt(activity);
        builder.show();
    }


}
