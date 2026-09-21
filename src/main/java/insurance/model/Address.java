/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public interface Address {
	/**
	 * @return Returns the line1.
	 */
	String getLine1();

	/**
	 * @return Returns the line2.
	 */
	String getLine2();

	/**
	 * @return Returns the townCity.
	 */
	String getTownCity();

	/**
	 * @return Returns the postcode.
	 */
	String getPostcode();

	/**
	 * @return Returns the phone.
	 */
	String getPhone();

	/**
	 * @param line1 The line1 to set.
	 */
	void setLine1(String line1);

	/**
	 * @param line2 The line2 to set.
	 */
	void setLine2(String line2);

	/**
	 * @param phone The phone to set.
	 */
	void setPhone(String phone);

	/**
	 * @param postcode The postcode to set.
	 */
	void setPostcode(String postcode);

	/**
	 * @param townCity The townCity to set.
	 */
	void setTownCity(String townCity);
}