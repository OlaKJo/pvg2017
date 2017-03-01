package main.java.sorter;

/**
 * Parser parses participant files and time stamps files to find different
 * fields (such as names, times and start number) separated by "; "
 * 
 * @author team03
 */
public class Parser {

	/**
	 * Separates strings on "; ".
	 * 
	 * @param string
	 *            input to be separated into substring.
	 * @return array of split strings.
	 */
	public static String[] parse(String line) {
		if (line == null || line == "") {
			throw new NullPointerException();
		}
		String[] substring = line.split("; ");
		return substring;
	}
}
