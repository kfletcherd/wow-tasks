package api.routes.util;

import api.engine.Route;
import api.engine.PassableState;

/**
 * Route for checking API responsiveness
 *
 * ANY /ping
 */
public final class Ping implements Route {

	public void execute(PassableState ps){
		ps.res.setCode(200);
		ps.res.setMessage("Success");
		ps.res.setHeader("Content-Type", "text/html");
		ps.res.setHeader("X-Custom", "yes");
	}

}


