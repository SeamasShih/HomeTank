package com.honhai.foxconn.hometank.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpTankClient {

    private final String TAG = "TcpTankClient";

    private static TcpTankClient tcpTankClient;
    private static TcpReceiveListener tcpReceiveListener;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private TcpTankClient(String ip, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                startReceiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static TcpTankClient getClient(Object object) {
        if (tcpTankClient == null) {
            tcpTankClient = new TcpTankClient(TcpSerCliConstant.SERVER_IP, TcpSerCliConstant.PORT);
        }
        tcpReceiveListener = (TcpReceiveListener) object;
        return tcpTankClient;
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                dataOutputStream.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startReceiveMessage() {
        new Thread(() -> {
            String msg = "";
            while (!msg.equals(TcpSerCliConstant.C_END)) {
                try {
                    msg = dataInputStream.readUTF();
                    tcpReceiveListener.onTcpMessageReceive(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            stopClient();
        }).start();
    }

    private void stopClient() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("tcp stop");
    }
}
