package api.routes.account;

import api.engine.Route;
import api.engine.PassableState;

import util.db.Postgresql;

import util.security.HashRobotsFromSpace;
import util.data.DataHolster;
import util.xml.XMLParser;

import java.util.HashMap;

/**
 * Route for users to log in
 *
 * POST /account/login
 *
 * Expected body params:
 *  username
 *  password
 */
public final class Login implements Route {

	public void execute(PassableState ps)
	throws Exception {
		String sql;
		String[] params;

		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("username");
		dh.addRequiredParam("password");
		HashMap<String, String> data = dh.getFinalData();

		// Get login information from the db
		sql = "SELECT password, salt, id FROM account WHERE username = lower(?)";
		params = new String[]{ data.get("username") };
		Postgresql db = new Postgresql();
		db.executeQuery(sql, params);

		// Check username was valid
		String[] passTheSalt = db.getNextResultRow();
		if(passTheSalt == null){
			ps.res.setCode(400);
			ps.res.setMessage("Bad login credentials");
			return;
		}

		// Hash the user's password and compare it against the db hash
		String hashword = HashRobotsFromSpace.HashRobot.iCanHazHash(data.get("password"), passTheSalt[1]);
		if(!hashword.equals(passTheSalt[0])){
			ps.res.setCode(400);
			ps.res.setMessage("Bad login credentials");
			return;
		}

		// Generate a random ass string to use as the user's token
		String token = HashRobotsFromSpace.HashRobot.iCanHazHash(
			HashRobotsFromSpace.SaltRobot.getNewSalt(),
			HashRobotsFromSpace.SaltRobot.getNewSalt()
		);

		// Save the token so it can be validated against later
		sql = "UPDATE account SET token = ?, expires = (current_date + integer '7') "
			+ "WHERE username = lower(?)";
		params = new String[]{token, data.get("username")};
		db.execute(sql, params);

		// Pass the token to the client
		ps.res.setCode(200);
		ps.res.setMessage("Success");
		ps.res.addData("accountNumber", passTheSalt[2]);
		ps.res.addData("token", token);
	}

}

