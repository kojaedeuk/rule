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
		 * 다건 처리 예
		 */
		//1. 대상건 조회
		//2. Fact에 값 세팅
		//3. Condition 생성
		//4. Action 생성
		//5. Rule 조회
		final Rule ruleMap = RuleBuilder.when(new ConditionMapHandler()).then(new ActionMaptHandler());

		/*
		 * 조건에 맞는 룰ID찾기
		 */
		List<HashMap<String, Object>> list = new ArrayList<>();
		for(int i=0; i<10; i++)
		{
			HashMap<String, Object> map_sub = new HashMap<>();
			map_sub.put("Aaa", 10+i);
			map_sub.put("Bbb", i+1);
			map_sub.put("Ccc", i);
			list.add(map_sub);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("Bbb", 12);
		map.put("condition", "@Aaa > Bbb && @Bbb == 5"); //조건식
		map.put("formula", "@Aaa *2 + Bbb"); //계산식
		map.put("res_col", "Ccc"); //반환 컬럼
		map.put("clf_cd", "02"); //분류코드(01:단건,02:다건)
	    final Fact fact = new Fact(list, map);
	    HashMap<String, Object> res = ruleMap.run(fact);
		System.out.println(">>>>"+res); 

		/*
		 * 단건 처리 예
		 */
		final Rule rule = RuleBuilder.when(new ConditionHandler()).then(new ActionHandler());
		fact.setValue("Aaa", 10);
		fact.setValue("Bbb", 10);
		fact.setValue("condition", "Aaa >= Bbb"); //조건식
		fact.setValue("formula", "Aaa *2 + Bbb"); //계산식
		fact.setValue("res_col", "Ccc"); //반환 컬럼
		fact.setValue("clf_cd", "01"); //분류코드(01:단건,02:다건)
	    res = rule.run(fact);
	    
		System.out.println(">>>>"+res.get("Ccc")); 

	}
}
