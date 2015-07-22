/**
 * @(#) $Id: NamingStrategie.java,v 1.0 24.02.2015 15:16:16 haraldmaida $
 *
 * Copyright (c) 2014-2015 Innoave.com
 * 
 *
 * Changes:
 *
 * $Log: NamingStrategie.java,v $
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
public interface NamingStrategie {

	<T extends LinkComponent> ComponentNameBuilder<T> forComponent(Class<T> component); 
	
	interface ComponentNameBuilder<T> {
		
		StepComponentNameBuilder<T> withName(String name);
		
		StepComponentNameBuilder<T> withNumber(int number);
		
		String withStepName(String stepName);

	}
	
	interface StepComponentNameBuilder<T> {
		
		String withStepName(String stepName);
		
	}
	
}
