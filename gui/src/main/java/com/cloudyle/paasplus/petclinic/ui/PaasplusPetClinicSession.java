/*
 * PaasplusPetClinicSession.java
 *
 * Created on Jun 18, 2014
 *
 * This file is property of the Cloudyle GmbH.
 * (c) Copyright Cloudyle GmbH.
 * All rights reserved.
 */
package com.cloudyle.paasplus.petclinic.ui;

import com.cloudyle.paasplus.ui.framework.BasicFrameworkServletService;
import com.cloudyle.paasplus.ui.framework.BasicFrameworkSession;


/**
 *
 *
 * @author sl
 * @since
 */
public class PaasplusPetClinicSession extends BasicFrameworkSession
{


  /**
   *
   */
  private static final long serialVersionUID = -1935435094852243802L;

  public static final String USER_OBJECT = "USER";



  public PaasplusPetClinicSession(final BasicFrameworkServletService vaadinService)
  {
    super(vaadinService);
  }



  @Override
  public boolean isAuthenticated()
  {
    return getAttribute(USER_OBJECT) != null;
  }



  @Override
  public void terminateSession()
  {
    setAttribute(USER_OBJECT, null);
  }
}