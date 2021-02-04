package api.engine;

import util.xml.XMLBuilder;

import java.util.HashMap;

/**
 * Helper class for handling response stuff to send back to the client
 */
public final class Response {

	/**
	 * HTTP code for the response (200, 404, etc)
	 */
	private int code;


	/**
	 * Response data, as a XMLBuilder for appending as you go!
	 */
	private XMLBuilder data;


	/**
	 * A human friendly response message
	 */
	private String message = "";


	/**
	 * Response headers, duh
	 */
	private HashMap<String, String> headers;


	/**
	 * Initializes some interal stuff and sets default response code and headers
	 */
	public Response(){
		resetData();
		headers = new HashMap<>();

		setCode(200);
		setHeader("Content-Type", "application/xml");
	}


	/**
	 * Override the response code (default 200 from constructor)
	 *
	 * @param code The new code
	 */
	public void setCode(int code){
		this.code = code;
	}


	/**
	 * Add (or override) a header
	 *
	 * @param key Header key
	 * @param val Header value
	 */
	public void setHeader(String key, String val){
		headers.put(key, val);
	}


	/**
	 * Add a human friendly response message
	 *
	 * @param msg The response message
	 */
	public void setMessage(String msg){
		message = msg;
	}


	/**
	 * Add response data
	 *
	 * @param tag The tag/key name for the data
	 * @param value The data's value
	 */
	public void addData(String tag, String value){
		data.addTag(tag);
		data.addValue(value);
		data.closeTag();
	}


	/**
	 * Resets the current response data with a fresh XMLBuilder
	 * and adds any default tags
	 */
	public void resetData(){
		data = new XMLBuilder();
	}


	/**
	 * Compiles the various components of Response into the proper HTTP
	 * response string
	 *
	 * @return The raw response as a string
	 */
	public String getResponse(){
		String resBody = getRawResponseBody();

		return new StringBuilder()
			.append("HTTP/1.1 ")
			.append(code)
			.append(" Done\r\n")
			.append(getRawHeaders(resBody.length()))
			.append("\r\n")
			.append(resBody)
			.toString();
	}


	/**
	 * Get the current set headers as a HTTP string
	 *
	 * Checks the currently set body length to generate the
	 * Content-Length header at the time this method is called
	 * so make sure the body is finished being edited before calling
	 * this method to send stuff to the client
	 *
	 * @return The stringafied headers in HTTP format
	 */
	private String getRawHeaders(int contentLength){
		StringBuilder rawHeader = new StringBuilder(256);

		for(String key : headers.keySet()){
			rawHeader
				.append(key)
				.append(": ")
				.append(headers.get(key))
				.append("\r\n");
		}

		rawHeader
			.append("Content-Length: ")
			.append(contentLength + 1)
			.append("\r\n");

		return rawHeader.toString();
	}


	/**
	 * Get the message and data as a raw string ready for transmission
	 *
	 * @return The XML Response data string
	 */
	private String getRawResponseBody(){
		XMLBuilder resBody = new XMLBuilder();
		resBody.addTag("response");

		resBody.addTag("message");
		resBody.addValue(message);
		resBody.closeTag();

		resBody.addTag("data");
		resBody.addValue(data.finalizeAndReturn());
		resBody.closeTag();

		return resBody.finalizeAndReturn();
	}

}

