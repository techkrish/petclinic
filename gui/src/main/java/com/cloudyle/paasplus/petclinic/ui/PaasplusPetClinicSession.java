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

import java.util.Locale;
import java.util.ResourceBundle;

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



  /*
   * (non-Javadoc)
   * 
   * @see com.cloudyle.paasplus.ui.framework.BasicFrameworkSession#loadBundle(java.lang.String, java.util.Locale)
   */
  @Override
  public ResourceBundle loadBundle(final String bundleName, final Locale locale)
  {
    return ResourceBundle.getBundle(bundleName, getLocale());
  }



  // @Override
  // public void terminateSession()
  // {
  // setAttribute(USER_OBJECT, null);
  // }

  @Override
  public String getDefaultBundleName()
  {
    return "language.PetClinicBundle";
  }



  @Override
  public Locale getLocale()
  {
    return Locale.ENGLISH;
  }

}