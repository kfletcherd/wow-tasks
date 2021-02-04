package api.engine;

import java.net.URI;

import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Handles headers and body from HTTP requests
 */
public final class Request {

	/**
	 * Method from the HTTP request
	 * GET POST and all that
	 */
	final private String method;


	/**
	 * URL from the HTTP request parsed to URI
	 * (cause it performed better than the URL class)
	 */
	final private URI uri;


	/**
	 * Dont remember, I guess the HTTP version or something?
	 * (HTTP/1.1)
	 */
	final private String protocol;


	/**
	 * HTTP headers split to key/value HashMap
	 */
	final private HashMap<String, String> headers;


	/**
	 * Request body (if applicable)
	 */
	final private String body;


	/**
	 * Takes a raw HTTP requests and parses its different components to be
	 * easier to work with
	 *
	 * @param req The HTTP request as a raw string
	 * @throws Exception when somethings borked
	 */
	public Request(String req)
	throws Exception {
		String tmpBody = null;
		headers = new HashMap<>();

		// Get line one of the request (GET / HTTP/1.1)
		StringTokenizer reqMain = new StringTokenizer(req, "\r\n");
		// Split line 1 into its three pieces
		StringTokenizer reqTop = new StringTokenizer(reqMain.nextToken(), " ");

		// Check its actually three pieces
		if(reqTop.countTokens() != 3)
			throw new Exception("Invalid start line");

		// Method should be first
		method = reqTop.nextToken();
		// URL next
		uri = new URI(reqTop.nextToken());
		// Protocol last
		protocol = reqTop.nextToken();
		// Save that memory bruh
		reqTop = null;

		// Go through the header lines and get em
		while(reqMain.hasMoreTokens()){
			String rawHead = reqMain.nextToken();

			// If a POST body is present, its supposed to be seperated by a blank line
			if(rawHead.isBlank()) break;

			// Work around for getting a body since the tokenizer seems to drop
			// the blank line that seperates these all together
			// Assumes all methods except GET will have a body
			if(!method.equals("GET") && !reqMain.hasMoreTokens()){
				tmpBody = rawHead;
				break;
			}

			// Seperate the header by the colon then stuff it into the headers HashMap
			StringTokenizer head = new StringTokenizer(rawHead, ": ");
			headers.put(head.nextToken(), head.nextToken());
		}

		// Assign the body if found (or null if not)
		body = tmpBody;
	}


	/**
	 * Returns the HTTP request method from this request
	 *
	 * @return (POST, GET, etc)
	 */
	public String getMethod(){
		return method;
	}


	/**
	 * Returns the url from the request as a URI
	 *
	 * @return idk what else to say about it
	 */
	public URI getURI(){
		return uri;
	}


	/**
	 * Returns the HTTP protocol used as a string
	 *
	 * @return (HTTP/1.1 and stuff)
	 */
	public String protocol(){
		return protocol;
	}


	/**
	 * Get the contents of a header
	 *
	 * @param key The key to search on
	 * @return The value
	 */
	public String getHeader(String key){
		return headers.get(key);
	}


	/**
	 * Is there a point of getting all the values? Seems dumb but keeping
	 * because why not
	 *
	 * @return All the header values that exist
	 */
	public Collection<String> getAllHeaders(){
		return headers.values();
	}


	/**
	 * Get the POST body if set, will be null if one wasn't provided
	 *
	 * @return The POST body as a raw string
	 */
	public String getBody(){
		return body;
	}

}

