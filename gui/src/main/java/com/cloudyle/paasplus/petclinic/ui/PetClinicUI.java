/**
 * DISCLAIMER
 *
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 *
 * @author jouni@vaadin.com
 *
 */

package com.cloudyle.paasplus.petclinic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.petclinic.ClinicService;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.ui.module.ClinicModule;
import com.cloudyle.paasplus.ui.framework.BasicFrameworkUI;
import com.cloudyle.paasplus.ui.framework.LoginHandler;
import com.cloudyle.paasplus.ui.framework.MenuComponentEntry;
import com.cloudyle.paasplus.ui.framework.Module;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.Transferable;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@Theme("bluedash")
@Title("Paasplus Demo")
public class PetClinicUI extends BasicFrameworkUI {

	private static final Logger log = LoggerFactory
			.getLogger(PetClinicUI.class);

	private static final long serialVersionUID = 1L;

	private final List<String> menuTitles = new ArrayList<>();

	private final Map<String, List<MenuComponentEntry>> menuItems = new HashMap<>();

	private final DataModel model = new DataModel();

	Table patients;

	boolean autoCreateReport = false;

	private Transferable items;

	private List<Module> modules;

	private ClinicModule clinicModule;

	public PetClinicUI() {

		try {
			final InitialContext ic = new InitialContext();
			ClinicService clinicService = (ClinicService) ic
					.lookup("osgi:service/" + ClinicService.class.getName());
			model.setClinicService(clinicService);
			log.info("Clinic Service found! " + clinicService);

		} catch (final NamingException e) {
			log.error("Clinic Service not found! ", e);
		}

	}

	protected Component buildBranding() {
		final VerticalLayout branding = new VerticalLayout();
		branding.setStyleName("branding");
		final Label logo = new Label("<span>Paasplus</span> Demo",
				ContentMode.HTML);

		// logo.addStyleName("h3 color");
		logo.setSizeUndefined();
		// branding.addComponent(logo);
		branding.addComponent(new Image(null, new ThemeResource("img/logo.png")));

		branding.setHeight("100px");

		return branding;
	}

	@Override
	protected void createLoginNavigator(final VerticalLayout root,
			final LoginHandler loginHandler) {
		final Navigator loginNavigator = new Navigator(this, root);
		loginNavigator.addView("", new LoginView(loginNavigator, loginHandler));
		loginNavigator.setErrorProvider(new LoginErrorProvider(loginNavigator,
				loginHandler));
		loginNavigator.navigateTo(""); // Ensure the login view is shown
										// initially
	}

	public ClinicService getClinicService() {
		return model.getClinicService();
	}

	@Override
	protected AbstractLayout getHeader() {
		final HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent(new Label("Cloudyle PaaS+ PetClinic"));
		final Button exit = new Button("logout");
		exit.setStyleName("borderless");
		// layout.setComponentAlignment(exit, Alignment.TOP_CENTER);
		// exit.setIcon(new ThemeResource("icons/32/button-cross.png"));
		// layout.addComponent(exit);
		exit.setDescription("Sign Out");
		exit.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				VaadinSession.getCurrent().setAttribute("USER", null);
				buildLoginView();
			}
		});
		return layout;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.cloudyle.paasplus.ui.framework.BasicFrameworkUI#getMenuComponents(java.lang.String)
	// */
	// @Override
	// protected List<MenuComponent> getMenuComponents(final String menuTitle)
	// {
	// List<MenuComponent> items = this.menuItems.get(menuTitle);
	// return items != null ? items : new ArrayList<MenuComponent>();
	// }
	//
	//
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.cloudyle.paasplus.ui.framework.BasicFrameworkUI#getMenuLabels()
	// */
	// @Override
	// protected List<String> getMenuLabels()
	// {
	// return this.menuTitles;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cloudyle.paasplus.ui.framework.BasicFrameworkUI#getMainView()
	 */
	protected String getMainView() {
		return "passplus";
	}

	public DataModel getModel() {
		return this.model;
	}

	@Override
	protected List<Module> getModules() {
		if (this.modules == null) {

			this.modules = new ArrayList<>();
			clinicModule = new ClinicModule();
			this.modules.add(clinicModule);

		}
		return this.modules;
	}

	@Override
	protected boolean isAuthenticated() {
		// throw new UnsupportedOperationException("Not yet implemented.");
		// return userName.equalsIgnoreCase("Tresor") &&
		// userName.equals(password);
		return "medisite20".equals(VaadinSession.getCurrent().getAttribute(
				"USER"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cloudyle.paasplus.ui.framework.BasicFrameworkUI#mainViewReady()
	 */
	@Override
	protected void mainViewReady() {
		getNavigator().navigateTo("overview");
	}

	public void openPatient(final Pet pet) {
		this.model.setSelectedPet(pet);
		// this.contentNavigator.navigateTo("/patient");
		// this.mainContent.clearMenuSelection();
		// this.viewNameToMenuButton.get("/patient").addStyleName("selected");
	}

	public void openReports(final Table t) {
		this.patients = t;
		this.autoCreateReport = true;
		// this.contentNavigator.navigateTo("/reports");
		// this.mainContent.clearMenuSelection();
		// this.viewNameToMenuButton.get("/reports").addStyleName("selected");
	}

}
