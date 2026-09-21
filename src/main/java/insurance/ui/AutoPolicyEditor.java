/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.AutoPolicy;
import insurance.model.Automobile;
import insurance.model.SimpleInsurance;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AutoPolicyEditor extends PolicyEditor {

	private Button noClaimsButton;
	private Text excess;
	private Text carColour;
	private Text insuranceGroup;
	private Text make;
	private Text marketValue;
	private Text model;
	private Text plate;
	private AutoPolicy ap;
	
	public AutoPolicyEditor(Shell parent,SimpleInsurance company, AutoPolicy policy, boolean isNew) {
		super(parent,company,policy,isNew);
		this.ap = policy;
		createContents();
	}
	
	protected boolean applyChanges() {
		boolean ok = true;
		Color red = new Color(myShell.getDisplay(),255,0,0);
		Color black = new Color(myShell.getDisplay(),0,0,0);
		excess.setForeground(black);
		marketValue.setForeground(black);
		insuranceGroup.setForeground(black);
		double excessVal = 0;
		double marketVal = 0;
		int insGroup = 10;
		try {
			excessVal = Double.parseDouble(excess.getText());	
		} catch (NumberFormatException ex) {
			excess.setForeground(red);
			ok = false;
		}
		try {
			marketVal = Double.parseDouble(marketValue.getText());
		} catch (NumberFormatException ex) {
			marketValue.setForeground(red);
			ok = false;
		}
		try {
			insGroup = Integer.parseInt(insuranceGroup.getText());
		} catch (NumberFormatException nex) {
			insuranceGroup.setForeground(red);
			ok = false;
		}
		if (ok) {
			ok = super.applyChanges();
		}
		if (ok) {
			ap.setExcess(excessVal);
			ap.setNoClaims(noClaimsButton.getSelection());
			Automobile car = new Automobile(
				make.getText(),
				model.getText(),
				carColour.getText(),
				plate.getText(),
				marketVal,
				insGroup
			);
			ap.setCar(car);
		}
		return ok;
	}
	protected Control createDialogArea(Composite parent) {
		Control ret = super.createDialogArea(parent);
		CLabel excessLabel = new CLabel(detailsGroup,SWT.NONE);
		excessLabel.setText(Messages.getString("auto.excess"));
		excess = createText(detailsGroup);
		CLabel noClaimsLabel = new CLabel(detailsGroup,SWT.NONE);
		noClaimsLabel.setText(Messages.getString("auto.noClaims"));
		noClaimsButton = new Button(detailsGroup,SWT.CHECK);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.make"));
		make = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.model"));
		model = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.colour"));
		carColour = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.plate"));
		plate = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.marketValue"));
		marketValue = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("auto.insuranceGroup"));
		insuranceGroup = createText(detailsGroup);		
		populateFields();
		return ret;
	}
	protected void populateFields() {
		super.populateFields();
		excess.setText(""+ap.getExcess());
		noClaimsButton.setSelection(ap.isNoClaims());
		Automobile car = ap.getCar();
		if (car == null) {
			car = new Automobile("","","","",0.0,10);
		}
		make.setText(car.getMake());
		model.setText(car.getModel());
		carColour.setText(car.getColour());
		plate.setText(car.getPlate());
		marketValue.setText(""+car.getMarketValue());
		insuranceGroup.setText(""+car.getInsuranceGroup());
	}
	
	/*
	 * DisposeListener interface implementation
	 */ 
	public void widgetDisposed(DisposeEvent arg0) {
		ap.removePolicyListener(this);
	}
}
