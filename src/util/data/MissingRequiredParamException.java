package util.data;

import java.util.ArrayList;

/**
 * Custom exception for user input that is missing required data
 */
public final class MissingRequiredParamException extends Exception {


	/**
	 * Store of missing required keys
	 */
	private ArrayList<String> lostAndFound;



	/**
	 * Default constructor, nothing special
	 */
	public MissingRequiredParamException(){
		super();
		lostAndFound = new ArrayList<>();
	}


	/**
	 * Does nothing special outside the default constructor,
	 * only implemented for forgetfulness or oversite
	 *
	 * @param message This is ignored
	 */
	public MissingRequiredParamException(String message){
		super();
		lostAndFound = new ArrayList<>();
	}



	/**
	 * Add the name value for a missing key from user input
	 *
	 * @param lamb The key name
	 */
	public void addLostLamb(String lamb){
		lostAndFound.add(lamb);
	}



	/**
	 * Checks if any missing keys were added to this class
	 *
	 * @return true if there are missing required keys
	 */
	public boolean anyLostLambs(){
		return !lostAndFound.isEmpty();
	}



	/**
	 * Override of the default exception's getMessage method to return
	 * a standardized string
	 *
	 * @return The message with missing keys listed
	 * @override
	 */
	public String getMessage(){
		return new StringBuilder()
			.append("Missing required parameters: ")
			.append(String.join(", ", lostAndFound))
			.toString();
	}

}

