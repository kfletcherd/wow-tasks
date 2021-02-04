package util.xml;

/**
 * Exception type for any XML related errors
 */
public final class XMLParsingException extends Exception {

	/**
	 * Compiler cries crocodile tears if this is not defined
	 *
	 * Would assume all the Exception constructors would carry over but I
	 * guess not
	 *
	 * @param message Typical exception message
	 */
	public XMLParsingException(String message){
		super(message);
	}

}

