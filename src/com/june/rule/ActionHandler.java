/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule ActionHandler.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.HashMap;

import com.june.calc.KCalc;

public class ActionHandler implements Action {

	@Override
	public HashMap<String, Object> execute(Fact fact) {
		String result_column = String.valueOf(fact.getValue("res_col"));
		String formula = String.valueOf(fact.getValue("formula"));
		HashMap<String, Object> map = fact.getMap();
		map.put(result_column, KCalc.calculate(map, formula) );
		
		return map;
	}

}
