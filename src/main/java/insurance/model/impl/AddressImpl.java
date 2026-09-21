/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.Address;

public class AddressImpl implements Address {

    private String line1;
    private String line2;
    private String townCity;
    private String postcode;
    private String phone;
    
     /**
     * Populate by constructor, immutable thereafter
     */
    public AddressImpl(String line1,
				   String line2,
				   String townCity,
				   String postcode,
				   String phone) {
    	this.line1 = line1;
    	this.line2 = line2;
    	this.townCity = townCity;
    	this.postcode = postcode;
    	this.phone = phone;
    }
    
    /**
     * @return Returns the line1.
     */
    public String getLine1() {
        return line1;
    }

    /**
     * @return Returns the line2.
     */
    public String getLine2() {
        return line2;
    }

    /**
     * @return Returns the townCity.
     */
    public String getTownCity() {
        return townCity;
    }

    /**
     * @return Returns the postcode.
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @return Returns the phone.
     */
    public String getPhone() {
        return phone;
    }
    
    
	/**
	 * @param line1 The line1 to set.
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	/**
	 * @param line2 The line2 to set.
	 */
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @param postcode The postcode to set.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * @param townCity The townCity to set.
	 */
	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}
}
