package com.cloudyle.paasplus.petclinic.ui;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;


public class PetClinicUIProvider extends UIProvider
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3726520560942337132L;



	@Override
	public Class<? extends UI> getUIClass(final UIClassSelectionEvent event)
	{
		return PetClinicUI.class;
	}
}