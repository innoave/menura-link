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
