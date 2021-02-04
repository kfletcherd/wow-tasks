package util;

import java.io.FileInputStream;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Utility class for loading/storing/retrieving system environment variables
 */
final public class SystemProperties {

	/**
	 * File name to load settings from
	 */
	private static final String FILE = "settings.ini";


	/**
	 * Internal store of properties
	 */
	private static HashMap<String, String> properties;


	/**
	 * Loads the config file the first time this class is called
	 */
	static {
		try (
			FileInputStream fis = new FileInputStream(FILE);
		){
			properties = new HashMap<>();

			// Load all the bytes!
			byte[] rawSettings = fis.readNBytes(fis.available());
			fis.close();

			// Tokenize all the lines!
			StringTokenizer lines = new StringTokenizer(new String(rawSettings), "\n");

			while (lines.hasMoreTokens()){
				StringTokenizer vals = new StringTokenizer(lines.nextToken(), "=");

				if(vals.countTokens() != 2)
					throw new Exception("Something doesnt add up in the config file");

				// Store all the properties!
				properties.put(vals.nextToken(), vals.nextToken());
			}

		} catch(Exception e) {
			System.err.println("Error reading system properties: " + e.getMessage());
		}
	}


	/**
	 * Get a value from the properties list
	 *
	 * @param key The key to use to look up
	 * @return The value
	 */
	public static String getProperty(String key){
		return properties.get(key);
	}


	/**
	 * Method for testing this class is working as intended
	 */
	public static void main(String[] a){
		System.out.println(properties.size());
		System.out.println(SystemProperties.getProperty("asdf"));
		System.out.println(getProperty("qwert"));
		System.out.println(getProperty("yes"));
	}

}

