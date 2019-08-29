package org.oasystem_dazhu.mvp.view.SignView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfReader;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.mvp.model.bean.TransformBean;
import org.oasystem_dazhu.utils.FileUtil;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.SharedPreferencesUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by www on 2019/1/16.
 */

public class SignatureView extends FrameLayout {
    public static final String TAG = "SignatureView";
    public boolean mCanSigning = false;
    private boolean mNeedSignature = false;
    private boolean mAutoSpacing = false;
    private boolean mAutoSave = false;
    private boolean mIsFirstViewChange = true;
    //翻页前的上一个页数标记
    private int mPrePage = 0;
    private PDFView.Configurator mConfigurator;
    private LayoutInflater mInflater;
    private Save_Pdf_Async mSavePdfAsync;
    //保存签字笔迹文件的Map
    private Map<Integer, String> mSaveSignMap = new HashMap<>();
    private AtomicBoolean mCanSave = new AtomicBoolean(true);
    private AtomicBoolean mCanSaveCache = new AtomicBoolean(true);
    private String mSourcePath;
    private String mNewPath;
    private MPenLayout mPenView;
    private PDFView mPdfView;
    private File mSourceFile;
    private TransformBean mBean;
    private String mTagPath = "";
    private String mSaveRootName;
    private Handler mHandler;

