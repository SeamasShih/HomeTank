package com.honhai.foxconn.hometank.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.honhai.foxconn.hometank.gameplay.GameData;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback , Runnable {

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private int mWidth , mHeight;
    private Paint fogPaint = new Paint();
    private Path fog;
    GameData gameData = GameData.getInstance();

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    private void initial() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        fogPaint.setColor(Color.DKGRAY);
        fogPaint.setAntiAlias(true);
        fog = new Path();
        fog.setFillType(Path.FillType.INVERSE_WINDING);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        fog.addCircle(0,0,getHeight()/2, Path.Direction.CCW);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            drawSomething();
        }
    }

    private void drawSomething() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.translate(mWidth/2,mHeight/2);
            mCanvas.drawColor(Color.WHITE);

            gameData.drawMap(mCanvas);
            gameData.drawMyself(mCanvas);
            gameData.drawBullet(mCanvas);

            mCanvas.drawPath(fog,fogPaint);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCanvas != null){
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

}
