package com.june.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class RuleTest {

	@Test
	void shouldPerformAnActionWithBuilder()
	{
		/*
		 * 수정보험료 이벤트 선언(다건)
		 */
		final Rule ruleMap = RuleBuilder.when(new ConditionMapHandler()).then(new ActionCalcHandler());

		/*
		 * 수정율 읽기
		 */
		List<HashMap<String, Object>> list = getList();

		/*
		 * 대상건 조회
		 */
		HashMap<String, Object> map = getMap();
//		System.out.println("map >>>>"+map); 
		
		/*
		 * Fact 생성
		 */
	    Fact fact = new Fact(list, map);
	    
	    /*
	     * rule 실행
	     */
	    HashMap<String, Object> res = ruleMap.run(fact);
	    
//		System.out.println("res >>>>"+res); 

		/*
		 * 수수료 이벤트 선언(단건)
		 */
		final Rule rule = RuleBuilder.when(new ConditionHandler()).then(new ActionCalcHandler());
		/*
		 * 대상건 조회2 및 fact 세팅
		 */
		HashMap<String, Object> map2 = getMap2();
		fact.setValue("res_col", map2.get("res_col"));
		fact.setValue("pRate", map2.get("pRate"));
		fact.setValue("condition", map2.get("condition")); 
		fact.setValue("clf_cd", map2.get("clf_cd")); //분류코드(01:단건,02:다건)
		fact.setValue("formula", map2.get("formula"));
		fact.setValue("aPremium", res.get("aPremium")); //수정보험료
//		System.out.println("map >>>>"+fact.getMap()); 
		
	    /*
	     * rule 실행
	     */
	    res = rule.run(fact);
	    
//		System.out.println(">>>>"+res.get("charge")); 

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
