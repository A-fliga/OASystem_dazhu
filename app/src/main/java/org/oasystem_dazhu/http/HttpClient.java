package org.oasystem_dazhu.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.oasystem_dazhu.BuildConfig;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.http.cookie.CookiesManager;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.LoginBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeListBean;
import org.oasystem_dazhu.mvp.model.bean.OfficeTypeBean;
import org.oasystem_dazhu.mvp.model.bean.ScreenBean;
import org.oasystem_dazhu.mvp.model.bean.UpFileBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.simplecache.ACache;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.oasystem_dazhu.constants.Constants.LOGIN_INFO;

/**
 * Created by www on 2017/11/13.
 */

public final class HttpClient {

    /**
     * HttpClient 对象
     */
    private static volatile HttpClient sHttpClient;
    private static AtomicInteger httpTaskNum = new AtomicInteger(0);
    /**
     * mmApi 接口
     */
    private final Api mApi;
    private Gson mGson;

    /**
     * 私有的构造方法
     */
    private HttpClient(String host) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        HttpLoggingInterceptor.Logger.DEFAULT.log(message);
//                        FileUtil.writeLog(message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                //是否设置cookie
                .cookieJar(new CookiesManager(MyApplication.getContext()))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        mApi = retrofit.create(Api.class);
    }


    private HashMap<String, String> getBodyMap(String[] key, String[] value) {
        HashMap<String, String> queryMap = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            queryMap.put(key[i], value[i]);
        }
        return queryMap;
    }

    private String[] getStrings(String content) {
        return content.split(",");
    }

    private String[] getStrings(String... strings) {
        String[] content = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            content[i] = strings[i];
        }
        return content;
    }

    /**
     * @return return {@link HttpClient} 单例
     */
    public static HttpClient getInstance() {
        if (sHttpClient == null) {
            synchronized (HttpClient.class) {
                if (sHttpClient == null) {
                    sHttpClient = new HttpClient(BuildConfig.HOST);
                }
            }
        }
        httpTaskNum.incrementAndGet();
        return sHttpClient;
    }

