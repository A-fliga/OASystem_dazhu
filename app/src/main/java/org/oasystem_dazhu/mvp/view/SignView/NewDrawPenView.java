package org.oasystem_dazhu.mvp.view.SignView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.lowagie.text.Document;

import org.greenrobot.eventbus.EventBus;
import org.oasystem_dazhu.mvp.model.bean.IsEraserMode;
import org.oasystem_dazhu.mvp.model.bean.IsSigningBean;
import org.oasystem_dazhu.mvp.model.bean.TransformBean;
import org.oasystem_dazhu.utils.ToastUtil;

import static org.oasystem_dazhu.mvp.view.SignView.IPenConfig.STROKE_TYPE_PEN;


/**
 * @author shiming
 * @version v1.0 create at 2017/8/24
 * @des DrawPenView实现手写关键类，目前只提供了，手绘的功能和清除画布，后期根据业务逻辑可以动态的设置方法
 */
public class NewDrawPenView extends View {
    private static final String TAG = "DrawPenView";
    private Paint mPaint, eraserPaint;//画笔
    private Canvas mCanvas;//画布
    private Bitmap mBitmap;
    private Context mContext;
    public static int mCanvasCode = STROKE_TYPE_PEN;
    private BasePenExtend mStokeBrushPen;
    private boolean mIsCanvasDraw, isEraserMode = false;
    private int mPenconfig;
    private float penWidth;
    private int penColor;
    private int bitMapWidth = 0, bitMapHeight = 0;
    private SignatureView signatureView;

    public NewDrawPenView(Context context) {
        super(context);
    }

    public NewDrawPenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewDrawPenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void initParameter(Context context, Document document, SignatureView signatureView) {
        mContext = context;
        PDFView pdfView = signatureView.getPDFView();
        if (document.getPageSize().getHeight() >= document.getPageSize().getWidth()) {
            this.bitMapHeight = pdfView.getHeight();
            this.bitMapWidth = (int) ((pdfView.getHeight() / document.getPageSize().getHeight()) * document.getPageSize().getWidth());
            if (bitMapWidth > pdfView.getWidth())
                bitMapWidth = pdfView.getWidth();
            if (bitMapWidth / document.getPageSize().getWidth() < bitMapHeight / document.getPageSize().getHeight()) {
                bitMapHeight = (int) (bitMapWidth / document.getPageSize().getWidth() * document.getPageSize().getHeight());
            }
        } else {
            this.bitMapWidth = pdfView.getWidth();
            this.bitMapHeight = (int) ((pdfView.getWidth() / document.getPageSize().getWidth()) * document.getPageSize().getHeight());
            if (this.bitMapHeight > pdfView.getHeight())
                bitMapHeight = pdfView.getHeight();
            if (bitMapHeight / document.getPageSize().getHeight() < bitMapWidth / document.getPageSize().getWidth()) {
                bitMapWidth = (int) (bitMapHeight / document.getPageSize().getHeight() * document.getPageSize().getWidth());
            }
        }
        this.signatureView = signatureView;
        mBitmap = Bitmap.createBitmap(pdfView.getWidth(), pdfView.getHeight(), Bitmap.Config.ARGB_8888);
        mStokeBrushPen = new SteelPen(context);
        penWidth = PenWidth.DEFAULT.getWidth();
        penColor = Color.BLACK;
        mPath = new Path();
        isEraserMode = false;
        initPaint();
        initCanvas(pdfView);
    }


    public void setPenWidth(float width) {
        this.penWidth = width;
        isEraserMode = false;
        initPaint();
    }

    public void setPenColor(int color) {
        this.penColor = color;
        isEraserMode = false;
        initPaint();
    }

