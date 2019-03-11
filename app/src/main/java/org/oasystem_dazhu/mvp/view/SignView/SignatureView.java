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
    private NoAnimationViewPager viewPager;
    private PDFView pdf_view;
    private PDFView.Configurator configurator;
    private List<NewDrawPenView> penViewList;
    private LayoutInflater inflater;
    private Save_Pdf_Async savePdfAsync;
    private Boolean needSignature = false;
    private AtomicBoolean canSave = new AtomicBoolean(true);
    private Context context;
    private String sourcePath, newPath;
    private Boolean canSigning = false;
    private int defaultPage = 0;
    private NewDrawPenView penView;
    private File sourceFile;
    //    private float scale, tagScale, currentXOffset, tagXOffset, currentYOffset, tagYOffset;
//    private int currentPage = 0, tagPage = 0;
    private Boolean autoSpacing = false;
    private TransformBean bean;


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
//        ProgressDialogUtil.instance().startLoad();
        sourcePath = sourceFile.getAbsolutePath();
        canSave.set(true);
//        LogUtil.d("pianyi", "提交的路径" + sourceFile);
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
            ProgressDialogUtil.instance().stopLoad();
        }
    };

    private OnPageScrollListener pageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
////            if(page == 0)
////            viewPager.scrollTo((int) (-pdf_view.getCurrentXOffset() / pdf_view.getZoom()), 0);
//            LogUtil.d("pianyi", "  偏移" + (-pdf_view.getCurrentXOffset()) + "  " + pdf_view.getCurrentYOffset() + " " + pdf_view.getZoom());
//            if (pdf_view.getZoom() > 1f && canSave.get()) {
//                canSave.set(false);
////                LogUtil.d("pianyi", "签字文件的路径" + sourcePath);
//                addSignature2Pdf(sourcePath, true, new DataFinishListener() {
//                    @Override
//                    public void onFinished(String path) {
////                        LogUtil.d("pianyi", "签完字后的路径" + path);
//                        defaultPage = getCurrentPage();
//                        canSave.set(true);
//                        loadFile(new File(path), true);
//                    }
//
//                    @Override
//                    public void nothingChange() {
//                        LogUtil.d("pianyi", "nothingChange");
//                        canSave.set(true);
//                    }
//
//                    @Override
//                    public void onFailed() {
//                        canSave.set(true);
//                    }
//                });
//            }
        }
    };

    public void resetConfig() {
        defaultPage = 0;
//        scale = 0;
//        tagXOffset = 0;
//        tagYOffset = 0;
//        currentXOffset = 0f;
//        currentYOffset = 0f;
    }


    public void loadFile(File file) {
        loadFile(file, false);
    }

    public void loadFile(File file, Boolean auto) {
        this.autoSpacing = auto;
        fromFile(file)
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .enableDoubleTap(false)
                .pageFitPolicy(FitPolicy.BOTH)
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
            if (!autoSpacing)
                resetConfig();
            else {
                resetZoomWithAnimation();
            }
            ProgressDialogUtil.instance().stopLoad();
            Document document = null;
            if (needSignature) {
                PdfReader reader;
                try {
                    reader = new PdfReader(sourceFile.getAbsolutePath());
                    document = new Document(reader.getPageSize(1));
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                penViewList = new ArrayList<>();
                viewPager = findViewById(R.id.signature_viewpager);
                //pdf加载完成后 先准备对应页数的签字页面，放入viewpager中
                for (int i = 0; i < nbPages; i++) {
                    View v = inflater.inflate(R.layout.item_signature, null);
                    penView = v.findViewById(R.id.item_signature);
                    penView.initParameter(context, document, SignatureView.this);
                    penViewList.add(penView);
                    penView = null;
                }
                MPagerAdapter adapter = new MPagerAdapter(SignatureView.this);
                viewPager.setAdapter(adapter);
            }
        }
    };

    public Boolean getCanSigning() {
        return canSigning;
    }

    public void setCanSigning(Boolean canSigning) {
        this.canSigning = canSigning;
    }

    public SignatureView setDefaultPage(int page) {
        configurator.defaultPage(page);
        return this;
    }


    private static class MPagerAdapter extends PagerAdapter {
        private WeakReference<SignatureView> weakSignView;
        private List<NewDrawPenView> penViewList;

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
            NewDrawPenView penView = penViewList.get(position);
            ViewGroup viewGroup = (ViewGroup) penView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(penView);
            }
            container.addView(penView);
            return penView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(penViewList.get(position));
        }
    }

    public void setPenWidth(float width) {
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).setPenWidth(width);
        }
    }

    public void setPenColor(int penColor) {
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).setPenColor(penColor);
        }

    }

    public void initEraserMode(int color, float width) {
        for (int i = 0; i < penViewList.size(); i++) {
            penViewList.get(i).initEraserMode(color, width);
        }
    }

    public void clearCanvas(File file) {
        for (int i = 0; i < penViewList.size(); i++) {
            synchronized (SignatureView.class) {
                penViewList.get(i).resetCanvas();
            }
        }
        if (file != null)
            loadFile(file);
    }

    public void addSignature2Pdf(String path, Boolean auto, DataFinishListener listener) {
        //得到有签字痕迹的页面
        if (needSignature) {
            NewDrawPenView penView;
            List<NewDrawPenView> newDrawPenViewList = new ArrayList<>();
            List<Integer> signPageList = new ArrayList<>();
            for (int i = 0; i < penViewList.size(); i++) {
                penView = penViewList.get(i);
                if (penView.getHasDraw()) {
                    signPageList.add(i);
                    newDrawPenViewList.add(penView);
                }
            }
            if (signPageList.size() == 0) {
                if (!auto)
                    ToastUtil.l("没有新签字的页面");
                else {
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
            }
        } else {
            ToastUtil.l("没有选择签字功能");
        }
    }


    private void toSaveSignature(String path, TransformBean bean, DataFinishListener listener, List<NewDrawPenView> newDrawPenViewList, List<Integer> signPageList) {
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
        pdf_view.stopFling();
        resetConfig();
        if (savePdfAsync != null) {
            savePdfAsync.cancel(true);
        }
    }

    private void stopShow() {
        pdf_view.stopFling();
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

    @NonNull
    public int getPageCount() {
        return pdf_view.getPageCount();
    }


    public TransformBean getTransformBean() {
        return bean;
    }

    public void setTransformBean(TransformBean bean) {
        this.bean = bean;
    }

    public void startSignature() {
        if (viewPager != null)
            viewPager.setVisibility(VISIBLE);
//        isSigning = true;
    }

    public void closeSignature() {
        if (viewPager != null)
            viewPager.setVisibility(GONE);
//        isSigning = false;
    }
}
