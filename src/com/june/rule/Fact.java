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

	private int str_index;
	private String token;
	private String type;
	
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
	 * 목록 반환
	 * 
	 * @return
	 */
	List<HashMap<String, Object>> getList() {
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
	 * 조건절을 치환한다.
	 * 
	 * @return 조건절
	 */
	String replaceCondition() {
		if (isNull(getMap().get("condition")))
			return null;

		setStrIndex(0);

		return replace(trimToValue(String.valueOf(getMap().get("condition"))));
	}

	/**
	 * 계산식을 치환한다.
	 * 
	 * @return 계산식
	 */
	String replaceFormula() {
		if (isNull(getMap().get("formula")))
			return null;

		setStrIndex(0);

		return replace(trimToValue(String.valueOf(getMap().get("formula"))));
	}
	
	/**
	 * @param index
	 */
	private void setStrIndex(int index)
	{
		this.str_index = index;
	}

	/**
	 * 조건식에서 map에 있는 값은 상수로 치환 하고, list에 있는 값은 읽을 수 있도록 변환
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	private String replace(String inStr) {
		StringBuffer resultStr = new StringBuffer();
		int strLength = inStr.length();
		getToken(inStr);

		for (int i = 0; i < strLength; i++) {
			if ("VARIABLE".equals(type)) {
				if (token.indexOf("@") >= 0) {
					resultStr.append(token.substring(1));
				} else {
					resultStr.append(map.get(token));
				}
			} else {
				resultStr.append(token);
			}

			getToken(inStr);
			if (strLength <= i || token.equals("EOL"))
				break;
		}
		return resultStr.toString();
	}

	/**
	 * arg is null
	 * 
	 * @param arg
	 * @return
	 */
	private boolean isNull(Object obj) {
		boolean isNull = false;
		String str = String.valueOf(obj);
		if (str == null || str.length() == 0)
			isNull = true;

		return isNull;
	}

	/**
	 * <pre>
	 * isDelim 설명 : 부호 인지 여부 체크
	 * Return true if c is a delimiter.
	 * </pre>
	 *
	 * @author 고재득
	 * @param char
	 * @return boolean
	 */
	private boolean isDelim(char c) {
		switch (c) {
		case ' ':
			// 산술 연산자
		case '+':
		case '*':
		case '/':
		case '%':
		case '-':
			// 최우선 연산자
		case '(':
		case ')':
			// 지수 연산자
		case '^':
		case '√':
			// 관계 연산자
		case '<':
		case '=':
		case '>':
		case '!':
			// 논리 연산자
		case '|':
		case '&':
			// 삼항 연산자
		case '?':
		case ':':
			// 순차 연산자
		case ',':
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * 입력값의 사이사이에 존재하는 공백을 제거한다.
	 * </pre>
	 *
	 * @author 고재득
	 * @param
	 * @return String
	 */
	private String trimToValue(String value) {
		String resultValue = "";

		if (null == value || value.length() == 0)
			return resultValue;

		int length = value.length();
		char[] chars = new char[length];
		char[] newChars = new char[length];
		value.getChars(0, length, chars, 0);
		char ch;
		int index = 0;
		int inx = 0;
		for (index = 0; index < length; index++) {
			ch = chars[index];
			if (ch != ' ' && ch != '\t') {
				newChars[inx] = ch;
				inx++;
			} // end if

		} // end for

		resultValue = new String(newChars, 0, inx);

		return resultValue;
	}

	/**
	 * <pre>
	 * 함수명 : getToken 설명 : 구간 값구하고, 문자 형식세팅
	 * Obtain the next token.
	 * </pre>
	 *
	 * @author 고재득
	 * @param inStr 입력 문자열
	 */
	private void getToken(String inStr) {
		token = "";

		// Check for end of inStr.
		if (str_index == inStr.length()) {
			token = "EOL";
			return;
		}

		char str = inStr.charAt(str_index); // 연산자 가져오기

		if (str == '<' || str == '>') {
			if (str_index + 1 == inStr.length()) {
				throw new RuleException("Syntax Error");
			}

			switch (str) {
			case '<': // 작다 <
				if (inStr.charAt(str_index + 1) == '=') // 같다 =
				{
					str_index = str_index + 2;
					token = "<="; // 작거나 같다 <=
				} else {
					str_index = str_index + 1;
					token = "<"; // 작다 <
				}
				type = "RELATIONAL";
				break;
			case '>': // 크다 >
				if (inStr.charAt(str_index + 1) == '=') // 같다 =
				{
					str_index = str_index + 2;
					token = ">="; // 크거나 같다 >=
				} else {
					str_index = str_index + 1;
					token = ">"; // 크다 >
				}
				type = "RELATIONAL";
				break;
			}
		} else if (str == '=') {
			if (inStr.charAt(str_index + 1) == '=') // 같다
			{
				str_index = str_index + 2;
				token = "=="; // 같다
			}
			type = "RELATIONAL";
		} else if (str == '!') {
			if (inStr.charAt(str_index + 1) == '=') // 크다>
			{
				str_index = str_index + 2;
				token = "!="; // 같지 않다
			} else {
				str_index = str_index + 1;
				token = "!"; // 부정
			}
			type = "RELATIONAL";
		} else if (str == '|') {
			if (inStr.charAt(str_index + 1) == '|') // OR
			{
				str_index = str_index + 2;
				token = "||"; // OR
				type = "LOGICAL";
			}
		} else if (str == '&') {
			if (inStr.charAt(str_index + 1) == '&') // AND
			{
				str_index = str_index + 2;
				token = "&&"; // AND
				type = "LOGICAL";
			}
		} else if ('\'' == str) {
			str_index = str_index + 1; // \
			StringBuffer buffer = new StringBuffer();
			buffer.append("'");
			while (true) {
				buffer.append(inStr.charAt(str_index));
				str_index = str_index + 1;
				if ('\'' == inStr.charAt(str_index)) {
					break;
				}
				if (str_index >= inStr.length()) {
					throw new RuleException("end of string");
				}
			} // end while
			buffer.append("'");
			token = buffer.toString();
			str_index = str_index + 1;
			type = "STRING";
		} else if (isDelim(str)) { // is operator
			token = token + str;
			str_index = str_index + 1;
			type = "DELIMITER";
		} else if (Character.isLetter(inStr.charAt(str_index)) || '@' == str) { // is variable
			next(inStr);
			if ("KEYWORD".equals(type)) {
				type = "KEYWORD";
			} else {
				type = "VARIABLE";
			}
		} else if (Character.isDigit(str)) { // is number
			next(inStr);
			type = "NUMBER";
		} else { // unknown character terminates inStr
			type = "EOL";
		}
	}

	/**
	 * <pre>
	 * 다음문자 값 구하기
	 * </pre>
	 *
	 * @author 고재득
	 * @param inStr 입력 문자열
	 */
	private void next(String inStr) {
		while (!isDelim(inStr.charAt(str_index))) {
			token = token + inStr.charAt(str_index);
			str_index = str_index + 1;
			if (str_index >= inStr.length()) {
				break;
			}
		} // end while
	}
}
