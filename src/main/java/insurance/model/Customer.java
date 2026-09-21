/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

import insurance.model.listeners.CustomerListener;

import java.util.Date;
import java.util.Set;

public interface Customer {
	/**
	 * Get the customer id
	 */
	public abstract String getCustomerID();

	/**
	 * @param firstName The firstName to set.
	 */
	public abstract void setFirstName(String firstName);

	/**
	 * @return Returns the firstName.
	 */
	public abstract String getFirstName();

	/**
	 * @param lastName The lastName to set.
	 */
	public abstract void setLastName(String lastName);

	/**
	 * @return Returns the lastName.
	 */
	public abstract String getLastName();

	/**
	 * @param dob The dob to set.
	 */
	public abstract void setDob(Date dob);

	/**
	 * @return Returns the dob.
	 */
	public abstract Date getDob();

	/**
	 * @param address The address to set.
	 */
	public abstract void setAddress(Address address);

	/**
	 * @return Returns the address.
	 */
	public abstract Address getAddress();

	public abstract void addCustomerListener(CustomerListener l);

	public abstract boolean removeCustomerListener(CustomerListener l);

	/**
	 * @return Returns the policies.
	 */
	public abstract Set getPolicies();

	public abstract void addPolicy(Policy policy);

	public abstract boolean removePolicy(Policy policy);
}