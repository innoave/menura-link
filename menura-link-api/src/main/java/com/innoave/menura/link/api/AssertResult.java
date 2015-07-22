/**
 *  Copyright (c) 2014 Innoave.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @author haraldmaida
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
