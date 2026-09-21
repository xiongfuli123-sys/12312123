/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.Policy;
import insurance.model.PolicyType;
import insurance.model.impl.AutoPolicyImpl;
import insurance.model.impl.HousePolicyImpl;
import insurance.model.impl.LifePolicyImpl;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class EditPolicyListener extends SelectionAdapter {

	private List policyList;
	private Shell shell;
	
	public EditPolicyListener(List policyList, Shell shell) {
		this.policyList = policyList;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		int idx = policyList.getSelectionIndex();
		if (idx == -1) return;
		Object[] pols = (Object[]) policyList.getData();
		Policy p = (Policy) pols[idx];
		if (p.getType() == PolicyType.LIFE) {
			new LifePolicyEditor(shell,
				SimpleInsuranceApp.getCompany(),(LifePolicyImpl)p,false);
		} else if (p.getType() == PolicyType.AUTO) {
			new AutoPolicyEditor(shell,
					SimpleInsuranceApp.getCompany(),(AutoPolicyImpl)p,false);
		} else {
			new HousePolicyEditor(shell,
					SimpleInsuranceApp.getCompany(),(HousePolicyImpl)p,false);
		}
	}
}
