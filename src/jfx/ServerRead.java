package jfx;


import Server.ServerMain;
import util.NetworkUtil;

import java.util.StringTokenizer;

public class ServerRead implements Runnable {
	private Thread thr;
	private NetworkUtil nc;
	private ServerMain main;

	public ServerRead(NetworkUtil nc, ServerMain main) {
		this.nc = nc;
		this.main=main;
		this.thr = new Thread(this);
		thr.start();
	}
	
	public void run() {
		try {
			int old=999,old2=0,old4=-1;
			Double old3=0.0;
			while(true) {
				String s=(String)nc.read();
				if(s!=null)
				{
					StringTokenizer st = new StringTokenizer(s, ",");
					main.my= Double.parseDouble(st.nextToken());
					main.ry = Double.parseDouble(st.nextToken());
					int b=Integer.parseInt(st.nextToken());
					if(old2!=b)
					{
						main.bat_hit2=b;
						old2=b;
					}
					int a=Integer.parseInt(st.nextToken());
					if(old!=a)
					{
						main.hit=a;
						old=a;
					}
					Double c=Double.parseDouble(st.nextToken());
					if(old3!=c)
					{
						main.dx2=c;
						old3=c;
					}
				}

			}
		} catch(Exception e) {
			System.out.println (e);                        
		}
		nc.closeConnection();
	}
}



