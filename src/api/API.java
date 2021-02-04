package api;

import api.engine.ClientThread;
import api.routes.Register;

import java.net.Socket;
import java.net.ServerSocket;

/**
 * The main run method for the API
 */
class API {

	/**
	 * Store of the running ServerSocket
	 */
	private static ServerSocket serverzor;


	/**
	 * Run Forest, ruuuuuuuuuuun
	 *
	 * @param a Stop arguing with me young man
	 */
	public static void main(String[] a){
		try {
			if(a.length == 0)
				throw new Exception("Provide a port");

			int port = Integer.parseInt(a[0]);
			if(port < 1024)
				throw new Exception("Port shouldn't be below 1024");

			serverzor = new ServerSocket(port);

			listen(); // here sonny boy
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Strong listener, as long as your a port
	 *
	 * TODO: Does any janky/weird stuff happen with these threads?
	 * (live forever and/or get prematurely killed if the var is overwritten?)
	 */
	private static void listen(){
		Register api = new Register();

		while(true) {
			try {
				Socket client = serverzor.accept();
				ClientThread ct = new ClientThread(client, api);
				ct.start();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}

