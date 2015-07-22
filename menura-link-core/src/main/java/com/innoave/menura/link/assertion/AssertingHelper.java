/**
 * @(#) $Id: AssertingHelper.java 9875 2014-11-24 11:51:53Z maida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: AssertingHelper.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.assertion;

/**
 *
 *
 * @version $Revision: 9875 $
 * @author haraldmaida
 * @author $Author: maida $
 *
 */
public class AssertingHelper {
	
	
	public static boolean assertEquals(final String expected, final String actual) {
		if (expected == null) {
			if (actual != null) {
				return false;
			}
		} else {
			if (actual == null) {
				return false;
			}
		}
		return expected.equals(actual); 
	}

}
