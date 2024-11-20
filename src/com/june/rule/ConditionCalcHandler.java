package com.june.rule;

import java.math.BigDecimal;
import java.util.HashMap;

import com.june.calc.KCalc;

public class ConditionCalcHandler implements Condition {

	@Override
	public boolean evaluate(Fact fact) {
		boolean isTrue = false;
		String clf_cd = String.valueOf(fact.getValue("clf_cd")); //분류코드(01:단건,02:다건)
		String condition = null; //조건문
		
		if("01".equals(clf_cd)) //단건
		{
			condition = String.valueOf(fact.getValue("condition")); //조건식을 변환 한다.
			isTrue = ((BigDecimal)KCalc.calculate(fact.getMap(), condition)).compareTo(BigDecimal.ZERO) > 0;
		}
		else //다건
		{
			condition = StringReplace.replace(fact.getMap(), String.valueOf(fact.getValue("condition"))); //조건식을 변환 한다.
			for(HashMap<String, Object> map : fact.getList())
			{
				if( ((BigDecimal)KCalc.calculate(map, condition)).compareTo(BigDecimal.ZERO) > 0 ) 
				{
					isTrue = true;
					break;
				}
				fact.nextListIndex();
			}
		}
		
		return isTrue;
	}

}
