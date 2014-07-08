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
import com.cloudyle.paasplus.ui.framework.Module;
import com.cloudyle.paasplus.ui.framework.menu.MenuComponentEntry;
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
		setDefaultView("overview");

	}

	protected Component buildBranding() {
		final VerticalLayout branding = new VerticalLayout();
		branding.setStyleName("branding");
		final Label logo = new Label("<span>Paasplus</span> Demo",
				ContentMode.HTML);

		logo.setSizeUndefined();

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

		exit.setDescription("Sign Out");
		exit.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				VaadinSession.getCurrent().setAttribute("USER", null);

			}
		});
		return layout;
	}

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

	public void openPatient(final Pet pet) {
		this.model.setSelectedPet(pet);

	}

	public void openReports(final Table t) {
		this.patients = t;
		this.autoCreateReport = true;

	}

}
