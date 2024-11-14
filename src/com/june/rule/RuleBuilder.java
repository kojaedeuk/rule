package com.june.rule;

/**
 * 규칙 작성
 */
public class RuleBuilder {
	private Condition condition;

	/**
	 * 생성자 
	 * 
	 * @param condition
	 */
	private RuleBuilder(final Condition condition) {
		this.condition = condition;
	}

	/**
	 * 조건절
	 * 
	 * @param condition 조건
	 * @return 규칙 작성기
	 */
	public static RuleBuilder when(final Condition condition) {
		return new RuleBuilder(condition);
	}

	/**
	 * 결과절
	 * 
	 * @param action
	 * @return 규칙
	 */
	public Rule then(final Action action) {
		return new DefaultRule(condition, action);
	}
}
