/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.Address;
import insurance.model.Customer;
import insurance.model.HousePolicy;
import insurance.model.PolicyType;
import insurance.model.Schedule;
import insurance.ui.Messages;

import java.util.Date;

public class HousePolicyImpl extends PolicyImpl implements HousePolicy {
    private double worth;
    private Address address;

    public HousePolicyImpl(Customer c) {
    	super(c);
    	this.type = PolicyType.HOUSE;
    }
    
    /**
     * Take out a new house policy...
     */
    public HousePolicyImpl(Date startDate,
					  Date endDate,
					  double value,
					  boolean isQuote,
					  Schedule schedule,
					  Customer customer,
					  double worth,
					  Address address) {
    	super(startDate,endDate,value,isQuote,schedule,customer);
    	this.worth = worth;
    	this.address = address;
        this.type = PolicyType.HOUSE;
    }
    
    public String toString() {
    	return getPolicyID() + " [" + Messages.getString("type.house") + "]";
    }

    /**
     * @param worth The worth to set.
     */
    public void setWorth(double worth) {
        this.worth = worth;
        notifyListeners();
    }
    
    /**
     * @return Returns the worth.
     */
    public double getWorth() {
        return worth;
    }

    /**
     * @param address The address to set.
     */
    public void setAddress(Address address) {
        this.address = address;
        notifyListeners();
    }

    /**
     * @return Returns the address.
     */
    public Address getAddress() {
        return address;
    }
}
