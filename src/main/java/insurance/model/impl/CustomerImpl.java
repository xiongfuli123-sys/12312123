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
import insurance.model.Policy;
import insurance.model.listeners.CustomerListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CustomerImpl implements Customer {

	private static int uid_counter = 1;
    private String firstName;
    private String lastName;
    private Date dob;
    private Address address;
    private String customerID;
    private Set policies = new HashSet();
    private transient Set listeners = new HashSet();
    
    /**
     * create an empty customer
     * used by UI dialogs
     */
    public CustomerImpl() {
    	createCustomerID();
    }
    	
    /**
     * create a new customer....
     */
    public CustomerImpl(String firstName,
					String lastName,
					Date dob,
					Address address) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.dob = dob;
    	this.address = address;
        createCustomerID();    
    }
    
    /**
     * Get the customer id
     */
    public String getCustomerID() {
    	return customerID;
    }
    
    public String toString() {
    	return (getCustomerID() + " " + lastName + "," + firstName);  
    }
    
	public boolean equals(Object obj) {
		if (obj instanceof CustomerImpl) {
			CustomerImpl other = (CustomerImpl)obj;
			return customerID.equals(other.customerID);
		}
		return false;
	}
	
	public int hashCode() {
		return customerID.hashCode();
	}

	/**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyListeners();
    }
    
    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyListeners();
    }
    
    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param dob The dob to set.
     */
    public void setDob(Date dob) {
        this.dob = dob;
        notifyListeners();
    }

    /**
     * @return Returns the dob.
     */
    public Date getDob() {
        return dob;
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
    
    public void addCustomerListener(CustomerListener l) {
    	if (listeners == null) listeners = new HashSet();
    	this.listeners.add(l);
    }
    
    public boolean removeCustomerListener(CustomerListener l) {
    	return this.listeners.remove(l);
    }
    
    protected void notifyListeners() {
    	if (listeners == null) return;
    	for (Iterator iter = listeners.iterator(); iter.hasNext();) {
    		CustomerListener l = (CustomerListener) iter.next();
    		l.customerUpdated(this);
    	}
    }

    /**
     * @param policies The policies to set.
     */
    public void setPolicies(Set policies) {
        this.policies = policies;
        notifyListeners();
    }

    /**
     * @return Returns the policies.
     */
    public Set getPolicies() {
        return policies;
    }
    
    private void createCustomerID() {
    	StringBuffer id = new StringBuffer("C");
    	id.append(System.currentTimeMillis());
    	id.append(".");
    	id.append(uid_counter++);
    	this.customerID = id.toString();
    }
    
  public void addPolicy(Policy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Received a null policy!");
        }
        this.policies.add(policy);
        notifyListeners();
    }

    public boolean removePolicy(Policy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Received a null policy!");
        }
        boolean result = this.policies.remove(policy);
        if (result) {
            notifyListeners(); 
        }
        return result;
    }
}
