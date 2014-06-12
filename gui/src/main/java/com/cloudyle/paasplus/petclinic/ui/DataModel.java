/*
 * DataModel.java
 *
 * Created on 04.03.2014
 *
 * This file is property of the Cloudyle GmbH.
 * (c) 2014 Copyright Cloudyle GmbH.
 * All rights reserved.
 */
package com.cloudyle.paasplus.petclinic.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.petclinic.ClinicService;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.ui.data.PetsContainer;
import com.cloudyle.paasplus.services.catalog.ICatalogService;
import com.cloudyle.paasplus.services.catalog.data.ICatalog;
import com.cloudyle.paasplus.services.catalog.data.ICode;

/**
 * @author ag
 * 
 */
public class DataModel {

	private static Logger logger = LoggerFactory.getLogger(DataModel.class);

	private Pet selectedPet;

	private List<Pet> pets;

	private final PetsContainer petTable = new PetsContainer();

	private ICatalog<? extends ICode> petTypes;

	private ICatalogService catalogService;

	private ClinicService clinicService;

	public ICatalogService getCatalogService() {
		return catalogService;
	}

	public ClinicService getClinicService() {
		return clinicService;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public PetsContainer getPetTable() {
		return petTable;
	}

	public ICatalog<? extends ICode> getPetTypes() {
		return petTypes;
	}

	public Pet getSelectedPet() {
		return selectedPet;
	}

	public void reload() {
		this.pets = clinicService.findPets();
		petTable.refresh(pets);
	}

	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setClinicService(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public void setPetTypes(ICatalog<? extends ICode> petTypes) {
		this.petTypes = petTypes;
	}

	public void setSelectedPet(Pet selectedPet) {
		this.selectedPet = selectedPet;
	}

}
