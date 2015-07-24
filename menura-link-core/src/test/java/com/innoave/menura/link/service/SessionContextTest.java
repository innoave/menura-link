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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 * @author haraldmaida
 *
 */
public class SessionContextTest {
	
	private SessionContext s1, s2, s3;
	
	
	@Before
	public void setUp() {
		SessionContext.resetSessionIdCounter();
	}
	
	@Test
	public void testGetSesssionId_OneInstance() {
		
		s1 = SessionContext.instance();
		
		assertEquals("0", s1.getSessionId());
		
		s2 = SessionContext.instance();
		
		assertSame(s1, s2);
		assertEquals("0", s2.getSessionId());
	}
	
	@Test
	public void testGetSesssionId_MultipleInstances() throws InterruptedException {
		
		Thread th1 = new Thread() {
			@Override
			public void run() {
				s1 = SessionContext.instance();
			}
		};
		th1.start();
		th1.join();
		
		Thread th2 = new Thread() {
			@Override
			public void run() {
				s2 = SessionContext.instance();
			}
		};
		th2.start();
		th2.join();

		Thread th3 = new Thread() {
			@Override
			public void run() {
				s3 = SessionContext.instance();
			}
		};
		th3.start();
		th3.join();

		assertEquals("0", s1.getSessionId());

		assertNotSame(s1, s2);
		assertEquals("1", s2.getSessionId());
		
		assertNotSame(s2, s3);
		assertEquals("2", s3.getSessionId());
	}
	
}
