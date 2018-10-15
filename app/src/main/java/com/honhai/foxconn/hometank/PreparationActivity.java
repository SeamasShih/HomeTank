package com.honhai.foxconn.hometank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.honhai.foxconn.hometank.gameplay.GameData;
import com.honhai.foxconn.hometank.map.MapData;
import com.honhai.foxconn.hometank.network.TcpReceiveListener;
import com.honhai.foxconn.hometank.network.TcpSerCliConstant;
import com.honhai.foxconn.hometank.network.TcpTankClient;
import com.honhai.foxconn.hometank.network.UdpReceiveListener;
import com.honhai.foxconn.hometank.network.UdpSerCliConstant;
import com.honhai.foxconn.hometank.network.UdpTankClient;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PreparationActivity extends AppCompatActivity implements UdpReceiveListener, TcpReceiveListener {

    private final String TAG = "PreparationActivity";
    private Button button;
    private View light , heavy , height;
    private TextView textView;
    private GameData gameData = GameData.getInstance();
    private UdpTankClient udpTankClient = UdpTankClient.getClient(this);
    private TcpTankClient tcpTankClient = TcpTankClient.getClient(this);
    private List<int[]> tempSiteList = new ArrayList<>();
    private int tankType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparation_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
        setTankClient();
        button.setClickable(false);
    }

    private void setListener() {
        button.setOnClickListener(v -> {
            udpTankClient.sendMessage(UdpSerCliConstant.C_READY + gameData.getMyOrder() + tankType);
            tcpTankClient.sendMessage(TcpSerCliConstant.C_PLAYER_SITE + gameData.getMyOrder());
            button.setClickable(false);
        });
        light.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            heavy.setBackground(null);
            height.setBackground(null);
            tankType = 0;
            button.setClickable(true);
        });
        heavy.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            light.setBackground(null);
            height.setBackground(null);
            tankType = 1;
            button.setClickable(true);
        });
        height.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            heavy.setBackground(null);
            light.setBackground(null);
            tankType = 2;
            button.setClickable(true);
        });
    }

    private void findViews() {
        button = findViewById(R.id.battle);
        textView = findViewById(R.id.textPlayerAmount);
        light = findViewById(R.id.lightTank);
        heavy = findViewById(R.id.heavyTank);
        height = findViewById(R.id.heightTank);
    }

    private void setTankClient() {
        udpTankClient.sendMessage(UdpSerCliConstant.C_REGISTER);
        tcpTankClient.sendMessage(TcpSerCliConstant.C_MAP);
    }

    @Override
    public void onBackPressed() {

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
            tempSiteList.forEach(ints -> gameData.initialPlayerSite(ints[0], ints[1], ints[2]));
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setClass(this, GameActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTcpMessageReceive(String message) {
        if (message.startsWith(TcpSerCliConstant.C_MAP)) {
            StringTokenizer tokenizer = new StringTokenizer(message, " ");
            tokenizer.nextToken();
            int w = Integer.valueOf(tokenizer.nextToken().substring(1));
            int h = Integer.valueOf(tokenizer.nextToken().substring(1));
            MapData[][] mapData = new MapData[w][h];

            int indexW = -1;
            int indexH = 0;
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();

                if (s.equals("l")) {
                    indexW++;
                    indexH = 0;
                } else {
                    mapData[indexW][indexH] = MapData.values()[Integer.valueOf(s)];
                    indexH++;
                }
            }

            gameData.setMapData(mapData);
        } else if (message.startsWith(TcpSerCliConstant.C_PLAYER_SITE)) {
            StringTokenizer tokenizer = new StringTokenizer(message, " ");
            tokenizer.nextToken();
            int order = -1;
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();

                if (s.equals("l")) {
                    order++;
                } else {
                    int x = Integer.valueOf(s);
                    int y = Integer.valueOf(tokenizer.nextToken());
                    tempSiteList.add(new int[]{order, x, y});
                }
            }
        }
    }
}