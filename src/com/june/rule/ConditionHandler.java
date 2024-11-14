package com.june.rule;

import java.math.BigDecimal;

import com.june.calc.KCalc;

/**
 * 조건절 핸들러(단건)
 */
public class ConditionHandler implements Condition
{
	@Override
	public boolean evaluate(Fact fact)
	{
		return extractTrue(fact); 
	}
	
	/**
	 * 단건 조건식 체크
	 * 
	 * @param fact 사실
	 * @return 조건결과
	 */
	private boolean extractTrue(Fact fact)
	{
		String condition = String.valueOf(fact.getValue("condition"));
		return ((BigDecimal)KCalc.calculate(fact.getMap(), condition)).compareTo(BigDecimal.ZERO) > 0;
	}
}
