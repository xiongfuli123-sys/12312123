/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;


import insurance.model.listeners.InsuranceCompanyListener;

import java.util.Set;

public interface SimpleInsurance {

	// Policies
	
	Policy createPolicy(Customer c,PolicyType type);
	
	void insertPolicy(Policy p);

	Set findPoliciesById(String policyId);

	Set findPoliciesByCustomerId(String customerId);

	Set findPoliciesByCustomerLastName(String lastName);

	void updatePolicy(Policy p);

	void terminatePolicy(Policy p);
	
	// Customers
	
	Customer createCustomer();
	
	void insertCustomer(Customer c);
	
	Set findCustomersById(String customerId);

	Set findCustomersByLastName(String lastName);
	
	void updateCustomer(Customer c);

	void removeCustomer(Customer c);
	
	// Claims
	
	Claim createClaim();
	
	void updateClaim(Claim c);
	
	void insertClaim(Policy p, Claim c);
	
	// Address
	
	Address createAddress();
	
	// Listening

	void addListener(InsuranceCompanyListener l);

	void removeListener(InsuranceCompanyListener l);
}