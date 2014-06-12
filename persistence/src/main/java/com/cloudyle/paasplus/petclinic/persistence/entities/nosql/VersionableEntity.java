/*
 * AbstractVersionablePaasplusEntity.java
 *
 * Createt on 20.01.2014, 09:22:45
 *
 * This file is property of the Cloudyle GmbH.
 * (c) Copyright Cloudyle GmbH.
 * All rights reserved.
 */

package com.cloudyle.paasplus.petclinic.persistence.entities.nosql;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

import com.cloudyle.paasplus.services.persistence.data.IVersionablePaasplusEntity;


/**
 * TODO: need to be moved
 * 
 * @version $Revision: 1.1.1.1 $
 * @author tn
 * @author Alexander Grzesik
 * @since
 */
@MappedSuperclass
@NoSql(dataFormat = DataFormatType.MAPPED)
public abstract class VersionableEntity<Entity extends IVersionablePaasplusEntity> extends
		CancellableEntity implements IVersionablePaasplusEntity<String, Entity>
{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7423019593882092282L;

	/**
	 * Version Number is starting by one, and will be increased by one for each new Version.
	 */
	@Column(name = "version_number")
	private long versionNumber = 1;

	/**
	 * Is a self reference, pointing to the current Version. Is null if this is the current Version.
	 */
	@JoinColumn(name = "previous_version")
	@ManyToOne(fetch = FetchType.LAZY)
	private Entity currentVersion;

	/**
	 * The timestamp the Entity got Versioned.
	 */
	@Column(name = "ts_versioned")
	private Timestamp tsVersioned;

	/**
	 * The User triggered the versioning. TODO: Change to User-Entity
	 */
	@Column(name = "user_versioned_id")
	private String userVersionedId;



	/**
	 * Erases the ID of the entity.
	 */
	@Override
	public void eraseId()
	{
		setId(null);
	}



	/**
	 * @return the currentVersion. Return null if this is the current Version.
	 * 
	 */
	@Override
	public Entity getCurrentVersion()
	{
		return this.currentVersion;
	}



	/**
	 * @return the tsVersioned
	 */
	@Override
	public Timestamp getTsVersioned()
	{
		return this.tsVersioned;
	}



	/**
	 * @return the userVersionedId
	 */
	@Override
	public String getUserVersionedId()
	{
		return this.userVersionedId;
	}



	/**
	 * @return the versionNumber
	 */
	@Override
	public long getVersionNumber()
	{
		return this.versionNumber;
	}



	/**
	 * Increases the number of the Version by 1.
	 */
	@Override
	public void increaseVersionNumber()
	{
		this.versionNumber = this.versionNumber + 1;
	}



	/**
	 * @param currentVersion
	 *          the previousVersion to set. Set null if this is the current Version.
	 */
	@Override
	public void setCurrentVersion(final Entity currentVersion)
	{
		this.currentVersion = currentVersion;
	}



	/**
	 * @param tsVersioned
	 *          the tsVersioned to set
	 */
	@Override
	public void setTsVersioned(final Timestamp tsVersioned)
	{
		this.tsVersioned = tsVersioned;
	}



	/**
	 * @param userVersionedId
	 *          the userVersionedId to set
	 */
	@Override
	public void setUserVersionedId(final String userVersionedId)
	{
		this.userVersionedId = userVersionedId;
	}



	@Override
	public String toString()
	{
		return super.toString() + DELIMITER + "versionNumber:" + getVersionNumber();
	}
}
