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

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Simple JavaBean domain object representing a veterinarian.
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 * @author Alexander Grzesik
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "vets")
@NamedQueries({ @NamedQuery(name = "Vet.getByName", query = "Select v from Vet v where v.lastName like :name") })
public class Vet extends Person {

	@ElementCollection
	private Set<String> specialties;

	public void addSpecality(String String) {
		getSpecialtiesInternal().add(String);
	}

	public int getNrOfSpecialties() {
		return getSpecialtiesInternal().size();
	}

	public List<String> getSpecialties() {
		List<String> sortedSpecs = new ArrayList<String>(
				getSpecialtiesInternal());
		return Collections.unmodifiableList(sortedSpecs);
	}

	protected Set<String> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<String>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(Set<String> specialties) {
		this.specialties = specialties;
	}

}
