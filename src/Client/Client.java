package Client;

import jfx.ClientRead;
import jfx.ClientWrite;
import util.NetworkUtil;

public class Client implements Runnable{

	private ClientMain main;
	Client(ClientMain main)
	{
		this.main=main;
	}

	@Override
	public void run() {
		try {
			String serverAddress = "127.0.0.1";
			int serverPort = 33333;
			NetworkUtil nc = new NetworkUtil(serverAddress,serverPort);
			new ClientRead(nc,main);
			new ClientWrite(nc,main);
		} catch(Exception e) {
			System.out.println (e);
		}
	}
}

