package com.honhai.foxconn.hometank.views.plate;

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

    private final String TAG = "GameSurfaceView";
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private int mWidth , mHeight;
    private float zoomRate = 1f;
    private Paint fogPaint = new Paint();
    private Path fog;
    private GameData gameData = GameData.getInstance();
    private float deadX , deadY;

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
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        mWidth = getWidth();
        mHeight = getHeight();
        fog.reset();
        fog.addCircle(0,0,mHeight/2, Path.Direction.CCW);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = getWidth();
        mHeight = getHeight();
        fog.reset();
        fog.addCircle(0,0,mHeight/2, Path.Direction.CCW);
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

    private void achieveZoomRate(){
        if (!gameData.amIAlive()){
            zoomRate = .7f;
            return;
        }
        switch (gameData.getMyType()){
            case 0:
            case 1:
                zoomRate = .9f;
                break;
            case 2:
                zoomRate = 5f/9f;
                break;
        }
    }

    public void setZoomRateByType(int type){
        switch (type){
            case 0:
            case 1:
                zoomRate = .9f;
                break;
            case 2:
                zoomRate = 5f/9f;
                break;
        }
    }

    public void moveDeadXUp(){
        deadX--;
    }

    public void moveDeadXDown(){
        deadX++;
    }

    public void moveDeadYLeft(){
        deadY--;
    }

    public void moveDeadYRight(){
        deadY++;
    }

    public void setZoomRate(float zoomRate) {
        this.zoomRate = zoomRate;
    }

    private void drawSomething() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.translate(mWidth/2,mHeight/2);
            mCanvas.drawColor(Color.BLACK);

            mCanvas.save();
            achieveZoomRate();
            mCanvas.translate(-deadY,-deadX);
            mCanvas.scale(zoomRate,zoomRate);
            gameData.drawMap(mCanvas);
            gameData.drawTank(mCanvas);
            gameData.drawBullet(mCanvas);
            gameData.drawBoom(mCanvas);

            mCanvas.restore();
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
