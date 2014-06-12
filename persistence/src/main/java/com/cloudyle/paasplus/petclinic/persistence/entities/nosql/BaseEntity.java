/*
 * AbstractPaasplusEntity.java
 *
 * Created on 09.01.2013, 13:57:30
 *
 * This file is property of the Cloudyle GmbH.
 * (c) Copyright Cloudyle GmbH.
 * All rights reserved.
 */
package com.cloudyle.paasplus.petclinic.persistence.entities.nosql;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.cloudyle.paasplus.services.persistence.data.IPaasplusEntity;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;


/**
 * 
 * @author ak
 * @since paasplus 0.0.1
 */
@MappedSuperclass
@NoSql(dataFormat = DataFormatType.MAPPED)
public abstract class BaseEntity implements IPaasplusEntity<String>
{


	/**
   *
   */
	private static final long serialVersionUID = 4741929527334361903L;

	protected static final String DELIMITER = ", ";

	@Id
	@GeneratedValue
	@Field(name = "_id")
	private String id;

	@Version
	private Long version;

	@Field(name = "ts_lastmodified")
	private Timestamp tsLastModified;



	/*
	 * Standard toString output for Paasplus entities <br> example: EntityName, PK#id OrganisationUnitEntity, PK#366288
	 */
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + DELIMITER + "PK:" + getId() + DELIMITER + "VERSION:" + getVersion();
	}



	@Override
	public String getId()
	{
		return this.id;
	}



	public void setId(final String id)
	{
		this.id = id;
	}



	@Override
	public Long getVersion()
	{
		return this.version;
	}



	public void setVersion(final Long version)
	{
		this.version = version;
	}



	@Override
	public void setTsLastModified(final Timestamp tsLastModified)
	{
		this.tsLastModified = tsLastModified;
	}



	@Override
	public Timestamp getTsLastModified()
	{
		return this.tsLastModified;
	}
	
	 public boolean isNew() {
        return (this.id == null);
    }
}