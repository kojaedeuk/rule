/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule Condition.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

/**
 * Condition : 팩트를 기준으로 판단을 수행할 때 사용하는 기준들 입니다.
 */
public interface Condition
{
    /**
     * 기준을 평가
     * 
	 * @param fact 사실 입력값
	 * @return 사실여부
	 */
	boolean evaluate(Fact fact);
}
