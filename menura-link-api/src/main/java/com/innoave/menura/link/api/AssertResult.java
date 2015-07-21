/**
 * @(#) $Id: AssertResult.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: AssertResult.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class AssertResult {

	private final ResultCode resultCode;
	
	private final String description;
	
	private final String propertyName;
	
	private final Object expectedValue;
	
	private final Object actualValue;

	
	public AssertResult(
			final ResultCode resultCode,
			final String description,
			final String propertyName,
			final Object expectedValue,
			final Object actualValue
			) {
		this.resultCode = resultCode;
		this.description = description;
		this.propertyName = propertyName;
		this.expectedValue = expectedValue;
		this.actualValue = actualValue;
	}
	
	public final ResultCode getResultCode() {
		return resultCode;
	}
	
	public final String getDescription() {
		return description;
	}
	
	public final String getPropertyName() {
		return propertyName;
	}
	
	public final Object getExpectedValue() {
		return expectedValue;
	}
	
	public final Object getActualValue() {
		return actualValue;
	}
	
	public static enum ResultCode {
		
		OK,
		ERROR,
		ATTRIBUTE;
		
	}
	
}
