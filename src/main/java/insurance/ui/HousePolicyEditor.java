/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.Address;
import insurance.model.HousePolicy;
import insurance.model.SimpleInsurance;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author colyer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HousePolicyEditor extends PolicyEditor {

	private Text worth;
	private Text line1Text;
	private Text line2Text;
	private Text townCityText;
	private Text postcodeText;
	private Text phoneText;
	private HousePolicy hp;
	
	public HousePolicyEditor(Shell parent,SimpleInsurance company, HousePolicy policy, boolean isNew) {
		super(parent,company,policy,isNew);
		this.hp = policy;
		createContents();
	}
	
	protected boolean applyChanges() {
		boolean ok = true;
		Color red = new Color(myShell.getDisplay(),255,0,0);
		Color black = new Color(myShell.getDisplay(),0,0,0);
		worth.setForeground(black);
		double marketWorth = 0;
		try {
			marketWorth = Double.parseDouble(worth.getText());	
		} catch (NumberFormatException ex) {
			worth.setForeground(red);
			ok = false;
		}
		if (ok) {
			ok = super.applyChanges();
		}
		if (ok) {
			hp.setWorth(marketWorth);
			Address a = hp.getAddress();
			if (a == null) {
				a = company.createAddress();
				hp.setAddress(a);
			}
			a.setLine1(line1Text.getText());
			a.setLine2(line2Text.getText());
			a.setTownCity(townCityText.getText());
			a.setPostcode(postcodeText.getText());
			a.setPhone(phoneText.getText());
		}
		return ok;
	}
	protected Control createDialogArea(Composite parent) {
		Control ret = super.createDialogArea(parent);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.value"));
		worth = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.addressLine1"));
		line1Text = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.addressLine2"));
		line2Text = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.townCity"));
		townCityText = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.postcode"));
		postcodeText = createText(detailsGroup);
		new CLabel(detailsGroup,SWT.NONE).setText(Messages.getString("house.phone"));
		phoneText = createText(detailsGroup);

		populateFields();
		return ret;
	}
	protected void populateFields() {
		super.populateFields();
		worth.setText(""+hp.getWorth());
		Address a = hp.getAddress();
		if (a == null) {
			a = company.createAddress();
			hp.setAddress(a);
		}
		line1Text.setText(a.getLine1());
		line2Text.setText(a.getLine2());
		townCityText.setText(a.getTownCity());
		postcodeText.setText(a.getPostcode());
		phoneText.setText(a.getPhone());
	}
	
	/*
	 * DisposeListener interface implementation
	 */ 
	public void widgetDisposed(DisposeEvent arg0) {
		hp.removePolicyListener(this);
	}
}
