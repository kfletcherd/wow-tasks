package api.routes.category;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.Postgresql;

import util.xml.XMLBuilder;

/**
 * Route for getting a list of existing categories
 *
 * GET /category
 */
public final class GetAllCategories extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// DB stuff
		Postgresql db = ps.getDatabaseConnection();
		db.executeQuery("SELECT id, label FROM category");

		// Parse db data and prep for da user
		String[] toon;
		XMLBuilder xml = new XMLBuilder();
		while((toon = db.getNextResultRow()) != null){
			xml.addTag("category");
			xml.addTag("id");
			xml.addValue(toon[0]);
			xml.closeTag();
			xml.addTag("label");
			xml.addValue(toon[1]);
			xml.closeTag();
			xml.closeTag();
		}

		ps.res.setCode(200);
		ps.res.setMessage("Success");
		ps.res.addData("categoryList", xml.finalizeAndReturn());
	}

}

