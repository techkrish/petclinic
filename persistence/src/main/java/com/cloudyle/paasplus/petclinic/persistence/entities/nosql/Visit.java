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

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 * Simple JavaBean domain object representing a visit.
 * 
 * @author Ken Krebs
 * @author Alexander Grzesik
 */
@Embeddable
@NoSql(dataFormat = DataFormatType.MAPPED)
public class Visit extends BaseEntity {

	/**
	 * Holds value of property date.
	 */
	@Field(name = "visit_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	/**
	 * Holds value of property description.
	 */
	@Field(name = "description")
	private String description;

	/**
	 * Creates a new instance of Visit for the current date
	 */
	public Visit() {
		this.date = new Date();
	}

	/**
	 * Getter for property date.
	 * 
	 * @return Value of property date.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Getter for property description.
	 * 
	 * @return Value of property description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter for property date.
	 * 
	 * @param date
	 *            New value of property date.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Setter for property description.
	 * 
	 * @param description
	 *            New value of property description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
