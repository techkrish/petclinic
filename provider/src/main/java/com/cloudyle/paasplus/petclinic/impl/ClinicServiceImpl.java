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
import com.cloudyle.paasplus.services.persistence.IPersistenceService;
import com.cloudyle.paasplus.services.persistence.data.IJQLQuery;
import com.cloudyle.paasplus.services.persistence.exceptions.PersistenceException;

public class ClinicServiceImpl implements ClinicService {

	private static Logger logger = LoggerFactory
			.getLogger(ClinicServiceImpl.class);

	private IPersistenceService persistenceService;

	private ICatalogService catalogService;

	private ReportHelper reportHelper;

	@Override
	public Owner findOwnerById(String id) throws PersistenceException {

		return persistenceService.find(id, Owner.class);
	}

	@Override
	public Collection<Owner> findOwnerByLastName(String lastName)
			throws PersistenceException {

		IJQLQuery<Owner> query = persistenceService.createNamedJQLQuery(
				"Owner.getByName", Owner.class);
		query.addNamedParameter("name", lastName);
		return persistenceService.executeQuery(query);
	}

	@Override
	public Pet findPetById(String id) throws PersistenceException {

		return persistenceService.find(id, Pet.class);
	}

	@Override
	public List<Pet> findPets() throws PersistenceException {
		IJQLQuery<Pet> query = persistenceService.createJQLQuery(
				"select p from Pet p", Pet.class);
		return persistenceService.executeQuery(query);
	}

	@Override
	public Collection<Vet> findVetByLastName(String lastName)
			throws PersistenceException {

		IJQLQuery<Vet> query = persistenceService.createNamedJQLQuery(
				"Vet.getByName", Vet.class);
		query.addNamedParameter("name", lastName);
		return persistenceService.executeQuery(query);
	}

	@Override
	public Collection<Vet> findVets() throws PersistenceException {
		IJQLQuery<Vet> query = persistenceService.createJQLQuery(
				"select v from Vet v", Vet.class);
		return persistenceService.executeQuery(query);
	}

	public ICatalogService getCatalogService() {
		return catalogService;
	}

	public IPersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	public ICatalog<? extends ICode> getPetTypes()
			throws CatalogServiceException {

		return catalogService.getCatalog("PetTypes", "1");
	}

	public ReportHelper getReportHelper() {
		return reportHelper;
	}

	@Override
	public ICatalog<? extends ICode> getVetSpecalities()
			throws CatalogServiceException {
		return catalogService.getCatalog("Specialities", "1");
	}

	@Override
	public byte[] printPets(Collection<Pet> pets) {
		return reportHelper.createReport("dynamicReportTemplate", "PetReport",
				pets);
	}

	@Override
	public void saveOwner(Owner owner) throws PersistenceException {
		persistenceService.persist(owner);

	}

	@Override
	public void savePet(Pet pet) throws PersistenceException {
		persistenceService.persist(pet);

	}

	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setPersistenceService(IPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void setReportHelper(ReportHelper reportHelper) {
		this.reportHelper = reportHelper;
	}

}
