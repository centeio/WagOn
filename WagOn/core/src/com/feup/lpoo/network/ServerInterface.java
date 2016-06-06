package com.feup.lpoo.network;

public interface ServerInterface {
    public void move(int id, float x, float y);
    public int join(ClientCallbackInterface c);
}
