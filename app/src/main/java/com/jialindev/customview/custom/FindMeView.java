package com.jialindev.customview.custom;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.jialindev.customview.R;

import java.util.Random;

public class FindMeView extends View {
    public FindMeView(Context context) {
        super(context);
        init();
    }

    public FindMeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FindMeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FindMeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Bitmap faceBitmap = null;
    private float faceX, faceY;
    private Path path;
    private Paint paint;

    private void init() {
        faceBitmap = getBitmapFromVectorDrawable(getContext(), R.drawable.ic_baseline_face_24);
        path = new Path();
        paint = new Paint();
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(300, 300,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void createFaceLocation() {
        //图片大小设定300,300
        float limitX = (float) (getWidth() - 300);
        float limitY = (float) (getHeight() - 300);
        faceX = (float) new Random().nextInt(getWidth());
        faceY = (float) new Random().nextInt(getHeight());
        boolean bX = true;
        boolean bY = true;
        // 防止坐标溢出屏幕
        while (bX) {
            if (faceX > limitX || faceX < 300) {
                faceX = (float) new Random().nextInt(getWidth());
            } else {
                bX = false;
            }
        }

        while (bY) {
            if (faceY > limitY || faceY < 300) {
                faceY = (float) new Random().nextInt(getHeight());
            } else {
                bY = false;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (faceBitmap != null) {
            canvas.drawBitmap(faceBitmap, faceX, faceY, null);
            canvas.drawPath(path, paint);
        } else {
            Toast.makeText(getContext(), "empty faceBitmap", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                createFaceLocation();
                initPath(event);
                break;
            case MotionEvent.ACTION_MOVE:
                initPath(event);
                getResult(event);
                break;
            case MotionEvent.ACTION_UP:
                path.reset();
                break;
        }
        invalidate();
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void initPath(MotionEvent event) {
        path.reset();
        path.addRect(0f, 0f, (float) getWidth(), (float) getHeight(), Path.Direction.CW);
        path.addCircle(event.getX(), event.getY(), 160f, Path.Direction.CCW);
    }

    private void getResult(MotionEvent event) {
        if (Math.abs(event.getX() - (faceX + 150f)) < 9 && Math.abs(event.getY() - (faceY + 150f)) < 9) {
            Toast.makeText(getContext(), "Oh you got me", Toast.LENGTH_SHORT).show();
            Vibrator vb = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
            if (vb.hasVibrator()) {
                vb.vibrate(1000);
                vb.cancel();
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    path.reset();
                }
            }
        }
    }
}
