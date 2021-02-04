package api.routes.toon;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

/**
 * Route for creating a new toon
 *
 * POST /toon
 *
 * TODO: Disallow duplicate toon names?
 *
 * Expected body params:
 *  name
 *  class
 *  [image]
 */
public final class CreateToon extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("name");
		dh.addRequiredParam("class");
		dh.addOptionalParam("image", null);
		HashMap<String, String> data = dh.getFinalData();

		String sql = "INSERT INTO toon (account_id, name, class, image) VALUES (?::UUID, ?, ?, ?)";

		String[] params = new String[]{
			ps.req.getHeader("accountNumber"),
			data.get("name"),
			data.get("class"),
			data.get("image")
		};

		ps.getDatabaseConnection().execute(sql, params);

		ps.res.setCode(200);
		ps.res.setMessage("Toon created");
	}

}

