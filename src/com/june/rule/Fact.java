/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule Fact.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fact : 애플리케이션에서 발생하는 여러 정보들 입니다.
 */
public class Fact
{
	private final List<HashMap<String, Object>> list;
	private final HashMap<String, Object> map;
	private int list_index;
	
	/**
	 * 생성자
	 */
	public Fact()
	{
		this(new ArrayList<>(), new HashMap<String, Object>());
	}
	
	/**
	 * 생성자
	 * 
	 * @param facts 입력값 단건
	 */
	public Fact(HashMap<String, Object> fact) {
		this(null, fact);
		this.list_index = 0;
	}
	
	/**
	 * 생성자
	 * 
	 * @param list 입력값(목록)
	 * @param facts 입력값 단건
	 */
	public Fact(List<HashMap<String, Object>> list, HashMap<String, Object> fact) {
		super();
		this.list = list;
		this.map = fact;
		this.list_index = 0;
	}

	/**
	 * 입력 문자열에 해당하는 값 반환
	 * 
	 * @param name 입력 문자열
	 * @return 결과값
	 */
	public Object getValue(String name) {
		return getMap().get(name);
	}

	/**
	 * Map객체에 입력문자열(name)에 해당하는 입력값(obj)을 세팅한다.
	 * 
	 * @param name 입력문자열
	 * @param obj 입력값
	 * @return 결과값
	 */
	public Object setValue(String name, Object obj) {
		return getMap().put(name, obj);
	}

	/**
	 * map에서 입력문자열에 해당하는 값을 반환 한다.
	 * 
	 * @param name 입력문자열 
	 * @return 결과값
	 */
	public Object getListValue(String name) {
		return getListMap().get(name);
	}

	/**
	 * 목록에서 조건절에 해당하는 map 반환
	 * 
	 * @return 결과값
	 */
	public HashMap<String, Object> getListMap() {
		return getList().get(getListIndex());
	}

	/**
	 * 목록 반환
	 * 
	 * @return
	 */
	public List<HashMap<String, Object>> getList() {
		return list;
	}

	/**
	 * map 반환
	 * 
	 * @return
	 */
	public HashMap<String, Object> getMap() {
		return map;
	}

	/**
	 * 조건절에 맞는 색인 반환
	 * 
	 * @return
	 */
	int getListIndex() {
		return list_index;
	}

	/**
	 * 조건절에 맞는 색인 설정
	 * 
	 * @param index
	 */
	void setListIndex(int index) {
		this.list_index = index;
	}

	/**
	 * 다음 색인 
	 */
	void nextListIndex() {
		list_index++;
	}

}
