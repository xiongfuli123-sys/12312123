/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.dao.inmemory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import insurance.dao.CustomerDao;
import insurance.dao.PolicyDao;
import insurance.dao.SIPersistenceException;
import insurance.model.Customer;
import insurance.model.Policy;

public class PolicyDaoImpl implements PolicyDao {

	private CustomerDao customerDao;
	private Set policies = new HashSet();
	
	/**
	 * 
	 */
	public PolicyDaoImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#insertPolicy(insurance.model.Policy)
	 */
	public void insertPolicy(Policy p) {
		if (!policies.add(p)) {
			throw new SIPersistenceException("Duplicate policy");
		}
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#findPoliciesById(java.lang.String)
	 */
	public Set findPoliciesById(String policyId) {
		Set found = new HashSet();
		policyId = policyId.replaceAll("\\*",".*");
		for (Iterator iter = policies.iterator(); iter.hasNext();) {
			Policy policy = (Policy) iter.next();
			if (policy.getPolicyID().matches(policyId)) {
				found.add(policy);
			}
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#findPoliciesByCustomerId(java.lang.String)
	 */
	public Set findPoliciesByCustomerId(String customerId) {
		Set found = new HashSet();
		Set matchingCusts = customerDao.findCustomersById(customerId);
		for (Iterator iter = matchingCusts.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			found.addAll(cust.getPolicies());			
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#findPoliciesByCustomerLastName(java.lang.String)
	 */
	public Set findPoliciesByCustomerLastName(String lastName) {
		Set found = new HashSet();
		Set matchingCusts = customerDao.findCustomersByLastName(lastName);
		for (Iterator iter = matchingCusts.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			found.addAll(cust.getPolicies());			
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#updatePolicy(insurance.model.Policy)
	 */
	public void updatePolicy(Policy p) {
		// no-op
	}

	/* (non-Javadoc)
	 * @see insurance.dao.PolicyDao#deletePolicy(insurance.model.Policy)
	 */
	public void deletePolicy(Policy p) {
		if (!policies.remove(p)) {
			throw new SIPersistenceException("Unknown Policy");
		}
	}

}
