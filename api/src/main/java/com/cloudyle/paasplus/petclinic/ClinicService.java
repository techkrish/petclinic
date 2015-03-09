/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudyle.paasplus.petclinic;

import java.util.Collection;
import java.util.List;

import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Owner;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Vet;
import com.cloudyle.paasplus.services.catalog.data.ICatalog;
import com.cloudyle.paasplus.services.catalog.data.ICode;
import com.cloudyle.paasplus.services.catalog.exceptions.CatalogServiceException;
import com.cloudyle.paasplus.services.persistence.exceptions.PersistenceException;


/**
 * Mostly used as a facade for all Petclinic controllers
 * 
 * @author Michael Isvy
 */
public interface ClinicService
{


  Owner findOwnerById(String id) throws PersistenceException;



  Collection<Owner> findOwnerByLastName(String lastName) throws PersistenceException;



  Pet findPetById(String id) throws PersistenceException;



  List<Pet> findPets() throws PersistenceException;



  Collection<Vet> findVetByLastName(String lastName) throws PersistenceException;



  Collection<Vet> findVets() throws PersistenceException;



  ICatalog getPetTypes() throws CatalogServiceException;



  // void saveVisit(Visit visit) throws PersistenceException;

  ICatalog getVetSpecalities() throws CatalogServiceException;



  byte[] printPets(Collection<Pet> pets);



  void saveOwner(Owner owner) throws PersistenceException;



  void savePet(Pet pet) throws PersistenceException;



  ICode getPetTypeFromCode(final String typeCode);

}
