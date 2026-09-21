/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.AutoPolicy;
import insurance.model.Automobile;
import insurance.model.Customer;
import insurance.model.PolicyType;
import insurance.model.Schedule;
import insurance.ui.Messages;

import java.util.Date;

public class AutoPolicyImpl extends PolicyImpl implements AutoPolicy {

    private boolean noClaims;
    private double excess;
    private Automobile car;

    public AutoPolicyImpl(Customer c) {
    	super(c);
    	this.type = PolicyType.AUTO;

    }
    
    /**
     * Take out a new auto policy...
     */
    public AutoPolicyImpl(Date startDate,
				  Date endDate,
				  double value,
				  boolean isQuote,
				  Schedule schedule,
				  Customer customer,
				  Automobile car,
				  double excess,
				  boolean noClaims) {
    	super(startDate,endDate,value,isQuote,schedule,customer);
    	this.car = car;
    	this.excess = excess;
    	this.noClaims = noClaims;
        this.type = PolicyType.AUTO;
    }

    public String toString() {
    	return getPolicyID() + " [" + Messages.getString("type.auto") + "]";
    }
    
    /**
     * @param noClaims The noClaims to set.
     */
    public void setNoClaims(boolean noClaims) {
        this.noClaims = noClaims;
        notifyListeners();
        int t=1;
    }
    
    /**
     * @return Returns the noClaims.
     */
    public boolean isNoClaims() {
        return noClaims;
    }

    /**
     * @param excess The excess to set.
     */
    public void setExcess(double excess) {
        this.excess = excess;
        notifyListeners();
    }

    /**
     * @return Returns the excess.
     */
    public double getExcess() {
        return excess;
    }

    /**
     * @param car The car to set.
     */
    public void setCar(Automobile car) {
        this.car = car;
        notifyListeners();
    }

    /**
     * @return Returns the car.
     */
    public Automobile getCar() {
        return car;
    } 
}
