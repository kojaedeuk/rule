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

/**
 * 실행 핸들러(단건)
 */
public class ActionCalcHandler implements Action {

	@Override
	public HashMap<String, Object> execute(Fact fact)
	{
		String clf_cd = String.valueOf(fact.getValue("clf_cd")); //분류코드(01:단건,02:다건)
		String result_column = String.valueOf(fact.getValue("res_col")); //반환컬럼명
		String formula = null;
		HashMap<String, Object> map = null;
		if("01".equals(clf_cd)) //단건
		{
			formula = String.valueOf(fact.getValue("formula"));
			map = fact.getMap();
		}
		else //다건
		{
			formula = StringReplace.replace(fact.getMap(), String.valueOf(fact.getValue("formula")));
			map = fact.getListMap(); 
			
		}
		map.put(result_column, KCalc.calculate(map, formula) );
		
		return map;
	}

}
