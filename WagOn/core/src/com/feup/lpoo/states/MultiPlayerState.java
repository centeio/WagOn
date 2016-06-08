package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.DefaultOnscreenKeyboard;
import com.feup.lpoo.WagOn;
import com.feup.lpoo.WagonStates.Falling;
import com.feup.lpoo.logic.Bomb;
import com.feup.lpoo.logic.FallingObj;
import com.feup.lpoo.logic.Floor;
import com.feup.lpoo.logic.Fruit;
import com.feup.lpoo.logic.Wagon;

import com.feup.lpoo.network.ClientCallbackInterface;
import com.feup.lpoo.network.ServerInterface;

import java.io.IOException;
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
    private int id, winnerid, winnerscore = 0;

    private Boolean _isServer, gameover;
    private ServerInterface _proxy;

    private ClientCallbackInterface _c1 = null;
    private ClientCallbackInterface _c2 = null;


    Server server;
    Client client;

    public MultiPlayerState(GameStateManager gsm, boolean isServer, String ip) {
        super(gsm);

        _isServer = isServer;
        gameover = false;
        sky = new Texture("sky.png");

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        bombSound = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
        caughtSound = Gdx.audio.newSound(Gdx.files.internal("bump.wav"));

        if (isServer) {
            id=0;
            System.out.println("id server " + id);
            floor = new Floor(31);
            wagon1 = new Wagon("wagon.png");
            wagon2 = new Wagon("wagon2.png");
            fruit = new Fruit(MathUtils.random(0, WagOn.WIDTH - FallingObj.WIDTH));
            bomb = new Bomb();


            try {
                CallHandler callHandler = new CallHandler();
                callHandler.registerGlobal(ServerInterface.class, this);
                server = new Server();
                int thePortIWantToBind = 2009;
                server.bind(thePortIWantToBind, callHandler);
                InetAddress IP = InetAddress.getLocalHost();
                System.err.println("Server ready at " + IP.getHostAddress() + " port " + 2009);


            } catch (Exception e) {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
        } else {
//            System.out.println("id player "+id);
            try {
                // get proxy for remote chat server


                CallHandler callHandler = new CallHandler();

                int portWasBinded = 2009;
                client = new Client(ip, portWasBinded, callHandler);
                _proxy = (ServerInterface) client.getGlobal(ServerInterface.class);

                // create and expose remote listener
                callHandler.exportObject(ClientCallbackInterface.class, this);
                id = _proxy.join(this);
                System.out.println("created client "+id);

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
        System.out.println("entra android handle");

        if (Gdx.input.justTouched()) {
            jumpSound.play();
            _proxy.jumpWagon(id);
        }

        if (WagOn.isMobile) {
            float acc = Gdx.input.getAccelerometerY();
            _proxy.accWagon(id, acc);
        } else
            _proxy.accWagon(id, 0);
        System.out.println("sai android handle");


    }

    @Override
    public void update(float dt) {
        if(_isServer && (_c1==null || _c2==null))
            return;
        handleInput();

        if(_isServer) {
            wagon1.update(dt);
            wagon2.update(dt);
            fruit.update(dt);
            bomb.update(dt);

            fruit.detectCollision(wagon1);
            fruit.detectCollision(wagon2);

            fruit.detectCollision(floor);

            //TODO lost state
            if (bomb.detectCollision(wagon1)) {
                bombSound.play();
                winnerid=2;
                winnerscore = wagon2.getScore();
                gameover = true;
            }
            if (bomb.detectCollision(wagon2)) {
                bombSound.play();
                winnerid=1;
                winnerscore = wagon1.getScore();
                gameover = true;
            }
            bomb.detectCollision(floor);

            wagon1.detectFall(floor);
            wagon2.detectFall(floor);

            if (wagon1.getPosition().y <= 0) {
                winnerid=2;
                winnerscore = wagon2.getScore();
                gameover = true;

            }
            if (wagon2.getPosition().y <= 0) {
                winnerid = 1;
                winnerscore = wagon1.getScore();
                gameover = true;
            }

        }

        if(gameover){
            if(_isServer){
                _c1.tellGameover(winnerid, winnerscore);
                _c2.tellGameover(winnerid, winnerscore);
            }
            gameover(winnerid, winnerscore);
        }
    }

    public void gameover(int winnerid, int winnerscore){
        if(_isServer)
            gsm.set(new LostMPState(gsm, id, winnerid, winnerscore, true));
        else if(id==winnerid)
            gsm.set(new LostMPState(gsm, id, winnerid, winnerscore, false));
        else
            gsm.set(new LostMPState(gsm, id, winnerid, winnerscore, false));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(sky, cam.position.x - (cam.viewportWidth / 2), 0, WagOn.WIDTH, WagOn.HEIGHT);
        if(_isServer) {
            sb.draw(fruit.getTex(), fruit.getPosition().x, fruit.getPosition().y, fruit.getWidth(), fruit.getHeight());
            sb.draw(bomb.getTex(), bomb.getPosition().x, bomb.getPosition().y, bomb.getWidth(), bomb.getHeight());
            sb.draw(wagon1.getTex(), wagon1.getPosition().x, wagon1.getPosition().y, wagon1.getWidth(), wagon1.getHeight());
            sb.draw(wagon2.getTex(), wagon2.getPosition().x, wagon2.getPosition().y, wagon2.getWidth(), wagon2.getHeight());
            floor.render(sb);
        }
        String strScore;

        if(_isServer){

            strScore = "PINK " +((Integer) wagon1.getScore()).toString();
            font.draw(sb, strScore, WagOn.WIDTH - strScore.length() * State.font_size, WagOn.HEIGHT - 10);

            strScore = "PURPLE " + ((Integer) wagon2.getScore()).toString();
            font.draw(sb, strScore, 10, WagOn.HEIGHT - 10);
        }
        else {

            font.draw(sb, "Player "+ id, WagOn.WIDTH/2, WagOn.HEIGHT/6);

            strScore = _proxy.getScore(id);

            titleFont.draw(sb, strScore, WagOn.WIDTH/2, WagOn.HEIGHT/2);

        }

        sb.end();
    }

    @Override
    public void dispose() {
        if(_isServer){
            fruit.getTex().dispose();
            bomb.getTex().dispose();
            wagon1.getTex().dispose();
            wagon2.getTex().dispose();
            server.close();
        } else {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sky.dispose();
    }

    @Override
    public int join(ClientCallbackInterface c) {
        if (_c1 == null) {
            _c1 = c;
            System.out.println("c1 connected");
            return 1;
        } else if (_c2 == null) {
            _c2 = c;
            System.out.println("c2 connected");
            return 2;
        }

        System.out.println("too many clients");

        return -1;
    }

    @Override
    public void accWagon(int id, float acc){
        if(id==1)
            wagon1.updateOnAccelerometer(acc);
        else if(id==2)
            wagon2.updateOnAccelerometer(acc);

    }

    @Override
    public void jumpWagon(int id){
        if(id==1 && !(wagon1.getState() instanceof Falling))
            wagon1.jump();
        else if(id==2 && !(wagon2.getState() instanceof Falling))
            wagon2.jump();
    }

    @Override
    public String getScore(int id){
        if(id==1)
            return ((Integer)wagon1.getScore()).toString();
        if(id==2)
            return ((Integer)wagon2.getScore()).toString();
        return "";
    }

    @Override
    public void tellGameover(int wid, int wscore) {
        winnerid = wid;
        winnerscore = wscore;
        gameover = true;

    }
}
