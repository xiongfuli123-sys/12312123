/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import insurance.model.Address;
import insurance.model.AutoPolicy;
import insurance.model.Customer;
import insurance.model.HousePolicy;
import insurance.model.LifePolicy;
import insurance.model.PolicyType;
import insurance.model.SimpleInsurance;
import insurance.model.listeners.CustomerListener;


public class CustomerEditor extends EntityEditor
implements CustomerListener {



	private Customer customer;
	private Text lastNameText;
	private Text firstNameText;
	private Text dobText;
	private Text line1Text;
	private Text line2Text;
	private Text townCityText;
	private Text postcodeText;
	private Text phoneText;
	private List policiesList;
	private boolean applyingChanges;
	private Button lifePolicyButton;
	private Button housePolicyButton;
	private Button autoPolicyButton;
	
	
	public CustomerEditor(Shell shell, SimpleInsurance company,
			Customer c, boolean isNew) {
		super(shell,company,isNew);
		if (isNew) {
			myShell.setText(Messages.getString("customer.addTitle"));
		} else {
			myShell.setText(Messages.getString("customer.editTitle"));
		}
		this.customer = c;
		this.applyingChanges = false;
		customer.addCustomerListener(this);
		createContents();
	}
	
	public Customer getCustomer() { return customer; }
	
	
	protected void cancelPressed() {
		super.cancelPressed();
		customer.removeCustomerListener(this);
	}
	
	
	protected void okPressed() {
		if (applyChanges()) {
			if (isNew) {
				company.insertCustomer(customer);
			} else {
				company.updateCustomer(customer);
			}
			customer.removeCustomerListener(this);
			super.okPressed();
		}
	}
	
	protected boolean applyChanges() {
		applyingChanges = true;
		boolean ok = true;
		Color red = new Color(myShell.getDisplay(),255,0,0);
		Color black = new Color(myShell.getDisplay(),0,0,0);
		lastNameText.setForeground(black);
		firstNameText.setForeground(black);
		dobText.setForeground(black);
		if (lastNameText.getText().equals("")) {
			ok = false;
			lastNameText.setForeground(red);
			lastNameText.setText(Messages.getString("validation.required"));
		}
		if (firstNameText.getText().equals("")) {
			ok = false;
			firstNameText.setForeground(red);
			firstNameText.setText(Messages.getString("validation.required"));
		}
		Date dob = null;
		try {
			 dob = new SimpleDateFormat("dd/MM/yyyy").parse(dobText.getText());
		} catch(ParseException pEx) {
			dobText.setForeground(red);
            if (dobText.getText().equals("")) dobText.setText(Messages.getString("validation.dateFormat"));
			ok = false;
		}
		if (ok) {
			customer.setLastName(lastNameText.getText());
			customer.setFirstName(firstNameText.getText());
			customer.setDob(dob);
			Address a = customer.getAddress();
			if (a == null) {
				a = company.createAddress();
				customer.setAddress(a);
			}
			a.setLine1(line1Text.getText());
			a.setLine2(line2Text.getText());
			a.setTownCity(townCityText.getText());
			a.setPostcode(postcodeText.getText());
			a.setPhone(phoneText.getText());
		}
		applyingChanges = false;
		return ok;
	}
	
	private void populateFields() {
		if (customer.getLastName() != null) lastNameText.setText(customer.getLastName());
		if (customer.getFirstName() != null) firstNameText.setText(customer.getFirstName());
		if (customer.getDob() != null) {
			dobText.setText(new SimpleDateFormat("dd/MM/yyyy").format(customer.getDob()));
		} 
		Address a = customer.getAddress();
		if (a == null) {
			a = company.createAddress();
			customer.setAddress(a);
		}
		line1Text.setText(a.getLine1() != null ? a.getLine1() : "");
		line2Text.setText(a.getLine2() != null ? a.getLine2() : "");
		townCityText.setText(a.getTownCity() != null ? a.getTownCity() : "");
		postcodeText.setText(a.getPostcode() != null ? a.getPostcode() : "");
		phoneText.setText(a.getPhone() != null ? a.getPhone() : "");
		Set policies = customer.getPolicies();
		String[] items = new String[policies.size()];
		Iterator it = policies.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = it.next().toString();
		}
		policiesList.setItems(items);
		policiesList.setData(policies.toArray());
		
	}
	

	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		composite.setLayout(layout);
		
		Group detailsGroup = new Group(composite,SWT.NONE);
		detailsGroup.setText(Messages.getString("customer.details"));
		detailsGroup.setLayout(new GridLayout(2,false));
		
		CLabel idLabel = new CLabel(detailsGroup,SWT.RIGHT);
		idLabel.setText(Messages.getString("customer.id"));
		Text idText = new Text(detailsGroup,SWT.BORDER);
		idText.setText(customer.getCustomerID());
		idText.setEditable(false);
		idText.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));

		CLabel lastNameLabel = new CLabel(detailsGroup,SWT.RIGHT);
		lastNameLabel.setText(Messages.getString("customer.lastName"));
		lastNameText = createText(detailsGroup);

		CLabel firstNameLabel = new CLabel(detailsGroup,SWT.RIGHT);
		firstNameLabel.setText(Messages.getString("customer.firstName"));
		firstNameText = createText(detailsGroup);

		CLabel dateOfBirthLabel = new CLabel(detailsGroup,SWT.RIGHT);
		dateOfBirthLabel.setText(Messages.getString("customer.dob"));
		dobText = createText(detailsGroup);
				
		CLabel line1 = new CLabel(detailsGroup,SWT.RIGHT);
		line1.setText(Messages.getString("customer.addressLine1"));
		line1Text = createText(detailsGroup);

		CLabel line2 = new CLabel(detailsGroup,SWT.RIGHT);
		line2.setText(Messages.getString("customer.addressLine2"));
		line2Text = createText(detailsGroup);

		CLabel townCity = new CLabel(detailsGroup,SWT.RIGHT);
		townCity.setText(Messages.getString("customer.townCity"));
		townCityText = createText(detailsGroup);

		CLabel postcode = new CLabel(detailsGroup,SWT.RIGHT);
		postcode.setText(Messages.getString("customer.postcode"));
		postcodeText = createText(detailsGroup);

		CLabel phone = new CLabel(detailsGroup,SWT.RIGHT);
		phone.setText(Messages.getString("customer.phone"));
		phoneText = createText(detailsGroup);

		Group policiesGroup = new Group(composite,SWT.NONE | SWT.TOP);
		policiesGroup.setText(Messages.getString("customer.policies"));
		policiesGroup.setLayout( new GridLayout(2,false));
		policiesGroup.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		policiesList = new List(policiesGroup,SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		gd.widthHint = 200;
		policiesList.setLayoutData (gd);
		
		Composite buttonsGroup = new Composite(policiesGroup,SWT.TOP);
		buttonsGroup.setLayout(new GridLayout(1,false));
		buttonsGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		Composite addGroup = new Composite(buttonsGroup,SWT.BORDER);
		addGroup.setLayout(new GridLayout(1,false));
		lifePolicyButton = new Button(addGroup,SWT.RADIO);
		lifePolicyButton.setText(Messages.getString("policy.type.life"));
		lifePolicyButton.setSelection(true);
		housePolicyButton = new Button(addGroup,SWT.RADIO);
		housePolicyButton.setText(Messages.getString("policy.type.house"));
		autoPolicyButton = new Button(addGroup,SWT.RADIO);
		autoPolicyButton.setText(Messages.getString("policy.type.auto"));
		
		Button addPolicyButton = new Button(addGroup,SWT.PUSH);
		addPolicyButton.setText(Messages.getString("button.add"));
		if (isNew) addPolicyButton.setEnabled(false);
		Button editPolicyButton = new Button(buttonsGroup,SWT.PUSH);
		editPolicyButton.setText(Messages.getString("button.edit"));
		if (isNew) editPolicyButton.setEnabled(false);
		Button deletePolicyButton = new Button(buttonsGroup,SWT.PUSH);
		deletePolicyButton.setText(Messages.getString("button.delete"));
		if (isNew) deletePolicyButton.setEnabled(false);
		
		// wiring
		deletePolicyButton.addSelectionListener(new DeletePolicyListener(policiesList,myShell));
		editPolicyButton.addSelectionListener(new EditPolicyListener(policiesList,myShell));
		addPolicyButton.addSelectionListener(new AddPolicyListener());
		populateFields();
		
		return composite;
	}

	/* (non-Javadoc)
	 * @see insurance.model.Customer.CustomerListener#customerUpdated(insurance.model.Customer)
	 */
	public void customerUpdated(Customer c) {
		if ((customer == c) && !applyingChanges) {
			populateFields();
		}
	}
	
	/*
	 * DisposeListener interface implementation
	 */ 
	public void widgetDisposed(DisposeEvent arg0) {
		customer.removeCustomerListener(this);
	}
	
	class AddPolicyListener extends SelectionAdapter {

		
		public void widgetSelected(SelectionEvent event) {
			if (lifePolicyButton.getSelection()) {
				LifePolicy lp = (LifePolicy) company.createPolicy(customer,PolicyType.LIFE);
				new LifePolicyEditor(myShell,
					SimpleInsuranceApp.getCompany(),lp,true);
			} else if (autoPolicyButton.getSelection()) {
				AutoPolicy ap = (AutoPolicy) company.createPolicy(customer,PolicyType.AUTO);
				new AutoPolicyEditor(myShell,
						SimpleInsuranceApp.getCompany(),ap,true);
			} else {
				HousePolicy hp = (HousePolicy) company.createPolicy(customer,PolicyType.HOUSE);
				new HousePolicyEditor(myShell,
						SimpleInsuranceApp.getCompany(),hp,true);
			}
		}
	}

}

