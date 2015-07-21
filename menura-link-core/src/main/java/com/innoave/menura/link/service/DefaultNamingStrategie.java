/**
 * @(#) $Id: DefaultNamingStrategie.java,v 1.0 24.02.2015 15:16:47 haraldmaida $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: DefaultNamingStrategie.java,v $
 * Revision 1.0  2011/10/03  haraldmaida
 * none
 *
 */
package com.innoave.menura.link.service;

import com.innoave.menura.link.api.LinkComponent;
import com.innoave.menura.link.api.NamingStrategie;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class DefaultNamingStrategie implements NamingStrategie {
	
	protected static String componentSeparator = ":";
	protected static String empty = "";
	
	@Override
	public <T extends LinkComponent> ComponentNameBuilder<T> forComponent(final Class<T> component) {
		return new DefaultComponentNameBuilder<T>(component);
	}
	
	public class DefaultComponentNameBuilder<T> implements ComponentNameBuilder<T> {
		
		private final Class<T> type;

		private DefaultComponentNameBuilder(final Class<T> type) {
			this.type = type;
		}
		
		@Override
		public StepComponentNameBuilder<T> withName(final String name) {
			return new DefaultStepComponentNameBuilder<T>(this, name);
		}
		
		@Override
		public StepComponentNameBuilder<T> withNumber(final int number) {
			return new DefaultStepComponentNameBuilder<T>(this, String.valueOf(number));
		}
		
		@Override
		public String withStepName(final String stepName) {
			return new DefaultStepComponentNameBuilder<T>(this, empty)
					.withStepName(stepName);
		}
		
	}
	
	public class DefaultStepComponentNameBuilder<T> implements StepComponentNameBuilder<T> {

		private final DefaultComponentNameBuilder<T> componentNameBuilder;
		private final String name;
		
		private DefaultStepComponentNameBuilder(
				final DefaultComponentNameBuilder<T> componentNameBuilder,
				final String name
				) {
			this.componentNameBuilder = componentNameBuilder;
			this.name = name;
		}
		
		@Override
		public String withStepName(final String stepName) {
			return new StringBuilder()
					.append(componentNameBuilder.type.getSimpleName())
					.append(componentSeparator)
					.append(name == null ? empty : name)
					.append(componentSeparator)
					.append(stepName)
					.toString();
		}
		
	}
	
}
