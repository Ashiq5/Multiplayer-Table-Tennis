package jfx;

import Client.ClientMain;
import Server.ServerMain;
import util.NetworkUtil;

import java.util.StringTokenizer;

public class ClientRead implements Runnable {
    private Thread thr;
    private NetworkUtil nc;
    private ClientMain main;
    public ClientRead(NetworkUtil nc,ClientMain main) {
        this.nc = nc;
        this.main=main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            int old=999,old2=0;
            while(true) {
                String s=(String)nc.read();
                if(s!=null)
                {
                    StringTokenizer st = new StringTokenizer(s, ",");
                    main.mx= Double.parseDouble(st.nextToken());
                    main.rx = Double.parseDouble(st.nextToken());
                    main.ball.setCenterX(Double.parseDouble(st.nextToken()));
                    main.ball.setCenterY(Double.parseDouble(st.nextToken()));
                    main.score1=Integer.parseInt(st.nextToken());
                    main.score2=Integer.parseInt(st.nextToken());


                }
            }
        } catch(Exception e) {
            System.out.println (e);
        }
        nc.closeConnection();
    }
}



