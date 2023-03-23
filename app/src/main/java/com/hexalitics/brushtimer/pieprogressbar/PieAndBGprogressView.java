package com.hexalitics.brushtimer.pieprogressbar;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;

import com.hexalitics.brushtimer.pieprogressbar.R;

public class PieAndBGprogressView extends View {
    private static final String TAG =PieAndBGprogressView.class.getSimpleName();
    private int mMax = 30;
    private int mProgress = 0;
    private int mStartAngle = 360;
    private int mViewSize;
    private int mCircleViewSize;
    private static final int DEFAULT_VIEW_SIZE = 96;
    private Paint mProgressPaintCircle;
    private Paint mProgressPaintRect;
    private Paint mBackgroundPaintCircle;
    private Paint textPaint;

    int mColorRed;

    private RectF mCircleRectF;

    public PieAndBGprogressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final Resources res = getResources();
        mColorRed = res.getColor(R.color.bg_red);

        int mColorWhite = res.getColor(android.R.color.white);

        mBackgroundPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaintCircle.setColor(mColorRed);
        mBackgroundPaintCircle.setStyle(Paint.Style.FILL);

        mProgressPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaintCircle.setColor(mColorWhite);
        mProgressPaintCircle.setStyle(Paint.Style.FILL);


        mProgressPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaintRect.setColor(mColorWhite);
        mProgressPaintRect.setStyle(Paint.Style.FILL);



        textPaint = new Paint();
        textPaint.setColor(mColorRed);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(48);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);


        mCircleRectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        float sweepAngle = mViewSize * mProgress / mMax;
        sweepAngle = mViewSize - sweepAngle;
        //canvas.drawRect(0, 0, sweepAngle, mViewSize, mProgressPaintRect);
        /*if(mProgress>93){
            canvas.drawText(String.valueOf(mProgress), sweepAngle+30, mViewSize-30, textPaint);
            Log.d(TAG,String.format("sweepAngle  : %f and View : %d progress %d",sweepAngle+30,mViewSize-30,mProgress));
        }else {
            canvas.drawText(String.valueOf(mProgress), sweepAngle-30, mViewSize-30, textPaint);
            Log.d(TAG,String.format("sweepAngle  : %f and View : %d progress %d",sweepAngle-30,mViewSize-30,mProgress));
        }*/
        mCircleRectF.set(mViewSize/2- mCircleViewSize/2, mViewSize/2 -  mCircleViewSize/2,
                mViewSize/2 + mCircleViewSize, mViewSize/2 + mCircleViewSize);


        canvas.drawArc(mCircleRectF, 0, 360, true, mBackgroundPaintCircle);

        float sweepAngleCircle = 360 * mProgress / mMax;

        canvas.drawArc(mCircleRectF, mStartAngle, sweepAngleCircle, true, mProgressPaintCircle);

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width, height);
        mCircleViewSize = 100;
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

    public void animateProgress() {
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, 30f);
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(valuesHolder);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                mProgress = value.intValue();
                invalidate();
            }
        });
        animator.start();
    }


}