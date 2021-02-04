package api.engine;

import util.db.Postgresql;

import java.sql.SQLException;

/**
 * Wrapper for route functionality that needs to be passed around to process
 * requests
 */
public final class PassableState {

	/**
	 * Store for the Request object
	 * Can be accessed directly
	 */
	public Request req;


	/**
	 * Store for the Response object
	 * Can be accessed directly
	 */
	public Response res;

	/**
	 * Store for a single database connection per request
	 */
	private Postgresql db;


	/**
	 * Instanciates the Response, the request must be instanciated via the
	 * setRawRequest method
	 */
	public PassableState(){
		res = new Response();
	}


	/**
	 * Instanciated the Request object with the given raw request string
	 *
	 * @param rawRequest The user's request string to be parsed
	 * @exception Exception On Request parsing errors
	 */
	public void setRawRequest(String rawRequest)
	throws Exception {
		req = new Request(rawRequest);
	}


	/**
	 * Get a database connection to use
	 *
	 * If a connection does not already exist, this will create a new one
	 *
	 * @return A Postgresql database connection
	 * @throws SQLException on database connection errors
	 */
	public Postgresql getDatabaseConnection()
	throws SQLException {
		if(db == null)
			db = new Postgresql();

		return db;
	}


	/**
	 * Does any necessary shut down of drivers/connections that are contained
	 * within this class
	 */
	public void shutDown(){
		req = null;
		res = null;

		if(db != null){
			try {
				db.close();
				db = null;
			} catch(Exception e){
				System.err.println("Error closing db connection: " + e.getMessage());
			}
		}
	}

}

