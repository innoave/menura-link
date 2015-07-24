/**
 *  Copyright (c) 2015 Innoave.com
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

import java.util.concurrent.TimeUnit;

import com.innoave.menura.link.api.InternalMessageQueue;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;
import com.innoave.menura.link.api.LinkMessageHandler;
import com.innoave.menura.link.api.SystemAdapter;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class VerificationTestStepExecutor extends AbstractTestStepExecutor {
	
	
	public VerificationTestStepExecutor(
			final String name,
			final LinkMessage message,
			final LinkMessageHandler messageHandler,
			final SystemAdapter systemAdapter
			) {
		super(name, message, messageHandler, systemAdapter);
	}
	
	@Override
	public void execute(final SessionContext context) throws LinkException {
		final InternalMessageQueue internalMessageQueue = getSystemAdapter().getInternalMessageQueue();
		try {
			final LinkMessage receivedMessage = internalMessageQueue.poll(
					context.getInternalMessageTimeout(), TimeUnit.SECONDS);
			if (receivedMessage == null) {
				throw new LinkException("Timeout on waiting for response message in verification step: " + getName());
			}
			getMessageHandler().handleMessage(receivedMessage);
		} catch (InterruptedException e) {
			throw new LinkException("Interrupted waiting for response message in verification step: " + getName(), e);
		}
	}

}
