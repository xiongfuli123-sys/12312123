/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.Customer;
import insurance.model.LifePolicy;
import insurance.model.PolicyType;
import insurance.model.Schedule;
import insurance.ui.Messages;

import java.util.Date;

public class LifePolicyImpl extends PolicyImpl implements LifePolicy {

    private boolean smoker;
    private boolean drinker;
    private boolean ironman;
    
    public LifePolicyImpl(Customer c) {
    	super(c);
    	this.type = PolicyType.LIFE;
    }
    
    /**
     * Take out a new life policy...
     */
    public LifePolicyImpl(Date startDate,
					  Date endDate,
					  double value,
					  boolean isQuote,
					  Schedule schedule,
					  Customer customer,
					  boolean smoker,
					  boolean drinker) {
    	super(startDate,endDate,value,isQuote,schedule,customer);
    	this.smoker = smoker;
    	this.drinker = drinker;
    	this.ironman = false; // by default
        this.type = PolicyType.LIFE;
    }
    
    public String toString() {
    	return getPolicyID() + " [" + Messages.getString("type.life") + "]";
    }

    /**
     * @param smoker The smoker to set.
     */
    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
        notifyListeners();
    }
    
    /**
     * @return Returns the smoker.
     */
    public boolean isSmoker() {
        return smoker;
    }

    /**
     * @param drinker The drinker to set.
     */
    public void setDrinker(boolean drinker) {
        this.drinker = drinker;
        notifyListeners();
    }

    /**
     * @return Returns the drinker.
     */
    public boolean isDrinker() {
        return drinker;
    }

    /**
     * @param ironman The ironman to set.
     */
    public void setIronman(boolean ironman) {
        this.ironman = ironman;
    }

    /**
     * @return Returns the ironman.
     */
    public boolean isIronman() {
        return ironman;
    }
}