    public void autoSave(float zoom, float xOffset, float yOffset) {

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(penColor);
        mPaint.setStrokeWidth(penWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//结束的笔画为圆心
        mPaint.setStrokeJoin(Paint.Join.ROUND);//连接处元
        mPaint.setAlpha(0xFF);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeMiter(1.0f);
        mStokeBrushPen.setPaint(mPaint);
    }


    public void initEraserMode(int color, float width) {
        isEraserMode = true;
        eraserPaint = new Paint();
        eraserPaint.setAlpha(0);
        //这个属性是设置paint为橡皮擦重中之重
        //这是重点
        //下面这句代码是橡皮擦设置的重点
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //上面这句代码是橡皮擦设置的重点（重要的事是不是一定要说三遍）
        eraserPaint.setAntiAlias(true);
        eraserPaint.setDither(true);
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeCap(Paint.Cap.ROUND);//结束的笔画为圆心
        eraserPaint.setStrokeJoin(Paint.Join.ROUND);//连接处元
        eraserPaint.setStrokeWidth(width);
        eraserPaint.setStrokeMiter(1.0f);
        eraserPaint.setColor(color);
    }

    private void initCanvas(PDFView pdfView) {
        mCanvas = new Canvas(mBitmap);
        if (bitMapWidth != 0 && bitMapHeight != 0) {
            if (bitMapHeight >= bitMapWidth) {
                mCanvas.clipRect((pdfView.getWidth() - bitMapWidth) / 2, 0, (pdfView.getWidth() - bitMapWidth) / 2 + bitMapWidth, bitMapHeight);
            } else
                mCanvas.clipRect(0, (pdfView.getHeight() - bitMapHeight) / 2, bitMapWidth, (pdfView.getHeight() - bitMapHeight) / 2 + bitMapHeight);
        } else {
            ToastUtil.l("bitMap不合法");
        }
        //设置画布的颜色的问题
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        switch (mCanvasCode) {
            case STROKE_TYPE_PEN:
                if (mStokeBrushPen.draw(canvas)) {
                    mIsCanvasDraw = true;
                }
                break;
            case IPenConfig.STROKE_TYPE_ERASER:
                resetCanvas();
                break;
            default:
                break;
        }
        super.onDraw(canvas);
    }


    public void setCanvasCode(int canvasCode) {
        mCanvasCode = canvasCode;
        switch (mCanvasCode) {
            case STROKE_TYPE_PEN:
                mStokeBrushPen = new SteelPen(mContext);
                break;
        }
        //设置
        if (mStokeBrushPen.isNull()) {
            mStokeBrushPen.setPaint(mPaint);
        }
//        invalidate();
    }

    /**
     * event.getAction() //获取触控动作比如ACTION_DOWN
     * event.getPointerCount(); //获取触控点的数量，比如2则可能是两个手指同时按压屏幕
     * event.getPointerId(nID); //对于每个触控的点的细节，我们可以通过一个循环执行getPointerId方法获取索引
     * event.getX(nID); //获取第nID个触控点的x位置,记录的第一个点为getX，getY
     * event.getY(nID); //获取第nID个点触控的y位置
     * event.getPressure(nID); //LCD可以感应出用户的手指压力，当然具体的级别由驱动和物理硬件决定的
     * event.getDownTime() //按下开始时间
     * event.getEventTime() // 事件结束时间
     * event.getEventTime()-event.getDownTime()); //总共按下时花费时间
     *
     * @param event
     * @return
     */
    MotionEvent event2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (createMotionElement(event).tooltype == MotionEvent.TOOL_TYPE_STYLUS) {
            return stylusMode(event);
        }
        if (createMotionElement(event).tooltype == MotionEvent.TOOL_TYPE_ERASER) {
            return eraserMode(event);
        } else {
            return isFingerMode(event);
        }

    }

