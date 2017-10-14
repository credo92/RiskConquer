package com.risk.validate;

import com.risk.constant.MapConstant;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.map.util.MapUtil;

public class MapValidator {

	public static boolean validateMap(Map map) {
		
		for (Continent continent : map.getContinents()) {
			if (continent == null) {
				MapUtil.infoBox("There should be atleast one continent", MapConstant.ERROR, "bye bye");
			}
		}
		
		return false;
	}
}
