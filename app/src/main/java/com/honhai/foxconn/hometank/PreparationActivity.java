package com.honhai.foxconn.hometank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.honhai.foxconn.hometank.gameplay.GameData;
import com.honhai.foxconn.hometank.network.UdpReceiveListener;
import com.honhai.foxconn.hometank.network.UdpSerCliConstant;
import com.honhai.foxconn.hometank.network.UdpTankClient;

public class PreparationActivity extends AppCompatActivity implements UdpReceiveListener {

    Button button;
    TextView textView;
    GameData gameData = GameData.getInstance();
    private UdpTankClient udpTankClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparation_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
        setTankClient();
    }

    private void setListener() {
        button.setOnClickListener(v -> {
            udpTankClient.sendMessage(UdpSerCliConstant.C_READY + gameData.getMyOrder() + 0);
            button.setClickable(false);
        });
    }

    private void findViews() {
        button = findViewById(R.id.battle);
        textView = findViewById(R.id.textPlayerAmount);
    }

    private void setTankClient() {
        udpTankClient = UdpTankClient.getClient(this);
        udpTankClient.sendMessage(UdpSerCliConstant.C_REGISTER);
    }

    @Override
    public void onUdpMessageReceive(String message) {
        if (message.startsWith(UdpSerCliConstant.C_ORDER)) {
            if (gameData.getMyOrder() == -1) {
                int order = Character.getNumericValue(message.charAt(1));
                gameData.setMyOrder(order);
            }

            String s = getResources().getString(R.string.player) + message.substring(2);
            runOnUiThread(() -> textView.setText(s));
        } else if (message.startsWith(UdpSerCliConstant.S_START_GAME)) {
            gameData.createPlayers(
                    Character.getNumericValue(message.charAt(UdpSerCliConstant.S_START_GAME.length())));

            Intent intent = new Intent();
            intent.setClass(this,GameActivity.class);
            startActivity(intent);
        }
    }
}