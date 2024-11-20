/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule DefaultRule.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.HashMap;

/**
 * 기본규칙
 */
public class DefaultRule implements Rule
{
	private final Condition condition;
	private final Action action;

	/**
	 * 생성자
	 * 
	 * @param condition 조건
	 * @param action 실행
	 */
	public DefaultRule(final Condition condition, final Action action)
	{
		this.condition = condition;
		this.action = action;
	}

	@Override
	public HashMap<String, Object> run(Fact facts)
	{
		HashMap<String, Object> res = new HashMap<>();
		if (condition.evaluate(facts))
		{
			res = action.execute(facts);
		}
		return res;
	}

}
