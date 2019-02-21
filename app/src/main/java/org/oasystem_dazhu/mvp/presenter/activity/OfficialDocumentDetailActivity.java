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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.tencent.smtt.sdk.TbsReaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.application.MyApplication;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.SignOfficialAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AccessoryBean;
import org.oasystem_dazhu.mvp.model.bean.AllUserBean;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;
import org.oasystem_dazhu.mvp.model.bean.IsSigningBean;
import org.oasystem_dazhu.mvp.model.bean.UpFileBean;
import org.oasystem_dazhu.mvp.view.OfficialDocumentDetailDelegate;
import org.oasystem_dazhu.mvp.view.SignView.PenColor;
import org.oasystem_dazhu.mvp.view.SignView.PenWidth;
import org.oasystem_dazhu.mvp.view.SignView.SignatureView;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.FileUtil;
import org.oasystem_dazhu.utils.LogUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.RealPathFromUriUtils;
import org.oasystem_dazhu.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

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
    private LinearLayout save_ll, pen_ll, clear_ll, yinzhang_ll, biaozhu_ll;
    private List<LinearLayout> linearList = new ArrayList<>();
    private List<String> contentTv;
    private TbsReaderView sign_fileView;
    private SignatureView mSignatureView;
    private String[] permissionStr = {"内存卡", "读取手机"};
    private int id, tagPosition, opType = 0, itemId, color = Color.BLACK, tagColor;
    private ArrayList<Integer> accessoryList;
    private String type;
    private static String OFFICE_PATH;
    private AlertDialog dialog, addAccessoryDialog;
    private float width = PenWidth.DEFAULT.getWidth(), tagWidth;
    private List<AllUserBean.DataBean> userBeanList;

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
        done = getIntent().getExtras().getBoolean("done");
        dataBean = (DocumentBean.DataBean) getIntent().getExtras().getSerializable("DocumentDataBean");
        dispatchBean = dataBean.getDispatch();
        //记录Id
        itemId = dataBean.getId();
        accessoryList = new ArrayList<>();
        accessoryList.add(dispatchBean.getForm_source_id());
        if (dispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < dispatchBean.getAccessory_list().size(); i++) {
                accessoryList.add(Integer.parseInt(dispatchBean.getAccessory_list().get(i).getSource_id()));
            }
        }

//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < accessoryList.size(); i++) {
//            sb.append(accessoryList.get(i)+" ");
//        }
//        LogUtil.d("paixu", "接收到的" + sb.toString());
        initView(done);
        viewDelegate.setOnClickListener(onClickListener, R.id.save_img, R.id.pen_img, R.id.clear_img, R.id.yinzhang_img, R.id.biaozhu_img);
        initNotDoneView();
        viewDelegate.initBottomRecyclerView(dataBean, done);
        checkLocationPermission();
        if (!done) {
            getAllUserBean();
        }
    }

    private void getAllUserBean() {
        userBeanList = new ArrayList<>();
        PublicModel.getInstance().getAllUser(new MSubscribe<BaseEntity<AllUserBean>>() {
            @Override
            public void onNext(BaseEntity<AllUserBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    userBeanList.addAll(bean.getData().getData());
                }
            }
        });
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(OfficialDocumentDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, WRITE_STORAGE_CODE);
        } else {
//            requestWriteSettings();
            id = dispatchBean.getForm_source_id();
            String[] str = dispatchBean.getForm_source().getName().split("\\.");
            type = str[str.length - 1];
            tagPosition = 0;
            showFile(id, type);
        }
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

