package api.engine;

import api.engine.Request;
import api.engine.Response;

/**
 * Routes (endpoints) just need an execute method for the Router to call
 * and pass Request/Response to
 */
public interface Route {

	/**
	 * An execute method to run logic specific to a Route
	 *
	 * Only use if the method will not throw anything or catching and handling
	 * inside this method is most desirable, otherwise use the method that is
	 * defined to throw and let the ClientThread handle catches with specific
	 * Excpetion extensions
	 *
	 * @param req The client's Request information
	 * @param res The response to work with to return stuff
	 */
	//public void execute(Request req, Response res);


	/**
	 * An execute method to run logic specific to a Route
	 *
	 * Though Exception is defined here generally for ease of use, what your
	 * actually throwing should be extremely specific
	 *
	 * @param ps The state object with Request, Response, and junk
	 * @throws Exception Exceptions bubble out for a higher level catcher
	 */
	public void execute(PassableState ps)
	throws Exception;
}

