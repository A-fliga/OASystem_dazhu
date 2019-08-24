package org.oasystem_dazhu.mvp.view.SignView;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.oasystem_dazhu.mvp.model.bean.TransformBean;
import org.oasystem_dazhu.mvp.view.customView.NoAnimationViewPager;
import org.oasystem_dazhu.utils.ProgressDialogUtil;
import org.oasystem_dazhu.utils.SharedPreferencesUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by www on 2019/1/16.
 */

public class SignatureView extends FrameLayout {
    public static final String TAG = "SignatureView";
    private List<MPenLayout> mPenViewList;
    private PDFView.Configurator mConfigurator;
    private LayoutInflater mInflater;
    private Save_Pdf_Async mSavePdfAsync;
    private Context mContext;
    private AtomicBoolean mCanSave = new AtomicBoolean(true);
    private String mSourcePath;
    private String mNewPath;
    private MPenLayout mPenView;
    private PDFView mPdfView;
    private NoAnimationViewPager mViewPager;
    private File mSourceFile;
    private TransformBean mBean;
    private String mTagPath = "";
    public boolean mCanSigning = false;
    private boolean mNeedSignature = false;
    private boolean mAutoSpacing = false;
    private boolean mAutoSave = false;
    private boolean mIsFirstViewChange = true;
    private int mDefaultPage = 0;


