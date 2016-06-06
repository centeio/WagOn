package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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

public class MultiPlayerState  extends State implements ServerInterface, ClientCallbackInterface {
    private Texture sky;
    private Floor floor;
    private Wagon wagon1;
    private Wagon wagon2;
    private Fruit fruit;
    private Bomb bomb;
    private Sound jumpSound;
    private Sound bombSound;
    private Sound caughtSound;

    private Boolean _isServer;
    private ServerInterface _proxy;
    private ClientCallbackInterface _c = null;

    public MultiPlayerState(GameStateManager gsm, boolean isServer) {
        super(gsm);

        _isServer = isServer;
        sky = new Texture("sky.png");
        floor = new Floor(31);
        wagon1 = new Wagon("wagon.png");
        wagon2 = new Wagon("wagon2.png");

        fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
        bomb = new Bomb();
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        bombSound = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
        caughtSound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));

        if (isServer){
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
            try {
                // get proxy for remote chat server
                CallHandler callHandler = new CallHandler();
                String remoteHost = "192.168.1.171";
                int portWasBinded = 4456;
                Client client = new Client(remoteHost, portWasBinded, callHandler);
                _proxy = (ServerInterface)client.getGlobal(ServerInterface.class);

                // create and expose remote listener
                callHandler.exportObject(ClientCallbackInterface.class, this);
                _proxy.join(this);

            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    private boolean _updated = false;

    @Override
    protected void handleInput() {

        Wagon wagon = _isServer ? wagon1 : wagon2;

        if(!(wagon.getState() instanceof Falling)) {
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
            } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
                wagon.updateOnAccelerometer(5);
                _updated = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
                wagon.updateOnAccelerometer(-5);
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

        if (_updated) {
            if (_isServer) {
                if (_c != null) _c.move2(wagon1.getPosition().x, wagon1.getPosition().y);
            } else {
                _proxy.move1(wagon2.getPosition().x, wagon2.getPosition().y);
            }

            _updated = false;
        }

        if(fruit.detectCollision(wagon1)){
            caughtSound.play();
        }
        if(fruit.detectCollision(wagon2)){
            caughtSound.play();
        }
        fruit.detectCollision(floor);

        if(bomb.detectCollision(wagon1)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon1.getScore()));
        }
        if(bomb.detectCollision(wagon2)) {
            bombSound.play();
            gsm.set(new LostState(gsm, wagon2.getScore()));
        }
        bomb.detectCollision(floor);

        wagon1.detectFall(floor);
        wagon2.detectFall(floor);

        if(wagon1.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon2.getScore()));
        if(wagon2.getPosition().y <= 0)
            gsm.set(new LostState(gsm, wagon1.getScore()));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sky, cam.position.x - (cam.viewportWidth  / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
        sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
        sb.draw(wagon1.getTex(), wagon1.getPosition().x, wagon1.getPosition().y, wagon1.getWidth(), wagon1.getHeight());
        sb.draw(wagon2.getTex(), wagon2.getPosition().x, wagon2.getPosition().y, wagon2.getWidth(), wagon2.getHeight());
        floor.render(sb);

        String strScore = ((Integer)wagon1.getScore()).toString();
        font.draw(sb,strScore,WagOn.WIDTH - strScore.length()*State.font_size, WagOn.HEIGHT - 10);

        strScore = ((Integer)wagon2.getScore()).toString();
        font.draw(sb,strScore,10, WagOn.HEIGHT - 10);

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
    public void move2(float x, float y) {
        wagon1.getPosition().set(x, y);
    }


    @Override
    public void move1(float x, float y) {
        wagon2.getPosition().set(x, y);
    }

    @Override
    public void join(ClientCallbackInterface c) {
        _c = c;
    }
}
