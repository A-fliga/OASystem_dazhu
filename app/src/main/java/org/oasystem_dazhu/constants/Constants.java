package org.oasystem_dazhu.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by www on 2017/11/13.
 * 常量类
 */

public final class Constants {
    public static Boolean CORE_INIT = false;
    public static Boolean ClearEraser = false;
    public static final String WANYUAN_URL = "http://112.35.6.11:9098/api/";
    public static final String DAZHU_URL = "http://112.35.1.224:9099/api/";
    public static final int SIZE_IN_DP = 640;
    public static final int TYPE_WIDTH_COUNT = 5;

    private static final String PACKAGE_PATH = "OASystem_dazhu";
    //已处理的文件路径
    public static final String OFFICE_PREVIEW = Environment.getExternalStorageDirectory().toString() +
            File.separator + PACKAGE_PATH + File.separator+"OfficePreView";
    public static int currenPage = 0;

    //准备签字的路径
    public static final String SIGN_OFFICIAL = Environment.getExternalStorageDirectory().toString() +
            File.separator + PACKAGE_PATH + File.separator+"SignOfficial";

//    public static int SIGN_BITMAP_WIDTH = 0,SIGN_BITMAP_HEIGHT = 0;
    //签完字的路径
    public static final String SIGN_RESULT = Environment.getExternalStorageDirectory().toString() +
            File.separator +PACKAGE_PATH + File.separator+"SignResult";

    //下载更新包的路径
    public static final String DOWNLOAD_UPDATE = Environment.getExternalStorageDirectory().toString() +
            File.separator + PACKAGE_PATH + File.separator+"Apk";
    //极光别名，不可修改
    public static final String JPUSH_NAME = "USER_ID";

    public static final String SP_COOKIE = "BAOSHENG_VILLAGE";
    public static final String OA_SYSTEM = PACKAGE_PATH;
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String PEN_WIDTH = "PEN_WIDTH";
    public static final String PEN_COLOR = "PEN_COLOR";
    //调用相机请求码
    public static final int TAKE_PHOTO_REQUEST_CODE = 2000;

    //从图库中选择图片
    public static final int CHOOSE_PHOTO_FROM_GALLERY_CODE = 2001;

    public static final long DEFAULT_ID = -1000;

    //选择配置信息的code
    public static final int CHOOSE_SETTINGS_REQUEST_CODE = 1000;

    public static final int CHOOSE_SETTINGS_RESULT_CODE = 1001;

    //SharedPreference的key值，不要重复

    public static final String JI_GUANG_TAG = "JI_GUANG_TAG";//极光别名

    public static final String EQUIPMENT_ID = "EQUIPMENT_ID";//保存设备ID

    public static final String IS_APP_ALIVE = "IS_APP_ALIVE";//极光别名


    //登录类型
    public static final String LOGIN_TYPE = "LOGIN_TYPE";

    public static class LoginType {
        //党员
        public static final String IS_PARTY_MEMBER = "IS_PARTY_MEMBER";
        //群众
        public static final String IS_PUBLIC = "IS_PUBLIC";
        //未登录
        public static final String IS_VISITOR = "IS_VISITOR";
    }

    public static String CHATROOM_ID = "";

    //用户的登录手机和密码
    public static final String USER_NAME = "USER_NAME";


    public static final String FROM_CHANGE_PWD = "FROM_CHANGE_PWD";
    public static final String FROM_NORMAL = "FROM_NORMAL";

    public static final int IS_INIT = 0;
    public static final int IS_REFRESH = 1;
    public static final int IS_LOAD_MORE = 2;

    public static final int FROM_BANNER = 1000;
    public static final int FROM_INFO = 1001;
    public static final int FROM_SPIRIT = 1002;
    public static final String FROM_WHERE = "FROM_WHERE";
    public static final String INFO_ID = "INFO_ID";
    public static final String BANNER_ID = "BANNER_ID";


    public static final String LOGIN_INFO = "LOGIN_INFO";
}
