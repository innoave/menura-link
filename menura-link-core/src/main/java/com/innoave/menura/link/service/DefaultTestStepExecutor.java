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

import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.SystemAdapter;
import com.innoave.menura.link.api.TestStepExecutor;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class DefaultTestStepExecutor implements TestStepExecutor {
	
	private final String name;
	
	private final LinkMessage message;
	
	private final LinkMessageHandler messageHandler; 
	
	private final SystemAdapter systemAdapter;
	
	
	public DefaultTestStepExecutor(
			final String name,
			final LinkMessage message,
			final LinkMessageHandler messageHandler,
			final SystemAdapter systemAdapter
			) {
		this.name = name;
		this.message = message;
		this.messageHandler = messageHandler;
		this.systemAdapter = systemAdapter;
	}
	
	public String getName() {
		return name;
	}
	
	public LinkMessage getMessage() {
		return message;
	}
	
	public LinkMessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	public SystemAdapter getSystemAdapter() {
		return systemAdapter;
	}
	
	@Override
	public void execute() {
		messageHandler.handleMessage(message);
	}
	
}
