package com.cloudyle.paasplus.petclinic.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Owner;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Vet;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Visit;
import com.cloudyle.paasplus.services.catalog.ICatalogService;
import com.cloudyle.paasplus.services.catalog.data.CustomCode;
import com.cloudyle.paasplus.services.catalog.data.ICatalog;
import com.cloudyle.paasplus.services.catalog.exceptions.CatalogNotEditableException;
import com.cloudyle.paasplus.services.catalog.exceptions.CatalogServiceException;
import com.cloudyle.paasplus.services.persistence.IPersistenceService;
import com.cloudyle.paasplus.services.persistence.data.IJQLQuery;
import com.cloudyle.paasplus.services.persistence.exceptions.PersistenceException;


public class SampleData
{


  private static Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

  private ICatalogService catalogService;

  private IPersistenceService persistenceService;

  private boolean createSampleData = true;



  protected Owner createOwner(final String firstName, final String lastName, final String address)
  {
    try
    {

      final IJQLQuery<Owner> query = this.persistenceService.createNamedJQLQuery("Owner.getByName", Owner.class);
      query.addNamedParameter("name", lastName);

      final List<Owner> queryResult = this.persistenceService.executeQuery(query);
      if (queryResult.size() > 0)
      {
        logger.info("Found Owner data: {}", lastName);
        return null;
      }

      final Owner owner = new Owner();
      owner.setFirstName(firstName);
      owner.setLastName(lastName);
      owner.setAddress(address);

      return this.persistenceService.persist(owner);
    }
    catch (final PersistenceException e)
    {
      logger.info("Exception while creating sample owner data: " + lastName, e);
      return null;
    }
  }



  protected Pet createPet(final String name, final Date birth, final String type, final Owner owner)
  {
    try
    {

      final Pet pet = new Pet();
      pet.setBirthDate(birth);
      pet.setName(name);
      pet.setType(type);
      final Visit v = new Visit();
      v.setDate(new Date());
      v.setDescription("Illness of " + name);
      pet.addVisit(v);

      owner.addPet(pet);

      return this.persistenceService.persist(pet);
    }
    catch (final PersistenceException e)
    {
      logger.info("Exception while creating sample pet data: " + name, e);
      return null;
    }
  }



  protected Owner createPetAndOwner(final String firstName, final String lastName, final String address,
      final String petName, final String petType, final Date petBirth)
  {
    try
    {

      final Owner owner = createOwner(firstName, lastName, address);
      if (owner == null)
      {
        return null;
      }
      createPet(petName, petBirth, petType, owner);

      return this.persistenceService.persist(owner);
    }
    catch (final PersistenceException e)
    {
      logger.info("Exception while creating sample owner data: " + lastName, e);
      return null;
    }
  }



  private void createPetTypes()
  {
    try
    {
      ICatalog catalog = this.catalogService.getCatalog("PetTypes", "1");
      if (catalog != null)
      {
        this.catalogService.removeCatalog(catalog);
      }
      catalog = this.catalogService.createCatalog(CustomCode.class, "PetTypes", "1", false);
      catalog = this.catalogService.saveCatalog(catalog);
      CustomCode code = new CustomCode("100", "Dog");
      this.catalogService.addToCustomCatalog(code, catalog);
      code = new CustomCode("200", "Cat");
      this.catalogService.addToCustomCatalog(code, catalog);
      code = new CustomCode("300", "Hamster");
      this.catalogService.addToCustomCatalog(code, catalog);
      this.catalogService.saveCatalog(catalog);
    }
    catch (final CatalogNotEditableException e)
    {
      logger.error("[createPetTypes] cannot save catalog [{}]", e);
    }
    catch (final CatalogServiceException e)
    {
      logger.error("[createPetTypes] cannot get the catalog", e);
    }
  }



  public void createSampleData()
  {
    if (this.createSampleData)
    {
      createPetTypes();
      createSpecialities();
      createVet("John", "Dover", "S1", "S2");
      createVet("Jane", "Meyers", "S2");
      createVet("Frank", "Smith", "S2", "S3");
      createPetAndOwner("Joe", "Williams", "123 Main Street", "Aunty", "200", new Date());
      createPetAndOwner("Sarah", "Scott", "456 River Road", "Barky", "100", new Date());
      createPetAndOwner("Billy", "Minsc", "231 Market Place", "Boo", "300", new Date());
    }

  }



  private void createSpecialities()
  {
    try
    {
      ICatalog catalog = this.catalogService.getCatalog("Specialities", "1");

      if (catalog != null)
      {
        this.catalogService.removeCatalog(catalog);
      }

      catalog = this.catalogService.createCatalog(CustomCode.class, "Specialities", "1", false);
      catalog = this.catalogService.saveCatalog(catalog);
      CustomCode code = new CustomCode("100", "radiology");
      this.catalogService.addToCustomCatalog(code, catalog);
      code = new CustomCode("200", "surgery");
      this.catalogService.addToCustomCatalog(code, catalog);
      code = new CustomCode("300", "dentistry");
      this.catalogService.addToCustomCatalog(code, catalog);
      this.catalogService.saveCatalog(catalog);
    }
    catch (final CatalogNotEditableException e)
    {
      logger.error("[createPetTypes] cannot save catalog [{}]", e);
    }
    catch (final CatalogServiceException e)
    {
      logger.error("[createPetTypes] cannot get the catalog", e);
    }
  }



  protected Vet createVet(final String firstName, final String lastName, final String... specialities)
  {
    try
    {

      final IJQLQuery<Vet> query = this.persistenceService.createNamedJQLQuery("Vet.getByName", Vet.class);
      query.addNamedParameter("name", lastName);

      final List<Vet> queryResult = this.persistenceService.executeQuery(query);
      if (queryResult.size() > 0)
      {
        logger.info("Found vet data: {}", lastName);
        return null;
      }

      final Vet vet = new Vet();
      vet.setFirstName(firstName);
      vet.setLastName(lastName);

      for (final String speciality : specialities)
      {
        vet.addSpecality(speciality);
      }

      return this.persistenceService.persist(vet);
    }
    catch (final PersistenceException e)
    {
      logger.info("Exception while creating sample vet data: " + lastName, e);
      return null;
    }
  }



  public ICatalogService getCatalogService()
  {
    return this.catalogService;
  }



  public IPersistenceService getPersistenceService()
  {
    return this.persistenceService;
  }



  public boolean isCreateSampleData()
  {
    return this.createSampleData;
  }



  public void setCatalogService(final ICatalogService catalogService)
  {
    this.catalogService = catalogService;
  }



  public void setCreateSampleData(final boolean createSampleData)
  {
    this.createSampleData = createSampleData;
  }



  public void setPersistenceService(final IPersistenceService persistenceService)
  {
    this.persistenceService = persistenceService;
  }

}
