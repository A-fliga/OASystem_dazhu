package org.oasystem_dazhu.constants;

import android.os.Environment;

import org.oasystem_dazhu.BuildConfig;

import java.io.File;

/**
 * Created by www on 2017/11/13.
 * 常量类
 */

public final class Constants {
    public static Boolean CORE_INIT = false;
    public static Boolean ClearEraser = false;
    public static final int SIZE_IN_DP = 640;
    public static final int TYPE_WIDTH_COUNT = 5;
    //从图库中选择图片
    public static final int CHOOSE_PHOTO_FROM_GALLERY_CODE = 2001;
    public static final String OA_SYSTEM = getOrg();
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String PEN_WIDTH = "PEN_WIDTH";
    public static final String PEN_COLOR = "PEN_COLOR";
    //登录类型
    public static final String LOGIN_TYPE = "LOGIN_TYPE";
    //用户的登录手机和密码
    public static final String USER_NAME = "USER_NAME";
    public static final String LOGIN_INFO = "LOGIN_INFO";
    public static final String FILE_PARENT_DIR = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg();
    //已处理的文件路径
    public static final String OFFICE_PREVIEW = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg() + File.separator + "OfficePreView";

    //准备签字的路径
    public static final String SIGN_OFFICIAL = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg() + File.separator + "SignOfficial";

    //签完字的路径
    public static final String SIGN_RESULT = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg() + File.separator + "SignResult";

    //下载更新包的路径
    public static final String DOWNLOAD_UPDATE = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg() + File.separator + "Apk";

    //签字临时文件保存的路径
    public static final String SIGN_CACHE = Environment.getExternalStorageDirectory().toString() +
            File.separator + getOrg() + File.separator + "sign_cache";



    public static String getOrg() {
        switch (BuildConfig.HOST) {
            case "http://112.33.26.41:9098/api/":
                return "jingkaiqu";
            case "http://112.35.0.188:9098/api/":
                return "xuanhan";
            case "http://112.35.1.224:9099/api/":
                return "dazhu";
            case "http://112.33.39.86:9098/api/":
                return "weiyuan";
            case "http://112.33.63.90:9098/api/":
                return "quxian";
            case "http://106.12.106.117/api/":
                return "ceshi";
            default:
                return "OA_System";
        }
    }
}
