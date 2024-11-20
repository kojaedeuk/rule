/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule ActionMaptHandler.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.HashMap;

import com.june.calc.KCalc;

/**
 * 실행 핸들러(다건)
 */
public class ActionMaptHandler implements Action {

	@Override
	public HashMap<String, Object> execute(Fact fact)
	{
		String result_column = String.valueOf(fact.getValue("res_col"));
		String formula = StringReplace.replace(fact.getMap(), String.valueOf(fact.getValue("formula")));
		HashMap<String, Object> map = fact.getListMap(); 
		map.put(result_column, KCalc.calculate(map, formula) );
		return map;
	}

}
