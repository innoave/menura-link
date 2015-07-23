/**
 * @(#) $Id: SessionContextTest.java,v 1.0 23.07.2015 18:53:21 maidahar $
 *
 * Copyright (c) 2015 Versicherungsanstalt Oeffentlich Bediensteter
 * 
 *
 * Aenderungshistorie:
 *
 * $Log: SessionContextTest.java,v $
 * Revision 1.0  2011/10/03  maidahar
 * none
 *
 */
package com.innoave.menura.link.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 * @version $Revision: 1.0 $
 * @author maidahar
 * @author $Author: maidahar $
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
