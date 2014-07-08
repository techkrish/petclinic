/*
 * BmiServlet.java
 *
 * Created on 11.11.2013
 *
 * This file is property of the Cloudyle GmbH.
 * (c) 2013 Copyright Cloudyle GmbH.
 * All rights reserved.
 */
package com.cloudyle.paasplus.petclinic.ui;

import com.cloudyle.paasplus.ui.framework.BasicFrameworkServlet;
import com.cloudyle.paasplus.ui.framework.BasicFrameworkServletService;

/**
 * @author ag
 * 
 */
public class PetClinicServlet extends BasicFrameworkServlet {

	@Override
	protected PaasplusPetClinicSession createCustomSession(
			BasicFrameworkServletService vaadinService) {
		return new PaasplusPetClinicSession(vaadinService);
	}

}
