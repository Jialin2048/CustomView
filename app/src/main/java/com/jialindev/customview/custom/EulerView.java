package com.jialindev.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EulerView extends View {
    public EulerView(Context context) {
        super(context);
        init();
    }

    public EulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint solidLinePaint;
    private Paint textPaint;
    private Paint circleLinePaint;
    private Paint vectorLinePaint;
    private Paint filledCirclePaint;
    private Path sineWaveSamplesPath;

    private void init() {
        solidLinePaint = new Paint();
        solidLinePaint.setStyle(Paint.Style.STROKE);
        solidLinePaint.setColor(Color.WHITE);
        solidLinePaint.setStrokeWidth(5f);

        textPaint = new Paint();
        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.DEFAULT.BOLD));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40f);

        circleLinePaint = new Paint();
        circleLinePaint.setStrokeWidth(5f);
        circleLinePaint.setColor(Color.YELLOW);
        circleLinePaint.setStyle(Paint.Style.STROKE);
        circleLinePaint.setPathEffect(new DashPathEffect(new float[] {10f, 10f}, 0f));

        vectorLinePaint = new Paint();
        vectorLinePaint.setStyle(Paint.Style.STROKE);
        vectorLinePaint.setColor(Color.GREEN);
        vectorLinePaint.setStrokeWidth(5f);

        filledCirclePaint = new Paint();
        filledCirclePaint.setColor(Color.WHITE);
        filledCirclePaint.setStyle(Paint.Style.FILL);

        sineWaveSamplesPath = new Path();
    }


    private float mWidth = 0f;
    private float mHeight = 0f;
    private float mRadius = 0f;
    private float mAngle = 10f;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(mWidth / 2, mHeight / 4) - 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxises(canvas);
        drawText(canvas);
        drawCircle(canvas);
        drawVector(canvas);
        drawSineWave(canvas);
    }

    //线
    private void drawAxises(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(0, -mHeight / 2, 0, mHeight / 2, solidLinePaint);
        canvas.drawLine(-mWidth / 2, 0, mWidth / 2, 0, solidLinePaint);
        canvas.drawLine(-mWidth / 2, mHeight / 4, mWidth / 2, mHeight / 4, solidLinePaint);
        canvas.restore();
    }

    //文字
    private void drawText(Canvas canvas) {
        canvas.drawRect(70f, 70f, 450f, 150f, solidLinePaint);
        canvas.drawText("指数函数与旋转矢量", 80f, 125f, textPaint);
    }

    //圆
    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 4 * 3);
        canvas.drawCircle(0f, 0f, mRadius, circleLinePaint);
        canvas.restore();
    }

    //半径
    private void drawVector(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 4 * 3);
        canvas.rotate(-mAngle);
        canvas.drawLine(0f, 0f, mRadius, 0f, vectorLinePaint);
        canvas.restore();
        startRotating();
        drawProjections(canvas);
    }

    //开始旋转
    private void startRotating() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    mAngle += 5f;
                    invalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void drawProjections(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle((float) (mRadius * Math.cos(getRadiusCorner(mAngle))),0f, 10f, filledCirclePaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 4 * 3);
        canvas.drawCircle((float) (mRadius * Math.cos(getRadiusCorner(mAngle))),0f, 10f, filledCirclePaint);
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 4 * 3);
        float x = (float) (mRadius * Math.cos(getRadiusCorner(mAngle)));
        float y = (float) (mRadius * Math.sin(getRadiusCorner(mAngle)));
        canvas.translate(x, -y);
        canvas.drawLine(0, 0, 0f, y, solidLinePaint);
        canvas.drawLine(0, 0, 0f, -mHeight / 4 + y, circleLinePaint);
        canvas.restore();

    }

    private double getRadiusCorner(float angle) {
        return (double) angle / 180 * Math.PI;
    }

    private void drawSineWave(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        int samplesCount = 90;
        float dy = mHeight / 2 / samplesCount;
        sineWaveSamplesPath.reset();
        sineWaveSamplesPath.moveTo((float) (mRadius * Math.cos(getRadiusCorner(mAngle))), 0);
        for (int i = 0; i < samplesCount; i++) {
            float x = (float) (mRadius * Math.cos(i * -0.15 + getRadiusCorner(mAngle)));
            float y = -dy * i;
            sineWaveSamplesPath.quadTo(x, y, x, y);
        }
        canvas.drawPath(sineWaveSamplesPath, vectorLinePaint);
        canvas.drawTextOnPath("hello world", sineWaveSamplesPath, 1000f, -20f, textPaint);
    }


}
