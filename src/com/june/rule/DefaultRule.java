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
