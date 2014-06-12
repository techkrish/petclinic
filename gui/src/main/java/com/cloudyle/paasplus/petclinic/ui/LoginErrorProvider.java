/*
* LoginErrorProvider.java
*
* Created on Apr 2, 2014
*
* This file is property of the Cloudyle GmbH.
* (c) Copyright Cloudyle GmbH.
* All rights reserved.
*/
package com.cloudyle.paasplus.petclinic.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.cloudyle.paasplus.ui.framework.LoginHandler;



/**
 * This ErrorViewProvider will always return the login view.
 *
 * @author sl
 * @since  0.0.1
 */
public class LoginErrorProvider implements ViewProvider
{
  private final Navigator navigator;
  private final LoginHandler loginHandler;



  public LoginErrorProvider(final Navigator loginNavigator, final LoginHandler loginHandler)
  {
    this.navigator = loginNavigator;
    this.loginHandler = loginHandler;
  }



  @Override
  public String getViewName(final String viewAndParameters)
  {
    return viewAndParameters;
  }



  @Override
  public View getView(final String viewName)
  {
    return new LoginView(this.navigator, this.loginHandler);
  }
}