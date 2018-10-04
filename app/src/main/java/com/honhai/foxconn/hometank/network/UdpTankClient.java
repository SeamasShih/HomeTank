package com.honhai.foxconn.hometank.network;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpTankClient {
    private final String TAG = "UdpTankClient";

    private static UdpTankClient udpTankClient;
    private static UdpReceiveListener udpReceiveListener;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private UdpTankClient(String ip, int port) {
        this.port = port;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
            startReceiveMessage();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static UdpTankClient getClient(Object object) {
        if (udpTankClient == null) {
            udpTankClient = new UdpTankClient(UdpSerCliConstant.SERVER_IP, UdpSerCliConstant.PORT);
        }
        udpReceiveListener = (UdpReceiveListener) object;
        return udpTankClient;
    }

    private void startReceiveMessage() {
        byte[] receiveBuffer = new byte[512];

        new Thread(() -> {
            while (true) {
                String returnString;
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                returnString = new String(packet.getData(), 0, packet.getLength());
                udpReceiveListener.onUdpMessageReceive(returnString);

                if (returnString.equals("end")) {
                    break;
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                byte[] sendBuffer = message.toLowerCase().getBytes();

                DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (message.equals("end")) {
                closeSocket();
            }
        }).start();
    }

    private void closeSocket() {
        socket.close();
    }
}