    private boolean eraserMode(MotionEvent event) {
        if (signatureView.getCanSigning()) {
            //TODO 里要做橡皮擦事件
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isEraserMode) {
                        IsEraserMode mode = new IsEraserMode();
                        mode.eraserMode = true;
                        EventBus.getDefault().post(mode);
                        isEraserMode = true;
                    }
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
        }
        return true;
    }

    private boolean stylusMode(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (signatureView.getCanSigning() && !isEraserMode && signatureView.getPDFView().getZoom() <= 1f) {
            mStokeBrushPen.onTouchEvent(event, mCanvas);
        }
        //event会被下一次事件重用，这里必须生成新的，否则会有问题
        //getActionMask:触摸的动作,按下，抬起，滑动，多点按下，多点抬起
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mGetTimeListner != null)
                    mGetTimeListner.stopTime();
                if (isEraserMode) {
                    touch_start(x, y);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mGetTimeListner != null)
                    mGetTimeListner.stopTime();
                if (isEraserMode) {
                    touch_move(x, y);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //只需要发送一次事件
                if (!signatureView.getCanSigning()) {
                    IsSigningBean bean = new IsSigningBean();
                    bean.isSigning = true;
                    EventBus.getDefault().post(bean);
                } else if (signatureView.getPDFView().getZoom() > 1f) {
                    IsSigningBean bean = new IsSigningBean();
                    bean.isSigning = true;
                    EventBus.getDefault().post(bean);
                }
                if (isEraserMode) {
                    touch_up();
                }
                invalidate();
                break;
            default:
                break;
        }
        return true;
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


    /**
     * event.getPressure(); //LCD可以感应出用户的手指压力，当然具体的级别由驱动和物理硬件决定的,我的手机上为1
     *
     * @param motionEvent
     * @return
     */
    public MotionElement createMotionElement(MotionEvent motionEvent) {
        MotionElement motionElement = new MotionElement(motionEvent.getX(), motionEvent.getY(),
                motionEvent.getPressure(), motionEvent.getToolType(0));
        return motionElement;
    }


    private float mX, mY;
    private Path mPath;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        //如果是“橡皮擦”模式就用mEraserPaint画笔进行绘制
        if (isEraserMode) {
            mCanvas.drawPath(mPath, eraserPaint);
        }
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
        mX = x;
        mY = y;
        if (isEraserMode)
            mCanvas.drawPath(mPath, eraserPaint);
    }


    private void touch_up() {
        mPath.lineTo(mX, mY);
        if (isEraserMode)
            mCanvas.drawPath(mPath, eraserPaint);
    }

    /**
     * @return 判断是否有绘制内容在画布上
     */
    public boolean getHasDraw() {
        return mIsCanvasDraw;
    }

    /**
     * 清除画布，记得清除点的集合
     */
    public void resetCanvas() {

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(null);
        signatureView.setCanSigning(false);
        mIsCanvasDraw = false;
        mStokeBrushPen.clear();
        //这里处理的不太好 需要优化
        mCanvasCode = mPenconfig;
        setCanvasCode(STROKE_TYPE_PEN);
    }

    public TimeListener mGetTimeListner;

    public void setGetTimeListener(TimeListener l) {
        mGetTimeListner = l;
    }

    public Bitmap getBitmap() {
        if (bitMapHeight >= bitMapWidth)
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - bitMapWidth) / 2, (mBitmap.getHeight() - bitMapHeight) / 2, bitMapWidth, bitMapHeight);
        else {
            return Bitmap.createBitmap(mBitmap, (mBitmap.getWidth() - bitMapWidth) / 2, (mBitmap.getHeight() - bitMapHeight) / 2, bitMapWidth, bitMapHeight);
        }
    }

    public void setPenconfig(int penconfig) {
        mPenconfig = penconfig;

    }

    public interface TimeListener {
        void getTime(long l);

        void stopTime();

    }

    private int mBackColor = Color.TRANSPARENT;

    /**
     * 逐行扫描 清楚边界空白。功能是生成一张bitmap位于正中间，不是位于顶部，此关键的是我们画布需要
     * 成透明色才能生效
     *
     * @param blank 边距留多少个像素
     * @return tks github E-signature
     */
    public Bitmap clearBlank(int blank) {
        if (mBitmap != null) {
            int HEIGHT = mBitmap.getHeight();//1794
            int WIDTH = mBitmap.getWidth();//1080
            int top = 0, left = 0, right = 0, bottom = 0;
            int[] pixs = new int[WIDTH];
            boolean isStop;
            for (int y = 0; y < HEIGHT; y++) {
                mBitmap.getPixels(pixs, 0, WIDTH, 0, y, WIDTH, 1);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != mBackColor) {

                        top = y;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            for (int y = HEIGHT - 1; y >= 0; y--) {
                mBitmap.getPixels(pixs, 0, WIDTH, 0, y, WIDTH, 1);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != mBackColor) {
                        bottom = y;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            pixs = new int[HEIGHT];
            for (int x = 0; x < WIDTH; x++) {
                mBitmap.getPixels(pixs, 0, 1, x, 0, 1, HEIGHT);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != mBackColor) {
                        left = x;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            for (int x = WIDTH - 1; x > 0; x--) {
                mBitmap.getPixels(pixs, 0, 1, x, 0, 1, HEIGHT);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != mBackColor) {
                        right = x;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            if (blank < 0) {
                blank = 0;
            }
            left = left - blank > 0 ? left - blank : 0;
            top = top - blank > 0 ? top - blank : 0;
            right = right + blank > WIDTH - 1 ? WIDTH - 1 : right + blank;
            bottom = bottom + blank > HEIGHT - 1 ? HEIGHT - 1 : bottom + blank;
            return Bitmap.createBitmap(mBitmap, left, top, right - left, bottom - top);
        } else {
            return null;
        }
    }


}
