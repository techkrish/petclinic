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
import com.cloudyle.paasplus.services.catalog.data.ICatalog;
import com.cloudyle.paasplus.services.catalog.data.ICustomCode;
import com.cloudyle.paasplus.services.catalog.enums.CodeTypes;
import com.cloudyle.paasplus.services.catalog.exceptions.CatalogServiceException;
import com.cloudyle.paasplus.services.persistence.IPersistenceService;
import com.cloudyle.paasplus.services.persistence.data.IJQLQuery;
import com.cloudyle.paasplus.services.persistence.exceptions.PersistenceException;

public class SampleData {

	private static Logger logger = LoggerFactory
			.getLogger(ClinicServiceImpl.class);

	private ICatalogService catalogService;
	private IPersistenceService persistenceService;
	private boolean createSampleData = true;

	protected Owner createOwner(String firstName, String lastName,
			String address) {
		try {

			IJQLQuery<Owner> query = persistenceService.createNamedJQLQuery(
					"Owner.getByName", Owner.class);
			query.addNamedParameter("name", lastName);

			List<Owner> queryResult = persistenceService.executeQuery(query);
			if (queryResult.size() > 0) {
				logger.info("Found Owner data: {}", lastName);
				return null;
			}

			Owner owner = new Owner();
			owner.setFirstName(firstName);
			owner.setLastName(lastName);
			owner.setAddress(address);

			return persistenceService.persist(owner);
		} catch (PersistenceException e) {
			logger.info("Exception while creating sample owner data: "
					+ lastName, e);
			return null;
		}
	}

	protected Pet createPet(String name, Date birth, String type, Owner owner) {
		try {

			Pet pet = new Pet();
			pet.setBirthDate(birth);
			pet.setName(name);
			pet.setType(type);
			Visit v = new Visit();
			v.setDate(new Date());
			v.setDescription("Illness of " + name);
			pet.addVisit(v);

			owner.addPet(pet);

			return persistenceService.persist(pet);
		} catch (PersistenceException e) {
			logger.info("Exception while creating sample pet data: " + name, e);
			return null;
		}
	}

	protected Owner createPetAndOwner(String firstName, String lastName,
			String address, String petName, String petType, Date petBirth) {
		try {

			Owner owner = createOwner(firstName, lastName, address);
			if (owner == null) {
				return null;
			}
			createPet(petName, petBirth, petType, owner);

			return persistenceService.persist(owner);
		} catch (PersistenceException e) {
			logger.info("Exception while creating sample owner data: "
					+ lastName, e);
			return null;
		}
	}

	private void createPetTypes() {
		try {
			ICatalog<ICustomCode> catalog = catalogService.getCatalog(
					"PetTypes", "1");
			if (catalog != null) {
				catalogService.deleteCatalog(catalog);
			}

			catalog = catalogService.createCatalog(CodeTypes.CUSTOM);
			catalog.setName("PetTypes");
			catalog.setCatalogVersion("1");
			catalog = catalogService.saveCatalog(catalog);
			ICustomCode code = catalogService.createCode(catalog, "100", "Dog");
			catalogService.addToCatalog(code, catalog);
			code = catalogService.createCode(catalog, "200", "Cat");
			catalogService.addToCatalog(code, catalog);
			code = catalogService.createCode(catalog, "300", "Hamster");
			catalogService.addToCatalog(code, catalog);
			catalogService.saveCatalog(catalog);

		} catch (CatalogServiceException e) {
			logger.info("Exception while creating sample Type data:", e);
		}
	}

	public void createSampleData() {
		if (createSampleData) {
			createPetTypes();
			createSpecialities();
			createVet("John", "Dover", "S1", "S2");
			createVet("Jane", "Meyers", "S2");
			createVet("Frank", "Smith", "S2", "S3");
			createPetAndOwner("Joe", "Williams", "123 Main Street", "Aunty",
					"200", new Date());
			createPetAndOwner("Sarah", "Scott", "456 River Road", "Barky",
					"100", new Date());
			createPetAndOwner("Billy", "Minsc", "231 Market Place", "Boo",
					"300", new Date());
		}

	}

	private void createSpecialities() {
		try {
			ICatalog<ICustomCode> catalog = catalogService.getCatalog(
					"Specialities", "1");

			if (catalog != null) {
				catalogService.deleteCatalog(catalog);
			}

			catalog = catalogService.createCatalog(CodeTypes.CUSTOM);
			catalog.setName("Specialities");
			catalog.setCatalogVersion("1");
			catalog = catalogService.saveCatalog(catalog);
			ICustomCode code = catalogService.createCode(catalog, "S1",
					"radiology");
			catalogService.addToCatalog(code, catalog);
			code = catalogService.createCode(catalog, "S2", "surgery");
			catalogService.addToCatalog(code, catalog);
			code = catalogService.createCode(catalog, "S3", "dentistry");
			catalogService.addToCatalog(code, catalog);
			catalogService.saveCatalog(catalog);

		} catch (CatalogServiceException e) {
			logger.info("Exception while creating sample speciality data:", e);
		}
	}

	protected Vet createVet(String firstName, String lastName,
			String... specialities) {
		try {

			IJQLQuery<Vet> query = persistenceService.createNamedJQLQuery(
					"Vet.getByName", Vet.class);
			query.addNamedParameter("name", lastName);

			List<Vet> queryResult = persistenceService.executeQuery(query);
			if (queryResult.size() > 0) {
				logger.info("Found vet data: {}", lastName);
				return null;
			}

			Vet vet = new Vet();
			vet.setFirstName(firstName);
			vet.setLastName(lastName);

			for (String speciality : specialities) {
				vet.addSpecality(speciality);
			}

			return persistenceService.persist(vet);
		} catch (PersistenceException e) {
			logger.info(
					"Exception while creating sample vet data: " + lastName, e);
			return null;
		}
	}

	public ICatalogService getCatalogService() {
		return catalogService;
	}

	public IPersistenceService getPersistenceService() {
		return persistenceService;
	}

	public boolean isCreateSampleData() {
		return createSampleData;
	}

	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setCreateSampleData(boolean createSampleData) {
		this.createSampleData = createSampleData;
	}

	public void setPersistenceService(IPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

}
