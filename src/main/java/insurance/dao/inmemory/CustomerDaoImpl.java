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
import insurance.dao.SIPersistenceException;
import insurance.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

	private Set customers = new HashSet();
	
	/**
	 * 
	 */
	public CustomerDaoImpl() {
	}

	/* (non-Javadoc)
	 * @see insurance.dao.CustomerDao#insertCustomer()
	 */
	public void insertCustomer(Customer c) {
		if (!customers.add(c)) {
			throw new SIPersistenceException("Duplicate customer");
		}
	}

	/* (non-Javadoc)
	 * @see insurance.dao.CustomerDao#findCustomersById(java.lang.String)
	 */
	public Set findCustomersById(String customerId) {
		Set found = new HashSet();
		customerId = customerId.replaceAll("\\*",".*");
		for (Iterator iter = customers.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			if (cust.getCustomerID().matches(customerId)) {
				found.add(cust);
			}
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.CustomerDao#findCustomersByLastName(java.lang.String)
	 */
	public Set findCustomersByLastName(String lastName) {
		Set found = new HashSet();
		lastName = lastName.replaceAll("\\*",".*");
		for (Iterator iter = customers.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			if (cust.getLastName().matches(lastName)) {
				found.add(cust);
			}
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see insurance.dao.CustomerDao#updateCustomer(insurance.model.Customer)
	 */
	public void updateCustomer(Customer c) {
		// no-op
	}

	/* (non-Javadoc)
	 * @see insurance.dao.CustomerDao#deleteCustomer(insurance.model.Customer)
	 */
	public void deleteCustomer(Customer c) {
		if (!customers.remove(c)) {
			throw new SIPersistenceException("Unknown customer");
		}
		
	}


}
