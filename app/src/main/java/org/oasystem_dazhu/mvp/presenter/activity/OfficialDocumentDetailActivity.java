package org.oasystem_dazhu.mvp.presenter.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsReaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.BuildConfig;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.http.HttpClient;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.SignOfficialAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AccessoryBean;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.IsEraserMode;
import org.oasystem_dazhu.mvp.model.bean.IsSigningBean;
import org.oasystem_dazhu.mvp.model.bean.UpFileBean;
import org.oasystem_dazhu.mvp.model.bean.UserInfo;
import org.oasystem_dazhu.mvp.view.OfficialDocumentDetailDelegate;
import org.oasystem_dazhu.mvp.view.SignView.PenColor;
import org.oasystem_dazhu.mvp.view.SignView.PenWidth;
import org.oasystem_dazhu.mvp.view.SignView.SignatureView;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.FileUtil;
import org.oasystem_dazhu.utils.LogUtil;
import org.oasystem_dazhu.utils.NetUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.RealPathFromUriUtils;
import org.oasystem_dazhu.utils.SharedPreferencesUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import static org.oasystem_dazhu.R.id.toolbar_right_tv;
import static org.oasystem_dazhu.constants.Constants.CHOOSE_PHOTO_FROM_GALLERY_CODE;
import static org.oasystem_dazhu.constants.Constants.SIGN_RESULT;

/**
 * Created by www on 2019/1/6.
 */

public class OfficialDocumentDetailActivity extends ActivityPresenter<OfficialDocumentDetailDelegate> implements TbsReaderView.ReaderCallback {
    private static String TAG = "wwwceshi";
    private final int WRITE_STORAGE_CODE = 1000;
    private final int REQUEST_CODE_WRITE_SETTINGS = 1002;
    private DocumentBean.DataBean.DispatchBean dispatchBean;
    private DocumentBean.DataBean dataBean;
    private RecyclerView recyclerView;
    private Boolean isShowing = true, isSigning = false, done = false, eraser = false, isPen = true;
    private LinearLayout sign_right_ll;
    private FrameLayout sign_full_fl;
    private ImageView save_ll, pen_ll, clear_ll, eraser_ll, yinzhang_ll;
    private List<ImageView> linearList = new ArrayList<>();
    private List<String> contentTv;
    private TbsReaderView sign_fileView;
    private SignatureView mSignatureView;
    private String[] permissionStr = {"内存卡", "读取手机"};
    private int id, tagPosition, opType = 0, itemId, color, tagColor;
    private ArrayList<Integer> accessoryList;
    private String type;
    private static String OFFICE_PATH;
    private AlertDialog dialog, addAccessoryDialog;
    private float width, tagWidth;
    private List<AllUserBean.DataBean> userBeanList;
    private List<AllUserBean.DataBean> daiqianUserBeanList;
    private int daiqianIndex = -1;
    public List<String> cacheFileList = new ArrayList<>();
    private MSubscribe<ResponseBody> subscribe;
    private EditText remarkEt;
    //加签用的
    private StringBuffer selectedUserId, selectedUserName;

    @Override
    public Class<OfficialDocumentDetailDelegate> getDelegateClass() {
        return OfficialDocumentDetailDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initAllData();
    }

