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
 * @version $Revision: 1.0 $
 * @author haraldmaida
 * @author $Author: haraldmaida $
 *
 */
public class TestStep {
	
	private String name;
	
	private FunctionalType type;

	private Class<SystemAdapter> systemAdapter;
	
	private Class<LinkMessageHandler> messageHandler;
	
	private LinkMessage message;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FunctionalType getType() {
		return type;
	}

	public void setType(FunctionalType type) {
		this.type = type;
	}

	public Class<SystemAdapter> getSystemAdapter() {
		return systemAdapter;
	}

	public void setSystemAdapter(Class<SystemAdapter> systemAdapter) {
		this.systemAdapter = systemAdapter;
	}

	public Class<LinkMessageHandler> getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(Class<LinkMessageHandler> messageHandler) {
		this.messageHandler = messageHandler;
	}

	public LinkMessage getMessage() {
		return message;
	}

	public void setMessage(LinkMessage message) {
		this.message = message;
	}
	
}
