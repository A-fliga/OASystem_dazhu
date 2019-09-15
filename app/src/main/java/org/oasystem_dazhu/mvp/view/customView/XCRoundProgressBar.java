package org.oasystem_dazhu.mvp.view.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import org.oasystem_dazhu.R;


/**
 * Created by www on 2019/6/24.
 */

public class XCRoundProgressBar extends View {
    private static final int STROKE = 0;//空心
    private static final int FILL = 1;//实心
    private Paint mPaint;//画笔对象的引用
    private boolean mIsDisplayText;//是否显示中间百分比进度字符串
    private float mTextSize;//中间进度百分比字符串的字体
    private float mRoundWidth;//圆环的宽度
    private int mRoundColor;//圆环的颜色
    private int mRoundProgressColor;//圆环进度的颜色
    private int mInnerRoundColor;//圆环内部圆颜色
    private int mTextColor;//中间进度百分比字符串的颜色
    private int mMax;//最大进度
    private int mProgress;//当前进度
    private int mStyle;//进度条的风格：空心圆环或者实心圆环


    public XCRoundProgressBar(Context context) {
        this(context, null);
    }

    public XCRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCRoundProgressBar(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub

        mPaint = new Paint();
        //从attrs.xml中获取自定义属性和默认值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XCRoundProgressBar);
        mRoundColor = typedArray.getColor(R.styleable.XCRoundProgressBar_roundColor, Color.GREEN);
        mRoundProgressColor = typedArray.getColor(R.styleable.XCRoundProgressBar_roundProgressColor, Color.RED);
        mInnerRoundColor = typedArray.getColor(R.styleable.XCRoundProgressBar_innerRoundColor, Color.GRAY);
        mRoundWidth = typedArray.getDimension(R.styleable.XCRoundProgressBar_roundWidth, 5);
        mTextColor = typedArray.getColor(R.styleable.XCRoundProgressBar_textColor, Color.RED);
        mTextSize = typedArray.getDimension(R.styleable.XCRoundProgressBar_textSize, 15);
        mMax = typedArray.getInteger(R.styleable.XCRoundProgressBar_max, 100);
        mStyle = typedArray.getInt(R.styleable.XCRoundProgressBar_style, STROKE);
        mIsDisplayText = typedArray.getBoolean(R.styleable.XCRoundProgressBar_textIsDisplayable, true);
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //画最外层大圆环
        int centerX = getWidth() / 2;//获取中心点X坐标
        int centerY = getHeight() / 2;//获取中心点Y坐标
        int radius = (int) (centerX - mRoundWidth / 2);//圆环的半径
        mPaint.setColor(mRoundColor);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeWidth(mRoundWidth);//设置圆环宽度
        mPaint.setAntiAlias(true);//消除锯齿
        canvas.drawCircle(centerX, centerY, radius, mPaint);//绘制圆环

        //绘制圆环内部圆
        mPaint.setColor(mInnerRoundColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY, radius - mRoundWidth / 2, mPaint);


        //画进度
        mPaint.setStrokeWidth(mRoundWidth);//设置圆环宽度
        mPaint.setColor(mRoundProgressColor);//设置进度颜色
        RectF oval = new RectF(centerX - radius, centerX - radius, centerX
                + radius, centerX + radius);  //用于定义的圆弧的形状和大小的界限
        switch (mStyle) {
            case STROKE: {
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, -90, 360 * mProgress / mMax, false, mPaint); // 根据进度画圆弧
                break;
            }
            case FILL: {
                mPaint.setStyle(Paint.Style.FILL);
                if (mProgress != 0)
                    canvas.drawArc(oval, -90, 360 * mProgress / mMax, true, mPaint); // 根据进度画圆弧
                break;
            }
        }
        //画中间进度百分比字符串
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int) (((float) mProgress / (float) mMax) * 100);//计算百分比
        float textWidth = mPaint.measureText(percent + "%");//测量字体宽度，需要居中显示
        if (mIsDisplayText && mStyle == STROKE) {
            canvas.drawText(percent + "%", centerX - textWidth / 2, centerY + mTextSize / 3, mPaint);
        }
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public int getRoundColor() {
        return mRoundColor;
    }

    public void setRoundColor(int roundColor) {
        this.mRoundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return mRoundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.mRoundProgressColor = roundProgressColor;
    }

    public float getRoundWidth() {
        return mRoundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.mRoundWidth = roundWidth;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public synchronized int getMax() {
        return mMax;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max must more than 0");
        }
        this.mMax = max;
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @author caizhiming
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress must more than 0");
        }
        if (progress > mMax) {
            this.mProgress = progress;
        }
        if (progress <= mMax) {
            this.mProgress = progress;
            postInvalidate();
        }
    }

    public boolean isDisplayText() {
        return mIsDisplayText;
    }

    public void setDisplayText(boolean isDisplayText) {
        this.mIsDisplayText = isDisplayText;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int style) {
        this.mStyle = style;
    }

}
