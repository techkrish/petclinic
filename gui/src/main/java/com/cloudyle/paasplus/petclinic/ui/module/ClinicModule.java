package com.cloudyle.paasplus.petclinic.ui.module;

import com.cloudyle.paasplus.petclinic.ui.ClinicView;
import com.cloudyle.paasplus.ui.framework.Module;
import com.cloudyle.paasplus.ui.framework.menu.MenuEntry;
import com.vaadin.navigator.View;

public class ClinicModule extends Module {

	public ClinicModule() {
		super("clinicModule");

		addMenuEntry(new MenuEntry("overview", "Overview"));
	}

	@Override
	public String getLabel() {
		return "Clinic";
	}

	@Override
	public View getView(final String viewName) {
		switch (viewName) {
		case "overview":
			return new ClinicView();
		default:
			return null;
		}
	}
}