//    //是否是橡皮擦状态
//    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
//    public void isEraserMode(IsEraserMode bean) {
//        if (bean.eraserMode) {
//            eraser = true;
//            setSelectedSates(viewDelegate.get(R.id.clear_ll));
//            mSignatureView.initEraserMode(Color.TRANSPARENT, width + 40f);
//        } else {
//
//        }
//    }

    private void showFile(int id, String type) {
        File fileDir = new File(OFFICE_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (isFileExist(id, type)) {
            if (!done && type.equals("pdf")) {
                //用自己的view加载
                viewDelegate.get(R.id.mSignatureView).setVisibility(View.VISIBLE);
                viewDelegate.get(R.id.tbs_contentView).setVisibility(View.GONE);
                disPlayBySignView(new File(getPath(id, type)));
            } else {
                viewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
                viewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
                displayFile(new File(getPath(id, type)));
            }
        } else {
            downLoadFile(id, type);
        }
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
                id = dispatchBean.getForm_source_id();
                String[] str = dispatchBean.getForm_source().getName().split("\\.");
                type = str[str.length - 1];
                tagPosition = 0;
                showFile(id, type);
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

    private void downLoadFile(final int id, final String type) {
        PublicModel.getInstance().getSource(new MSubscribe<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtil.l("下载文件出现错误\n" + e.toString());
            }

            @Override
            public void onNext(final ResponseBody bean) {
                super.onNext(bean);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDialogUtil.instance().startLoad("下载中");
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
//                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
//                                sum += len;
//                                int progress = (int) (sum * 1.0f / total * 100);
//                                LogUtil.d(TAG, "写入缓存文件" + tempFile.getName() + "进度: " + progress);
                            }
                            fos.flush();
                            tempFile.renameTo(new File(getPath(id, type)));
                            LogUtil.d(TAG, "文件下载成功,准备展示文件。");
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
                            } catch (IOException e) {
                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }).start();
            }
        }, id + "");
    }


    private Boolean isFileExist(int id, String type) {
        File file = new File(getPath(id, type));
        if (file.length() == 0) {
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
        save_ll = viewDelegate.get(R.id.save_img);
        pen_ll = viewDelegate.get(R.id.pen_img);
        clear_ll = viewDelegate.get(R.id.clear_img);
        yinzhang_ll = viewDelegate.get(R.id.yinzhang_img);
        biaozhu_ll = viewDelegate.get(R.id.biaozhu_img);
        linearList.add(save_ll);
        linearList.add(pen_ll);
        linearList.add(clear_ll);
        linearList.add(yinzhang_ll);
        linearList.add(biaozhu_ll);
        recyclerView = viewDelegate.get(R.id.sign_right_list);
        //需要代码创建view才可以显示多次
        createSignView();
        if (done) {
            viewDelegate.get(R.id.tbs_contentView).setVisibility(View.VISIBLE);
            OFFICE_PATH = Constants.OFFICE_PREVIEW;
            viewDelegate.getToolBarRightImg().setVisibility(View.GONE);
            viewDelegate.get(R.id.sign_left_ll).setVisibility(View.GONE);
            viewDelegate.get(R.id.mSignatureView).setVisibility(View.GONE);
        } else {
            viewDelegate.setToolBarRightImg(R.mipmap.sign);
            viewDelegate.getToolBarRightImg().setOnClickListener(onClickListener);
            viewDelegate.hideLeftBtn(dataBean.getAuth());
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
        viewDelegate.setOnClickListener(onClickListener, R.id.sign_add_advise, R.id.sign_add_person, R.id.sign_agree, R.id.sign_refuse, R.id.sign_close);
        contentTv = new ArrayList<>();
        contentTv.add("审批单");
        if (dispatchBean.getAccessory_list() != null) {
            for (int i = 0; i < dispatchBean.getAccessory_list().size(); i++) {
                contentTv.add("附件" + (i + 1));
            }
        }
        SignOfficialAdapter adapter = new SignOfficialAdapter(contentTv, this);
        viewDelegate.setRecycler(recyclerView, adapter, true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (tagPosition != position) {
                    //切换界面要恢复一下设置
                    if (mSignatureView != null) {
                        mSignatureView.resetConfig();
                        mSignatureView.setNewPath("");
                    }
                    tagPosition = position;
                    //回收掉原来的tbsView，否则不能显示
                    if (sign_fileView != null) {
                        FrameLayout frameLayout = viewDelegate.get(R.id.tbs_contentView);
                        frameLayout.removeAllViews();
                        sign_fileView.onStop();
                        sign_fileView = null;
                        createSignView();
                    }
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

                }
            }
        });
    }

    private String getNowType() {
        String type = "";
        for (int i = 0; i < contentTv.size(); i++) {
            if (tagPosition == 0) {
                String[] str = dispatchBean.getForm_source().getName().split("\\.");
                type = str[str.length - 1];
            } else {
                String[] str = dispatchBean.getAccessory_list().get(tagPosition - 1).getName().split("\\.");
                type = str[str.length - 1];
            }
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
                    setSelectedSates(view);
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
                case R.id.save_img:
                    setSelectedSates(view);
                    opType = 1;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要保存签批吗？", "确定", "取消", dOnClickListener);
                    break;
                //选择笔的样式
                case R.id.pen_img:
                    if (isPen)
                        showSettingView();
                    setSelectedSates(view);
                    break;
                //橡皮擦
                case R.id.clear_img:
//                    eraser = !eraser;
//                    if (eraser) {
//                        setSelectedSates(view);
//                        mSignatureView.initEraserMode(Color.TRANSPARENT, width + 30f);
//                    } else {
//                        setSelectedSates(viewDelegate.get(R.id.pen_ll));
//                        mSignatureView.setPenColor(color);
//                        mSignatureView.setPenWidth(width);
//                    }
                    opType = 4;
                    DialogUtil.showDialog(OfficialDocumentDetailActivity.this, "您确定要清除签字吗？", "确定", "取消", dOnClickListener);
                    break;
                //选择印章
                case R.id.yinzhang_img:
                    setSelectedSates(view);
                    break;
                //选择标注
                case R.id.biaozhu_img:
                    setSelectedSates(view);
                    break;
                //确认笔记颜色
                case R.id.confirm_color:
                    if (tagWidth != width && tagWidth != -1) {
                        width = tagWidth;
                        mSignatureView.setPenWidth(width);
                    }
                    if (tagColor != color && tagColor != -1) {
                        color = tagColor;
                        mSignatureView.setPenColor(color);
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

            }
        }
    };


    private void setSigningStatus() {
        isSigning = true;
        isSigning();
        mSignatureView.setCanSigning(true);
//        mSignatureView.resetZoomWithAnimation();
    }

    private void isSigning() {
        viewDelegate.setToolBarRightImg(R.mipmap.sign_cancel);
        sign_full_fl.setVisibility(View.VISIBLE);
        sign_right_ll.setVisibility(View.GONE);
        viewDelegate.get(R.id.sign_left_ll).setVisibility(View.GONE);
        setSelectedSates(viewDelegate.get(R.id.pen_img));
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
                DialogUtil.showChoiceDialog(this, "确定", "取消", peopleList, booleanList, getOnClick(5), multiChoiceClickListener);
            }
        }
    }

    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
            userBeanList.get(i).setSelected(b);
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
//                    if (mType == 1) {
//                        nextOperation(1);
//                    }
//                    if (mType == 3) {
//                        nextOperation(3);
//                    }
//                    if (mType == 2) {
//
//                    }
//                    if(mType == 4){
//
//                    }
                }
                dialogInterface.dismiss();
            }
        };
        return onClickListener;
    }

    private void nextOperation(int status) {
        if (status != 5) {
            String form_source_id;
            StringBuffer sb = new StringBuffer();
            form_source_id = String.valueOf(accessoryList.get(0));
            for (int i = 1; i < accessoryList.size(); i++) {
                if (i == accessoryList.size() - 1) {
                    sb.append(accessoryList.get(i));
                } else {
                    sb.append(accessoryList.get(i)).append("#");
                }
            }
            if (status == 1 || status == 2)
                toExamine(status, form_source_id, sb.toString());
            if (status == 3) {
                toClose(form_source_id, sb.toString());
            }
            if (status == 4) {
                toAdvise(form_source_id, sb.toString());
            }
        } else
            add_countersign();
    }

    private void add_countersign() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userBeanList.size(); i++) {
            if (userBeanList.get(i).getSelected()) {
                sb.append(userBeanList.get(i).getId()).append(",");
            }
        }
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
        }, itemId, sb.substring(0, sb.length() - 1));
    }

    private void toExamine(int status, String form_source_id, String accessoryId) {
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
        }, String.valueOf(itemId), String.valueOf(status), accessoryId, form_source_id);
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
                    saveImg(true);
                }
                if (opType == 2) {
                    finish();
                }
                if (opType == 3) {
                    clearCanvas(null);
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
        width = PenWidth.DEFAULT.getWidth();
        color = Color.BLACK;
        viewDelegate.setToolBarRightImg(R.mipmap.sign);
        viewDelegate.get(R.id.sign_left_ll).setVisibility(View.VISIBLE);
        sign_full_fl.setVisibility(View.GONE);
        sign_right_ll.setVisibility(View.VISIBLE);
        for (int j = 0; j < linearList.size(); j++) {
            linearList.get(j).setSelected(false);
        }
//        mSignatureView.resetZoomWithAnimation();
    }

    private void clearCanvas(String path) {
        if (path != null)
            mSignatureView.clearCanvas(new File(path));
        else {
//            mSignatureView.clearCanvas(null);
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
        if (view.getId() != R.id.clear_img) {
            if (eraser) {
                eraser = !eraser;
                mSignatureView.setPenColor(color);
                mSignatureView.setPenWidth(width);
            }
        }
        if (view.getId() != R.id.pen_img) {
            isPen = false;
        }
        if (view.getId() == R.id.pen_img) {
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
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
