package com.hexalitics.brushtimer.pieprogressbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PieProgressView extends View {

    /*private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_PROGRESS = 0;
    private static final int DEFAULT_START_ANGLE = -90;
*/

    private int mMax = 100;
    private int mProgress = 0;
    private int mStartAngle = -90;


    private int mViewSize;
    private int mCircleViewSize;

    private static final int DEFAULT_VIEW_SIZE = 96;

    private Paint mProgressPaint;
    private Paint mBackgroundPaint;
    private RectF mInnerRectF;





    public PieProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final Resources res = getResources();

        int backgroundColor = res.getColor(android.R.color.holo_orange_light);
        int progressColor = res.getColor(android.R.color.white);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);


        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);



        mInnerRectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mInnerRectF.set(0, 0, mViewSize, mViewSize);
        mInnerRectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);

        mInnerRectF.set(mViewSize/2- 30, mViewSize/2 -  30, mViewSize/2 + mCircleViewSize, mViewSize/2 + mCircleViewSize);


        canvas.drawArc(mInnerRectF, 0, 360, true, mBackgroundPaint);



        float sweepAngleCircle = 360 * mProgress / mMax;

        canvas.drawArc(mInnerRectF, mStartAngle, sweepAngleCircle, true, mProgressPaint);




        canvas.drawRect(0, 0, mViewSize, mViewSize, mBackgroundPaint);

        float sweepAngleRect = mViewSize * mProgress / mMax;

        sweepAngleRect = mViewSize - sweepAngleRect;

        canvas.drawRect(0, 0, sweepAngleRect, mViewSize, mProgressPaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width, height);
        mCircleViewSize = 20;
        setMeasuredDimension(width, height);
    }

    /**
     * Sets the current progress (must be between 0 and max).
     */
    public void setProgress(int progress) {
        if (progress > mMax || progress < 0) {
            throw new IllegalArgumentException(
                String.format("Progress (%d) must be between %d and %d", progress, 0, mMax));
        }
        mProgress = progress;

        invalidate();
    }


}