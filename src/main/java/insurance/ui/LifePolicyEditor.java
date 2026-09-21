/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.LifePolicy;
import insurance.model.SimpleInsurance;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author colyer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LifePolicyEditor extends PolicyEditor {

	private Button smokerButton;
	private Button drinkerButton;
	private Button ironmanButton;
	private LifePolicy lp;
	
	public LifePolicyEditor(Shell parent,SimpleInsurance company, LifePolicy policy, boolean isNew) {
		super(parent,company,policy,isNew);
		this.lp = policy;
		createContents();
	}
	
	protected boolean applyChanges() {
		if (super.applyChanges()) {
			lp.setSmoker(smokerButton.getSelection());
			lp.setDrinker(drinkerButton.getSelection());
			lp.setIronman(ironmanButton.getSelection());
			return true;
		} else {
			return false;
		}
	}
	protected Control createDialogArea(Composite parent) {
		Control ret = super.createDialogArea(parent);
		CLabel smoker = new CLabel(detailsGroup,SWT.NONE);
		smoker.setText(Messages.getString("life.smoker"));
		smokerButton = new Button(detailsGroup,SWT.CHECK);
		CLabel drinker = new CLabel(detailsGroup,SWT.NONE);
		drinker.setText(Messages.getString("life.drinker"));
		drinkerButton = new Button(detailsGroup,SWT.CHECK);
		CLabel ironman = new CLabel(detailsGroup,SWT.NONE);
		ironman.setText(Messages.getString("life.ironman"));
		ironmanButton = new Button(detailsGroup,SWT.CHECK);
		populateFields();
		return ret;
	}
	protected void populateFields() {
		super.populateFields();
		smokerButton.setSelection(lp.isSmoker());
		drinkerButton.setSelection(lp.isDrinker());
		ironmanButton.setSelection(lp.isIronman());
	}
	
	/*
	 * DisposeListener interface implementation
	 */ 
	public void widgetDisposed(DisposeEvent arg0) {
		lp.removePolicyListener(this);
	}
}
