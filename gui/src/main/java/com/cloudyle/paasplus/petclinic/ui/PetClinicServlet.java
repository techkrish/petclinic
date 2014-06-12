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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.cloudyle.paasplus.ui.framework.VaadinOSGiHelper;
import com.vaadin.server.VaadinServlet;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.annotation.WebServlet;

/**
 * @author ag
 * 
 */
// @WebServlet(urlPatterns = "/*", initParams = { @WebInitParam(name = "UI",
// value =
// "com.cloudyle.paasplus.test.vaadin.bmi.VaadinBmi") })
public class PetClinicServlet extends VaadinServlet {

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		VaadinOSGiHelper.clearRPCCache();
		// TODO Auto-generated method stub
		super.init(servletConfig);
		System.out.println("Petclinic Servlet Started");
	}

}
