package com.jialindev.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.jialindev.customview.bean.Bubble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleView extends SurfaceView implements SurfaceHolder.Callback{
    private Canvas canvas = null;
    private List<Integer> colors ;
    private Paint paint ;
    private List<Bubble> bubbleList = new ArrayList<>();

    public float seg = 30f, dev = 20f;
    public boolean aLive = false;


    public BubbleView(Context context) {
        super(context);
        initView();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView() {
        getHolder().addCallback(this);
        colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);
        colors.add(Color.MAGENTA);

        paint = new Paint();
        paint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        float x = event.getX();
        float y = event.getY();
        int color = colors.get(new Random().nextInt(colors.size()));
        Bubble bubble = new Bubble(x, y, 1f, color);
        bubbleList.add(bubble);
        if (bubbleList.size() > 30) {
            bubbleList.remove(0);
        }
        return super.onTouchEvent(event);
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (aLive) {
                    if (getHolder().getSurface().isValid()) {
                        try {
                            canvas = getHolder().lockCanvas();
                            if (canvas != null) {
                                canvas.drawColor(Color.BLACK);
                                if (paint != null) {
                                    paint.setPathEffect(new DiscretePathEffect(seg, dev));
                                }
                                Bubble bubble;
                                List<Bubble> copyList = bubbleList;
                                for (int i = 0; i < copyList.size(); i++) {
                                    bubble = copyList.get(i);
                                    if (bubble.getRadius() < 3000) {
                                        paint.setColor(bubble.getColor());
                                        canvas.drawCircle(bubble.getX(), bubble.getY(), bubble.getRadius(), paint);
                                        bubble.setRadius(bubble.getRadius() + 10f);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        } finally {
                            if (getHolder().getSurface().isValid()) {
                                getHolder().unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        aLive = true;
        if (holder != null) {
            if (holder.getSurface().isValid()) {
                init();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        aLive = false;
    }
}

