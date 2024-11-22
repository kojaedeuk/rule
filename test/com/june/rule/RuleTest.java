/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule RuleTest.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.june.calc.KCalc;

class RuleTest {

	@Test
	void shouldPerformAnActionWithBuilder()
	{
		//로깅 여부 세팅
		KCalc.setLogging(true);

		/*
		 * 수정보험료 이벤트 선언(다건)
		 */
		final Rule rule = RuleBuilder.when(new ConditionCalcHandler()).then(new ActionCalcHandler());

		/*
		 * 수정율 읽기
		 */
		List<HashMap<String, Object>> list = getList();

		/*
		 * 대상건 조회
		 */
		HashMap<String, Object> map = getMap();
		
		/*
		 * Fact 생성
		 */
	    Fact fact = new Fact(list, map);
	    
	    /*
	     * rule 실행
	     */
	    HashMap<String, Object> res = rule.run(fact);
	    System.out.println(">>>>"+res.get("aPremium")); 

		/*
		 * 대상건 조회2 및 fact 세팅
		 */
		fact.setMap(getMap2());
		fact.setValue("aPremium", res.get("aPremium")); //수정보험료
		
	    /*
	     * rule 실행
	     */
	    res = rule.run(fact);
	    
		System.out.println(">>>>"+res.get("charge")); 

	}

	/**
	 * 대상건2 map
	 * @return map
	 */
	private HashMap<String, Object> getMap2()
	{
		HashMap<String, Object> map = new HashMap<>();
	    
		map.put("pRate", 0.1); //지급율
		map.put("condition", "1"); //조건식
		map.put("formula", "pRate * aPremium"); //계산식
		map.put("res_col", "charge"); //반환 컬럼
		map.put("clf_cd", "01"); //분류코드(01:단건,02:다건)
		
		return map;
	}
	
	/**
	 * 대상건 map
	 * @return map
	 */
	private HashMap<String, Object> getMap()
	{
		HashMap<String, Object> map = new HashMap<>();
		map.put("premium", 100000); //보험료
		map.put("pod", "A001"); //상품코드
		map.put("condition", "@pod == 'A001' && @pPeriod == 12"); //조건식
		map.put("formula", "@cRate * premium"); //계산식
		map.put("res_col", "aPremium"); //반환 컬럼
		map.put("clf_cd", "02"); //분류코드(01:단건,02:다건)
		
		return map;
	}
	
	/**
	 * 수정율 list
	 * @return 수정율
	 */
	private List<HashMap<String, Object>> getList()
	{
		List<HashMap<String, Object>> list = new ArrayList<>();
		for(int i=0; i<20; i++)
		{
			HashMap<String, Object> map_sub = new HashMap<>();
			map_sub.put("pod", "A001"); //상품코드
			map_sub.put("pPeriod", 1+i); //납입회차
			map_sub.put("cRate", i*0.1); //수정율
			list.add(map_sub);
		}
		
		return list;
	}
}
