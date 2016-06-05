package com.feup.lpoo.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.feup.lpoo.WagOn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;

/**
 * Created by inesf on 04/06/2016.
 */
public class ConnectionTestState extends State {
    private String message;
    public static String ipAddress;
    public ConnectionTestState(GameStateManager gsm) {
        super(gsm);

        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                    addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        StringBuilder ipBuilder = new StringBuilder();
        for(String str:addresses)
        {
            ipBuilder.append(str + "\n");
        }
        ipAddress = ipBuilder.toString();

        // Now we create a thread that will listen for incoming socket connections
        new Thread(new Runnable(){

            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                // 0 means no timeout.  Probably not the greatest idea in production!
                serverSocketHint.acceptTimeout = 60;

                // Create the socket server using TCP protocol and listening on 9021
                // Only one app can listen to a port at a time, keep in mind many ports are reserved
                // especially in the lower numbers ( like 21, 80, etc )
                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 4456, serverSocketHint);

                // Loop forever
                while(true){
                    // Create a socket
                    Socket socket = serverSocket.accept(null);

                    // Read data from the socket into a BufferedReader
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    try {
                        // Read to the next newline (\n) and display that text on labelMessage
                        message = buffer.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); // And, start the thread running

        message = "Hi";
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            String textToSend = new String();
            if (message.length() == 0)
                textToSend = "Doesn't say much but likes clicking buttons\n";
            else
                textToSend = message + ("1\n"); // Brute for a newline so readline gets a line

            SocketHints socketHints = new SocketHints();
            // Socket will time our in 4 seconds
            socketHints.connectTimeout = 4000;
            //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
            Socket socket;
            if(WagOn.isMobile)
                socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "192.168.1.9", 4456, socketHints);
            else
                socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "192.168.1.5", 4456, socketHints);
            try {
                // write our entered message to the stream
                socket.getOutputStream().write(textToSend.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        font.draw(sb, ipAddress, 10, 400);
        font.draw(sb,message,10,50);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
