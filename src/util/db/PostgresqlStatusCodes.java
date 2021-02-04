package util.db;

/**
 * Collection of Postgresql status codes
 *
 * Useful with SQLException.getSQLState()
 */
public enum PostgresqlStatusCodes {

	/**
	 * Error code for trying to delete something a FK relies on
	 */
	ERROR_FK_DEPENDANCY (23503),

	/**
	 * Error code for trying to save an existing value on a UNIQUE INDEX
	 */
	ERROR_DUPLICATE_KEY (23505);

// -- -- -- -- -- -- -- -- -- -- -- -- --

	/**
	 * Store for the status code
	 */
	private int code;


	/**
	 * Stores the requested code for use
	 *
	 * @param c The code
	 */
	PostgresqlStatusCodes(int c){
		code = c;
	}


	/**
	 * Get the status code
	 *
	 * @return The code as an int
	 */
	public int getCode(){
		return code;
	}


	/**
	 * Get the status code, but as a string
	 *
	 * This is simply a convience method since the SQLException.getSQLState
	 * method returns a string
	 *
	 * @return The status code converted to a string
	 */
	public String getCodeAsString(){
		return String.valueOf(code);
	}


	/**
	 * Check if the given status code (as a string) matches the requested code
	 *
	 * TODO: Is it more efficent to convert to an int and check that way? Does
	 * it even matter that much?
	 *
	 * @param c The status code to check against
	 * @return true if the values match, false otherwise
	 */
	public boolean checkCode(String c){
		return getCodeAsString().equals(c);
	}

}

