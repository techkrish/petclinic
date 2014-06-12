/*
 * AbstractCancellablePaasplusEntity.java
 *
 * Createt on 20.01.2014, 08:55:07
 *
 * This file is property of the Cloudyle GmbH.
 * (c) Copyright Cloudyle GmbH.
 * All rights reserved.
 */

package com.cloudyle.paasplus.petclinic.persistence.entities.nosql;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;

import com.cloudyle.paasplus.services.persistence.data.ICancellablePaasplusEntity;
import com.cloudyle.paasplus.services.persistence.enums.CancellationState;

/**
 * TODO: move and comment in Dependencies
 * 
 * @version $Revision: 1.1.1.1 $
 * @author tn
 * @since
 */
@MappedSuperclass
@NoSql(dataFormat = DataFormatType.MAPPED)
public abstract class CancellableEntity extends BaseEntity implements
		ICancellablePaasplusEntity<String>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7361897437504140375L;

	/**
	 * The timestamp of the cancellation.
	 */
	@Field(name = "ts_cancellation")
	private Timestamp tsCancellation;

	/**
	 * The User triggered the cancellation. TODO: Change to User-Entity
	 */
	@Field(name = "user_cancelled_id")
	private String userCancelledId;

	/**
	 * The CancellationState (reason for the cancellation).
	 */
	@Field(name = "cancellation_state")
	private CancellationState cancellationState;

	/**
	 * Get the CancellationState.
	 * 
	 * @return
	 */
	@Override
	public CancellationState getCancellationState()
	{
		return this.cancellationState;
	}

	/**
	 * Returns the Timestamp of the cancellation.
	 * 
	 * @return
	 */
	@Override
	public Timestamp getTsCancellation()
	{
		return this.tsCancellation;
	}

	/**
	 * Gets the ID of the user, made the cancellation.
	 * 
	 * @return
	 */
	@Override
	public String getUserCancelledId()
	{
		return this.userCancelledId;
	}

	/**
	 * Checks if the Entity is cancelled.
	 * 
	 * @return
	 */
	@Override
	public boolean isCancelled()
	{
		if (this.tsCancellation != null)
		{
			return Boolean.TRUE;
		} else
		{
			return Boolean.FALSE;
		}
	}

	/**
	 * Sets the CancellationState.
	 * 
	 * @param cancellationState
	 */
	@Override
	public void setCancellationState(final CancellationState cancellationState)
	{
		this.cancellationState = cancellationState;
	}

	/**
	 * Sets the Timestamp of the cancellation
	 * 
	 * @param tsCancellation
	 */
	@Override
	public void setTsCancellation(final Timestamp tsCancellation)
	{
		this.tsCancellation = tsCancellation;
	}

	/**
	 * Sets the ID of the user, made the cancellation.
	 * 
	 * @param userCancelledId
	 */
	@Override
	public void setUserCancelledId(final String userCancelledId)
	{
		this.userCancelledId = userCancelledId;
	}

	@Override
	public String toString()
	{
		return super.toString() + DELIMITER + "canceled:" + isCancelled() + DELIMITER + "cancelState:"
				+ getCancellationState();
	}
}
