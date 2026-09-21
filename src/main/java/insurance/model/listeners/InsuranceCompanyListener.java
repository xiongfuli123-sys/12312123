/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.listeners;

import insurance.model.Customer;
import insurance.model.Policy;


public interface InsuranceCompanyListener {
	void policyTerminated(Policy p);
	void policyAdded(Policy p);
	void customerAdded(Customer c);
	void customerRemoved(Customer c);		
}