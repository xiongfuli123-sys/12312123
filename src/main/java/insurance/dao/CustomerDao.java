/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.dao;

import insurance.model.Customer;

import java.util.Set;

public interface CustomerDao {

	void insertCustomer(Customer c);
	
	Set findCustomersById(String customerId);

	Set findCustomersByLastName(String lastName);
	
	void updateCustomer(Customer c);

	void deleteCustomer(Customer c);

}
