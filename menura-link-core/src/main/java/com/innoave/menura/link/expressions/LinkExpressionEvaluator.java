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
package com.innoave.menura.link.expressions;

import static com.innoave.menura.link.assertion.XpathAsserts.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.innoave.menura.link.api.AssertResult;
import com.innoave.menura.link.api.AssertResult.ResultCode;
import com.innoave.menura.link.assertion.ResultDescriptionBuilder;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class LinkExpressionEvaluator {
	
	private ResultDescriptionBuilder resultDescriptionBuilder = new ResultDescriptionBuilder();

	
	public AssertResult evaluate(final String expression, final String xpath, final Document dom) {
		final AssertResult result;
		if (expression == null) {
			throw new IllegalArgumentException("Expression must not be null");
		}
		if (expression.startsWith("?")) {
			AssertResult.ResultCode resultCode;
			String expected, actual;
			if (expression.contains("exists")) {
				expected = "exists";
				if (assertXpathExists(xpath, dom)) {
					resultCode = AssertResult.ResultCode.OK;
					actual = "!existing";
				} else {
					resultCode = AssertResult.ResultCode.ERROR;
					actual = "!missing";
				}
			} else if (expression.contains("empty")) {
				expected = "empty";
				if (assertXpathNotEmpty(xpath, dom)) {
					resultCode = AssertResult.ResultCode.ERROR;
					actual = "!not empty";
				} else {
					resultCode = AssertResult.ResultCode.OK;
					actual = "!empty";
				}
			} else {
				throw new IllegalArgumentException("Invalid expression: " + expression);
			}
			if (expression.contains("not")) {
				expected = "?not " + expected;
				switch (resultCode) {
				case OK:
					resultCode = AssertResult.ResultCode.ERROR;
					break;
				case ERROR:
					resultCode = AssertResult.ResultCode.OK;
					break;
				case ATTRIBUTE:
				}
			} else {
				expected = "?" + expected;
			}
			final String description = resultDescriptionBuilder.buildDescription(resultCode, xpath, expected, actual);
			result = new AssertResult(resultCode, description, xpath, expected, actual);
			
		} else if (expression.startsWith("$")) {
			final AssertResult.ResultCode resultCode = ResultCode.ATTRIBUTE;
			final String attributeName = expression.substring(1);
			String attributeValue;
			try {
				attributeValue = evaluateXpath(xpath, dom);
			} catch (XPathExpressionException e) {
				attributeValue = null;
			}
			final String description = resultDescriptionBuilder.buildDescription(resultCode, xpath, attributeName, attributeValue);
			result = new AssertResult(resultCode, description, xpath, attributeName, attributeValue);
			
	 	} else {
			final String expected = expression;
			final XPathFactory xpf = XPathFactory.newInstance();
			final XPath xp = xpf.newXPath();
			String actual;
			AssertResult.ResultCode resultCode;
			try {
				actual = xp.evaluate(xpath, dom);
				if (assertXpathEquals(xpath, dom, expression)) {
					resultCode = AssertResult.ResultCode.OK;
				} else {
					resultCode = AssertResult.ResultCode.ERROR;
				}
			} catch (XPathExpressionException e) {
				resultCode = AssertResult.ResultCode.ERROR;
				actual = "!invalid XPath expression";
			}
			final String description = resultDescriptionBuilder.buildDescription(resultCode, xpath, expected, actual);
			result = new AssertResult(resultCode, description, xpath, expected, actual);
		}
		return result;
	}
	
}