    public SignatureView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }

    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载视图的布局
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }


    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.view_signature, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPdfView = findViewById(R.id.pdf_view_sign);
        mViewPager = findViewById(R.id.signature_viewpager);
    }

    @NonNull
    public PDFView getPDFView() {
        return mPdfView;
    }


    private SignatureView fromFile(File file) {
        mSourceFile = file;
        ProgressDialogUtil.instance().startLoad();
        mSourcePath = mSourceFile.getAbsolutePath();
        mCanSave.set(true);
        mConfigurator = mPdfView.fromFile(mSourceFile)
                .onLoad(completeListener)
                .onError(errorListener)
                .onPageScroll(pageScrollListener);
        return this;
    }

    private OnErrorListener errorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {
            ToastUtil.l("文件已损坏");
            if (mSourceFile.exists()) {
                mSourceFile.delete();
            }
            ProgressDialogUtil.instance().stopLoad();
        }
    };

    private OnPageScrollListener pageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            if (mPdfView.getZoom() > 1f && mCanSave.get()) {
                mCanSave.set(false);
                addSignature2Pdf(mSourcePath, true, new DataFinishListener() {
                    @Override
                    public void onFinished(String path) {
                        mTagPath = path;
                        mDefaultPage = getCurrentPage();
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

    public void resetConfig() {
        mDefaultPage = 0;
    }


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
                .needSignature(true)
                .setDefaultPage(mDefaultPage)
                .setSwipeEnabled(false)
                .load();
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
                    reader.close();
                    mPenViewList = new ArrayList<>();
                    getSuitableSizeAndInitView(nbPages, document);
                    MPagerAdapter adapter = new MPagerAdapter(SignatureView.this);
                    mViewPager.setAdapter(adapter);
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mIsFirstViewChange = false;
        }
    };

    private void getSuitableSizeAndInitView(final int nbPages, final Document document) {
        int bitMapHeight;
        int bitMapWidth;
        if (document.getPageSize().getHeight() >= document.getPageSize().getWidth()) {
            bitMapHeight = mViewPager.getHeight();
            bitMapWidth = (int) ((mViewPager.getHeight() / document.getPageSize().getHeight()) * document.getPageSize().getWidth());
            if (bitMapWidth > mViewPager.getWidth()) {
                bitMapWidth = mViewPager.getWidth();
            }
            if (bitMapWidth / document.getPageSize().getWidth() < bitMapHeight / document.getPageSize().getHeight()) {
                bitMapHeight = (int) (bitMapWidth / document.getPageSize().getWidth() * document.getPageSize().getHeight());
            }
        } else {
            bitMapWidth = mViewPager.getWidth();
            bitMapHeight = (int) ((mPdfView.getWidth() / document.getPageSize().getWidth()) * document.getPageSize().getHeight());
            if (bitMapHeight > mViewPager.getHeight()) {
                bitMapHeight = mViewPager.getHeight();
            }
            if (bitMapHeight / document.getPageSize().getHeight() < bitMapWidth / document.getPageSize().getWidth()) {
                bitMapWidth = (int) (bitMapHeight / document.getPageSize().getHeight() * document.getPageSize().getWidth());
            }
        }
        LayoutParams params = (LayoutParams) mPdfView.getLayoutParams();
        params.width = bitMapWidth;
        params.height = bitMapHeight;
        mPdfView.setLayoutParams(params);
        //pdf加载完成后 先准备对应页数的签字页面，放入viewpager中
        for (int i = 0; i < nbPages; i++) {
            mPenView = new MPenLayout(mContext, bitMapWidth, bitMapHeight);
            mPenView.setSignatureView(this);
            mPenViewList.add(mPenView);
        }
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


    private static class MPagerAdapter extends PagerAdapter {
        private WeakReference<SignatureView> weakSignView;
        private List<MPenLayout> penViewList;

        public MPagerAdapter(SignatureView signView) {
            super();
            this.weakSignView = new WeakReference<SignatureView>(signView);
            penViewList = new ArrayList<>();
            if (weakSignView.get() != null) {
                penViewList.addAll(weakSignView.get().mPenViewList);
            }
        }


        @Override
        public int getCount() {
            return penViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MPenLayout penView = penViewList.get(position);
            container.addView(penView);
            return penView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((MPenLayout) object);
        }
    }

    public void setPenWidth(float width) {
        for (int i = 0; i < mPenViewList.size(); i++) {
            mPenViewList.get(i).setStrokeWidth(width);
            mPenViewList.get(i).setEraserMode(false);
        }
    }

    public void setPenColor(int penColor) {
        for (int i = 0; i < mPenViewList.size(); i++) {
            mPenViewList.get(i).setStrokeColor(penColor);
            mPenViewList.get(i).setEraserMode(false);
        }
    }

    public void initEraserMode(int color, float width) {
        for (int i = 0; i < mPenViewList.size(); i++) {
            mPenViewList.get(i).setEraserMode(true);
            mPenViewList.get(i).setStrokeWidth(width);
            mPenViewList.get(i).setStrokeColor(color);
        }
    }

    public void clearCanvas(File file) {
        setCanSigning(false);
        for (int i = 0; i < mPenViewList.size(); i++) {
            synchronized (SignatureView.class) {
                mPenViewList.get(i).clear();
            }
        }
        if (file != null)
            loadFile(file);
    }

    public void addSignature2Pdf(String path, Boolean auto, DataFinishListener listener) {
        //得到有签字痕迹的页面
        if (mNeedSignature) {
            MPenLayout penView;
            List<MPenLayout> newDrawPenViewList = new ArrayList<>();
            List<Integer> signPageList = new ArrayList<>();
            for (int i = 0; i < mPenViewList.size(); i++) {
                penView = mPenViewList.get(i);
                if (penView.isDataModified()) {
                    signPageList.add(i);
                    newDrawPenViewList.add(penView);
                }
            }
            if (signPageList.size() == 0) {
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
                TransformBean bean;
                if (!auto) {
                    bean = new TransformBean();
                    bean.setZoom(mPdfView.getZoom());
                    bean.setCurrentPage(getCurrentPage());
                    bean.setCurrentXOffset(mPdfView.getCurrentXOffset());
                    bean.setCurrentYOffset(mPdfView.getCurrentYOffset());
                } else {
                    bean = this.mBean;
                }
                toSaveSignature(path, bean, listener, newDrawPenViewList, signPageList);
                mAutoSave = auto;
            }

        } else {
            ToastUtil.l("没有选择签字功能");
        }
    }


    private void toSaveSignature(String path, TransformBean bean, DataFinishListener listener, List<MPenLayout> newDrawPenViewList, List<Integer> signPageList) {
        SavePdf savePdf = new SavePdf();
        savePdf.setPdfView(getPDFView());
        savePdf.setPenViewList(newDrawPenViewList);
        savePdf.setSignatureList(signPageList);
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
                    listener.onFailed();
                }
            } else {
                if (listener != null)
                    listener.onFinished(mNewPath);
            }

        }

    }

    public String getmNewPath() {
        return mNewPath;
    }

    public void setmNewPath(String mNewPath) {
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

    public SignatureView setOnPageChangeListener() {
        mConfigurator.onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(page);
                }
                if (mPdfView.isZooming()) {
                    mPdfView.resetZoomWithAnimation();
                }

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
        if (mViewPager != null) {
            mViewPager.removeAllViews();
        }
        if (mPenViewList != null) {
            for (int i = 0; i < mPenViewList.size(); i++) {
                mPenViewList.get(i).setSignatureView(null);
                mPenViewList.get(i).clear();
                mPenViewList.get(i).removeAllViews();
            }
            mPenViewList.clear();
        }
        resetConfig();
        if (mSavePdfAsync != null) {
            mSavePdfAsync.cancel(true);
        }
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
        if (mViewPager != null) {
            mViewPager.setVisibility(VISIBLE);
        }
    }
}
