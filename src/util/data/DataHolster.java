package util.data;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Utility class for holding and validating data
 */
final public class DataHolster {

	/**
	 * Store for the user data to validate
	 */
	private HashMap<String, String> data;


	/**
	 * Store for optional param keys and default values
	 */
	private HashMap<String, String> optionalParams;


	/**
	 * Store for required param key names
	 */
	private ArrayList<String> requiredParams;


	/**
	 * Sets the user data and instanciates everything that needs to be
	 *
	 * @param d The user data that will be validated
	 */
	public DataHolster(HashMap<String, String> d){
		data = d;
		optionalParams = new HashMap<>();
		requiredParams = new ArrayList<>();
	}


	/**
	 * Add an optional param to validate against
	 *
	 * @param key The key to check for
	 * @param defaultValue The value to add if the key is missing
	 */
	public void addOptionalParam(String key, String defaultValue){
		optionalParams.put(key, defaultValue);
	}


	/**
	 * Add a required param to validate exists
	 *
	 * @param key The key to ensure is present
	 */
	public void addRequiredParam(String key){
		requiredParams.add(key);
	}


	/**
	 * Validates the user input against any set required/optional params
	 * and gathers a list of missing required params and/or sets default
	 * values for any missing optional params
	 *
	 * If nothing required is missing, this will return the validated data
	 * set, otherwise it will throw
	 *
	 * @return The validated data
	 * @throws MissingRequiredParamException On missing required params
	 */
	public HashMap<String, String> getFinalData()
	throws MissingRequiredParamException, Exception {
		MissingRequiredParamException missing = new MissingRequiredParamException();

		// Error prone code is cool!
		if(!requiredParams.isEmpty())
			for(String rp : requiredParams)
				if(data.get(rp) == null)
					missing.addLostLamb(rp);

		// Double the trouble!
		if(!optionalParams.isEmpty())
			for(String op : optionalParams.keySet())
				if(data.get(op) == null)
					data.put(op, optionalParams.get(op));


		if(missing.anyLostLambs())
			throw missing;

		return data;
	}
}

