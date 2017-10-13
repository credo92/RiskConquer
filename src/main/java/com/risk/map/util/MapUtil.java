package com.risk.map.util;

import javafx.scene.control.TextField;

/**
 * @author rahul
 *
 */
public class MapUtil {
	/**
	 * @param field
	 */
	public static void clearTextField(TextField ... fields) {
		for (TextField field : fields) {
			field.clear();
		}

	}
}
