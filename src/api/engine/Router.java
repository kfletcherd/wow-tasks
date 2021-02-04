package api.engine;

import java.util.HashMap;

/**
 * Route register for storing valid routes and their Route controllers
 *
 * The cleanest way to use this would be to extend this class and put
 * the desired routes as part of the constructor, but idk, maybe you can
 * do some fancier stuff with it; it's your Router, use it when you need it!
 */
public class Router {

	/**
	 * Constant for the ANY method to ignore/default routes that dont give a
	 * toot about an actual method
	 */
	final public static String METHOD_ANY = "ANY";


	/**
	 * Store of the available routes, where the key is the URL of the route
	 * and the value is the RouteMethod which does a subsequent check on the
	 * requested method before returning the Route for doing stuff to the request
	 *
	 * So the nested routes stuff would look like ['/login': ['POST': LogInRoute]]
	 */
	private HashMap<String, RouteMethod> routes;


	/**
	 * Initializes the routes HashMap,,, that's about it
	 */
	public Router(){
		routes = new HashMap<>();
	}


	/**
	 * Adds a route to the register using the more specific registerRoute
	 * method, but defaults the method param to the METHOD_ANY constant
	 */
	public void registerRoute(String path, Route r){
		registerRoute(METHOD_ANY, path, r);
	}


	/**
	 * Adds a given route to the register by URL path and HTTP method
	 *
	 * @param method The HTTP method
	 * @param path The URL
	 * @param r The route to execute logic on the request
	 */
	public void registerRoute(String method, String path, Route r){
		if(!routes.containsKey(path))
			routes.put(path, new RouteMethod());

		routes.get(path).put(method, r);
	}


	/**
	 * Execute a route's logic with the provided Request
	 *
	 * This method will use info from the request to search for the path/method
	 * that the user request, but if it cannot find a Route, it will set the
	 * Response with relevant 404 not found info and call it a day
	 *
	 * @param req The Request object with relevant request data
	 * @param res The Response object for the Route to put reponse related stuff in
	 * @throws Exception Exceptions bubble out to be handled by the parent caller
	 */
	public void executeRoute(PassableState ps)
	throws Exception {
		RouteMethod rm = routes.get(ps.req.getURI().getPath());

		if(rm != null){
			Route r = rm.getRouteByMethod(ps.req.getMethod());

			if(r != null){
				r.execute(ps);
				return;
			}
		}

		ps.res.setCode(404);
		ps.res.setMessage("Not found");
	}


	/**
	 * Extension of a HashMap to allow nesting HashMaps in the routes property
	 */
	private class RouteMethod extends HashMap<String, Route> {

		/**
		 * Attempt to get the requested route with the given method, or falls
		 * back to the ANY method if the requested one does not exist
		 *
		 * @param method The method to search on
		 * @return The found route (or if nothing relevant was found)
		 */
		public Route getRouteByMethod(String method){
			return containsKey(method) ? get(method) : get(METHOD_ANY);
		}

	}

}

