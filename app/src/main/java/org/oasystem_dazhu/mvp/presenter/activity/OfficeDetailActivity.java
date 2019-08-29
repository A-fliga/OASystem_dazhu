package org.oasystem_dazhu.mvp.presenter.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.view.OfficeDetailDelegate;
import org.oasystem_dazhu.mvp.view.SignView.SignatureView;
import org.oasystem_dazhu.utils.LogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import static org.oasystem_dazhu.constants.Constants.OFFICE_PREVIEW;

/**
 * Created by www on 2019/1/5.
 */

public class OfficeDetailActivity extends ActivityPresenter {
    private static String TAG = "OASystem";
    private final int WRITE_STORAGE_CODE = 1000;
    private int id;
    private String type;
    private SignatureView pdf_view;
    private String[] permissionStr = {"内存卡", "读取手机"};

    @Override
    public Class getDelegateClass() {
        return OfficeDetailDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = Integer.parseInt(getIntent().getExtras().getString("source_id"));
        type = getIntent().getExtras().getString("type");
        pdf_view = viewDelegate.get(R.id.pdf_view);
        checkLocationPermission();
        viewDelegate.setToolBarRightTv("测试");
        viewDelegate.setToolBarLeftTv("测试1");
        viewDelegate.getToolBarLeftTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pdf_view.addSignature2Pdf(getPath(id, type));
            }
        });
        viewDelegate.getToolBarRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf_view.startSignature();
            }
        });
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(OfficeDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, WRITE_STORAGE_CODE);
        } else {
            showFile(id, type);
        }
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

    private Boolean isFileExist(int id, String type) {
        return new File(getPath(id, type)).exists();
    }

    private String getPath(int id, String type) {
        return OFFICE_PREVIEW + File.separator + id + "." + type;
    }

    private String getTempPath(int id) {
        return OFFICE_PREVIEW + File.separator + id + ".temp";
    }

    private void showFile(int id, String type) {

        File fileDir = new File(OFFICE_PREVIEW);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (isFileExist(id, type)) {
//            pdf_view.setMaxZoom(1f);
            pdf_view.startSignature();
            pdf_view.loadFile(new File(getPath(id, type)));

        } else {
            downLoadFile();
        }
    }


    private void downLoadFile() {
        PublicModel.getInstance().getSource(new MSubscribe<ResponseBody>() {
            @Override
            public void onNext(final ResponseBody bean) {
                super.onNext(bean);
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
                            long total = bean.contentLength();

                            File tempFile = new File(getTempPath(id));
                            if (tempFile.exists()) {
                                tempFile.delete();
                            } else tempFile.createNewFile();

                            fos = new FileOutputStream(tempFile);
                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                LogUtil.d(TAG, "写入缓存文件" + tempFile.getName() + "进度: " + progress);
                            }
                            fos.flush();
                            tempFile.renameTo(new File(getPath(id, type)));
                            LogUtil.d(TAG, "文件下载成功,准备展示文件。");
                            //2.ACache记录文件的有效期
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    pdf_view.fromFile(new File(getPath(id, type))).swipeHorizontal(true).pageSnap(true)
//                                            .autoSpacing(true).onDraw(new OnDrawListener() {
//                                        @Override
//                                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
//                                            LogUtil.d("asdsa");
//                                        }
//                                    })
//                                            .pageFling(true).enableDoubleTap(false).load();
//                                    pdf_view.setSwipeEnabled(false);
//                                    pdf_view.useBestQuality(true);
                                }
                            });

                        } catch (Exception e) {
                            LogUtil.d(TAG, "文件下载异常 = " + e.toString());
                        } finally {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdf_view != null)
            pdf_view.stopFling();
    }

}
