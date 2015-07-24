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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoave.menura.link.api.Configuration;
import com.innoave.menura.link.api.InternalMessageQueue;
import com.innoave.menura.link.api.LinkException;
import com.innoave.menura.link.api.LinkMessage;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class DefaultInternalMessageQueue implements InternalMessageQueue {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private BlockingQueue<LinkMessage> messageQueue;
	
	
	public String getName() {
		return getClass().getSimpleName();
	}
	
	@Override
	public void startup(final Configuration configuration) throws LinkException {
		log.debug("Initialisiere {}...", getName());
		final String capacityParam = configuration.get(CoreConfigKey.INTERNAL_MESSAGE_QUEUE_CAPACITY);
		messageQueue = new ArrayBlockingQueue<LinkMessage>(Integer.valueOf(capacityParam));
		log.debug("...Initialisieren von {} beendet", getName());
	}
	
	@Override
	public void shutdown() throws LinkException {
		log.debug("Shutting down {}...", getName());
		messageQueue.clear();
		log.debug("...Shutdown von {} beendet", getName());
	}
	
	@Override
	public void put(final LinkMessage message) throws InterruptedException {
		messageQueue.put(message);
	}
	
	@Override
	public LinkMessage take() throws InterruptedException {
		return messageQueue.take();
	}

	@Override
	public LinkMessage poll(final long timeout, final TimeUnit unit) throws InterruptedException {
		return messageQueue.poll(timeout, unit);
	}
	
}
