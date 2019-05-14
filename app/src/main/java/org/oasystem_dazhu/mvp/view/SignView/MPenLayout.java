package org.oasystem_dazhu.mvp.view.SignView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ebensz.eink.api.PennableLayout;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.mvp.model.bean.IsSigningBean;
import org.oasystem_dazhu.mvp.model.bean.TransformBean;

/**
 * Created by www on 2019/5/7.
 */

public class MPenLayout extends PennableLayout {
    private SignatureView signatureView;
    private int bitMapHeight;
    private int bitMapWidth;

    public MPenLayout(Context context, int bitMapWidth, int bitMapHeight) {
        super(context);
        this.bitMapHeight = bitMapHeight;
        this.bitMapWidth = bitMapWidth;
    }

    public MPenLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MPenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSignatureView(SignatureView signatureView) {
        this.signatureView = signatureView;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (createMotionElement(event).toolType == MotionEvent.TOOL_TYPE_STYLUS) {
            return stylusMode(event);
        }
        if (createMotionElement(event).toolType == MotionEvent.TOOL_TYPE_FINGER) {
            return isFingerMode(event);
        }
        return super.onTouchEvent(event);
    }

    private boolean isFingerMode(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            TransformBean bean = new TransformBean();
            bean.setZoom(signatureView.getPDFView().getZoom());
            bean.setCurrentPage(signatureView.getCurrentPage());
            bean.setCurrentXOffset(signatureView.getPDFView().getCurrentXOffset());
            bean.setCurrentYOffset(signatureView.getPDFView().getCurrentYOffset());
            signatureView.setTransformBean(bean);
        }
        return false;
    }

    @Override
    public Bitmap export() {
        Bitmap mBitmap = super.export();
        if (bitMapHeight >= bitMapWidth) {
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - bitMapWidth) / 2, (mBitmap.getHeight() - bitMapHeight) / 2, bitMapWidth, bitMapHeight);
        } else {
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - bitMapWidth) / 2, (mBitmap.getHeight() - bitMapHeight) / 2, bitMapWidth, bitMapHeight);
        }
    }

    private boolean stylusMode(MotionEvent event) {
        if (signatureView.getCanSigning()) {
            return super.onTouchEvent(event);
        }
        //getActionMask:触摸的动作,按下，抬起，滑动，多点按下，多点抬起
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //只需要发送一次事件
                if (!signatureView.getCanSigning()) {
                    IsSigningBean bean = new IsSigningBean();
                    bean.isSigning = true;
                    EventBus.getDefault().post(bean);
                }
//                else if (signatureView.getPDFView().getZoom() > 1f) {
//                    IsSigningBean bean = new IsSigningBean();
//                    bean.isSigning = true;
//                    EventBus.getDefault().post(bean);
//                }
                break;
            default:
                break;
        }
        return true;
    }


    public MotionElement createMotionElement(MotionEvent motionEvent) {
        MotionElement motionElement = new MotionElement(motionEvent.getX(), motionEvent.getY(),
                motionEvent.getPressure(), motionEvent.getToolType(0));
        return motionElement;
    }

}
