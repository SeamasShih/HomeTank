package com.honhai.foxconn.hometank;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.honhai.foxconn.hometank.gameplay.GameData;
import com.honhai.foxconn.hometank.views.keys.FireKey;

public class GameActivity extends AppCompatActivity {

    private GameData gameData = GameData.getInstance();
    private View up,down, left, right , raise , lower  , gunLeft , gunRight;
    private TextView textView;
    private FireKey fire;
    private boolean goUp = false;
    private boolean goDown = false;
    private boolean goLeft = false;
    private boolean goRight = false;
    private boolean goGunRight = false;
    private boolean goGunLeft = false;
    private int bulletAmount = 4;
    private ValueAnimator bulletCD;
    private ValueAnimator bulletAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViews();
        setListener();
        gameData.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.boom));
        setAnimation();
        textView.setText(String.valueOf(bulletAmount));
    }

    private void setAnimation() {
        bulletCD = ValueAnimator.ofFloat(0,100);
        bulletCD.setInterpolator(new LinearInterpolator());
        bulletCD.setDuration(gameData.getMySpeed()*150);
        bulletCD.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            fire.setCD(value);
        });
        bulletAdd = ValueAnimator.ofFloat(0,100);
        bulletAdd.setInterpolator(new LinearInterpolator());
        bulletAdd.setDuration(gameData.getMySpeed()*400);
        bulletAdd.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            fire.setAddTime(value);
        });
        bulletAdd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bulletAmount++;
                textView.setText(String.valueOf(bulletAmount));
                if (bulletAmount < 4)
                    bulletAdd.start();
            }
        });
    }


    private void setListener() {
        setUpListener();
        setDownListener();
        setRightListener();
        setLeftListener();
        setGunRightListener();
        setGunLeftListener();
        setFireListener();
    }

    private void setFireListener() {
        fire.setOnClickListener(v -> {
            if (bulletAmount > 0 && !bulletCD.isRunning()) {
                gameData.addBullet(gameData.getMySelf());
                bulletAmount--;
                textView.setText(String.valueOf(bulletAmount));
                bulletCD.start();
                if (!bulletAdd.isRunning())
                    bulletAdd.start();
            }
        });
    }

    private void setGunLeftListener() {
        gunLeft.setOnTouchListener((v, event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goGunLeft = true;
                    new Thread(() -> {
                        while (goGunLeft) {
                            try {
                                gameData.gunLeft();
                                Thread.sleep(gameData.getMySpeed()*2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goGunLeft = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void setGunRightListener() {
        gunRight.setOnTouchListener((v, event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goGunRight = true;
                    new Thread(() -> {
                        while (goGunRight) {
                            try {
                                gameData.gunRight();
                                Thread.sleep(gameData.getMySpeed()*2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goGunRight = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void setLeftListener() {
        left.setOnTouchListener((v, event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goRight = true;
                    new Thread(() -> {
                        while (goRight) {
                            try {
                                gameData.littleStepBackRotationMyBox();
                                if (!gameData.checkCollision()) {
                                    gameData.littleBackStepRotationMySite();
                                }
                                else {
                                    gameData.littleStepRotationMyBox();
                                    goRight = false;
                                }
                                Thread.sleep(gameData.getMySpeed()*2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goRight = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void setRightListener() {
        right.setOnTouchListener((v, event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goLeft = true;
                    new Thread(() -> {
                        while (goLeft) {
                            try {
                                gameData.littleStepRotationMyBox();
                                if (!gameData.checkCollision()) {
                                    gameData.littleStepRotationMySite();
                                }
                                else {
                                    gameData.littleStepBackRotationMyBox();
                                    goLeft = false;
                                }
                                Thread.sleep(gameData.getMySpeed()*2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goLeft = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void setDownListener() {
        down.setOnTouchListener((v,event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goDown = true;
                    new Thread(() -> {
                        while (goDown) {
                            try {
                                gameData.littleStepBackMyBox();
                                if (!gameData.checkCollision()) {
                                    gameData.littleStepBackMySite();
                                }
                                else {
                                    gameData.littleStepMyBox();
                                    goUp = false;
                                }
                                Thread.sleep(gameData.getMySpeed());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goDown = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void setUpListener() {
        up.setOnTouchListener((v,event) ->{
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    goUp = true;
                    new Thread(() -> {
                        while (goUp) {
                            try {
                                gameData.littleStepMyBox();
                                if (!gameData.checkCollision()) {
                                    gameData.littleStepMySite();
                                }
                                else {
                                    gameData.littleStepBackMyBox();
                                    goUp = false;
                                }
                                Thread.sleep(gameData.getMySpeed());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    goUp = false;
                    break;
            }
            v.performClick();
            return true;
        });
    }

    private void findViews() {
        up = findViewById(R.id.upKey);
        down = findViewById(R.id.downKey);
        left = findViewById(R.id.leftKey);
        right = findViewById(R.id.rightKey);
        raise = findViewById(R.id.raiseKey);
        lower = findViewById(R.id.lowerKey);
        gunLeft = findViewById(R.id.turnLeftKey);
        gunRight = findViewById(R.id.turnRightKey);
        fire = findViewById(R.id.fireKey);
        textView = findViewById(R.id.amount);
    }
}