    public SignatureView(@NonNull Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }

    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载视图的布局
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }


    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPdfView = findViewById(R.id.pdf_view_sign);
        mPenView = findViewById(R.id.signature_penLayout);
        mPenView.setSignatureView(this);
        mHandler = new StaticHandler(this);
    }

    private static class StaticHandler extends Handler {
        private WeakReference<SignatureView> mWeakSignView;

        public StaticHandler(SignatureView signatureView) {
            mWeakSignView = new WeakReference<SignatureView>(signatureView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @NonNull
    public PDFView getPDFView() {
        return mPdfView;
    }


    private SignatureView fromFile(File file) {
        mSourceFile = file;
        mPenView.setSignatureView(this);
        ProgressDialogUtil.instance().startLoad();
        mSourcePath = mSourceFile.getAbsolutePath();
        mCanSave.set(true);
        mConfigurator = mPdfView.fromFile(mSourceFile)
                .onLoad(completeListener)
                .onError(mErrorListener)
                .onPageScroll(mPageScrollListener);
        return this;
    }

    private OnErrorListener mErrorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {
            ToastUtil.l("文件已损坏");
            if (mSourceFile.exists()) {
                mSourceFile.delete();
            }
            ProgressDialogUtil.instance().stopLoad();
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private OnPageScrollListener mPageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            if (mPdfView.getZoom() > 1f && (mCanSave.getAndSet(false))) {
                addSignature2Pdf(mSourcePath, true, new DataFinishListener() {
                    @Override
                    public void onFinished(String path) {
                        mTagPath = path;
                        mCanSave.set(true);
                        loadFile(new File(path), true);
                    }

                    @Override
                    public void nothingChange() {
                        mCanSave.set(true);
                    }

                    @Override
                    public void onFailed() {
                        mCanSave.set(true);
                    }
                });
            }
        }
    };

    public void loadFile(File file) {
        loadFile(file, false);
    }

    public void loadFile(File file, Boolean auto) {
        stopFling();
        ProgressDialogUtil.instance().startLoad("加载文件中");
        this.mAutoSpacing = auto;

        fromFile(file)
                .swipeHorizontal(true)
                .pageSnap(true)
                .pageFling(true)
                .enableDoubleTap(false)
                .pageFitPolicy(mIsFirstViewChange ? FitPolicy.BOTH : FitPolicy.WIDTH)
                .setOnPageChangeListener()
                .setDefaultPage(mPrePage)
                .needSignature(true)
                .setSwipeEnabled(false)
                .load();
    }

    public void resetConfig() {
        mPrePage = 0;
    }

    private OnLoadCompleteListener completeListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {

            //如果不是自动保存的 就清空一下设置
            if (!mAutoSpacing) {
                resetConfig();
            }
            resetZoomWithAnimation();
            ProgressDialogUtil.instance().stopLoad();
            Document document = null;
            if (mNeedSignature) {
                PdfReader reader;
                try {
                    reader = new PdfReader(mSourceFile.getAbsolutePath());
                    //这里是第几页，不能写0
                    document = new Document(reader.getPageSize(1));
                    getSuitableSizeAndInitView(nbPages, document.getPageSize().getHeight(), document.getPageSize().getWidth());
                    reader.close();
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mIsFirstViewChange = false;
        }
    };

    private void getSuitableSizeAndInitView(final int nbPages, float height, float width) {
        int bitMapHeight;
        int bitMapWidth;
        if (height >= width) {
            bitMapHeight = mPenView.getHeight();
            bitMapWidth = (int) ((mPenView.getHeight() / height) * width);
            if (bitMapWidth > mPenView.getWidth()) {
                bitMapWidth = mPenView.getWidth();
            }
            if (bitMapWidth / width < bitMapHeight / height) {
                bitMapHeight = (int) (bitMapWidth / width * height);
            }
        } else {
            bitMapWidth = mPenView.getWidth();
            bitMapHeight = (int) ((mPdfView.getWidth() / width) * height);
            if (bitMapHeight > mPenView.getHeight()) {
                bitMapHeight = mPenView.getHeight();
            }
            if (bitMapHeight / height < bitMapWidth / width) {
                bitMapWidth = (int) (bitMapHeight / height * width);
            }
        }
        LayoutParams params = (LayoutParams) mPdfView.getLayoutParams();
        params.width = bitMapWidth;
        params.height = bitMapHeight;
        mPdfView.setLayoutParams(params);
        //加载完成后，通知签字的view裁剪bitMap时合适的宽高
        mPenView.setHeightAndWidth(bitMapHeight, bitMapWidth);
        initPenAfterAutoSpacing();
    }

    private void initPenAfterAutoSpacing() {
        if (mAutoSpacing) {
            setPenColor(SharedPreferencesUtil.getColor());
            setPenWidth(SharedPreferencesUtil.getWidth());
        }
    }

    public void setCanSigning(Boolean isSigning) {
        mCanSigning = isSigning;
    }

    public Boolean getCanSigning() {
        return mCanSigning;
    }

    public SignatureView setDefaultPage(int page) {
        mConfigurator.defaultPage(page);
        return this;
    }

    public SignatureView setDocumentId(String id) {
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.SIGN_CACHE).append(File.separator).append(id).append("_");
        this.mSaveRootName = sb.toString();
        return this;
    }

    public void setPenWidth(float width) {
        mPenView.setStrokeWidth(width);
        mPenView.setEraserMode(false);
    }

    public void setPenColor(int penColor) {
        mPenView.setStrokeColor(penColor);
        mPenView.setEraserMode(false);
    }

    public void initEraserMode(int color, float width) {
        mPenView.setEraserMode(true);
        mPenView.setStrokeWidth(width);
        mPenView.setStrokeColor(color);
    }

    public void clearCanvas(File file) {
        setCanSigning(false);
        mPenView.clear();
        if (file != null) {
            loadFile(file);
        }
    }

    public void addSignature2Pdf(String path, Boolean auto, DataFinishListener listener) {
        //得到有签字痕迹的页面
        if (mNeedSignature) {
            //保存时需要判断当前页面有无数据，有数据，需要添加进去，且要保存下来。没有数据了，需要移除
            if (mPenView.getData().length != 0) {
                mSaveSignMap.put(mPrePage, mSaveRootName + mPrePage);
                if (new File(mSaveSignMap.get(mPrePage)).exists()) {
                    new File(mSaveSignMap.get(mPrePage)).delete();
                }
                mPenView.save(mSaveSignMap.get(mPrePage));
            } else {
                if (mSaveSignMap.containsKey(mPrePage)) {
                    if (new File(mSaveSignMap.get(mPrePage)).exists()) {
                        new File(mSaveSignMap.get(mPrePage)).delete();
                    }
                }
                if (mSaveSignMap.containsKey(mPrePage)) {
                    mSaveSignMap.remove(mPrePage);
                }
            }
            //因为有自动保存 和手动点击保存两种保存方式，所以需要区分一下上一次保存是什么类型的
            if (mSaveSignMap.size() == 0) {
                //先判断这次的保存模式，如果是点击保存按钮的，就要判断上一次的模式
                if (!auto) {
                    //如果上一次没有自动保存过，就弹框提示
                    if (!mAutoSave) {
                        ToastUtil.l("没有新签字的页面");
                    }
                    //如果上一次有自动保存过，那么点击后直接把保存后的路径返回
                    else {
                        mAutoSave = false;
                        if (listener != null && !TextUtils.isEmpty(mTagPath)) {
                            listener.onFinished(mTagPath);
                            mTagPath = "";
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.nothingChange();
                    }
                }
            } else {
                //有签字数据时，如果是自己点击保存的，需要保存一下当前的参数
                TransformBean bean;
                if (!auto) {
                    bean = new TransformBean();
                    bean.setZoom(mPdfView.getZoom());
                    bean.setCurrentPage(getCurrentPage());
                    bean.setCurrentXOffset(mPdfView.getCurrentXOffset());
                    bean.setCurrentYOffset(mPdfView.getCurrentYOffset());
                } else {
                    //如果是自动保存的，就获取上一次的参数
                    bean = this.mBean;
                }

                /**
                 *   开始保存，去遍历map，用penView加载得到bitMap，此时可以先把penView隐藏起来，
                 *   避免加载过程中看到页面误以为加载错误，操作完成后再显示。
                 */
                mPenView.setVisibility(INVISIBLE);
                Map<Integer, Bitmap> signResultMap = new HashMap<>();
                for (int page : mSaveSignMap.keySet()) {
                    if (mPenView.load(mSaveSignMap.get(page))) {
                        signResultMap.put(page, mPenView.export());
                    }
                }
                toSaveSignature(path, bean, listener, signResultMap);
                mAutoSave = auto;
            }

        } else {
            ToastUtil.l("没有选择签字功能");
        }
    }


    private void toSaveSignature(String path, TransformBean bean, DataFinishListener listener, Map<Integer, Bitmap> beanMap) {
        SavePdf savePdf = new SavePdf();
        savePdf.setPdfView(getPDFView());
        savePdf.setSignatureBeanMap(beanMap);
        savePdf.setPdfSourcePath(path);
        savePdf.setZoom(bean.getZoom());
        savePdf.setCurrentPage(bean.getCurrentPage());
        savePdf.setCurrentXOffset(bean.getCurrentXOffset());
        savePdf.setCurrentYOffset(bean.getCurrentYOffset());
        mSavePdfAsync = new Save_Pdf_Async(savePdf, listener);
        mSavePdfAsync.execute();
    }


    public interface DataFinishListener {
        void onFinished(String path);

        void nothingChange();

        void onFailed();
    }

    /*
    * 用于存储的异步,并上传更新
    * */
    class Save_Pdf_Async extends AsyncTask {
        SavePdf savePdf;
        private DataFinishListener listener;

        public Save_Pdf_Async(SavePdf savePdf, DataFinishListener listener) {
            this.savePdf = savePdf;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            mNewPath = savePdf.addImg();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (TextUtils.isEmpty(mNewPath)) {
                if (listener != null) {
                    mPenView.clear();
                    mPenView.setVisibility(VISIBLE);
                    mSaveSignMap.clear();
                    listener.onFailed();
                }
            } else {
                if (listener != null) {
                    mPenView.clear();
                    mPenView.setVisibility(VISIBLE);
                    mSaveSignMap.clear();
                    listener.onFinished(mNewPath);
                }
            }
        }
    }

    public String getNewPath() {
        return mNewPath;
    }

    public void setNewPath(String mNewPath) {
        this.mNewPath = mNewPath;
    }

    public SignatureView swipeHorizontal(Boolean b) {
        mConfigurator.swipeHorizontal(b);
        return this;
    }

    public SignatureView autoSpacing(Boolean b) {
        mConfigurator.autoSpacing(b);
        return this;
    }

    public void setMaxZoom(float zoom) {
        mPdfView.setMaxZoom(zoom);
    }

    public SignatureView pageSnap(Boolean b) {
        mConfigurator.pageSnap(b);
        return this;
    }

    public SignatureView pageFling(Boolean b) {
        mConfigurator.pageFling(b);
        return this;
    }

    public SignatureView needSignature(Boolean b) {
        this.mNeedSignature = b;
        return this;
    }

    public SignatureView enableDoubleTap(Boolean b) {
        mConfigurator.enableDoubletap(b);
        return this;
    }


    public SignatureView pageFitPolicy(FitPolicy pageFitPolicy) {
        mConfigurator.pageFitPolicy(pageFitPolicy);
        return this;
    }

    public void resetCacheMap() {
        if (mSaveSignMap != null) {
            mSaveSignMap.clear();
        }
    }

    public SignatureView setOnPageChangeListener() {
        mConfigurator.onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                if ((mPenView != null) && (mPrePage != page)) {
                    if (mCanSaveCache.getAndSet(false)) {
                        //翻页时，判断当前页面有无笔迹，有的话，就保存成文件，然后清空笔迹，再去取翻页后的缓存笔迹文件
                        if (mPenView.getData().length != 0) {
                            //删掉原来有的缓存
                            if (new File(mSaveRootName + mPrePage).exists()) {
                                new File(mSaveRootName + mPrePage).delete();
                            }
                            //保存翻页前签字的缓存文件，然后清空面板
                            if (mPenView.save(mSaveRootName + mPrePage)) {
                                mSaveSignMap.put(mPrePage, mSaveRootName + mPrePage);
                                mPenView.clear();
                            } else {
                                ToastUtil.s("保存签字出错");
                            }
                        } else {
                            //这种情况是翻页前的页面用橡皮擦擦干净了，然后翻页。此时要从map里剔除,并且删掉缓存文件
                            if (mSaveSignMap.containsKey(mPrePage)) {
                                mSaveSignMap.remove(mPrePage);
                            }
                            if (new File(mSaveRootName + mPrePage).exists()) {
                                new File(mSaveRootName + mPrePage).delete();
                            }
                        }
                        //如果翻页的页面有缓存的话，就去加载出来
                        if (new File(mSaveRootName + page).exists()) {
                            mPenView.load(mSaveRootName + page);
                        } else {
                            mPenView.clear();
                        }
                    }
                    //翻页之后，要恢复可保存的状态
                    mCanSaveCache.set(true);
                }
                if (mPdfView.isZooming()) {
                    mPdfView.resetZoomWithAnimation();
                }
                mPrePage = page;
            }
        });
        return this;
    }

    public void load() {
        mConfigurator.load();
    }

    public void stopFling() {
        if (mPdfView != null) {
            mPdfView.stopFling();
            mPdfView.recycle();
        }
        if (mPenView != null) {
            mPenView.setSignatureView(null);
            mPenView.clear();
        }
        if (mSavePdfAsync != null) {
            mSavePdfAsync.cancel(true);
        }
        //需要清除掉所有的签字临时缓存
        FileUtil.deleteFile(new File(Constants.SIGN_CACHE));
        new File(Constants.SIGN_CACHE).mkdirs();
    }


    public void resetZoomWithAnimation() {
        if (mPdfView != null) {
            mPdfView.resetZoomWithAnimation();
        }
    }

    public SignatureView setSwipeEnabled(Boolean b) {
        mPdfView.setSwipeEnabled(b);
        return this;
    }

    @NonNull
    public int getCurrentPage() {
        return mPdfView.getCurrentPage();
    }


    public void setTransformBean(TransformBean bean) {
        this.mBean = bean;
    }

    public void startSignature() {
        if (mPenView != null) {
            mPenView.setVisibility(VISIBLE);
        }
    }
}
