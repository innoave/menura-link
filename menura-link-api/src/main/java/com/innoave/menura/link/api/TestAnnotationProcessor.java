/**
 * @(#) $Id: TestAnnotationProcessor.java,v 1.0 25.02.2015 13:10:19 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: TestAnnotationProcessor.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.api;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class TestAnnotationProcessor {

	
	public FunctionalType determineFunctionalType(final Class<?> annotatedClazz) {
		if (annotatedClazz == null) {
			throw new IllegalArgumentException("AnnotatedClazz must not be null");
		}
		final Test test = annotatedClazz.getAnnotation(Test.class);
		if (test == null) {
			return null;
		}
		return test.value();
	}
	
}
