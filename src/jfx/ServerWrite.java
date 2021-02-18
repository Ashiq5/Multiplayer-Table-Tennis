package jfx;

import java.util.*;

import Server.ServerMain;
import util.NetworkUtil;

public class ServerWrite implements Runnable {
	
	private Thread thr;
	private NetworkUtil nc;
	private ServerMain main;

	public ServerWrite(NetworkUtil nc, ServerMain main) {
		this.nc = nc;
		this.main=main;
		this.thr = new Thread(this);
		thr.start();
	}
	
	public void run() {
		try {
			while(true) {
				nc.write(main.mx + "," + main.rx+","+main.ball.getCenterX()+","+main.ball.getCenterY()+","+main.score1+","+main.score2);

			}
		} catch(Exception e) {
			System.out.println (e);
		}			
		nc.closeConnection();
	}
}



 //+","+main.x1+","+main.x2+","+main.y1+","+main.y2+","+main.go+","+main.upward+","+main.dx+","+main.servelost+","+main.serve+","+main.bat_hit