package com.feup.lpoo.network;

public interface ServerInterface {
    public void accWagon(int id, float acc);
    public void jumpWagon(int id);
    public int join(ClientCallbackInterface c);
    public String getScore(int id);
}
