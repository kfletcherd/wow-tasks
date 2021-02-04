package api.routes.account;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.Postgresql;
import util.db.PostgresqlStatusCodes;

import util.xml.XMLParser;
import util.data.DataHolster;
import util.security.HashRobotsFromSpace;

import java.util.HashMap;

import java.sql.SQLException;

/**
 * Route for creating new accounts
 *
 * No public accounts!
 * This is a SecureRoute because this is not a public app (yet)
 *
 * POST /account/create
 *
 * Expected body params:
 *  username
 *  password
 */
public final class CreateAccount extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		String sql;
		String[] params;

		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("username");
		dh.addRequiredParam("password");
		HashMap<String, String> data = dh.getFinalData();

		// Make some Hashbrowns
		String saltyMother = HashRobotsFromSpace.SaltRobot.getNewSalt();
		String hashword = HashRobotsFromSpace.HashRobot.iCanHazHash(data.get("password"), saltyMother);

		// Store the new account information
		Postgresql db = new Postgresql();
		sql = "INSERT INTO account (username, password, salt) VALUES (lower(?), ?, ?)";
		params = new String[]{
			data.get("username"),
			hashword,
			saltyMother
		};

		try {
			db.execute(sql, params);

			ps.res.setCode(200);
			ps.res.setMessage("Account created");
		} catch(SQLException e){
			if(!PostgresqlStatusCodes.ERROR_DUPLICATE_KEY.checkCode(e.getSQLState()))
				throw e;

			ps.res.setCode(400);
			ps.res.setMessage("Account name already exists");

		}

	}

}

