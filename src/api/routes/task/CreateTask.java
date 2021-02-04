package api.routes.task;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

/**
 * Route for creating a new task
 *
 * POST /task
 *
 * Expected body params:
 *  label
 *  [description]
 *  [image]
 *  [externaLink]
 */
public final class CreateTask extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("label");
		dh.addOptionalParam("description", null);
		dh.addOptionalParam("image", null);
		dh.addOptionalParam("externalLink", null);
		HashMap<String, String> data = dh.getFinalData();

		ps.getDatabaseConnection().execute(
			"INSERT INTO task (label, description, image, external_link) VALUES (?, ?, ?, ?)",
			new String[]{
				data.get("label"),
				data.get("description"),
				data.get("image"),
				data.get("externaLink")
			}
		);

		ps.res.setCode(200);
		ps.res.setMessage("Task created");
	}

}

