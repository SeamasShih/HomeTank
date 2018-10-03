package com.honhai.foxconn.hometank.network;

public interface TcpReceiveListener {
    void onTcpMessageReceive(String message);
}