    private void initAllData() {
        initPenWidthAndColor();
        done = getIntent().getExtras().getBoolean("done");
        dataBean = (DocumentBean.DataBean) getIntent().getExtras().getSerializable("DocumentDataBean");
        dispatchBean = dataBean.getDispatch();
        //记录Id
        itemId = dataBean.getId();
        accessoryList = new ArrayList<>();
        selectedUserId = new StringBuffer();
        selectedUserName = new StringBuffer();
        if (hasFormData()) {
            accessoryList.add(dispatchBean.getForm_source_id());
            cacheFileList.add("");
            LogUtil.d("itemId", "itemId:" + itemId);
            LogUtil.d("itemId", "formId:" + dispatchBean.getForm_source_id());
        }
        if (dispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < dispatchBean.getAccessory_list().size(); i++) {
                accessoryList.add(Integer.parseInt(dispatchBean.getAccessory_list().get(i).getSource_id()));
                LogUtil.d("itemId", "AccessoryId:" + dispatchBean.getAccessory_list().get(i).getSource_id());
                cacheFileList.add("");
            }
        }
        initView(done);
        viewDelegate.setOnClickListener(onClickListener, R.id.toolBar_img_right, R.id.pen_ll, R.id.eraser_ll, R.id.clear_ll);
        initNotDoneView();
        viewDelegate.initBottomRecyclerView(dataBean, done);
        checkLocationPermission();
        if (!done) {
            getAllUserBean();
        }
    }

    private void initPenWidthAndColor() {
        width = SharedPreferencesUtil.getWidth() == -1f ? PenWidth.DEFAULT.getWidth() : SharedPreferencesUtil.getWidth();
        color = SharedPreferencesUtil.getColor() == -1 ? Color.BLACK : SharedPreferencesUtil.getColor();
    }

    private boolean hasFormData() {
        return dispatchBean.getForm_source_id() != 0;
    }

    private void getAllUserBean() {
        userBeanList = UserManager.getInstance().getAllUserInfo();
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(OfficialDocumentDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, WRITE_STORAGE_CODE);
        } else {
            startShow();
        }
    }

    private void startShow() {
        if (hasFormData()) {
            id = dispatchBean.getForm_source_id();
            String[] str = dispatchBean.getForm_source().getName().split("\\.");
            type = str[str.length - 1];
        } else {
            id = Integer.parseInt(dispatchBean.getAccessory_list().get(0).getSource_id());
            String[] str = dispatchBean.getAccessory_list().get(0).getName().split("\\.");
            type = str[str.length - 1];
        }
        tagPosition = 0;
        showFile(id, type);
    }


    //添加附件的监听
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void addAccessory(AccessoryBean bean) {
        if (bean != null) {
            addAccessory(bean.getSource_id(), bean.getName());
        }
    }

    //是否是签字状态的监听
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void isSigning(IsSigningBean bean) {
        if (bean.isSigning) {
            setSigningStatus();
        } else {

        }
    }

    //是否是橡皮擦状态
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void isEraserMode(IsEraserMode bean) {
        if (bean.eraserMode) {
            eraser = true;
            setSelectedSates(viewDelegate.get(R.id.eraser_ll));
            mSignatureView.initEraserMode(Color.TRANSPARENT, width + 40f);
        } else {

        }
    }

    private void showFile(int id, String type) {
        File fileDir = new File(OFFICE_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (isFileExist(id, type)) {
            if (isPhoto(type)) {
                ImageView img = viewDelegate.get(R.id.sign_img);
                img.setVisibility(View.VISIBLE);
                Glide.with(this).load(getPath(id, type)).into(img);
                viewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
                viewDelegate.get(R.id.tbs_contentView).setVisibility(View.GONE);
            } else {
                viewDelegate.get(R.id.sign_img).setVisibility(View.GONE);
                if (!done && type.equals("pdf")) {
                    //用自己的view加载
                    viewDelegate.get(R.id.mSignatureView).setVisibility(View.VISIBLE);
                    viewDelegate.get(R.id.tbs_contentView).setVisibility(View.GONE);
                    if (!cacheFileList.get(tagPosition).isEmpty())
                        disPlayBySignView(new File(cacheFileList.get(tagPosition)));
                    else
                        disPlayBySignView(new File(getPath(id, type)));
                } else {
                    viewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
                    viewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
                    displayFile(new File(getPath(id, type)));
                }
            }
        } else {
            downLoadFile(id, type);
        }
    }

    private boolean isPhoto(String type) {
        return type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")
                || type.toLowerCase().equals("png") || type.toLowerCase().equals("gif");
    }

    private void disPlayBySignView(File file) {
        if (mSignatureView != null) {
            mSignatureView.stopFling();
            mSignatureView = null;
        }
        mSignatureView = viewDelegate.get(R.id.mSignatureView);
        mSignatureView.loadFile(file);
    }

    public void displayFile(File mFile) {

        if (mFile != null && !TextUtils.isEmpty(mFile.getAbsolutePath())) {
            //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
            String bsReaderTemp = Environment.getExternalStorageDirectory().toString() +
                    File.separator + "TbsReaderTemp";
            File bsReaderTempFile = new File(bsReaderTemp);

            if (!bsReaderTempFile.exists()) {
                LogUtil.d("准备创建/storage/emulated/0/TbsReaderTemp！！");
                boolean mkdir = bsReaderTempFile.mkdirs();
                if (!mkdir) {
                    LogUtil.e("创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
                }
            }

            //加载文件
            Bundle localBundle = new Bundle();
            localBundle.putString("filePath", mFile.getAbsolutePath());

            localBundle.putString("tempPath", bsReaderTemp);

            boolean bool = this.sign_fileView.preOpen(getFileType(mFile.toString()), false);
            if (bool) {
                this.sign_fileView.openFile(localBundle);
            }
        } else {
            LogUtil.e("文件路径无效！");
        }

    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            LogUtil.d(TAG, "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        return str;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> j = new ArrayList<>();
        if (requestCode == WRITE_STORAGE_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    j.add(permissionStr[i]);
                }
            }
            if (j.size() == 0) {
                startShow();
            } else {
                // 没有获取到权限，做特殊处理
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < j.size(); i++) {
                    sb.append(j.get(i));
                    if (i == 0 && j.size() == 2)
                        sb.append("和");
                }
                ToastUtil.l("请打开" + sb.toString() + "权限");
                finish();
            }

        }
    }

    private MSubscribe<ResponseBody> getSubscribe(final int id, final String type) {
        MSubscribe<ResponseBody> subscribe = new MSubscribe<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.l("下载文件出现错误\n" + e.toString());
            }

            @Override
            public void onNext(final ResponseBody bean) {
                super.onNext(bean);
                ProgressDialogUtil.instance().stopLoad();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d(TAG, "下载文件-->onResponse");
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            is = bean.byteStream();
//                            long total = bean.contentLength();

                            File tempFile = new File(getTempPath(id));
                            if (tempFile.exists()) {
                                tempFile.delete();
                            } else tempFile.createNewFile();

                            fos = new FileOutputStream(tempFile);
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                LogUtil.d("itemId", "下载中");
                            }
                            fos.flush();
                            tempFile.renameTo(new File(getPath(id, type)));
                            ProgressDialogUtil.instance().stopLoad();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showFile(id, type);
                                }
                            });

                        } catch (Exception e) {
                            LogUtil.d(TAG, "文件下载异常 = " + e.toString());
                        } finally {
                            ProgressDialogUtil.instance().stopLoad();
                            try {
                                if (is != null)
                                    is.close();
                                if (fos != null)
                                    fos.close();
                            } catch (IOException ignored) {
                            }

                        }
                    }
                }).start();
            }

            @Override
            public void onStart() {
                ProgressDialogUtil.instance().startLoad("下载中");
                if (!NetUtil.isConnect()) {
                    this.unsubscribe();
                    HttpClient.finishRequest();
                    ProgressDialogUtil.instance().stopLoad();

                }
            }
        };
        this.subscribe = subscribe;
        return subscribe;
    }


    private void downLoadFile(final int id, final String type) {
        PublicModel.getInstance().getSource(getSubscribe(id, type), id + "");
    }


    private Boolean isFileExist(int id, String type) {
        File file = new File(getPath(id, type));
        if (file.length() == 0) {
            file.delete();
            return false;
        }
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if (suffix.equals("temp")) {
            file.delete();
            return false;
        }
        return new File(getPath(id, type)).exists();
    }

    private String getPath(int id, String type) {
        return OFFICE_PATH + File.separator + id + "." + type;
    }

    private String getTempPath(int id) {
        return OFFICE_PATH + File.separator + id + ".temp";
    }


    private void initView(final Boolean done) {
        sign_right_ll = viewDelegate.get(R.id.sign_right_ll);
        sign_full_fl = viewDelegate.get(R.id.sign_full_fl);
        pen_ll = viewDelegate.get(R.id.pen_ll);
        clear_ll = viewDelegate.get(R.id.clear_ll);
        eraser_ll = viewDelegate.get(R.id.eraser_ll);
//        yinzhang_ll = viewDelegate.get(R.id.yinzhang_ll);
        linearList.add(pen_ll);
        linearList.add(clear_ll);
        linearList.add(eraser_ll);
//        linearList.add(yinzhang_ll);
        recyclerView = viewDelegate.get(R.id.sign_right_list);
        //需要代码创建view才可以显示多次
        createSignView();
        if (done) {
            viewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
            OFFICE_PATH = Constants.OFFICE_PREVIEW;
            viewDelegate.getToolBarRightImg().setVisibility(View.GONE);
            viewDelegate.get(R.id.sign_top_ll).setVisibility(View.GONE);
            viewDelegate.get(R.id.sign_bottom).setVisibility(View.GONE);
        } else {
            viewDelegate.setToolBarRightTv("签批");
            viewDelegate.getToolBarRightTv().setOnClickListener(onClickListener);
            UserInfo.SysAuthBean authBean = UserManager.getInstance().getUserInfo().getAuthBean();
            if (authBean != null) {
                viewDelegate.hideLeftBtn(authBean.getApp_auth());
            }
            OFFICE_PATH = Constants.SIGN_OFFICIAL;
        }
        viewDelegate.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!done) {
                    opType = 2;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "退出前请确认工作已保存", "确定", "不确定", dOnClickListener);
                } else finish();
            }
        });
    }

    private void createSignView() {
        sign_fileView = new TbsReaderView(this, this);
        FrameLayout tbs_contentView = viewDelegate.get(R.id.tbs_contentView);
        tbs_contentView.addView(sign_fileView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void initNotDoneView() {
        viewDelegate.setOnClickListener(onClickListener, R.id.sign_add_advise, R.id.sign_add_person, R.id.sign_agree, R.id.sign_refuse, R.id.sign_close, R.id.sign_daiqian);
        contentTv = new ArrayList<>();
        if (hasFormData()) {
            contentTv.add("审批单");
        }
        String text;
        if (dispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < dispatchBean.getAccessory_list().size(); i++) {
                if (BuildConfig.HOST.equals("http://112.35.0.188:9098/api/")) {
                    String[] contentName = dispatchBean.getAccessory_list().get(i).getName().split("_");
                    if (contentName.length > 1) {
                        text = contentName[1];
                    } else {
                        text = contentName[0];
                    }
                    if (text.length() <= 3) {
                        contentTv.add(text);
                    } else if (text.length() >= 4 && text.length() <= 6) {
                        contentTv.add(text.substring(0, 3) + "\n" + text.substring(3, text.length()));
                    } else {
                        contentTv.add(text.substring(0, 3) + "\n" + text.substring(3, 6));
                    }
                } else {
                    contentTv.add("附件" + (i + 1));
                }

            }
        }
        contentTv.add("办理\n意见");
        SignOfficialAdapter adapter = new SignOfficialAdapter(contentTv, this);
        viewDelegate.setRecycler(recyclerView, adapter, true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position < contentTv.size() - 1) {
                    if (tagPosition != position) {
                        tagPosition = position;
                        //切换界面要恢复一下设置
                        if (mSignatureView != null) {
                            mSignatureView.resetConfig();
                            mSignatureView.setNewPath(cacheFileList.get(position));
                        }

                        //回收掉原来的tbsView，否则不能显示
                        if (sign_fileView != null) {
                            FrameLayout frameLayout = viewDelegate.get(R.id.tbs_contentView);
                            frameLayout.removeAllViews();
                            sign_fileView.onStop();
                            sign_fileView = null;
                            createSignView();
                        }
                        if (hasFormData()) {
                            if (position == 0) {
                                id = dispatchBean.getForm_source_id();
                                String[] str = dispatchBean.getForm_source().getName().split("\\.");
                                type = str[str.length - 1];
                                showFile(dispatchBean.getForm_source_id(), str[str.length - 1]);

                            } else {
                                id = Integer.parseInt(dispatchBean.getAccessory_list().get(position - 1).getSource_id());
                                String[] str = dispatchBean.getAccessory_list().get(position - 1).getName().split("\\.");
                                type = str[str.length - 1];
                                showFile(Integer.parseInt(dispatchBean.getAccessory_list().get(position - 1).getSource_id()), str[str.length - 1]);
                            }
                        } else {
                            id = Integer.parseInt(dispatchBean.getAccessory_list().get(position).getSource_id());
                            String[] str = dispatchBean.getAccessory_list().get(position).getName().split("\\.");
                            type = str[str.length - 1];
                            showFile(Integer.parseInt(dispatchBean.getAccessory_list().get(position).getSource_id()), type);
                        }

                    }
                }
                //办理意见
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("listId", dispatchBean.getId());
                    bundle.putInt("itemId", itemId);
                    bundle.putBoolean("done", done);
                    startMyActivity(DealWithOptionFormActivity.class, bundle);
                }

            }
        });
    }

    private String getNowType() {
        String type;
        if (hasFormData()) {
            if (tagPosition == 0) {
                String[] str = dispatchBean.getForm_source().getName().split("\\.");
                type = str[str.length - 1];
            } else {
                String[] str = dispatchBean.getAccessory_list().get(tagPosition - 1).getName().split("\\.");
                type = str[str.length - 1];
            }
        } else {
            String[] str = dispatchBean.getAccessory_list().get(tagPosition).getName().split("\\.");
            type = str[str.length - 1];
        }
        return type;

    }


    /**
     * 签字有关的图标点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //判断是否是签字状态
                case toolbar_right_tv:
                    if (isSigning) {
                        opType = 3;
                        DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要取消吗？", "确定", "不确定", dOnClickListener);
                    } else {
                        if (getNowType().equals("pdf")) {
                            setSigningStatus();
                        } else {
                            ToastUtil.l("当前仅支持pdf文件");
                        }
                    }
                    break;
                //保存签字图片
                case R.id.toolBar_img_right:
                    setSelectedSates(view);
                    opType = 1;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要保存签批吗？", "确定", "取消", dOnClickListener);
                    break;
                //选择笔的样式
                case R.id.pen_ll:
                    if (isPen)
                        showSettingView();
                    setSelectedSates(view);
                    break;
                //清除
                case R.id.clear_ll:
                    opType = 4;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要清除签字吗？", "确定", "取消", dOnClickListener);
                    break;
                //橡皮擦
                case R.id.eraser_ll:
                    eraser = !eraser;
                    if (eraser) {
                        setSelectedSates(view);
                        mSignatureView.initEraserMode(Color.TRANSPARENT, width + 40f);
                    } else {
                        setSelectedSates(viewDelegate.get(R.id.pen_ll));
                        mSignatureView.setPenColor(color);
                        mSignatureView.setPenWidth(width);
                    }
                    break;
                //确认笔记颜色
                case R.id.confirm_color:
                    if (tagWidth != width && tagWidth != -1) {
                        width = tagWidth;
                        mSignatureView.setPenWidth(width);
                        SharedPreferencesUtil.savePenWidth(width);
                    }
                    if (tagColor != color && tagColor != -1) {
                        color = tagColor;
                        mSignatureView.setPenColor(color);
                        SharedPreferencesUtil.savePenColor(color);
                    }
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    break;

                case R.id.cancel:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    break;
                //添加意见
                case R.id.sign_add_advise:
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定把审批单的意见上传吗", "确定", "取消", getOnClick(4));
                    break;
                //添加人员
                case R.id.sign_add_person:
                    toAddPeople();
                    break;
                //同意
                case R.id.sign_agree:
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定通过吗?(通过后会流转到下一位审批人)", "确定", "取消", getOnClick(1));
                    break;
                //退回
                case R.id.sign_refuse:
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要退回该文件吗？", "确定", "取消", getOnClick(2));
                    break;
                //关闭整个流程
                case R.id.sign_close:
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要关闭整个流程吗？", "确定", "取消", getOnClick(3));
                    break;
                //选择代签
                case R.id.sign_daiqian:
                    showUserDialog();
                    break;

            }
        }
    };


    private void showUserDialog() {
        daiqianUserBeanList = UserManager.getInstance().getAllUserInfo();
        String[] peopleList = new String[daiqianUserBeanList.size()];
        for (int i = 0; i < daiqianUserBeanList.size(); i++) {
            peopleList[i] = daiqianUserBeanList.get(i).getName();
        }
        daiqianIndex = -1;
        DialogUtil.showSingleChoiceDialog(this, "请选择代签人员", "确定", "取消", peopleList,
                getmOnClickListener(false, -1));
    }

    public DialogInterface.OnClickListener getmOnClickListener(final Boolean sure, final int user_id) {
        DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i >= 0) {
                    daiqianIndex = i;
                }
                if (i == -1) {
                    if (!sure) {
                        showDaiqianConfirmDialog(daiqianIndex);
                    } else {
                        toDaiqian(user_id);
                    }
                    dialogInterface.dismiss();
                }

            }
        };
        return mOnClickListener;
    }

    private void toDaiqian(int user_id) {
        if (user_id < 0) {
            ToastUtil.s("非法Id");
        } else {
            PublicModel.getInstance().addDaiqian(new MSubscribe<BaseEntity>() {
                @Override
                public void onNext(BaseEntity bean) {
                    super.onNext(bean);
                    if (bean.getCode() == 0) {
                        ToastUtil.s("操作成功");
                        EventBus.getDefault().post("upLoadSuccess");
                        finish();
                    }
                }
            }, String.valueOf(itemId), String.valueOf(user_id));
        }
    }


    private void showDaiqianConfirmDialog(int index) {
        if (index >= 0) {
            DialogUtil.showDialog(this, "确定 \"" + daiqianUserBeanList.get(index).getName() + "\" 帮您代签吗？"
                    , "确定", "取消", getmOnClickListener(true, daiqianUserBeanList.get(index).getId()));
        }
    }


    private void setSigningStatus() {
        isSigning = true;
        isSigning();
        mSignatureView.setCanSigning(true);
//        mSignatureView.resetZoomWithAnimation();
    }

    private void isSigning() {
        viewDelegate.setToolBarRightTv("取消");
        sign_full_fl.setVisibility(View.VISIBLE);
        viewDelegate.getToolBarRightImg().setVisibility(View.VISIBLE);
        sign_right_ll.setVisibility(View.GONE);
        viewDelegate.get(R.id.sign_top_ll).setVisibility(View.GONE);
        viewDelegate.get(R.id.sign_bottom).setVisibility(View.GONE);
        setSelectedSates(viewDelegate.get(R.id.pen_ll));
        mSignatureView.setPenColor(color);
        mSignatureView.setPenWidth(width);
    }


    private void toAdvise(String form_source_id, String accessoryId) {
        PublicModel.getInstance().add_opinion(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                    EventBus.getDefault().post("upLoadSuccess");
                    finish();
                }
            }
        }, itemId, accessoryId, form_source_id);
    }

    private void toAddPeople() {
        if (userBeanList != null) {
            if (userBeanList.size() == 0) {
                ToastUtil.l("没有可添加的人员");
            } else {
                String[] peopleList = new String[userBeanList.size()];
                boolean[] booleanList = new boolean[userBeanList.size()];
                for (int i = 0; i < userBeanList.size(); i++) {
                    peopleList[i] = userBeanList.get(i).getName();
                    booleanList[i] = false;
                    userBeanList.get(i).setSelected(false);
                }
                selectedUserId.setLength(0);
                selectedUserName.setLength(0);
                DialogUtil.showChoiceDialog(this, "请选择加签人员(请注意顺序)", "确定", "取消", peopleList, booleanList, getOnClick(5), multiChoiceClickListener);
            }
        }
    }

    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            userBeanList.get(i).setSelected(b);
            if (b) {
                selectedUserId.append(userBeanList.get(i).getId()).append(",");
                selectedUserName.append(userBeanList.get(i).getName()).append(",");
            } else {
                String content = selectedUserId.toString();
                if (content.contains(userBeanList.get(i).getId() + ",")) {
                    content = content.replace(userBeanList.get(i).getId() + ",", "");
                    selectedUserId.setLength(0);
                    selectedUserId.append(content);
                }
                String contentName = selectedUserName.toString();
                if (contentName.contains(userBeanList.get(i).getName() + ",")) {
                    contentName = contentName.replace(userBeanList.get(i).getName() + ",", "");
                    selectedUserName.setLength(0);
                    selectedUserName.append(contentName);
                }

            }

        }
    };

    private void addAccessory() {
        addAccessoryDialog = DialogUtil.createAlertDialog(OfficialDocumentDetailActivity.this, showDialogAndItemClickListener());
        addAccessoryDialog.show();
    }


    @NonNull
    private View showDialogAndItemClickListener() {
        View view = DialogUtil.getDialogView(OfficialDocumentDetailActivity.this);
        CheckedTextView fromDir = view.findViewById(R.id.fromDir);
        CheckedTextView fromGallery = view.findViewById(R.id.fromGallery);
        CheckedTextView cancel = view.findViewById(R.id.cancel);
        fromDir.setOnClickListener(dialogOnClickListener);
        fromGallery.setOnClickListener(dialogOnClickListener);
        cancel.setOnClickListener(dialogOnClickListener);
        return view;
    }

    private View.OnClickListener dialogOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fromDir:
                    toUserDir();
                    addAccessoryDialog.dismiss();
                    break;
                case R.id.fromGallery:
                    chooseFromGallery();
                    break;
                case R.id.cancel:
                    addAccessoryDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private void toUserDir() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromHome", false);
        startMyActivity(MyOfficeActivity.class, bundle);
    }

    /**
     * 从相册选择图片
     * WRITE_EXTERNAL_STORAGE0-
     */
    private void chooseFromGallery() {
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //设置选择类型为图片类型
        //打开图片选择
        startActivityForResult(intent, CHOOSE_PHOTO_FROM_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO_FROM_GALLERY_CODE:
                if (data != null) {
                    //用户从图库选择图片后会返回所选图片的Uri
                    String truePath = RealPathFromUriUtils.getRealPathFromUri(MyApplication.getContext(), data.getData());
//                    upLoadSource(truePath);
                    addAccessoryDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private void upLoadSource(String truePath) {
        PublicModel.getInstance().upload_file(new MSubscribe<BaseEntity<UpFileBean>>() {
            @Override
            public void onNext(BaseEntity<UpFileBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    addAccessory(bean.getData().getId(), bean.getData().getName());
                }
            }
        }, new File(truePath));
    }

    private void addAccessory(int id, String name) {
        PublicModel.getInstance().addAccessory(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                }
            }
        }, itemId, name, id);
    }


    private void toClose(String form_source_id, String accessoryId) {
        PublicModel.getInstance().closeAll(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                    EventBus.getDefault().post("upLoadSuccess");
                    finish();
                }
            }
        }, itemId, accessoryId, form_source_id);
    }

    private DialogInterface.OnClickListener getOnClick(final int mType) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -1) {
                    nextOperation(mType);
                }
                dialogInterface.dismiss();
            }
        };
        return onClickListener;
    }

    private void nextOperation(int status) {
        if (status != 5) {
            if (status == 1) {
                toExamine(status, getFormSourceId(), getAccessorySourceId(), "");
            }
            if (status == 2) {
                showReasonDialog(status, getFormSourceId(), getAccessorySourceId());
            }
            if (status == 3) {
                toClose(getFormSourceId(), getAccessorySourceId());
            }
            if (status == 4) {
                toAdvise(getFormSourceId(), getAccessorySourceId());
            }
        } else
            add_countersign();
    }


    private void showReasonDialog(final int status, final String form_source_id, final String accessoryId) {
        remarkEt = new EditText(this);
        new AlertDialog.Builder(this).setTitle("退回原因(必填)")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(remarkEt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(remarkEt.getText().toString())) {
                            ToastUtil.l("必须填写原因");
                        } else
                            toExamine(status, form_source_id, accessoryId, remarkEt.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void add_countersign() {
        if (selectedUserId.length() == 0) {
            ToastUtil.s("未选择加签人员");
        } else {
            PublicModel.getInstance().add_countersign(new MSubscribe<BaseEntity>() {
                @Override
                public void onNext(BaseEntity bean) {
                    super.onNext(bean);
                    if (bean.getCode() == 0) {
                        ToastUtil.l("操作成功");
                        EventBus.getDefault().post("upLoadSuccess");
                        finish();
                    }
                }
            }, itemId, selectedUserId.substring(0, selectedUserId.length() - 1), getFormSourceId(), getAccessorySourceId());
        }
    }

    private String getFormSourceId() {
        if (hasFormData()) {
            return String.valueOf(accessoryList.get(0));
        } else return "0";
    }

    private String getAccessorySourceId() {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < accessoryList.size(); i++) {
            if (i == accessoryList.size() - 1) {
                sb.append(accessoryList.get(i));
            } else {
                sb.append(accessoryList.get(i)).append("#");
            }
        }
        return sb.toString();
    }


    private void toExamine(int status, String form_source_id, String accessoryId, String reason) {
        PublicModel.getInstance().examine(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                    EventBus.getDefault().post("upLoadSuccess");
                    finish();
                }
            }
        }, String.valueOf(itemId), String.valueOf(status), accessoryId, form_source_id, reason);
    }

    private void showSettingView() {
        Spinner width_spinner, color_spinner;
        Button confirm_color, cancel;
        View view = LayoutInflater.from(this).inflate(R.layout.pensetting_view, null);
        width_spinner = view.findViewById(R.id.width_spinner);
        width_spinner.setOnItemSelectedListener(itemClickListener);
        color_spinner = view.findViewById(R.id.color_spinner);
        color_spinner.setOnItemSelectedListener(itemClickListener);
        width_spinner.setSelection(PenWidth.getKeyByValue(width));
        color_spinner.setSelection(PenColor.getKeyByColor(color));
        confirm_color = view.findViewById(R.id.confirm_color);
        cancel = view.findViewById(R.id.cancel);
        confirm_color.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = DialogUtil.createAlertDialog(this, view);
        dialog.show();
    }

    private AdapterView.OnItemSelectedListener itemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.width_spinner) {
                tagWidth = PenWidth.getWidthByKey(position);
            }

            if (parent.getId() == R.id.color_spinner) {
                tagColor = PenColor.getColorByKey(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    DialogInterface.OnClickListener dOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            if (i == -1) {
                //要做对应的操作
                if (opType == 1) {
                    setSelectedSates(viewDelegate.get(R.id.pen_ll));
                    saveImg(true);
                }
                if (opType == 2) {
                    finish();
                }
                if (opType == 3) {
                    setSelectedSates(viewDelegate.get(toolbar_right_tv));
                    if (cacheFileList.get(tagPosition).isEmpty())
                        clearCanvas(null);
                    else
                        clearCanvas(cacheFileList.get(tagPosition));
                    noSigning();
                }
                if (opType == 4) {
                    clearCanvas(null);
                    noSigning();
                }
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!done) {
                opType = 2;
                DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "退出前请确认工作已保存", "确定", "不确定", dOnClickListener);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void noSigning() {
        isSigning = false;
        eraser = false;
        initPenWidthAndColor();
        viewDelegate.setToolBarRightTv("签批");
        viewDelegate.get(R.id.sign_top_ll).setVisibility(View.VISIBLE);
        viewDelegate.get(R.id.sign_bottom).setVisibility(View.VISIBLE);
        sign_full_fl.setVisibility(View.GONE);
        viewDelegate.getToolBarRightImg().setVisibility(View.GONE);
        sign_right_ll.setVisibility(View.VISIBLE);
        for (int j = 0; j < linearList.size(); j++) {
            linearList.get(j).setSelected(false);
        }
//        mSignatureView.resetZoomWithAnimation();
    }

    private void clearCanvas(String path) {
        if (path != null) {
            //签字完成后保存一下文件路径
            cacheFileList.set(tagPosition, path);
            mSignatureView.clearCanvas(new File(path));
        } else {
            //清除字迹的时候要清除掉缓存的文件路径
            if (cacheFileList != null && cacheFileList.size() != 0) {
                cacheFileList.set(tagPosition, "");
            }
            mSignatureView.setNewPath("");
            mSignatureView.clearCanvas(new File(getPath(id, type)));
        }
    }

    private void saveImg(final Boolean needUpLoad) {
        if (mSignatureView != null) {
            LogUtil.d("pianyi", "签字文件的路径" + (TextUtils.isEmpty(mSignatureView.getNewPath()) ? getPath(id, getNowType()) : mSignatureView.getNewPath()));
            mSignatureView.addSignature2Pdf(TextUtils.isEmpty(mSignatureView.getNewPath()) ? getPath(id, getNowType()) : mSignatureView.getNewPath()
                    , false, new SignatureView.DataFinishListener() {
                        @Override
                        public void onFinished(String path) {
                            LogUtil.d("pianyi", "签完字后的路径" + path);
                            if (needUpLoad)
                                upLoadFile(path);
                        }

                        @Override
                        public void nothingChange() {

                        }

                        @Override
                        public void onFailed() {
                            ToastUtil.l("签字未保存成功");
                        }
                    });
        }
    }


    private void upLoadFile(final String path) {
        File file = new File(path);
        ProgressDialogUtil.instance().startLoad("上传中");
        PublicModel.getInstance().upload_file(new MSubscribe<BaseEntity<UpFileBean>>() {
            @Override
            public void onNext(BaseEntity<UpFileBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    accessoryList.set(tagPosition, bean.getData().getId());
                    ToastUtil.l("签字已保存");
                    mSignatureView.setCanSigning(false);
                    ProgressDialogUtil.instance().stopLoad();
                    clearCanvas(path);
                    noSigning();
                }
            }
        }, file);
    }


    private void setSelectedSates(View view) {
        for (int i = 0; i < linearList.size(); i++) {
            if (linearList.get(i).getId() != view.getId()) {
                linearList.get(i).setSelected(false);
            } else linearList.get(i).setSelected(true);
        }
        if (view.getId() != R.id.eraser_ll) {
            if (eraser) {
                eraser = !eraser;
                mSignatureView.setPenColor(color);
                mSignatureView.setPenWidth(width);
            }
        }
        if (view.getId() != R.id.pen_ll) {
            isPen = false;
        }
        if (view.getId() == R.id.pen_ll) {
            isPen = true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addAccessoryDialog != null && addAccessoryDialog.isShowing()) {
            addAccessoryDialog.dismiss();
        }
        if (sign_fileView != null) {
            sign_fileView.onStop();
            sign_fileView = null;
        }
        if (mSignatureView != null) {
            mSignatureView.stopFling();
        }
        File file = new File(SIGN_RESULT);
        FileUtil.deleteFile(file);
        EventBus.getDefault().unregister(this);
        if (cacheFileList != null) {
            cacheFileList.clear();
        }
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
