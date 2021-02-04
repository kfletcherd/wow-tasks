package api.engine;

import util.db.Postgresql;
import util.security.NotLoggedInException;

/**
 * Implementation of a Route that requires a user to be logged in
 * <p>
 * This class handles the execute method from Route already to validate
 * a user's token/account id, and any routes that wants to utilize this
 * need only extend this class and implement the runLogic method
 */
public abstract class SecureRoute implements Route {


	/**
	 * Default implementation of checking a user is logged in before executing
	 * any endpoint logic on their request
	 *
	 * @param ps The state object
	 * @throws Exception Exceptions bubble out for a higher level catcher
	 */
	final public void execute(PassableState ps)
	throws Exception {
		String account = ps.req.getHeader("accountNumber");
		String token = ps.req.getHeader("token");

		if(account == null || token == null){
			ps.res.setCode(400);
			ps.res.setMessage("Bad headers");
			return;
		}

		Postgresql db = ps.getDatabaseConnection();

		String sql = "SELECT username FROM account "
			+ "WHERE id = ?::UUID "
			+ "AND token = ? "
			+ "AND expires > current_date";

		db.executeQuery(sql, new String[]{account, token});

		if(db.getNextResultRow() == null)
			throw new NotLoggedInException();

		runLogic(ps);
	}


	/**
	 * Implementation for a route's specific logic
	 *
	 * @param ps The PassableState being passed around
	 * @throws Exception Exceptions bubble out for a higher level catcher
	 */
	protected abstract void runLogic(PassableState ps)
	throws Exception;
}

