/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

import insurance.model.listeners.ClaimListener;

import java.util.Date;

public interface Claim {
	public abstract String getClaimId();

	/**
	 * @param created The created to set.
	 */
	public abstract void setCreated(Date created);

	/**
	 * @return Returns the created.
	 */
	public abstract Date getCreated();

	/**
	 * @param amount The amount to set.
	 */
	public abstract void setAmount(double amount);

	/**
	 * @return Returns the amount.
	 */
	public abstract double getAmount();

	/**
	 * @param status The status to set.
	 */
	public abstract void setStatus(String status);

	/**
	 * @return Returns the status.
	 */
	public abstract String getStatus();

	/**
	 * @param comments The comments to set.
	 */
	public abstract void setComments(String comments);

	/**
	 * @return Returns the comments.
	 */
	public abstract String getComments();

	/**
	 * @param l
	 */
	public abstract void addClaimListener(ClaimListener l);

	/**
	 * @param l
	 * @return
	 */
	public abstract boolean removeClaimListener(ClaimListener l);
}