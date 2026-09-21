/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.dao.ClaimDao;
import insurance.dao.CustomerDao;
import insurance.dao.PolicyDao;
import insurance.dao.SIPersistenceException;
import insurance.model.Address;
import insurance.dao.inmemory.ClaimDaoImpl;
import insurance.dao.inmemory.CustomerDaoImpl;
import insurance.dao.inmemory.PolicyDaoImpl;
import insurance.model.Claim;
import insurance.model.Customer;
import insurance.model.Policy;
import insurance.model.PolicyType;
import insurance.model.SimpleInsurance;
import insurance.model.listeners.InsuranceCompanyListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimpleInsuranceImpl implements SimpleInsurance {

	private transient Set listeners = new HashSet();	
	private ClaimDao claimDao;
	private CustomerDao customerDao;
	private PolicyDao policyDao;
	
	public SimpleInsuranceImpl() {
		setClaimDao(new ClaimDaoImpl());
		setCustomerDao(new CustomerDaoImpl());
		setPolicyDao(new PolicyDaoImpl(customerDao));
	}

	
	public void setClaimDao(ClaimDao claimDao) { 
		this.claimDao = claimDao;
	}
	
	public void setCustomerDao(CustomerDao custDao) {
		this.customerDao = custDao;
	}
	
	public void setPolicyDao(PolicyDao policyDao) {
		this.policyDao = policyDao;
	}
	
	// Policy

	public Policy createPolicy(Customer c, PolicyType type) {
		if (type == PolicyType.AUTO) {
			return new AutoPolicyImpl(c);
		} else if (type == PolicyType.HOUSE) {
			return new HousePolicyImpl(c);
		} else {
			return new LifePolicyImpl(c);
		}
	}

	public void insertPolicy(Policy p) {
		policyDao.insertPolicy(p);
        
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			InsuranceCompanyListener l = (InsuranceCompanyListener) iter.next();
			l.policyAdded(p);			
		}
	}

	public Set findPoliciesById(String policyId) {
		return policyDao.findPoliciesById(policyId);
	}
	
	public Set findPoliciesByCustomerId(String customerId) {
		return policyDao.findPoliciesByCustomerId(customerId);
	}
	
	public Set findPoliciesByCustomerLastName(String lastName) {
		return policyDao.findPoliciesByCustomerLastName(lastName);
	}

	public void updatePolicy(Policy p) {
		policyDao.updatePolicy(p);
	}
	
	public void terminatePolicy(Policy p) {
        if (p.getCustomer().removePolicy(p)) {
            updateCustomer(p.getCustomer());
            policyDao.deletePolicy(p);
            
            for (Iterator iter = listeners.iterator(); iter.hasNext();) {
                InsuranceCompanyListener l =
                    (InsuranceCompanyListener) iter.next();
                l.policyTerminated(p);
            }
        }
        else {
			throw new SIPersistenceException("Unknown Policy: " + p);
        }
    }

	// Customer

	public Customer createCustomer() {
		return new CustomerImpl();
	}

	public void insertCustomer(Customer c) {
		customerDao.insertCustomer(c);
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			InsuranceCompanyListener l = (InsuranceCompanyListener) iter.next();
			l.customerAdded(c);				
		}					
	}

	public void updateCustomer(Customer c) {
		customerDao.updateCustomer(c);
	}

	public Set findCustomersById(String customerId) {
		return customerDao.findCustomersById(customerId);
	}

	public Set findCustomersByLastName(String lastName) {
		return customerDao.findCustomersByLastName(lastName);
	}

	public void removeCustomer(Customer c) {
        // Tidy up policies for the customer when it gets deleted.
        Set policies = c.getPolicies();
        for (Iterator iter = policies.iterator(); iter.hasNext();) {
            Policy p = (Policy) iter.next();
            terminatePolicy(p);
        }

        customerDao.deleteCustomer(c);

        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            InsuranceCompanyListener l = (InsuranceCompanyListener) iter.next();
            l.customerRemoved(c);
        }
    }

	// Claims
	
	public Claim createClaim() {
		Claim c = new ClaimImpl(new Date(),0.0,"","");
		return c;
	}

	public void updateClaim(Claim c) {
		claimDao.updateClaim(c);
	}
	
	public void insertClaim(Policy p, Claim c) {
		p.addClaim(c);
		claimDao.saveClaim(c);
		policyDao.updatePolicy(p);
	}
	
	// Address
	
	public Address createAddress() {
		return new AddressImpl("","","","","");
	}
	
	public void addListener(InsuranceCompanyListener l) {
		listeners.add(l);
	}
	
	public void removeListener(InsuranceCompanyListener l) {
		listeners.remove(l);
	}

}
