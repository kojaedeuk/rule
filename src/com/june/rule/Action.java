package com.june.rule;

import java.util.HashMap;

/**
 * Action : 팩트와 조건의 조합을 통해 나온 결과값에 따라 수행하는 작업들 입니다.
 */
public interface Action
{
	/**
	 * 실행
	 * 
	 * @param fact 사실
	 * @return 처리결과
	 */
	HashMap<String, Object> execute(Fact fact);
}
