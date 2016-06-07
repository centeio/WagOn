package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.Falling;
import com.feup.lpoo.logic.Bomb;
import com.feup.lpoo.logic.FallingObj;
import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Fruit;
import com.feup.lpoo.logic.Wagon;

import com.feup.lpoo.network.ClientCallbackInterface;
import com.feup.lpoo.network.ServerInterface;

import java.net.InetAddress;

import lipermi.handler.CallHandler;
import lipermi.net.Client;
import lipermi.net.Server;

public class MultiPlayerState extends State implements ServerInterface, ClientCallbackInterface {
    private Texture sky;
    private Floor floor;
    private Wagon wagon1;
    private Wagon wagon2;
    private Fruit fruit;
    private Bomb bomb;
    private Sound jumpSound;
    private Sound bombSound;
    private Sound caughtSound;
    private int id;

    private Boolean _isServer;
    private ServerInterface _proxy;

    private ClientCallbackInterface _c1 = null;
    private ClientCallbackInterface _c2 = null;

    public MultiPlayerState(GameStateManager gsm, boolean isServer) {
        super(gsm);

        _isServer = isServer;
        sky = new Texture("sky.png");
        floor = new Floor(31);
        wagon1 = new Wagon("wagon.png");
        wagon2 = new Wagon("wagon2.png");


        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        bombSound = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
        caughtSound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));

        if (isServer) {
            id=0;
            System.out.println("id server " + id);
            fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
            bomb = new Bomb();
            try {
                CallHandler callHandler = new CallHandler();
                callHandler.registerGlobal(ServerInterface.class, this);
                Server server = new Server();
                int thePortIWantToBind = 4456;
                server.bind(thePortIWantToBind, callHandler);
                InetAddress IP = InetAddress.getLocalHost();
                System.err.println("Server ready at " + IP.getHostAddress() + " port " + 4456);
            } catch (Exception e) {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
        } else {
//            System.out.println("id player "+id);
            try {
                // get proxy for remote chat server
                CallHandler callHandler = new CallHandler();
                String remoteHost = "192.168.1.171";

                int portWasBinded = 4456;
                Client client = new Client(remoteHost, portWasBinded, callHandler);
                _proxy = (ServerInterface) client.getGlobal(ServerInterface.class);

                // create and expose remote listener
                callHandler.exportObject(ClientCallbackInterface.class, this);
                id = _proxy.join(this);

            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }

        }

    }

    private boolean _updated = false;

    @Override
    protected void handleInput() {

        if (_isServer)
            return;

        Wagon wagon;
        if(id==1){
            wagon = wagon1;
            System.out.println(id+" wagon1");
        }
        else {
            wagon = wagon2;
            System.out.println(id+" wagon2");
        }



        if (!(wagon.getState() instanceof Falling)) {
            _updated = false;

            if (Gdx.input.justTouched()) {
                jumpSound.play();
                wagon.jump();
                _updated = true;
            }

            if (WagOn.isMobile) {
                float acc = Gdx.input.getAccelerometerY();

                wagon.updateOnAccelerometer(2 * acc);
                _updated = true;
            } else
                wagon.updateOnAccelerometer(0);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        wagon1.update(dt);
        wagon2.update(dt);
        fruit.update(dt);
        bomb.update(dt);

        /*if (_updated) {
            if (id == 1) {
                //          if (_c != null)
                _proxy.move1(wagon1.getPosition().x, wagon1.getPosition().y);
//                    _c.movec1(wagon1.getPosition().x, wagon1.getPosition().y);
            } else if (id == 2) {
                //         if (_c != null)
                _proxy.move2(wagon2.getPosition().x, wagon2.getPosition().y);
//                    _c.movec2(wagon2.getPosition().x, wagon2.getPosition().y);

                //_proxy.move1(wagon2.getPosition().x, wagon2.getPosition().y);
            }

            _updated = false;
        } */

        if (!_isServer && _updated) {
            if(id==1)
                _proxy.move(id, wagon1.getPosition().x, wagon1.getPosition().y);
            else if (id==2)
                _proxy.move(id, wagon2.getPosition().x, wagon2.getPosition().y);
            _updated = false;
        }


        if (fruit.detectCollision(wagon1)) {
            caughtSound.play();
        }
        if (fruit.detectCollision(wagon2)) {
            caughtSound.play();
        }
        fruit.detectCollision(floor);

        if (bomb.detectCollision(wagon1)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon1.getScore()));
        }
        if (bomb.detectCollision(wagon2)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon2.getScore()));
        }
        bomb.detectCollision(floor);

        wagon1.detectFall(floor);
        wagon2.detectFall(floor);

        if (wagon1.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon2.getScore()));
        if (wagon2.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon1.getScore()));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
        sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
        sb.draw(wagon1.getTex(), wagon1.getPosition().x, wagon1.getPosition().y, wagon1.getWidth(), wagon1.getHeight());
        sb.draw(wagon2.getTex(), wagon2.getPosition().x, wagon2.getPosition().y, wagon2.getWidth(), wagon2.getHeight());
        floor.render(sb);

        String strScore = ((Integer) wagon1.getScore()).toString();
        font.draw(sb, strScore, WagOn.WIDTH - strScore.length() * State.font_size, WagOn.HEIGHT - 10);

        strScore = ((Integer) wagon2.getScore()).toString();
        font.draw(sb, strScore, 10, WagOn.HEIGHT - 10);

        sb.end();
    }

    @Override
    public void dispose() {
        fruit.getTex().dispose();
        bomb.getTex().dispose();
        wagon1.getTex().dispose();
        wagon2.getTex().dispose();
        sky.dispose();
    }

    @Override
    public void moveC(int id, float x, float y) {
        if(id==1)
            wagon1.getPosition().set(x, y);
        else if(id==2)
            wagon2.getPosition().set(x, y);

    }

    @Override
    public void move(int id, float x, float y) {
        if (id == 1){
            wagon1.getPosition().set(x, y);
            if(_c2!=null)
                _c2.moveC(id,x,y);
        }
        else if (id == 2) {
            wagon2.getPosition().set(x, y);
            if(_c1!=null)
                _c1.moveC(id, x, y);
        } else {
            System.err.println("unknown move id " + id);
        }
    }

    @Override
    public int join(ClientCallbackInterface c) {
        if (_c1 == null) {
            _c1 = c;

            return 1;
        } else if (_c2 == null) {
            _c2 = c;

            return 2;
        }

        System.out.println("too many clients");
        return -1;
    }
}
