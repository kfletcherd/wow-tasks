package api.routes.category;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.xml.XMLParser;
import util.data.DataHolster;
import util.db.PostgresqlStatusCodes;

import java.sql.SQLException;

import java.util.HashMap;

/**
 * Route for creating a new category
 *
 * POST /category
 *
 * Expected body params:
 *  label
 */
public final class CreateCategory extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		// Parse user input
		DataHolster dh = new DataHolster(new XMLParser().parseToHashMap(ps.req.getBody()));
		dh.addRequiredParam("label");
		HashMap<String, String> data = dh.getFinalData();

		try {
			ps.getDatabaseConnection().execute(
				"INSERT INTO category (label) VALUES (?)",
				new String[]{ data.get("label"), }
			);

			ps.res.setCode(200);
			ps.res.setMessage("Category created");

		} catch(SQLException e){
			if(!PostgresqlStatusCodes.ERROR_DUPLICATE_KEY.checkCode(e.getSQLState()))
				throw e;

			ps.res.setCode(400);
			ps.res.setMessage("Category already exists");
		}
	}

}

