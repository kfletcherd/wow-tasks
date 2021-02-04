package util.xml;

import java.io.StringReader;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;

/**
 * Simple XML parser to convert xml into a hashmap
 * This is not very featureful
 */
public final class XMLParser {

	/**
	 * Store of the parsed XML to avoid passing stuff around
	 */
	private HashMap<String, String> data;


	/**
	 * Initialize the hashmap!
	 */
	public XMLParser(){
		data = new HashMap<>();
	}


	/**
	 * Parse the given XML string to the hashmap
	 *
	 * @param in Input
	 * @return The xml as tag=key innerContent=val
	 * @throws Exception on parsing errors
	 */
	public HashMap<String, String> parseToHashMap(String in)
	throws XMLParsingException {
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(
				(new InputSource(new StringReader(in))),
				new ReadHandle()
			);

			return data;
		} catch(Exception e){
			throw new XMLParsingException(e.getMessage());
		}
	}


	/**
	 * Contained helper class to implement the DefaultHandler needed for SAX
	 * stuff
	 */
	private final class ReadHandle
	extends DefaultHandler {

		/**
		 * The current tag
		 */
		private String key;


		/**
		 * The current value
		 */
		private String val;


		/**
		 * Implementation of the DefaultHandler's startElement method
		 * Simply stuffs the current tag name into the class's key property
		 *
		 * @throws SAXException On SAX errors
		 */
		public void startElement(String uri, String localName, String qName, Attributes atts)
		throws SAXException {
			key = qName;
		}


		/**
		 * Implementation of the DefaultHandler's endElement method
		 * Adds the key/val to the parent class's hashmap so long as the value
		 * is not null
		 *
		 * @throws SAXException On SAX errors
		 */
		public void endElement(String uri, String localName, String qName)
		throws SAXException {
			if(val != null) data.put(key, val);
			key = null;
			val = null;
		}


		/**
		 * Implementation of the DefaultHandler's characters method
		 * Converts all the data to a string and trims it, then puts it in
		 * this class's var property
		 */
		public void characters(char[] ch, int start, int length){
			val = new String(ch, start, length).trim();
		}

	}


	/**
	 * Method for testing this class is working from the cli
	 *
	 * @param a The first element will be taken as an XML string to test with
	 */
	public static void main(String[] a){
		try {
			for(String v : (new XMLParser()).parseToHashMap(a[0]).values()){
				System.out.println(v);
			}
		} catch(Exception e) {
			System.out.println("broke: " + e.getMessage());
			e.printStackTrace();
		}
	}

}

