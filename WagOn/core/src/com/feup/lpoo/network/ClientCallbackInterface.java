package com.feup.lpoo.network;

import com.badlogic.gdx.math.Vector2;

public interface ClientCallbackInterface {
    public void moveC(int id, float x, float y);
    public void syncBomb(long startime, Vector2 p);
    public void syncFruit(long startime, Vector2 p);

}
