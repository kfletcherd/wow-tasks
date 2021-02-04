package util.security;

/**
 * Exception class for peeps who aren't logged in who need to be for
 * accessing resources
 */
final public class NotLoggedInException extends Exception {


	/**
	 * Returns a static string
	 *
	 * @return The default message for this exception
	 */
	public String getMessage(){
		return "Not logged in";
	}

}

