package api.routes.task;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.PostgresqlStatusCodes;
import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

import java.sql.SQLException;

/**
 * Route for assigning a task to a toon
 *
 * POST /task/assign
 *
 * Expected body params:
 *  taskId
 *  toonId
 */
public final class AssignTask extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("taskId");
		dh.addRequiredParam("toonId");
		HashMap<String, String> data = dh.getFinalData();

		try {
			ps.getDatabaseConnection().execute(
				"INSERT INTO task_assignment (toon_id, task_id, completed) VALUES (?::UUID, ?::UUID, false)",
				new String[]{ data.get("toonId"), data.get("taskId") }
			);

			ps.res.setCode(200);
			ps.res.setMessage("Task assigned");
		} catch(SQLException e){
			if(!PostgresqlStatusCodes.ERROR_DUPLICATE_KEY.checkCode(e.getSQLState()))
				throw e;

			ps.res.setCode(400);
			ps.res.setMessage("Task is already assigned to that toon");

		}
	}

}

