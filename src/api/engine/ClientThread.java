package api.engine;

import util.security.NotLoggedInException;
import util.data.MissingRequiredParamException;
import util.xml.XMLParsingException;

import java.net.Socket;

import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.StringTokenizer;

/**
 * Thread for processing a client's request
 */
public final class ClientThread extends Thread {

	/**
	 * The client's socket for client i/o
	 */
	private Socket client;


	/**
	 * List of pre-established routes to use for the request
	 */
	private Router router;


	/**
	 * Constructs a new thread with a given Socket and Router
	 *
	 * @param Socket clientSocket The socket from the client
	 * @param Router r The pre-established routes to use
	 */
	public ClientThread(Socket clientSocket, Router r){
		client = clientSocket;
		router = r;
	}


	/**
	 * Handles getting input from the input stream, creating a new request and
	 * response, then passing those along to the Router for processing.
	 *
	 * Implementation of the Thread run method.
	 */
	public void run(){
		PassableState state = new PassableState();

		try (
			// Convert Socket OutputStream into a PrintWriter for ease of use
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		) {

			// Get the headers and body out of the input stream and stuff it into a raw String
			InputStream in = client.getInputStream();

			// Web browsers apparently like to open a connection then sit
			// around with their thumbs in there bums, so this jankyness
			// is needed to cater to their dumbasses
			String rawReq = "";
			int retries = 0;
			while(rawReq.isEmpty()){
				rawReq = new String(in.readNBytes(in.available()));
				if(rawReq.isEmpty()) Thread.sleep(100);
				if(retries++ > 20){
					state.res.setCode(408);
					state.res.setMessage("Timed out: did not recieve any data in a timely manner");
					out.println(state.res.getResponse());
					throw new Exception("Client timed out");
				}
			}

			try {
				// Give the raw request string to the Request object for parsing
				state.setRawRequest(rawReq.toString());
				// Send the Request and Response to the router
				router.executeRoute(state);

			} catch(NotLoggedInException e){
				state.res.setCode(401);
				state.res.setMessage(e.getMessage());

			} catch(MissingRequiredParamException e){
				state.res.setCode(400);
				state.res.setMessage(e.getMessage());

			} catch(XMLParsingException e){
				state.res.setCode(400);
				state.res.setMessage("Error processing XML, ensure your XML is valid");

			} catch(SQLException e){
				state.res.setCode(500);
				state.res.setMessage("Internal system error");
				System.err.println("Database Code: " + e.getSQLState());
				System.err.println("Database Error: " + e.getMessage());

			} catch(Exception e){
				state.res.setCode(500);
				state.res.setMessage("Something went horribly wrong");
				System.err.println("Internal Error: " + e.getMessage());
				e.printStackTrace();
			}

			// Spit any response data back to the client
			out.println(state.res.getResponse());

			// Gracefully try to close down the PassableState
			// Then kill it br00taly to be sure nothing is lingering/hanging
			state.shutDown();
			state = null;

		} catch(Exception e) {
			System.err.println("Client Error: " + e.getMessage());
		}

		try {
			// Close the socket
			client.close();
		} catch(Exception e){
			System.err.println("Couldn't close client: " + e.getMessage());
		}
	}

}

