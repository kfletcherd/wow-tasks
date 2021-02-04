package api.routes.task;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

/**
 * Route for flagging a task as completed
 *
 * POST /task/complete
 *
 * Expected body params:
 *  taskId
 *  toonId
 */
public final class FlagTaskComplete extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("taskId");
		dh.addRequiredParam("toonId");
		HashMap<String, String> data = dh.getFinalData();

		ps.getDatabaseConnection().execute(
			"UPDATE task_assignment SET completed = true WHERE toon_id = ?::UUID AND task_id = ?::UUID",
			new String[]{ data.get("toonId"), data.get("taskId") }
		);

		ps.res.setCode(200);
		ps.res.setMessage("Task completed");
	}

}

