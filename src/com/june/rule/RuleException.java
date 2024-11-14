package com.june.rule;

/**
 * 규칙 예외
 */
public class RuleException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * 생성자
	 */
	public RuleException() {
		super();
	}

	/**
	 * 생성자
	 * @param message 메세지
	 * @param cause 예외
	 */
	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 생성자
	 * @param message 메세지
	 */
	public RuleException(String message) {
		super(message);
	}
}
