package Server;

import java.net.ServerSocket;
import java.net.Socket;

import jfx.ServerRead;
import jfx.ServerWrite;
import util.NetworkUtil;

public class Server implements Runnable {

	private ServerSocket ServSock;
	private ServerMain main;
	Server(ServerMain main) {
		this.main=main;
	}

	@Override
	public void run() {
		try {
			ServSock = new ServerSocket(33333);
			while (true) {
				Socket clientSock = ServSock.accept();
				NetworkUtil nc=new NetworkUtil(clientSock);
				new ServerRead(nc,main);
				new ServerWrite(nc,main);
			}
		}catch(Exception e) {
			System.out.println("Server starts:"+e);
		}

	}
}
