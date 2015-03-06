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


/**
 * @author ag
 * 
 */
public class DataModel
{


  private static Logger logger = LoggerFactory.getLogger(DataModel.class);

  private Pet selectedPet;

  private List<Pet> pets;

  private final PetsContainer petTable = new PetsContainer();

  private ICatalog petTypes;

  private ICatalogService catalogService;

  private ClinicService clinicService;



  public ICatalogService getCatalogService()
  {
    return this.catalogService;
  }



  public ClinicService getClinicService()
  {
    return this.clinicService;
  }



  public List<Pet> getPets()
  {
    return this.pets;
  }



  public PetsContainer getPetTable()
  {
    return this.petTable;
  }



  public ICatalog getPetTypes()
  {
    return this.petTypes;
  }



  public Pet getSelectedPet()
  {
    return this.selectedPet;
  }



  public void reload()
  {
    this.pets = this.clinicService.findPets();
    this.petTable.refresh(this.pets);
  }



  public void setCatalogService(final ICatalogService catalogService)
  {
    this.catalogService = catalogService;
  }



  public void setClinicService(final ClinicService clinicService)
  {
    this.clinicService = clinicService;
  }



  public void setPets(final List<Pet> pets)
  {
    this.pets = pets;
  }



  public void setPetTypes(final ICatalog petTypes)
  {
    this.petTypes = petTypes;
  }



  public void setSelectedPet(final Pet selectedPet)
  {
    this.selectedPet = selectedPet;
  }

}
