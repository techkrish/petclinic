package com.cloudyle.paasplus.petclinic.impl;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.petclinic.ClinicService;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Owner;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Vet;
import com.cloudyle.paasplus.services.catalog.ICatalogService;
import com.cloudyle.paasplus.services.catalog.data.ICatalog;
import com.cloudyle.paasplus.services.catalog.data.ICode;
import com.cloudyle.paasplus.services.catalog.exceptions.CatalogServiceException;
import com.cloudyle.paasplus.services.catalog.exceptions.DuplicateCodesException;
import com.cloudyle.paasplus.services.persistence.IPersistenceService;
import com.cloudyle.paasplus.services.persistence.data.IJQLQuery;
import com.cloudyle.paasplus.services.persistence.exceptions.PersistenceException;


public class ClinicServiceImpl implements ClinicService
{


  private static Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

  private IPersistenceService persistenceService;

  private ICatalogService catalogService;

  private ReportHelper reportHelper;



  @Override
  public Owner findOwnerById(final String id) throws PersistenceException
  {

    return this.persistenceService.find(id, Owner.class);
  }



  @Override
  public Collection<Owner> findOwnerByLastName(final String lastName) throws PersistenceException
  {

    final IJQLQuery<Owner> query = this.persistenceService.createNamedJQLQuery("Owner.getByName", Owner.class);
    query.addNamedParameter("name", lastName);
    return this.persistenceService.executeQuery(query);
  }



  @Override
  public Pet findPetById(final String id) throws PersistenceException
  {

    return this.persistenceService.find(id, Pet.class);
  }



  @Override
  public List<Pet> findPets() throws PersistenceException
  {
    final IJQLQuery<Pet> query = this.persistenceService.createJQLQuery("select p from Pet p", Pet.class);
    return this.persistenceService.executeQuery(query);
  }



  @Override
  public Collection<Vet> findVetByLastName(final String lastName) throws PersistenceException
  {

    final IJQLQuery<Vet> query = this.persistenceService.createNamedJQLQuery("Vet.getByName", Vet.class);
    query.addNamedParameter("name", lastName);
    return this.persistenceService.executeQuery(query);
  }



  @Override
  public Collection<Vet> findVets() throws PersistenceException
  {
    final IJQLQuery<Vet> query = this.persistenceService.createJQLQuery("select v from Vet v", Vet.class);
    return this.persistenceService.executeQuery(query);
  }



  public ICatalogService getCatalogService()
  {
    return this.catalogService;
  }



  public IPersistenceService getPersistenceService()
  {
    return this.persistenceService;
  }



  @Override
  public ICatalog getPetTypes() throws CatalogServiceException
  {

    return this.catalogService.getCatalog("PetTypes", "1");
  }



  public ICode getPetTypeFromCode(final String typeCode)
  {
    try
    {
      return this.catalogService.getCodeByName(typeCode, "PetTypes", "1");
    }
    catch (final CatalogServiceException e)
    {
      logger.error("[getPetTypeFromCode] cannot get the catalog service", e);
    }
    catch (final DuplicateCodesException e)
    {
      logger.error("[getPetTypeFromCode] duplicate code", e);
    }
    return null;
  }



  public ReportHelper getReportHelper()
  {
    return this.reportHelper;
  }



  @Override
  public ICatalog getVetSpecalities() throws CatalogServiceException
  {
    return this.catalogService.getCatalog("Specialities", "1");
  }



  @Override
  public byte[] printPets(final Collection<Pet> pets)
  {
    return this.reportHelper.createReport("dynamicReportTemplate", "PetReport", pets);
  }



  @Override
  public void saveOwner(final Owner owner) throws PersistenceException
  {
    this.persistenceService.persist(owner);

  }



  @Override
  public void savePet(final Pet pet) throws PersistenceException
  {
    this.persistenceService.persist(pet);

  }



  public void setCatalogService(final ICatalogService catalogService)
  {
    this.catalogService = catalogService;
  }



  public void setPersistenceService(final IPersistenceService persistenceService)
  {
    this.persistenceService = persistenceService;
  }



  public void setReportHelper(final ReportHelper reportHelper)
  {
    this.reportHelper = reportHelper;
  }

}
