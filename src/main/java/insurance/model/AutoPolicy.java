/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public interface AutoPolicy extends Policy {
	/**
	 * @param noClaims The noClaims to set.
	 */
	public abstract void setNoClaims(boolean noClaims);

	/**
	 * @return Returns the noClaims.
	 */
	public abstract boolean isNoClaims();

	/**
	 * @param excess The excess to set.
	 */
	public abstract void setExcess(double excess);

	/**
	 * @return Returns the excess.
	 */
	public abstract double getExcess();

	/**
	 * @param car The car to set.
	 */
	public abstract void setCar(Automobile car);

	/**
	 * @return Returns the car.
	 */
	public abstract Automobile getCar();
}