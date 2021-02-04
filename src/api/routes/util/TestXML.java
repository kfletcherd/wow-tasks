package api.routes.util;

import api.engine.SecureRoute;
import api.engine.PassableState;

import util.db.Postgresql;

import util.xml.XMLParser;

import java.util.HashMap;

/**
 * Route for testing XML requests
 *
 * POST /xml
 */
public final class TestXML extends SecureRoute {

	public void runLogic(PassableState ps)
	throws Exception {
		try {
			HashMap<String, String> data = new XMLParser().parseToHashMap(ps.req.getBody());
			String v = data.get("val");
			String b = data.get("boo");

			if(v == null || b == null){
				ps.res.setCode(400);
				ps.res.setMessage("Missing required param: res or boo");
				return;
			}

			Postgresql db = new Postgresql();
			db.execute("INSERT INTO test (val, bool) VALUES (?, ?)", new String[]{v, b});

			ps.res.setCode(200);
			ps.res.setMessage("Success");
		} catch(Exception e){
			System.err.println("Internal error with /xml route: " + e.getMessage());
			ps.res.setCode(500);
			ps.res.setMessage("Internal Error");
		}

	}

}



