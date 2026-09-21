/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.dao;

import insurance.model.Claim;

public interface ClaimDao {
	
	// claims are inserted by adding them to a policy and
	// updating the policy object.
	// claims are deleted by removing them from a policy
	// and updating the policy object.
	
	void updateClaim(Claim c);
	
	void saveClaim(Claim c);

}
