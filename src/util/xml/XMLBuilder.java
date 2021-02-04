package util.xml;

import java.util.ArrayList;

/**
 * Simple XML string builder
 */
final public class XMLBuilder {

	/**
	 * Store of the open tags
	 */
	private ArrayList<String> tagDepth;


	/**
	 * Store of the current xml string
	 */
	private StringBuilder xml;


	/**
	 * Instanciated the internal goods using the reset method
	 */
	public XMLBuilder(){
		reset();
	}


	/**
	 * Add a new opening tag
	 *
	 * @param tag The tag name as a string
	 */
	public void addTag(String tag){
		tagDepth.add(tag);
		xml.append("<").append(tag).append(">");
	}


	/**
	 * Add a value to the current open tag
	 *
	 * @param value The value to add
	 */
	public void addValue(String value){
		String tag = tagDepth.get(tagDepth.size() - 1);
		xml.append(value);
	}


	/**
	 * Close the current open tag
	 */
	public void closeTag(){
		int index = tagDepth.size() - 1;
		if(index == -1) return;
		xml.append("</").append(tagDepth.get(index)).append(">");
		tagDepth.remove(index);
	}


	/**
	 * Automatically closes any remaining tags and returns the XML as a string
	 *
	 * @return The xml as a single string
	 */
	public String finalizeAndReturn(){
		while(tagDepth.size() > 0) closeTag();
		return xml.toString();
	}


	/**
	 * Resets all currently stored tags/xml to start fresh
	 */
	public void reset(){
		tagDepth = new ArrayList<>();
		xml = new StringBuilder();
	}


	/**
	 * Test method to check this is working
	 *
	 * @param a ignored
	 */
	public static void main(String[] a){
		XMLBuilder x = new XMLBuilder();

		x.addTag("layerOne");

		x.addTag("layerTwo");
		x.addValue("asdf");
		x.closeTag();

		x.addTag("layerTwo");
		x.addValue("qwerty");

		x.addTag("layerThree");
		x.addTag("ul");

		x.addTag("li");
		x.addValue("1");
		x.closeTag();

		x.addTag("li");
		x.addValue("2");
		x.closeTag();

		x.addTag("li");
		x.addValue("3");

		String xml = x.finalizeAndReturn();

		System.out.println(xml);
	}

}

