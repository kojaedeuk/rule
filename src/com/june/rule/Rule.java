/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule Rule.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
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
