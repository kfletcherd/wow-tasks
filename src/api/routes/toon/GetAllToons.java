package api.routes.toon;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.Postgresql;

import util.xml.XMLBuilder;

/**
 * Route for getting a list of existing toons
 *
 * GET /toon
 */
public final class GetAllToons extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// DB stuff
		Postgresql db = ps.getDatabaseConnection();
		db.executeQuery("SELECT id, name FROM toon");

		// Parse db data and prep for da user
		String[] toon;
		XMLBuilder xml = new XMLBuilder();
		while((toon = db.getNextResultRow()) != null){
			xml.addTag("toon");
			xml.addTag("id");
			xml.addValue(toon[0]);
			xml.closeTag();
			xml.addTag("name");
			xml.addValue(toon[1]);
			xml.closeTag();
			xml.closeTag();
		}

		ps.res.setCode(200);
		ps.res.setMessage("Success");
		ps.res.addData("toonList", xml.finalizeAndReturn());
	}

}

