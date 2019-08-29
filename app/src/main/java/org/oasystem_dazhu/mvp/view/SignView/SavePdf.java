package org.oasystem_dazhu.mvp.view.SignView;

import android.graphics.Bitmap;

import com.github.barteksc.pdfviewer.PDFView;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import org.oasystem_dazhu.utils.ProgressDialogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.oasystem_dazhu.constants.Constants.SIGN_RESULT;


/**
 * Created by www on 2019/1/17.
 */

public class SavePdf {
    private float mZoom;
    private float mCurrentXOffset;
    private float mCurrentYOffset;
    private String mPdfPath;
    private String mNewPath;
    private Map<Integer, Bitmap> mSignResultMap;
    private PDFView mPdfView;
    private Bitmap mBitmap = null;


    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public float getZoom() {
        return mZoom;
    }

    public void setZoom(float mZoom) {
        this.mZoom = mZoom;
    }

    public float getCurrentXOffset() {
        return mCurrentXOffset;
    }

    public void setCurrentXOffset(float mCurrentXOffset) {
        this.mCurrentXOffset = mCurrentXOffset;
    }

    public float getCurrentYOffset() {
        return mCurrentYOffset;
    }

    public void setCurrentYOffset(float mCurrentYOffset) {
        this.mCurrentYOffset = mCurrentYOffset;
    }

    float widthScale;
    float heightScale;

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }

    public void setPdfView(PDFView mPdfView) {
        this.mPdfView = mPdfView;
    }


    public void setSignatureBeanMap(Map<Integer, Bitmap> signatureList) {
        this.mSignResultMap = signatureList;
    }

    public void setPdfSourcePath(String pdfPath) {
        this.mPdfPath = pdfPath;
    }


    public String addImg() {
        PdfReader reader;
        ProgressDialogUtil.instance().startLoad("请勿关闭！");
        try {
            reader = new PdfReader(mPdfPath);
            /**
             * 解决Exception in thread "main" java.lang.IllegalArgumentException:
             * PdfReader not opened with owner password
             */

            java.lang.reflect.Field f = reader.getClass().getDeclaredField(
                    "encrypted");
            f.setAccessible(true);
            f.set(reader, false);

            File fileDir = new File(SIGN_RESULT);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            mNewPath = SIGN_RESULT + File.separator + "newPDF" + System.currentTimeMillis() + ".pdf";
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(mNewPath));
            // Create a stamper that will copy the document to a new file
            for (int page : mSignResultMap.keySet()) {
                com.lowagie.text.Rectangle rectangle = reader.getPageSize(page + 1);
                PdfContentByte over = stamp.getOverContent(page + 1);//////用于设置在第几页打印签名
                mBitmap = mSignResultMap.get(page);
                byte[] bytes = Bitmap2Bytes(mBitmap);
                Image img = Image.getInstance(bytes);//将要放到PDF的图片传过来，要设置为byte[]类型
                img.setAlignment(1);
                float imgZoom = mBitmap.getHeight() / rectangle.getHeight();
                float imgZoom2 = mBitmap.getWidth() / rectangle.getWidth();
                if (getZoom() > 1f) {
                    float xOffset;
                    if (getCurrentPage() == 0) {
                        // 乘以 (getZoom()-1)/getZoom() 这个系数至关重要！ 否则会出现偏移  也就是zoom越小 往左偏移越多，实际上就是减多了
                        xOffset = -getCurrentXOffset() / getZoom() - (mPdfView.getWidth() - mBitmap.getWidth()) / 2 * (getZoom() - 1) / getZoom();
                    } else {
                        xOffset = (-getCurrentXOffset() - (mPdfView.getWidth() * getZoom() * getCurrentPage())) / getZoom() - (mPdfView.getWidth() - mBitmap.getWidth()) / 2 * (getZoom() - 1) / getZoom();
                    }
                    img.scaleAbsolute(rectangle.getWidth() / getZoom(), rectangle.getHeight() / getZoom());
                    img.setAbsolutePosition(xOffset / imgZoom2, (mBitmap.getHeight() * getZoom() + getCurrentYOffset() - mBitmap.getHeight()) / getZoom() / imgZoom);
                } else {
                    img.scaleAbsolute(rectangle.getWidth(), rectangle.getHeight());
                    img.setAbsolutePosition(0, 0);

                }
                mBitmap.recycle();
                over.addImage(img);
            }
            stamp.close();
            reader.close();
            mPdfView = null;
            mSignResultMap.clear();
            return mNewPath;
        } catch (IOException | DocumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            ProgressDialogUtil.instance().stopLoad();
        }
        return "";
    }


    public String getmNewPath() {
        return mNewPath;
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
