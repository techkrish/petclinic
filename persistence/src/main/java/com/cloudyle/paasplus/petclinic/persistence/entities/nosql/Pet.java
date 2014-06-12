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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Simple business object representing a pet.
 * 
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Alexander Grzesik
 */
@Entity
@NoSql(dataFormat = DataFormatType.MAPPED, dataType = "pets")
@NamedQueries({ @NamedQuery(name = "Pet.getByName", query = "Select p from Pet p where p.name like :name") })
public class Pet extends NamedEntity {

	@Field(name = "birth_date")
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Basic
	private String type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@ElementCollection
	private Set<Visit> visits;

	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);

	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public String getType() {
		return type;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<Visit>(getVisitsInternal());
		return Collections.unmodifiableList(sortedVisits);
	}

	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<Visit>();
		}
		return this.visits;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	protected void setOwner(Owner owner) {
		this.owner = owner;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

}
