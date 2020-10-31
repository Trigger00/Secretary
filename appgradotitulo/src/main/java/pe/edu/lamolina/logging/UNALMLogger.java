package pe.edu.lamolina.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class UNALMLogger {

	public static void entry(Logger log, String method) {
		log.trace(method + " ENTRY");
	}

	public static void entry(Logger log, String method, String params, Object[] data) {
		log.trace(method + " ENTRY: (" + params + ") [" + getString(data) + "]");
	}

	public static void exit(Logger log, String method) {
		log.trace(method + " EXIT");
	}

	public static void exit(Logger log, String method, Object data) {
		log.trace(method + " RETURN: " + " [" + getString(data) + "]");
	}

	public static void trace(Logger log, String method, String message) {
		log.trace(method + " " + message);
	}

	public static void info(Logger log, String method, String message) {
		log.trace(method + " " + message);
	}

	public static void debug(Logger log, String method, String message) {
		log.trace(method + " " + message);
	}

	public static void error(Logger log, String method, String message, Exception e) {
		log.error(method + " " + message);
		if (Level.ERROR.isGreaterOrEqual(log.getEffectiveLevel())) {
			e.printStackTrace();
		}
	}

	private static String getString(final Object obj) {
		String finalString = "";
		if (obj != null) {
			if (obj instanceof Object[]) {
				finalString += "[";
				Object[] array = (Object[]) obj;
				for (int i = 0; i < array.length; i++) {
					Object item = array[i];
					if (i > 0) {
						finalString += ",";
					}
					finalString += getString(item);
				}
				finalString += "]";
			} else {
				finalString += obj.toString();
			}
		} else {
			finalString = "NULL";
		}
		return finalString;
	}
}
