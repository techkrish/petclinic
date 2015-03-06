package com.cloudyle.paasplus.petclinic.ui;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Vet;
import com.cloudyle.paasplus.ui.framework.LoginHandler;
import com.cloudyle.paasplus.ui.framework.language.I18n;
import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class LoginView extends LoginForm implements View
{


  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginView.class);

  private final LoginHandler loginHandler;

  private Window loginWindow;

  private final Navigator navigator;



  public LoginView(final Navigator loginNavigator, final LoginHandler loginHandler)
  {
    super();

    this.loginHandler = loginHandler;
    this.navigator = loginNavigator;
  }



  private boolean checkLoginAndStoreUser(final String userName, final String password)
  {
    try
    {
      logger.debug("[checkLoginAndStoreUser] Retrieving Shiro subject");
      final Subject subject = SecurityUtils.getSubject();
      logger.debug("[checkLoginAndStoreUser] Trying to login user {}", userName);
      subject.login(new UsernamePasswordToken(userName, password));
    }
    catch (final AuthenticationException ex)
    {
      logger.error("[checkLoginAndStoreUser] Unable to authenticate user " + userName, ex);
      return false;
    }

    final Collection<Vet> user = ((PetClinicUI) UI.getCurrent()).getClinicService().findVetByLastName(userName);
    if (user.size() == 0)
    {
      logger.debug("[checkLoginAndStoreUser] Unable to find user " + userName);
      return false;
    }
    VaadinSession.getCurrent().setAttribute("USER", user.iterator().next());
    return true;
  }



  @Override
  protected Component createContent(final TextField userNameField, final PasswordField passwordField,
      final Button loginButton)
  {
    final VerticalLayout layout = new VerticalLayout();
    layout.setSpacing(true);
    layout.setMargin(true);

    layout.addComponent(userNameField);
    layout.addComponent(passwordField);
    layout.addComponent(loginButton);
    layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
    return layout;
  }



  @Override
  public void enter(final ViewChangeEvent event)
  {
    if (this.loginWindow != null)
    {
      this.loginWindow.close();
      this.loginWindow = null;
    }

    final VerticalLayout loginLayout = new VerticalLayout();
    loginLayout.addStyleName("login");
    loginLayout.setSizeFull();
    loginLayout.addComponent(this);
    loginLayout.setComponentAlignment(this, Alignment.MIDDLE_CENTER);

    this.loginWindow = new Window(I18n.getString(LoginView.class, "loginTitle"));
    this.loginWindow.setContent(loginLayout);
    this.loginWindow.center();
    this.loginWindow.setWidth("400px");
    this.loginWindow.setHeight("250px");
    this.loginWindow.setResizable(false);
    this.loginWindow.setClosable(false);
    UI.getCurrent().addWindow(this.loginWindow);
  }



  @Override
  protected String getUserNameFieldCaption()
  {
    return I18n.getString(LoginView.class, "userName");
  }



  @Override
  protected String getPasswordFieldCaption()
  {
    return I18n.getString(LoginView.class, "password");
  }



  @Override
  protected void login(final String userName, final String password)
  {

    if (userName != null && password != null && checkLoginAndStoreUser(userName.trim(), password.trim()))
    {
      logger.info("[login] Login of user {} successful", userName);
      this.loginWindow.close();
      this.loginWindow = null;
      this.loginHandler.afterSuccessfulLogin();
    }
    else
    {
      Notification.show(I18n.getString(LoginView.class, "LoginView.credentialFailure"));
    }
  }
}
