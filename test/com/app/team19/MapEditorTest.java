package com.app.team19;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import com.risk.map.util.MapUtil;
/**
 * Test Class for MapEditorTest.java 
 * @author Vipul Srivastav
 * 
 *
 */
public class MapEditorTest {
		// Validating file type
		@Test
		public void testTypeFile() {
		File file = MapUtil.showFileChooser();
		String[] tokens = file.getName().split("\\.(?=[^\\.]+$)");
		assertEquals("map",tokens[1]);
		}
		
}