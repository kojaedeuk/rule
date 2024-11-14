package com.june.rule;

import java.util.HashMap;

/**
 * 규칙 실행 
 */
public interface Rule
{
	/**
	 * 규칙 실행
	 * @param facts 사실
	 * @return 결과값
	 */
	HashMap<String, Object> run(Fact facts);
}
