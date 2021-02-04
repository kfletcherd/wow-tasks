package api.routes.toon;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

/**
 * Route for sending a toon to the Neather
 *
 * DELETE /toon
 */
public final class DeleteToon extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("id");
		HashMap<String, String> data = dh.getFinalData();

		// DB stuff
		ps.getDatabaseConnection().execute(
			"DELETE FROM toon WHERE id = ?::UUID",
			new String[]{ data.get("id") }
		);

		ps.res.setCode(200);
		ps.res.setMessage("Toon deleted, sad face");
	}

}

