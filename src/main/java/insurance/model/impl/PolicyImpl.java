/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.Claim;
import insurance.model.Customer;
import insurance.model.Policy;
import insurance.model.PolicyType;
import insurance.model.Schedule;
import insurance.model.listeners.PolicyListener;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public abstract class PolicyImpl implements Policy {

	private static int uid_counter = 1;
    private Date startDate;
    private Date endDate;
    private double value;
    private boolean isQuote;
    private Schedule schedule;
    private Customer customer;
    private String policyID;
    private Set claims;
    protected PolicyType type;
    private transient Set listeners = new HashSet();
    
    
    /**
     * for the UI
     * @param customer
     */
    public PolicyImpl(Customer customer) {
    	this.customer = customer;
    	createPolicyID();
        // Add this instance to the customer's list of policies.
        if (this.getCustomer() != null) {
            this.getCustomer().addPolicy(this);
        }
        this.claims = new HashSet();
    }
    
    /**
     * Take out a new policy...
     */
    public PolicyImpl(Date startDate,
				  Date endDate,
				  double value,
				  boolean isQuote,
				  Schedule schedule,
				  Customer customer) {
    	this(customer);
    	this.startDate = startDate;
    	this.endDate = endDate;
        this.value = value;
    	this.isQuote = isQuote;
    	this.schedule = schedule;
    	this.customer = customer;
    }
    
	public boolean equals(Object obj) {
		if (obj instanceof PolicyImpl) {
			PolicyImpl other = (PolicyImpl)obj;
			return policyID.equals(other.policyID);
		}
		return false;
	}
	
	public int hashCode() {
		return policyID.hashCode();
	}
    
    public String getPolicyID() {
    	return policyID;
    }
    
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        notifyListeners();
    }
    
    /**
     * @return Returns the startDate.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        notifyListeners();
    }

    /**
     * @return Returns the endDate.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param value The value to set.
     */
    public void setValue(double value) {
        this.value = value;
        notifyListeners();
    }

    /**
     * @return Returns the value.
     */
    public double getValue() {
        return value;
    }

    /**
     * @param quote The quote to set.
     */
    public void setIsQuote(boolean isQuote) {
        this.isQuote = isQuote;
        notifyListeners();
    }

    /**
     * @return Returns the quote.
     */
    public boolean isQuote() {
        return isQuote;
    }

    /**
     * @param schedule The schedule to set.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        notifyListeners();
    }

    /**
     * @return Returns the schedule.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /** 
     * @return Returns the customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return Returns the type.
     */
    public PolicyType getType() {
        return type;
    }
    
    private void createPolicyID() {
    	StringBuffer id = new StringBuffer("P");
    	id.append(System.currentTimeMillis());
    	id.append(".");
    	id.append(uid_counter++);
    	this.policyID = id.toString();
    }
    
    /**
     * @param claims The claims to set.
     */
    public void setClaims(Set claims) {
        this.claims = claims;
        notifyListeners();
    }

    /**
     * @return Returns the claims.
     */
    public Set getClaims() {
        return claims;
    }
    
    /**
     * @param claim
     */
    public void addClaim(Claim claim) {
        if (claim == null) {
            throw new IllegalArgumentException("Received a null claim!");
        }
        this.claims.add(claim);
        notifyListeners();
    }
    
    /**
     * @param claim
     */
    public void removeClaim(Claim claim) {
        if (claim == null) {
            throw new IllegalArgumentException("Received a null claim!");
        }
        this.claims.remove(claim);
        notifyListeners();
    }
    
    public void addPolicyListener(PolicyListener l) {
    	if (listeners == null) listeners = new HashSet();
    	this.listeners.add(l);
    }
    
    public boolean removePolicyListener(PolicyListener l) {
    	return this.listeners.remove(l);
    }
    
    protected void notifyListeners() {
    	if (listeners == null) return;
    	for (Iterator iter = listeners.iterator(); iter.hasNext();) {
    		PolicyListener l = (PolicyListener) iter.next();
    		l.policyUpdated(this);
    	}
    }
}
