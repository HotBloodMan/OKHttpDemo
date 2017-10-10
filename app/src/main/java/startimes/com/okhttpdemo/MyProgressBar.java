package startimes.com.okhttpdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ${JT.L} on 2017/10/10.
 */

public class MyProgressBar extends ImageView {
    /**
     * View默认的宽
     */
    private static final int DEFAULTWIDTH = 200;
    /**
     * View默认的高度
     */
    private static final int DEFAULTHEIGHT = 200;
    private Paint sweepPaint;
    private int padding = 20;
    /**
     * 内层实体圆的颜色
     */
    private int sweepColor = getResources().getColor(R.color.colorAccent);
    /**
     * 开始绘制的角度
     */
    private int startAngle = -90;
    /**
     * 已经绘制的角度
     */
    private float sweepAngle = 0;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAlpha(0.8f);
        sweepPaint = new Paint();
        sweepPaint.setColor(sweepColor);
        sweepPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽的测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取宽的测量值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //获取高的测量模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取高的测量值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //如果宽为wrap_content,则给定一个默认值
                widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULTWIDTH, getResources().getDisplayMetrics());
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULTHEIGHT, getResources().getDisplayMetrics());
                break;
        }
        widthSize = heightSize = Math.min(widthSize, heightSize);
        //设置测量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (sweepAngle != 360 && sweepAngle != 0) {
            RectF oval = new RectF(padding, padding, getWidth() - padding, getHeight() - padding);
            Log.d("google_lenve_fb", "onDraw: " + sweepAngle);
            canvas.drawArc(oval, startAngle, sweepAngle, true, sweepPaint);
        }
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        if (Build.VERSION.SDK_INT > 15) {
            postInvalidateOnAnimation();
        } else {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}