package api.routes.category;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.PostgresqlStatusCodes;
import util.xml.XMLParser;
import util.data.DataHolster;

import java.util.HashMap;

import java.sql.SQLException;

/**
 * Route for assigning a category to a task
 *
 * POST /category/assign
 *
 * Expected body params:
 *  taskId
 *  categoryId
 */
public final class AssignCategory extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("taskId");
		dh.addRequiredParam("categoryId");
		HashMap<String, String> data = dh.getFinalData();

		try {
			ps.getDatabaseConnection().execute(
				"INSERT INTO task_category (task_id, category_id) VALUES (?::UUID, ?::UUID)",
				new String[]{ data.get("taskId"), data.get("categoryId") }
			);

			ps.res.setCode(200);
			ps.res.setMessage("Category assigned");
		} catch(SQLException e){
			if(!PostgresqlStatusCodes.ERROR_DUPLICATE_KEY.checkCode(e.getSQLState()))
				throw e;

			ps.res.setCode(400);
			ps.res.setMessage("Task is already assigned to that category");

		}
	}

}

