/**
 * 
 */
package pe.edu.lamolina.util;

/**
 * @author emiprav
 *
 */
public class StringUtils {


	/**
	 * returns true if input is null or blank
	 * @param input
	 * @return boolean
	 */
	public static boolean isNullOrBlank(String input) {

		boolean result = false;

		if (input != null && input.trim().length() != 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
}
