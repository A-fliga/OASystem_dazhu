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
    private SignatureView mSignatureView;
    private int mBitMapHeight;
    private int mBitMapWidth;


    public MPenLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MPenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSignatureView(SignatureView signatureView) {
        this.mSignatureView = signatureView;
    }

    public void setHeightAndWidth(int bitMapHeight, int bitMapWidth) {
        this.mBitMapHeight = bitMapHeight;
        this.mBitMapWidth = bitMapWidth;
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                TransformBean bean = new TransformBean();
                bean.setZoom(mSignatureView.getPDFView().getZoom());
                bean.setCurrentPage(mSignatureView.getCurrentPage());
                bean.setCurrentXOffset(mSignatureView.getPDFView().getCurrentXOffset());
                bean.setCurrentYOffset(mSignatureView.getPDFView().getCurrentYOffset());
                mSignatureView.setTransformBean(bean);
                break;

        }
        return false;
    }

    @Override
    public Bitmap export() {
        Bitmap mBitmap = super.export();
        if (mBitMapHeight >= mBitMapWidth) {
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - mBitMapWidth) / 2, (mBitmap.getHeight() - mBitMapHeight) / 2, mBitMapWidth, mBitMapHeight);
        } else {
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - mBitMapWidth) / 2, (mBitmap.getHeight() - mBitMapHeight) / 2, mBitMapWidth, mBitMapHeight);
        }
    }

    private boolean stylusMode(MotionEvent event) {
        if (mSignatureView.getCanSigning()) {
            return super.onTouchEvent(event);
        }
        //getActionMask:触摸的动作,按下，抬起，滑动，多点按下，多点抬起
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //只需要发送一次事件
                if (!mSignatureView.getCanSigning()) {
                    IsSigningBean bean = new IsSigningBean();
                    bean.isSigning = true;
                    EventBus.getDefault().post(bean);
                }
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
