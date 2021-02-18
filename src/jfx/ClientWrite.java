package jfx;

import java.util.*;

import Client.ClientMain;
import Server.ServerMain;
import util.NetworkUtil;

public class ClientWrite implements Runnable {

    private Thread thr;
    private NetworkUtil nc;
    private ClientMain main;

    public ClientWrite(NetworkUtil nc, ClientMain main) {
        this.nc = nc;
        this.main=main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while(true) {
                nc.write(main.my+","+main.ry+","+main.bat_hit2+","+main.hit+","+main.dx2);

            }
        } catch(Exception e) {
            System.out.println (e);
        }
        nc.closeConnection();
    }
}



