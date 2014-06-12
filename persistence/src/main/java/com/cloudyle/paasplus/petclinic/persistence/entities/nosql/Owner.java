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
package com.cloudyle.paasplus.petclinic.persistence.entities.nosql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Simple JavaBean domain object representing an owner.
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
@NamedQueries({ @NamedQuery(name = "Owner.getByName", query = "Select o from Owner o where o.lastName like :name") })
public class Owner extends Person {
	@Field(name = "address")
	private String address;

	@Field(name = "city")
	private String city;

	@Field(name = "telephone")
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Pet> pets;

	public void addPet(Pet pet) {
		getPetsInternal().add(pet);
		pet.setOwner(this);
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity() {
		return this.city;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * 
	 * @param name
	 *            to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * 
	 * @param name
	 *            to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<Pet>(getPetsInternal());
		return Collections.unmodifiableList(sortedPets);
	}

	protected Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<Pet>();
		}
		return this.pets;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	protected void setPetsInternal(Set<Pet> pets) {
		this.pets = pets;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)

		.append("id", this.getId()).append("new", this.isNew())
				.append("lastName", this.getLastName())
				.append("firstName", this.getFirstName())
				.append("address", this.address).append("city", this.city)
				.append("telephone", this.telephone).toString();
	}
}
