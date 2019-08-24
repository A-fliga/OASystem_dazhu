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
    private NoAnimationViewPager viewPager;
    private PDFView pdf_view;
    private PDFView.Configurator configurator;
    private List<MPenLayout> penViewList;
    private LayoutInflater inflater;
    private Save_Pdf_Async savePdfAsync;
    private boolean needSignature = false;
    public boolean canSigning = false;
    private boolean autoSpacing = false;
    private Context context;
    private AtomicBoolean canSave = new AtomicBoolean(true);
    private String sourcePath, newPath;
    private MPenLayout penView;
    private File sourceFile;
    private TransformBean bean;
    private int defaultPage = 0;
    private Boolean autoSave = false;
    private String tagPath = "";
    private boolean isFirstViewChange = true;

    public SignatureView(@NonNull Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_signature, this, true);
    }

    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载视图的布局
        this.context = context;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_signature, this, true);
    }


    public SignatureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_signature, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pdf_view = findViewById(R.id.pdf_view_sign);
    }

    @NonNull
    public PDFView getPDFView() {
        return pdf_view;
    }


    private SignatureView fromFile(File file) {
        sourceFile = file;
        ProgressDialogUtil.instance().startLoad();
        sourcePath = sourceFile.getAbsolutePath();
        canSave.set(true);
        configurator = pdf_view.fromFile(sourceFile)
                .onLoad(completeListener)
                .onError(errorListener)
                .onPageScroll(pageScrollListener);
        return this;
    }

    private OnErrorListener errorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {
            ToastUtil.l("文件已损坏");
            if(sourceFile.exists()){
                sourceFile.delete();
            }
            ProgressDialogUtil.instance().stopLoad();
        }
    };

    private OnPageScrollListener pageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            if (pdf_view.getZoom() > 1f && canSave.get()) {
                canSave.set(false);
//                LogUtil.d("pianyi", "签字文件的路径" + sourcePath);
                addSignature2Pdf(sourcePath, true, new DataFinishListener() {
                    @Override
                    public void onFinished(String path) {
//                        LogUtil.d("pianyi", "签完字后的路径" + path);
                        tagPath = path;
                        defaultPage = getCurrentPage();
                        canSave.set(true);
                        loadFile(new File(path), true);
                    }

                    @Override
                    public void nothingChange() {
                        canSave.set(true);
                    }

                    @Override
                    public void onFailed() {
                        canSave.set(true);
                    }
                });
            }
        }
    };

    public void resetConfig() {
        defaultPage = 0;
    }


    public void loadFile(File file) {
        loadFile(file, false);
    }

    public void loadFile(File file, Boolean auto) {
        stopFling();
        ProgressDialogUtil.instance().startLoad("加载文件中");
        this.autoSpacing = auto;

        fromFile(file)
                .swipeHorizontal(true)
                .pageSnap(true)
                .pageFling(true)
                .enableDoubleTap(false)
                .pageFitPolicy(isFirstViewChange ? FitPolicy.BOTH : FitPolicy.WIDTH)
                .setOnPageChangeListener()
                .needSignature(true)
                .setDefaultPage(defaultPage)
                .setSwipeEnabled(false)
                .load();
    }


    private OnLoadCompleteListener completeListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {
            //如果不是自动保存的 就清空一下设置
            if (!autoSpacing) {
                resetConfig();
            }
            resetZoomWithAnimation();
            ProgressDialogUtil.instance().stopLoad();
            Document document = null;
            if (needSignature) {
                PdfReader reader;
                try {
                    reader = new PdfReader(sourceFile.getAbsolutePath());
                    document = new Document(reader.getPageSize(1));
                    reader.close();
                    penViewList = new ArrayList<>();
                    viewPager = findViewById(R.id.signature_viewpager);
                    getSuitableSizeAndInitView(nbPages, document);
                    MPagerAdapter adapter = new MPagerAdapter(SignatureView.this);
                    viewPager.setAdapter(adapter);
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            isFirstViewChange = false;
        }
    };

    private void getSuitableSizeAndInitView(final int nbPages, final Document document) {
        int bitMapHeight;
        int bitMapWidth;
        if (document.getPageSize().getHeight() >= document.getPageSize().getWidth()) {
            bitMapHeight = viewPager.getHeight();
            bitMapWidth = (int) ((viewPager.getHeight() / document.getPageSize().getHeight()) * document.getPageSize().getWidth());
            if (bitMapWidth > viewPager.getWidth()) {
                bitMapWidth = viewPager.getWidth();
            }
            if (bitMapWidth / document.getPageSize().getWidth() < bitMapHeight / document.getPageSize().getHeight()) {
                bitMapHeight = (int) (bitMapWidth / document.getPageSize().getWidth() * document.getPageSize().getHeight());
            }
        } else {
            bitMapWidth = viewPager.getWidth();
            bitMapHeight = (int) ((pdf_view.getWidth() / document.getPageSize().getWidth()) * document.getPageSize().getHeight());
            if (bitMapHeight > viewPager.getHeight()) {
                bitMapHeight = viewPager.getHeight();
            }
            if (bitMapHeight / document.getPageSize().getHeight() < bitMapWidth / document.getPageSize().getWidth()) {
                bitMapWidth = (int) (bitMapHeight / document.getPageSize().getHeight() * document.getPageSize().getWidth());
            }
        }
        LayoutParams params = (LayoutParams) pdf_view.getLayoutParams();
        params.width = bitMapWidth;
        params.height = bitMapHeight;
        pdf_view.setLayoutParams(params);
        //pdf加载完成后 先准备对应页数的签字页面，放入viewpager中
        for (int i = 0; i < nbPages; i++) {
            penView = new MPenLayout(context, bitMapWidth, bitMapHeight);
            penView.setSignatureView(this);
            penViewList.add(penView);
        }
        initPenAfterAutoSpacing();
    }

    private void initPenAfterAutoSpacing() {
        if (autoSpacing) {
            setPenColor(SharedPreferencesUtil.getColor());
            setPenWidth(SharedPreferencesUtil.getWidth());
        }
    }

    public void setCanSigning(Boolean isSigning) {
        canSigning = isSigning;
    }

    public Boolean getCanSigning() {
        return canSigning;
    }

    public SignatureView setDefaultPage(int page) {
        configurator.defaultPage(page);
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
                penViewList.addAll(weakSignView.get().penViewList);
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
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).setStrokeWidth(width);
            penViewList.get(i).setEraserMode(false);
        }
    }

    public void setPenColor(int penColor) {
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).setStrokeColor(penColor);
            penViewList.get(i).setEraserMode(false);
        }
    }

    public void initEraserMode(int color, float width) {
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).setEraserMode(true);
            penViewList.get(i).setStrokeWidth(width);
            penViewList.get(i).setStrokeColor(color);
        }
    }

    public void clearCanvas(File file) {
        setCanSigning(false);
        for (int i = 0; i < penViewList.size(); i++) {
            synchronized (SignatureView.class) {
                penViewList.get(i).clear();
            }
        }
        if (file != null)
            loadFile(file);
    }

    public void addSignature2Pdf(String path, Boolean auto, DataFinishListener listener) {
        //得到有签字痕迹的页面
        if (needSignature) {
            MPenLayout penView;
            List<MPenLayout> newDrawPenViewList = new ArrayList<>();
            List<Integer> signPageList = new ArrayList<>();
            for (int i = 0; i < penViewList.size(); i++) {
                penView = penViewList.get(i);
                if (penView.isDataModified()) {
                    signPageList.add(i);
                    newDrawPenViewList.add(penView);
                }
            }
            if (signPageList.size() == 0) {
                //先判断这次的保存模式，如果是点击保存按钮的，就要判断上一次的模式
                if (!auto) {
                    //如果上一次没有自动保存过，就弹框提示
                    if (!autoSave) {
                        ToastUtil.l("没有新签字的页面");
                    }
                    //如果上一次有自动保存过，那么点击后直接把保存后的路径返回
                    else {
                        autoSave = false;
                        if (listener != null && !TextUtils.isEmpty(tagPath)) {
                            listener.onFinished(tagPath);
                            tagPath = "";
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
                    bean.setZoom(pdf_view.getZoom());
                    bean.setCurrentPage(getCurrentPage());
                    bean.setCurrentXOffset(pdf_view.getCurrentXOffset());
                    bean.setCurrentYOffset(pdf_view.getCurrentYOffset());
                } else {
                    bean = this.bean;
                }
                toSaveSignature(path, bean, listener, newDrawPenViewList, signPageList);
                autoSave = auto;
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
        savePdfAsync = new Save_Pdf_Async(savePdf, listener);
        savePdfAsync.execute();
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
            newPath = savePdf.addImg();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (TextUtils.isEmpty(newPath)) {
                if (listener != null) {
                    listener.onFailed();
                }
            } else {
                if (listener != null)
                    listener.onFinished(newPath);
            }

        }

    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public SignatureView swipeHorizontal(Boolean b) {
        configurator.swipeHorizontal(b);
        return this;
    }

    public SignatureView autoSpacing(Boolean b) {
        configurator.autoSpacing(b);
        return this;
    }

    public void setMaxZoom(float zoom) {
        pdf_view.setMaxZoom(zoom);
    }

    public SignatureView pageSnap(Boolean b) {
        configurator.pageSnap(b);
        return this;
    }

    public SignatureView pageFling(Boolean b) {
        configurator.pageFling(b);
        return this;
    }

    public SignatureView needSignature(Boolean b) {
        this.needSignature = b;
        return this;
    }

    public SignatureView enableDoubleTap(Boolean b) {
        configurator.enableDoubletap(b);
        return this;
    }


    public SignatureView pageFitPolicy(FitPolicy pageFitPolicy) {
        configurator.pageFitPolicy(pageFitPolicy);
        return this;
    }

    public SignatureView setOnPageChangeListener() {
        configurator.onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                if (viewPager != null)
                    viewPager.setCurrentItem(page);
                if (pdf_view.isZooming()) {
                    pdf_view.resetZoomWithAnimation();
                }

            }
        });
        return this;
    }

    public void load() {
        configurator.load();
    }

    public void stopFling() {
        if (pdf_view != null) {
            pdf_view.stopFling();
            pdf_view.recycle();
        }
        if (penViewList != null) {
            for (int i = 0; i < penViewList.size(); i++) {
                penViewList.get(i).setSignatureView(null);
                penViewList.get(i).clear();
                penViewList.get(i).removeAllViews();
            }
            penViewList.clear();
        }
        resetConfig();
        if (savePdfAsync != null) {
            savePdfAsync.cancel(true);
        }
    }


    public void resetZoomWithAnimation() {
        if (pdf_view != null) {
            pdf_view.resetZoomWithAnimation();
        }
    }

    public SignatureView setSwipeEnabled(Boolean b) {
        pdf_view.setSwipeEnabled(b);
        return this;
    }

    @NonNull
    public int getCurrentPage() {
        return pdf_view.getCurrentPage();
    }


    public void setTransformBean(TransformBean bean) {
        this.bean = bean;
    }


    public void startSignature() {
        if (viewPager != null)
            viewPager.setVisibility(VISIBLE);
    }

}
