/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public interface HousePolicy extends Policy {
	/**
	 * @param worth The worth to set.
	 */
	public abstract void setWorth(double worth);

	/**
	 * @return Returns the worth.
	 */
	public abstract double getWorth();

	/**
	 * @param address The address to set.
	 */
	public abstract void setAddress(Address address);

	/**
	 * @return Returns the address.
	 */
	public abstract Address getAddress();
}