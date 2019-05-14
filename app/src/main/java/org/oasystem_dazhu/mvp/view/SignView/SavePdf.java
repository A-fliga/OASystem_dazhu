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
import java.util.List;

import static org.oasystem_dazhu.constants.Constants.SIGN_RESULT;


/**
 * Created by www on 2019/1/17.
 */

public class SavePdf {
    private List<MPenLayout> penViewList;
    private List<Integer> signatureList;
    private String pdfPath;
    private String newPath;
    private PDFView pdfView;
    private float currentXOffset;
    private float currentYOffset;
    private Bitmap bitmap = null;
    private float zoom;


    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getCurrentXOffset() {
        return currentXOffset;
    }

    public void setCurrentXOffset(float currentXOffset) {
        this.currentXOffset = currentXOffset;
    }

    public float getCurrentYOffset() {
        return currentYOffset;
    }

    public void setCurrentYOffset(float currentYOffset) {
        this.currentYOffset = currentYOffset;
    }

    float widthScale;
    float heightScale;

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }

    public void setPdfView(PDFView pdfView) {
        this.pdfView = pdfView;
    }

    public void setPenViewList(List<MPenLayout> penViewList) {
        this.penViewList = penViewList;
    }

    public void setSignatureList(List<Integer> signatureList) {
        this.signatureList = signatureList;
    }

    public void setPdfSourcePath(String pdfPath) {
        this.pdfPath = pdfPath;
    }


    public String addImg() {
        PdfReader reader;
        ProgressDialogUtil.instance().startLoad("请勿关闭！");
        try {
            reader = new PdfReader(pdfPath);
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
            newPath = SIGN_RESULT + File.separator + "newPDF" + System.currentTimeMillis() + ".pdf";
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(newPath));
            // Create a stamper that will copy the document to a new file
            for (int i = 0; i < signatureList.size(); i++) {
                com.lowagie.text.Rectangle rectangle = reader.getPageSize(signatureList.get(i) + 1);
                PdfContentByte over = stamp.getOverContent(signatureList.get(i) + 1);//////用于设置在第几页打印签名
                bitmap = penViewList.get(i).export();
                byte[] bytes = Bitmap2Bytes(bitmap);
                Image img = Image.getInstance(bytes);//将要放到PDF的图片传过来，要设置为byte[]类型
                img.setAlignment(1);
                float imgZoom = bitmap.getHeight() / rectangle.getHeight();
                float imgZoom2 = bitmap.getWidth() / rectangle.getWidth();

                if (getCurrentPage() == signatureList.get(i) && getZoom() > 1f) {
                    float xOffset;
                    if (getCurrentPage() == 0) {
                        // 乘以 (getZoom()-1)/getZoom() 这个系数至关重要！ 否则会出现偏移  也就是zoom越小 往左偏移越多，实际上就是减多了
                        xOffset = -getCurrentXOffset() / getZoom() - (pdfView.getWidth() - bitmap.getWidth()) / 2 * (getZoom() - 1) / getZoom();
                    } else {
                        xOffset = (-getCurrentXOffset() - (pdfView.getWidth() * getZoom() * getCurrentPage())) / getZoom() - (pdfView.getWidth() - bitmap.getWidth()) / 2 * (getZoom() - 1) / getZoom();
                    }
//                    LogUtil.d("pianyi", "我是第" + getCurrentPage() + "页，我来位移了");
//                    LogUtil.d("pianyi", "原pdf大小 x:" + rectangle.getWidth() + " y: " + rectangle.getHeight());
//                    LogUtil.d("pianyi", "当前偏移量" + getCurrentXOffset() + "   " + getCurrentYOffset() + "   " + getZoom());
//                    LogUtil.d("pianyi", "缩放的图片 x: " + rectangle.getWidth() / getZoom() + "  y: " + rectangle.getHeight() / getZoom());
//                    LogUtil.d("pianyi", "要偏移x: " + (xOffset / imgZoom2) + "  要偏移y: " + (bitmap.getHeight() * getZoom() + getCurrentYOffset() - bitmap.getHeight()) / getZoom() / imgZoom);
                    img.scaleAbsolute(rectangle.getWidth() / getZoom(), rectangle.getHeight() / getZoom());
                    img.setAbsolutePosition(xOffset / imgZoom2, (bitmap.getHeight() * getZoom() + getCurrentYOffset() - bitmap.getHeight()) / getZoom() / imgZoom);
                } else {
//                    LogUtil.d("pianyi", "我是第" + signatureList.get(i) + "页，我来复位了");
                    img.scaleAbsolute(rectangle.getWidth(), rectangle.getHeight());
                    img.setAbsolutePosition(0, 0);

                }
                bitmap.recycle();
                over.addImage(img);
            }
            stamp.close();
            reader.close();
            return newPath;
        } catch (IOException | DocumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            ProgressDialogUtil.instance().stopLoad();
        }
        return "";
    }


    public String getNewPath() {
        return newPath;
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
