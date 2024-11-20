/**------------------------------------------------------------------------------
 * PROJ : JUNE PROJECT
 * NAME : com.june.rule StringReplace.java
 * DESC : Rule system engine Project
 * VER  : v2.0
 * Copyright 2000 JUNE All rights reserved
 *------------------------------------------------------------------------------
 */
package com.june.rule;

import java.util.HashMap;

/**
 * 문자열 교체
 * list 형태의 자료형에서 조건을 비교할 때 단 한번만 파싱을 하기 위해 단건 변수를 상수로 치환하고, 다건 변수는 단건 변수 형으로 치환 한다.
 * "@"키워드가 있는 문자열은 단건 문자열 변수로 변경, 일반 변수는 상수로 치환 한다.   
 */
public class StringReplace
{
	private static int str_index;
	private static String token;
	private static String type;

	/**
	 * 조건식에서 map에 있는 값은 상수로 치환 하고, list에 있는 값은 읽을 수 있도록 변환
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String replace(HashMap<String, Object> map, String inStr)
	{
		if (isNull(inStr))
			return null;
		
		setStrIndex(0);
		StringBuffer resultStr = new StringBuffer();
		String varStr = trimToValue(inStr);
		int strLength = varStr.length();
		getToken(varStr);

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

			getToken(varStr);
			if (strLength <= i || token.equals("EOL"))
				break;
		}
		return resultStr.toString();
	}
	
	/**
	 * @param index
	 */
	private static void setStrIndex(int index)
	{
		str_index = index;
	}
	
	/**
	 * arg is null
	 * 
	 * @param arg
	 * @return
	 */
	private static boolean isNull(Object obj) {
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
	private static boolean isDelim(char c) {
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
	private static String trimToValue(String value) {
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
	private static void getToken(String inStr) {
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
	private static void next(String inStr) {
		while (!isDelim(inStr.charAt(str_index))) {
			token = token + inStr.charAt(str_index);
			str_index = str_index + 1;
			if (str_index >= inStr.length()) {
				break;
			}
		} // end while
	}
}