//    /**
//     * @return return {@link HttpClient} 单例
//     */
//    public static HttpClient getNewInstance() {
//        if (sHttpClient == null) {
//            synchronized (HttpClient.class) {
//                if (sHttpClient == null) {
//                    sHttpClient = new HttpClient(BuildConfig.HOST,1);
//                }
//            }
//        }
//        httpTaskNum.incrementAndGet();
//        return sHttpClient;
//    }


    public static void finishRequest() {
        httpTaskNum.decrementAndGet();
    }

    public static int getTaskNum() {
        return httpTaskNum.intValue();
    }

    /**
     * 线程切换
     *
     * @param o   {@link Observable}
     * @param s   {@link Subscriber}
     * @param <T> 可变类型
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private RequestBody getMapRequestBody(HashMap<String, String> params) {
        return RequestBody.create(MediaType.parse(Constants.CONTENT_TYPE), getGson().toJson(params));
    }

    private RequestBody getObjRequestBody(Object o) {
        return RequestBody.create(MediaType.parse(Constants.CONTENT_TYPE), getGson().toJson(o));
    }

    private String addToken() {
        LoginBean bean = (LoginBean) ACache.get(MyApplication.getContext()).getAsObject(LOGIN_INFO);
        StringBuffer sb = new StringBuffer();
        sb.append(bean.getToken_type()).append(" ").append(bean.getAccess_token());
        return sb.toString();
    }

    private MultipartBody.Part getFileRequestBody(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }

    private Gson getGson() {
        if (mGson == null)
            mGson = new GsonBuilder().disableHtmlEscaping().create();
        return mGson;
    }


    /**
     * 登录
     */
    public void login(Subscriber<BaseEntity<LoginBean>> subscriber, String username, String password) {
        Observable observable = mApi.login(
                getMapRequestBody(getBodyMap(getStrings("username,password"), getStrings(username, password))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     */
    public void updatePwd(Subscriber<BaseEntity> subscriber, String ypass, String npass) {
        Observable observable = mApi.updatePwd(addToken(),
                getMapRequestBody(getBodyMap(getStrings("ypass,npass"), getStrings(ypass, npass))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(Subscriber<BaseEntity<UserInfo>> subscriber) {
        Observable observable = mApi.getUserInfo(addToken());
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取资源
     */
    public void getSource(Subscriber<ResponseBody> subscriber, String sourceId) {
        Observable observable = mApi.getSource(addToken(), Integer.parseInt(sourceId));
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取文档分类
     */
    public void getOfficeType(Subscriber<BaseEntity<OfficeTypeBean>> subscriber, String type) {
        Observable observable = mApi.getOfficeType(addToken(), getMapRequestBody(getBodyMap(getStrings("type"), getStrings(type))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取个人文档
     */
    public void getOfficeList(Subscriber<BaseEntity<OfficeListBean>> subscriber, String document_type_id, String page) {
        Observable observable = mApi.getOfficeList(addToken(), getMapRequestBody(getBodyMap(getStrings("document_type_id", "page"), getStrings(document_type_id, page))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 待我审批
     */
    public void getNotDoneDocument(Subscriber<BaseEntity<DocumentBean>> subscriber, ScreenBean bean) {
        String data = new Gson().toJson(bean);
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("param", data);
        Observable observable = mApi.getNotDoneDocument(addToken(), getMapRequestBody(bodyMap));
        toSubscribe(observable, subscriber);
    }


    /**
     * 我已审批
     */
    public void getDoneDocument(Subscriber<BaseEntity<DocumentBean>> subscriber, ScreenBean bean) {
        String data = new Gson().toJson(bean);
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("param", data);
        Observable observable = mApi.getDoneDocument(addToken(), getMapRequestBody(bodyMap));
        toSubscribe(observable, subscriber);
    }


    /**
     * 上传资源
     */
    public void upload_file(Subscriber<BaseEntity<UpFileBean>> subscriber, File file) {
        Observable observable = mApi.upload_file(addToken(), getFileRequestBody(file));
        toSubscribe(observable, subscriber);
    }

    /**
     * 审批接口
     */
    public void examine(Subscriber<BaseEntity> subscriber, String id, String status, String accessory_source_id, String form_source_id) {
        Observable observable = mApi.examine(addToken(), getMapRequestBody(getBodyMap(getStrings("id", "status", "accessory_source_id", "form_source_id")
                , getStrings(id, status, accessory_source_id, form_source_id))));
        toSubscribe(observable, subscriber);
    }


    /**
     * 结束流程
     */
    public void closeAll(Subscriber<BaseEntity> subscriber, int id, String accessory_source_id, String form_source_id) {
        Observable observable = mApi.closeAll(addToken(), getMapRequestBody(getBodyMap(getStrings("id", "accessory_source_id", "form_source_id"),
                getStrings(String.valueOf(id), accessory_source_id, form_source_id))));

        toSubscribe(observable, subscriber);
    }

    /**
     * 添加附件
     */
    public void addAccessory(Subscriber<BaseEntity> subscriber, int id, String name, int source_id) {
        Observable observable = mApi.addAccessory(addToken(), getMapRequestBody(getBodyMap(getStrings("id", "name", "source_id"),
                getStrings(String.valueOf(id), name, String.valueOf(source_id)))));
        toSubscribe(observable, subscriber);
    }


    /**
     * 添加意见
     */
    public void add_opinion(Subscriber<BaseEntity> subscriber, int id, String accessory_source_id, String form_source_id) {
        Observable observable = mApi.add_opinion(addToken(), getMapRequestBody(getBodyMap(getStrings("id", "accessory_source_id", "form_source_id"),
                getStrings(String.valueOf(id), accessory_source_id, form_source_id))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取全部用户的信息
     */
    public void getAllUser(Subscriber<BaseEntity<AllUserBean>> subscriber) {
        Observable observable = mApi.getAllUser(addToken());
        toSubscribe(observable, subscriber);
    }


    /**
     * 加签
     */
    public void add_countersign(Subscriber<BaseEntity> subscriber, int id, String user_id) {
        Observable observable = mApi.add_countersign(addToken(), getMapRequestBody(getBodyMap(getStrings("id", "user_id"),
                getStrings(String.valueOf(id), user_id))));
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取固定分类
     */
    public void getType(Subscriber<BaseEntity<HomeTypeBean>> subscriber) {
        Observable observable = mApi.getType(addToken());
        toSubscribe(observable, subscriber);
    }

    /**
     * 文件监控
     */
    public void getMonitorList(Subscriber<BaseEntity<DocumentBean>> subscriber, ScreenBean bean) {
        String data = new Gson().toJson(bean);
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("param", data);

        Observable observable = mApi.getMonitorList(addToken(), getMapRequestBody(bodyMap));
        toSubscribe(observable, subscriber);
    }
}
