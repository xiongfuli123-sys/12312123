/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.Policy;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class DeletePolicyListener extends SelectionAdapter {

	private List policyList;
	private Shell shell;
	
	public DeletePolicyListener(List policyList, Shell shell) {
		this.policyList = policyList;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		String[] ids = policyList.getSelection();
		if (ids.length == 1) {
			boolean confirmed = MessageDialog.openConfirm(shell,
					Messages.getString("confirm.policyDeleteTitle"),
					Messages.getString("confirm.policyDeleteMessage", ids[0]));
			if (confirmed) {
				System.out.println(Messages.getString("log.deleting"));
				Object[] pols = (Object[]) policyList.getData();
				int index = policyList.getSelectionIndices()[0];
				Policy p = (Policy) pols[index];
				SimpleInsuranceApp.getCompany().terminatePolicy(p);
			}
		}
	}
}