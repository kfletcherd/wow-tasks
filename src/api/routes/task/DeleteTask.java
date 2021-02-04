package api.routes.task;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

/**
 * Route for deleting a task
 *
 * DELETE /task
 *
 * Expected body params:
 *  id
 */
public final class DeleteTask extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("id");
		HashMap<String, String> data = dh.getFinalData();

		ps.getDatabaseConnection().execute(
			"DELETE FROM task WHERE id = ?::UUID",
			new String[]{ data.get("id") }
		);

		ps.res.setCode(200);
		ps.res.setMessage("Task deleted");
	}

}

