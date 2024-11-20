/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule ConditionMapHandler.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.math.BigDecimal;
import java.util.HashMap;

import com.june.calc.KCalc;

/**
 * 조건절 핸들러(다건)
 */
public class ConditionMapHandler implements Condition
{
	@Override
	public boolean evaluate(Fact fact)
	{
		return extractTrueList(fact); 
	}
	
	/**
	 * 다건 조건식 체크
	 * @param fact
	 * @return
	 */
	private boolean extractTrueList(Fact fact)
	{
		boolean isTrue = false;

		//조건식을 변환 한다.
		String condition = StringReplace.replace(fact.getMap(), String.valueOf(fact.getValue("condition")));
		for(HashMap<String, Object> map : fact.getList())
		{
			if( ((BigDecimal)KCalc.calculate(map, condition)).compareTo(BigDecimal.ZERO) > 0 ) 
			{
				isTrue = true;
				break;
			}
			fact.nextListIndex();
		}
		return isTrue;
	}
}
