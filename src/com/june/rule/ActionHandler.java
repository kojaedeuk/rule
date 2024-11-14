package com.june.rule;

import java.util.HashMap;

import com.june.calc.KCalc;

/**
 * 실행 핸들러(단건)
 */
public class ActionHandler implements Action {

	@Override
	public HashMap<String, Object> execute(Fact fact) {
		String result_column = String.valueOf(fact.getValue("res_col"));
		HashMap<String, Object> map = new HashMap<>();
		map.put(result_column, KCalc.calculate(map, fact.replaceFormula()) );
		return map;
	}

}
