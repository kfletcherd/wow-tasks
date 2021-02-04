package api.routes;

import api.routes.util.*;
import api.routes.account.*;
import api.routes.task.*;
import api.routes.toon.*;
import api.routes.category.*;

import api.engine.Router;

/**
 * Implementation of the Router to hold all the routes'n'stuff
 */
public final class Register extends Router {

	/**
	 * Dump all your routes here home-boy, lets see how gross we can
	 * make this biznitch
	 */
	public Register(){
		super();

		// Utility and helper routes
		registerRoute("/util/ping", new Ping());
		registerRoute("POST", "/util/xml", new TestXML());

		// Category related routes
		registerRoute("GET", "/category", new GetAllCategories());
		registerRoute("POST", "/category", new CreateCategory());
		registerRoute("DELETE", "/category", new DeleteCategory());
		registerRoute("POST", "/category/assign", new AssignCategory());

		// Account related routes
		registerRoute("POST", "/account/create", new CreateAccount());
		registerRoute("POST", "/account/login", new Login());
		registerRoute("POST", "/account/logout", new Logout());

		// Task related routes
		// TODO: Get tasks endpoint
		registerRoute("POST", "/task", new CreateTask());
		registerRoute("DELETE", "/task", new DeleteTask());

		// Task assignment (task<>toon related) routes
		registerRoute("POST", "/task/assign", new AssignTask());
		registerRoute("POST", "/task/complete", new FlagTaskComplete());
		registerRoute("POST", "/task/incomplete", new FlagTaskIncomplete());

		// Toon related routes
		registerRoute("GET", "/toon", new GetAllToons());
		registerRoute("POST", "/toon", new CreateToon());
		registerRoute("DELETE", "/toon", new DeleteToon());
	}

}

