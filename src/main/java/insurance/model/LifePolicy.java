/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public interface LifePolicy extends Policy {
	/**
	 * @param smoker The smoker to set.
	 */
	public abstract void setSmoker(boolean smoker);

	/**
	 * @return Returns the smoker.
	 */
	public abstract boolean isSmoker();

	/**
	 * @param drinker The drinker to set.
	 */
	public abstract void setDrinker(boolean drinker);

	/**
	 * @return Returns the drinker.
	 */
	public abstract boolean isDrinker();

	/**
	 * @param ironman The ironman to set.
	 */
	public abstract void setIronman(boolean ironman);

	/**
	 * @return Returns the ironman.
	 */
	public abstract boolean isIronman();
}