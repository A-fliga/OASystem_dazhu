package org.oasystem_dazhu.mvp.presenter.activity;

import android.Manifest;
import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
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
import org.oasystem_dazhu.utils.AppUtil;
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

import me.jessyan.autosize.AutoSize;
import okhttp3.ResponseBody;

import static org.oasystem_dazhu.constants.Constants.CHOOSE_PHOTO_FROM_GALLERY_CODE;
import static org.oasystem_dazhu.constants.Constants.SIGN_RESULT;

/**
 * Created by www on 2019/1/6.
 * 处理签字的页面，是核心类
 */

public class OfficialDocumentDetailActivity extends ActivityPresenter<OfficialDocumentDetailDelegate> implements TbsReaderView.ReaderCallback {
    private static String TAG = "OfficialDocumentDetailActivity";
    private final int WRITE_STORAGE_CODE = 1000;
    private static String sOfficePath;
    private DocumentBean.DataBean.DispatchBean mDispatchBean;
    private RecyclerView mRecyclerView;
    private LinearLayout mSignRightLL;
    private FrameLayout mSignFullFL;
    private TbsReaderView mSignFileView;
    private SignatureView mSignatureView;
    //加签用的
    private StringBuilder mSelectedUserId;
    private StringBuilder mSelectedUserName;
    private AlertDialog mDialog;
    private AlertDialog mAddAccessoryDialog;
    private MSubscribe<ResponseBody> mSubscribe;
    private EditText mRemarkEt;
    private List<LinearLayout> mLinearList = new ArrayList<>();
    private List<AllUserBean.DataBean> mUserBeanList;
    //代签列表
    private List<AllUserBean.DataBean> mEndorsementUserBeanList;
    private List<String> mCacheFileList = new ArrayList<>();
    private ArrayList<Integer> mAccessoryList;
    private List<String> mContentTv;
    private String[] mPermissionStr = {"内存卡", "读取手机"};
    private String mType;
    private float mWidth;
    private float mTagWidth;
    private int mId;
    private int mTagPosition;
    private int mOpType = 0;
    private int mItemId;
    private int mColor;
    private int mTagColor;
    private int mEndorsementIndex = -1;
    private boolean mIsShowing = true;
    private boolean mIsSigning = false;
    private boolean mDone = false;
    private boolean mEraser = false;
    private boolean mIsPen = true;
    private boolean mHideWeight = false;


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
        mDone = getIntent().getExtras().getBoolean("done");
        DocumentBean.DataBean mDataBean = (DocumentBean.DataBean) getIntent().getExtras().getSerializable("DocumentDataBean");
        mDispatchBean = mDataBean.getDispatch();
        //记录Id
        mItemId = mDataBean.getId();
        mAccessoryList = new ArrayList<>();
        mSelectedUserId = new StringBuilder();
        mSelectedUserName = new StringBuilder();
        if (hasFormData()) {
            mAccessoryList.add(mDispatchBean.getForm_source_id());
            mCacheFileList.add("");
            LogUtil.d("itemId", "itemId:" + mItemId);
            LogUtil.d("itemId", "formId:" + mDispatchBean.getForm_source_id());
        }
        if (mDispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < mDispatchBean.getAccessory_list().size(); i++) {
                mAccessoryList.add(Integer.parseInt(mDispatchBean.getAccessory_list().get(i).getSource_id()));
                LogUtil.d("itemId", "AccessoryId:" + mDispatchBean.getAccessory_list().get(i).getSource_id());
                mCacheFileList.add("");
            }
        }
        initView(mDone);
        mViewDelegate.setOnClickListener(onClickListener, R.id.save_ll, R.id.pen_ll, R.id.clear_ll, R.id.eraser_ll, R.id.mToolbar_rl);
        initNotDoneView();
        mViewDelegate.initBottomRecyclerView(mDataBean, mDone);
        checkLocationPermission();
        if (!mDone) {
            getAllUserBean();
        }
    }

    private void initPenWidthAndColor() {
        mWidth = SharedPreferencesUtil.getWidth() == -1f ? PenWidth.DEFAULT.getWidth() : SharedPreferencesUtil.getWidth();
        mColor = SharedPreferencesUtil.getColor() == -1 ? Color.BLACK : SharedPreferencesUtil.getColor();
    }

    private boolean hasFormData() {
        return mDispatchBean.getForm_source_id() != 0;
    }

    private void getAllUserBean() {
        mUserBeanList = UserManager.getInstance().getAllUserInfo();
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
            mId = mDispatchBean.getForm_source_id();
            String[] str = mDispatchBean.getForm_source().getName().split("\\.");
            mType = str[str.length - 1];
        } else {
            mId = Integer.parseInt(mDispatchBean.getAccessory_list().get(0).getSource_id());
            String[] str = mDispatchBean.getAccessory_list().get(0).getName().split("\\.");
            mType = str[str.length - 1];
        }
        mTagPosition = 0;
        showFile(mId, mType);
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
            mEraser = true;
            setSelectedSates(mViewDelegate.get(R.id.eraser_ll));
            mSignatureView.initEraserMode(Color.TRANSPARENT, mWidth + 40f);
        } else {

        }
    }

    private void showFile(int id, String type) {
        File fileDir = new File(sOfficePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (isFileExist(id, type)) {
            if (isPhoto(type)) {
                ImageView img = mViewDelegate.get(R.id.sign_img);
                img.setVisibility(View.VISIBLE);
                Glide.with(this).load(getPath(id, type)).into(img);
                mViewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
                mViewDelegate.get(R.id.tbs_contentView).setVisibility(View.GONE);
            } else {
                mViewDelegate.get(R.id.sign_img).setVisibility(View.GONE);
                if (!mDone && type.toLowerCase().equals("pdf")) {
                    //用自己的view加载
                    mViewDelegate.get(R.id.mSignatureView).setVisibility(View.VISIBLE);
                    mViewDelegate.get(R.id.tbs_contentView).setVisibility(View.GONE);
                    if (!mCacheFileList.get(mTagPosition).isEmpty())
                        disPlayBySignView(new File(mCacheFileList.get(mTagPosition)));
                    else {
                        disPlayBySignView(new File(getPath(id, type)));
                    }
                } else {
                    mViewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
                    mViewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
                    displayFile(new File(getPath(id, type)));
                }
            }
        } else {
            downLoadFile(id, type.toLowerCase());
        }
    }

    private boolean isPhoto(String type) {
        return type.toLowerCase().equals("jpg") || type.toLowerCase().equals("jpeg")
                || type.toLowerCase().equals("png") || type.toLowerCase().equals("gif");
    }

    private void disPlayBySignView(File file) {
        mSignatureView = mViewDelegate.get(R.id.mSignatureView);
        mSignatureView.setDocumentId(String.valueOf(mId));
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

            boolean bool = this.mSignFileView.preOpen(getFileType(mFile.toString()), false);
            if (bool) {
                this.mSignFileView.openFile(localBundle);
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
                    j.add(mPermissionStr[i]);
                }
            }
            if (j.size() == 0) {
                startShow();
            } else {
                // 没有获取到权限，做特殊处理
                StringBuilder sb = new StringBuilder();
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
        this.mSubscribe = subscribe;
        return subscribe;
    }


    private void downLoadFile(final int id, final String type) {
        PublicModel.getInstance().getSource(getSubscribe(id, type), id + "");
    }


    @Override
    protected void onResume() {
        super.onResume();
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
        return sOfficePath + File.separator + id + "." + type;
    }

    private String getTempPath(int id) {
        return sOfficePath + File.separator + id + ".temp";
    }


    private void initView(final Boolean done) {
        mSignRightLL = mViewDelegate.get(R.id.sign_right_ll);
        mSignFullFL = mViewDelegate.get(R.id.sign_full_fl);
        LinearLayout save_ll = mViewDelegate.get(R.id.save_ll);
        LinearLayout pen_ll = mViewDelegate.get(R.id.pen_ll);
        LinearLayout clear_ll = mViewDelegate.get(R.id.clear_ll);
        LinearLayout eraser_ll = mViewDelegate.get(R.id.eraser_ll);
//        yinzhang_ll = viewDelegate.get(R.id.yinzhang_ll);
        mLinearList.add(save_ll);
        mLinearList.add(pen_ll);
        mLinearList.add(clear_ll);
        mLinearList.add(eraser_ll);
//        linearList.add(yinzhang_ll);
        mRecyclerView = mViewDelegate.get(R.id.sign_right_list);
        //需要代码创建view才可以显示多次
        createSignView();
        if (done) {
            mViewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
            sOfficePath = Constants.OFFICE_PREVIEW;
            mViewDelegate.getToolBarRightImg().setVisibility(View.GONE);
            mViewDelegate.get(R.id.sign_left_ll).setVisibility(View.GONE);
            mViewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
        } else {
            mViewDelegate.setToolBarRightImg(R.mipmap.sign);
            mViewDelegate.getToolBarRightImg().setOnClickListener(onClickListener);
            UserInfo.SysAuthBean authBean = UserManager.getInstance().getUserInfo().getAuthBean();
            if (authBean != null) {
                mViewDelegate.hideLeftBtn(authBean.getApp_auth());
            }
            sOfficePath = Constants.SIGN_OFFICIAL;
        }
        mViewDelegate.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!done) {
                    mOpType = 2;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "退出前请确认工作已保存", "确定", "不确定", dOnClickListener);
                } else finish();
            }
        });
    }

    private void createSignView() {
        mSignFileView = new TbsReaderView(this, this);
        FrameLayout tbs_contentView = mViewDelegate.get(R.id.tbs_contentView);
        tbs_contentView.addView(mSignFileView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void initNotDoneView() {
        mViewDelegate.setOnClickListener(onClickListener, R.id.sign_add_advise, R.id.sign_add_person, R.id.sign_agree, R.id.sign_refuse, R.id.sign_close, R.id.sign_daiqian);
        mContentTv = new ArrayList<>();
        if (hasFormData()) {
            mContentTv.add("审批单");
        }
        String text;
        if (mDispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < mDispatchBean.getAccessory_list().size(); i++) {
                if ("xuanhan".equals(Constants.getOrg())) {
                    String[] contentName = mDispatchBean.getAccessory_list().get(i).getName().split("_");
                    if (contentName.length > 1) {
                        text = contentName[1];
                    } else {
                        text = contentName[0];
                    }
                    if (text.length() <= 3) {
                        mContentTv.add(text);
                    } else if (text.length() >= 4 && text.length() <= 6) {
                        mContentTv.add(text.substring(0, 3) + "\n" + text.substring(3, text.length()));
                    } else {
                        mContentTv.add(text.substring(0, 3) + "\n" + text.substring(3, 6));
                    }
                } else {
                    mContentTv.add("附件" + (i + 1));
                }

            }
        }
        mContentTv.add("办理\n情况");
        SignOfficialAdapter adapter = new SignOfficialAdapter(mContentTv, this);
        mViewDelegate.setRecycler(mRecyclerView, adapter, true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position < mContentTv.size() - 1) {
                    if (mTagPosition != position) {
                        mTagPosition = position;
                        //切换界面要恢复一下设置
                        if (mSignatureView != null) {
                            mSignatureView.resetConfig();
                            mSignatureView.setNewPath(mCacheFileList.get(position));
                        }

                        //回收掉原来的tbsView，否则不能显示
                        if (mSignFileView != null) {
                            FrameLayout frameLayout = mViewDelegate.get(R.id.tbs_contentView);
                            frameLayout.removeAllViews();
                            mSignFileView.onStop();
                            mSignFileView = null;
                            createSignView();
                        }
                        if (hasFormData()) {
                            if (position == 0) {
                                mId = mDispatchBean.getForm_source_id();
                                String[] str = mDispatchBean.getForm_source().getName().split("\\.");
                                mType = str[str.length - 1];
                                showFile(mDispatchBean.getForm_source_id(), str[str.length - 1]);

                            } else {
                                mId = Integer.parseInt(mDispatchBean.getAccessory_list().get(position - 1).getSource_id());
                                String[] str = mDispatchBean.getAccessory_list().get(position - 1).getName().split("\\.");
                                mType = str[str.length - 1];
                                showFile(Integer.parseInt(mDispatchBean.getAccessory_list().get(position - 1).getSource_id()), str[str.length - 1]);
                            }
                        } else {
                            mId = Integer.parseInt(mDispatchBean.getAccessory_list().get(position).getSource_id());
                            String[] str = mDispatchBean.getAccessory_list().get(position).getName().split("\\.");
                            mType = str[str.length - 1];
                            showFile(Integer.parseInt(mDispatchBean.getAccessory_list().get(position).getSource_id()), mType);
                        }

                    }
                }
                //办理意见
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("listId", mDispatchBean.getId());
                    bundle.putInt("itemId", mItemId);
                    bundle.putBoolean("done", mDone);
                    startMyActivity(DealWithOptionFormActivity.class, bundle);
                }

            }
        });
    }

    private String getNowType() {
        String type;
        if (hasFormData()) {
            if (mTagPosition == 0) {
                String[] str = mDispatchBean.getForm_source().getName().split("\\.");
                type = str[str.length - 1];
            } else {
                String[] str = mDispatchBean.getAccessory_list().get(mTagPosition - 1).getName().split("\\.");
                type = str[str.length - 1];
            }
        } else {
            String[] str = mDispatchBean.getAccessory_list().get(mTagPosition).getName().split("\\.");
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
                case R.id.toolBar_img_right:
                    if (mIsSigning) {
                        mOpType = 3;
                        DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要取消吗？", "确定", "不确定", dOnClickListener);
                    } else {
                        if (getNowType().toLowerCase().equals("pdf")) {
                            setSigningStatus();
                        } else {
                            ToastUtil.l("当前仅支持pdf文件");
                        }
                    }
                    break;
                //保存签字图片
                case R.id.save_ll:
                    setSelectedSates(view);
                    mOpType = 1;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要保存签批吗？", "确定", "取消", dOnClickListener);
                    break;
                //选择笔的样式
                case R.id.pen_ll:
                    if (mIsPen) {
                        showSettingView();
                    }
                    setSelectedSates(view);
                    break;
                //清除
                case R.id.clear_ll:
                    mOpType = 4;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要清除签字吗？", "确定", "取消", dOnClickListener);
                    break;
                //橡皮擦
                case R.id.eraser_ll:
                    mEraser = !mEraser;
                    if (mEraser) {
                        setSelectedSates(view);
                        mSignatureView.initEraserMode(Color.TRANSPARENT, mWidth + 40f);
                    } else {
                        setSelectedSates(mViewDelegate.get(R.id.pen_ll));
                        mSignatureView.setPenColor(mColor);
                        mSignatureView.setPenWidth(mWidth);
                    }
                    break;
                //确认笔记颜色
                case R.id.confirm_color:
                    if (mTagWidth != mWidth && mTagWidth != -1) {
                        mWidth = mTagWidth;
                        mSignatureView.setPenWidth(mWidth);
                        SharedPreferencesUtil.savePenWidth(mWidth);
                    }
                    if (mTagColor != mColor && mTagColor != -1) {
                        mColor = mTagColor;
                        mSignatureView.setPenColor(mColor);
                        SharedPreferencesUtil.savePenColor(mColor);
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    break;

                case R.id.cancel:
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
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
                //双击可以隐藏侧边栏
                case R.id.mToolbar_rl:
                    if (AppUtil.isFastDoubleClick(500) && !mDone && !mIsSigning) {
                        if (!mHideWeight) {
                            hideWeight(true);
                        } else {
                            hideWeight(false);
                        }
                    }
                    break;

            }
        }
    };


    private void showUserDialog() {
        mEndorsementUserBeanList = UserManager.getInstance().getAllUserInfo();
        String[] peopleList = new String[mEndorsementUserBeanList.size()];
        for (int i = 0; i < mEndorsementUserBeanList.size(); i++) {
            peopleList[i] = mEndorsementUserBeanList.get(i).getName();
        }
        mEndorsementIndex = -1;
        DialogUtil.showSingleChoiceDialog(this, "请选择代签人员", "确定", "取消", peopleList,
                getmOnClickListener(false, -1));
    }

    public DialogInterface.OnClickListener getmOnClickListener(final Boolean sure, final int user_id) {
        DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i >= 0) {
                    mEndorsementIndex = i;
                }
                if (i == -1) {
                    if (!sure) {
                        showDaiqianConfirmDialog(mEndorsementIndex);
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
                        EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
                        finish();
                    }
                }
            }, String.valueOf(mItemId), String.valueOf(user_id));
        }
    }


    private void showDaiqianConfirmDialog(int index) {
        if (index >= 0) {
            DialogUtil.showDialog(this, "确定 \"" + mEndorsementUserBeanList.get(index).getName() + "\" 帮您代签吗？"
                    , "确定", "取消", getmOnClickListener(true, mEndorsementUserBeanList.get(index).getId()));
        }
    }


    private void setSigningStatus() {
        mIsSigning = true;
        isSigning();
        mSignatureView.setCanSigning(true);
//        mSignatureView.resetZoomWithAnimation();
    }

    private void isSigning() {
        mViewDelegate.setToolBarRightImg(R.mipmap.sign_cancel);
        mSignFullFL.setVisibility(View.VISIBLE);
        mSignRightLL.setVisibility(View.GONE);
        hideWeight(true);
        setSelectedSates(mViewDelegate.get(R.id.pen_ll));
        mSignatureView.setPenColor(mColor);
        mSignatureView.setPenWidth(mWidth);
    }

    private void hideWeight(boolean hide) {
        mHideWeight = hide;
        if (hide) {
            mViewDelegate.get(R.id.sign_left_ll).setVisibility(View.GONE);
            mViewDelegate.get(R.id.sign_top_ll).setVisibility(View.VISIBLE);
            mViewDelegate.get(R.id.sign_bottom).setVisibility(View.GONE);
        } else {
            mViewDelegate.get(R.id.sign_left_ll).setVisibility(View.VISIBLE);
            mViewDelegate.get(R.id.sign_top_ll).setVisibility(View.GONE);
            mViewDelegate.get(R.id.sign_bottom).setVisibility(View.VISIBLE);
        }
    }


    private void toAdvise(String form_source_id, String accessoryId) {
        PublicModel.getInstance().add_opinion(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                    EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
                    finish();
                }
            }
        }, mItemId, accessoryId, form_source_id);
    }

    private void toAddPeople() {
        if (mUserBeanList != null) {
            if (mUserBeanList.size() == 0) {
                ToastUtil.l("没有可添加的人员");
            } else {
                String[] peopleList = new String[mUserBeanList.size()];
                boolean[] booleanList = new boolean[mUserBeanList.size()];
                for (int i = 0; i < mUserBeanList.size(); i++) {
                    peopleList[i] = mUserBeanList.get(i).getName();
                    booleanList[i] = false;
                    mUserBeanList.get(i).setSelected(false);
                }
                mSelectedUserId.setLength(0);
                mSelectedUserName.setLength(0);
                DialogUtil.showChoiceDialog(this, "请选择加签人员(请注意顺序)", "确定", "取消", peopleList, booleanList, getOnClick(5), multiChoiceClickListener);
            }
        }
    }

    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            mUserBeanList.get(i).setSelected(b);
            if (b) {
                mSelectedUserId.append(mUserBeanList.get(i).getId()).append(",");
                mSelectedUserName.append(mUserBeanList.get(i).getName()).append(",");
            } else {
                String content = mSelectedUserId.toString();
                if (content.contains(mUserBeanList.get(i).getId() + ",")) {
                    content = content.replace(mUserBeanList.get(i).getId() + ",", "");
                    mSelectedUserId.setLength(0);
                    mSelectedUserId.append(content);
                }
                String contentName = mSelectedUserName.toString();
                if (contentName.contains(mUserBeanList.get(i).getName() + ",")) {
                    contentName = contentName.replace(mUserBeanList.get(i).getName() + ",", "");
                    mSelectedUserName.setLength(0);
                    mSelectedUserName.append(contentName);
                }

            }

        }
    };

    private void addAccessory() {
        mAddAccessoryDialog = DialogUtil.createAlertDialog(OfficialDocumentDetailActivity.this, showDialogAndItemClickListener());
        AutoSize.cancelAdapt(this);
        mAddAccessoryDialog.show();
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
                    mAddAccessoryDialog.dismiss();
                    break;
                case R.id.fromGallery:
                    chooseFromGallery();
                    break;
                case R.id.cancel:
                    mAddAccessoryDialog.dismiss();
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
                    mAddAccessoryDialog.dismiss();
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
        }, mItemId, name, id);
    }


    private void toClose(String form_source_id, String accessoryId) {
        PublicModel.getInstance().closeAll(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.l("操作成功");
                    EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
                    finish();
                }
            }
        }, mItemId, accessoryId, form_source_id);
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
        mRemarkEt = new EditText(this);
        new AlertDialog.Builder(this).setTitle("退回原因(必填)")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(mRemarkEt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(mRemarkEt.getText().toString())) {
                            ToastUtil.l("必须填写原因");
                        } else
                            toExamine(status, form_source_id, accessoryId, mRemarkEt.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void add_countersign() {
        if (mSelectedUserId.length() == 0) {
            ToastUtil.s("未选择加签人员");
        } else {
            PublicModel.getInstance().add_countersign(new MSubscribe<BaseEntity>() {
                @Override
                public void onNext(BaseEntity bean) {
                    super.onNext(bean);
                    if (bean.getCode() == 0) {
                        ToastUtil.l("操作成功");
                        EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
                        finish();
                    }
                }
            }, mItemId, mSelectedUserId.substring(0, mSelectedUserId.length() - 1), getFormSourceId(), getAccessorySourceId());
        }
    }

    private String getFormSourceId() {
        if (hasFormData()) {
            return String.valueOf(mAccessoryList.get(0));
        } else return "0";
    }

    private String getAccessorySourceId() {
        StringBuilder sb = new StringBuilder();
        if (hasFormData()) {
            for (int i = 1; i < mAccessoryList.size(); i++) {
                if (i == mAccessoryList.size() - 1) {
                    sb.append(mAccessoryList.get(i));
                } else {
                    sb.append(mAccessoryList.get(i)).append("#");
                }
            }
        } else {
            for (int i = 0; i < mAccessoryList.size(); i++) {
                if (i == mAccessoryList.size() - 1) {
                    sb.append(mAccessoryList.get(i));
                } else {
                    sb.append(mAccessoryList.get(i)).append("#");
                }
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
                    EventBus.getDefault().post(UserManager.getInstance().getRefreshBean());
                    finish();
                }
            }
        }, String.valueOf(mItemId), String.valueOf(status), accessoryId, form_source_id, reason);
    }

    private void showSettingView() {
        AutoSize.cancelAdapt(this);
        Spinner width_spinner, color_spinner;
        Button confirm_color, cancel;
        View view = LayoutInflater.from(this).inflate(R.layout.pensetting_view, null);
        width_spinner = view.findViewById(R.id.width_spinner);
        width_spinner.setOnItemSelectedListener(itemClickListener);
        color_spinner = view.findViewById(R.id.color_spinner);
        color_spinner.setOnItemSelectedListener(itemClickListener);
        width_spinner.setSelection(PenWidth.getKeyByValue(mWidth));
        color_spinner.setSelection(PenColor.getKeyByColor(mColor));
        confirm_color = view.findViewById(R.id.confirm_color);
        cancel = view.findViewById(R.id.cancel);
        confirm_color.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogUtil.createAlertDialog(this, view);
        mDialog.show();
    }

    private AdapterView.OnItemSelectedListener itemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.width_spinner) {
                mTagWidth = PenWidth.getWidthByKey(position);
            }

            if (parent.getId() == R.id.color_spinner) {
                mTagColor = PenColor.getColorByKey(position);
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
                if (mOpType == 1) {
                    setSelectedSates(mViewDelegate.get(R.id.pen_ll));
                    saveImg(true);
                }
                if (mOpType == 2) {
                    finish();
                }
                if (mOpType == 3) {
                    setSelectedSates(mViewDelegate.get(R.id.toolBar_img_right));
                    if (mCacheFileList.get(mTagPosition).isEmpty()) {
                        clearCanvas(null);
                    } else {
                        clearCanvas(mCacheFileList.get(mTagPosition));
                    }
                    noSigning();
                }
                if (mOpType == 4) {
                    clearCanvas(null);
                    noSigning();
                }
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!mDone) {
                mOpType = 2;
                DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "退出前请确认工作已保存", "确定", "不确定", dOnClickListener);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void noSigning() {
        mIsSigning = false;
        mEraser = false;
        mViewDelegate.setToolBarRightImg(R.mipmap.sign);
        mViewDelegate.get(R.id.sign_left_ll).setVisibility(View.VISIBLE);
        initPenWidthAndColor();
        hideWeight(false);
        mSignFullFL.setVisibility(View.GONE);
        mSignRightLL.setVisibility(View.VISIBLE);
        for (int j = 0; j < mLinearList.size(); j++) {
            mLinearList.get(j).setSelected(false);
        }
        mSignatureView.resetCacheMap();
    }

    private void clearCanvas(String path) {
        if (path != null) {
            //签字完成后保存一下文件路径
            mCacheFileList.set(mTagPosition, path);
            mSignatureView.clearCanvas(new File(path));
        } else {
            //清除字迹的时候要清除掉缓存的文件路径
            if (mCacheFileList != null && mCacheFileList.size() != 0) {
                mCacheFileList.set(mTagPosition, "");
            }
            mSignatureView.setNewPath("");
            mSignatureView.clearCanvas(new File(getPath(mId, mType)));
        }
    }

    private void saveImg(final Boolean needUpLoad) {
        if (mSignatureView != null) {
            LogUtil.d(TAG, "签字文件的路径" + (TextUtils.isEmpty(mSignatureView.getNewPath()) ? getPath(mId, getNowType()) : mSignatureView.getNewPath()));
            mSignatureView.addSignature2Pdf(TextUtils.isEmpty(mSignatureView.getNewPath()) ? getPath(mId, getNowType()) : mSignatureView.getNewPath()
                    , false, new SignatureView.DataFinishListener() {
                        @Override
                        public void onFinished(String path) {
                            LogUtil.d(TAG, "签完字后的路径" + path);
                            if (needUpLoad) {
                                upLoadFile(path);
                            }
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
                    mAccessoryList.set(mTagPosition, bean.getData().getId());
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
        for (int i = 0; i < mLinearList.size(); i++) {
            if (mLinearList.get(i).getId() != view.getId()) {
                mLinearList.get(i).setSelected(false);
            } else mLinearList.get(i).setSelected(true);
        }
        if (view.getId() != R.id.eraser_ll) {
            if (mEraser) {
                mEraser = !mEraser;
                mSignatureView.setPenColor(mColor);
                mSignatureView.setPenWidth(mWidth);
            }
        }
        if (view.getId() != R.id.pen_ll) {
            mIsPen = false;
        }
        if (view.getId() == R.id.pen_ll) {
            mIsPen = true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAddAccessoryDialog != null && mAddAccessoryDialog.isShowing()) {
            mAddAccessoryDialog.dismiss();
        }
        if (mSignFileView != null) {
            mSignFileView.onStop();
            mSignFileView = null;
        }
        if (mSignatureView != null) {
            mSignatureView.stopFling();
        }
        File file = new File(SIGN_RESULT);
        FileUtil.deleteFile(file);
        EventBus.getDefault().unregister(this);
        if (mCacheFileList != null) {
            mCacheFileList.clear();
        }
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
