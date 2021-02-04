package api.routes.account;

import api.engine.Route;
import api.engine.PassableState;

/**
 * Route for users to log out
 *
 * POST /logout
 *
 * Expected body params:
 *  username
 *  token
 */
public final class Logout implements Route {

	/**
	 * TODO
	 */
	public void execute(PassableState ps){
		ps.res.setCode(666);
		ps.res.setMessage("bro, you just logged out!");
	}

}


