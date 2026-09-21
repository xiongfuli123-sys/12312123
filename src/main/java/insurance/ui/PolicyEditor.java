/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.Claim;
import insurance.model.Customer;
import insurance.model.Policy;
import insurance.model.Schedule;
import insurance.model.SimpleInsurance;
import insurance.model.listeners.CustomerListener;
import insurance.model.listeners.PolicyListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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

/**
 * @author colyer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class PolicyEditor extends EntityEditor 
implements PolicyListener, CustomerListener {

	protected Policy policy;
	private List claimsList;
	private Text customerName;
	private Text endDate;
	private Text startDate;
	private Text policyValue;
	private Button isQuote;
	private boolean applyingChanges = false;
	private Text scheduleNumPayments;
	private Text schedulePaymentAmount;
	private Text schedulePaymentDue;
	
//	protected Composite detailsGroup;
	protected Group detailsGroup;
	
	public PolicyEditor(Shell parent,SimpleInsurance company, Policy policy, boolean isNew) {
		super(parent,company,isNew);
//		if (isNew) {
//			myShell.setText("Add " + policy.getType().toString() + " Policy");
//		} else {
//			myShell.setText("Edit " + policy.getType().toString() + " Policy");
//		}
		this.policy = policy;
		policy.addPolicyListener(this);
		policy.getCustomer().addCustomerListener(this);
		setTitle();
	}
	
	protected void setTitle () {
		String typeLabel = Messages.getString("type." + policy.getType().toString());
		String title;
		if (isNew) {
			title = Messages.getString("policy.addTitle", typeLabel);
		} else {
			title = Messages.getString("policy.editTitle", typeLabel);
		}
		myShell.setText(title);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite dialogContent = new Composite(parent,SWT.NONE);
		dialogContent.setLayout(new GridLayout(2,false));
	
		detailsGroup = new Group(dialogContent,SWT.NONE);
		detailsGroup.setText(Messages.getString("policy.details"));
		detailsGroup.setLayout(new GridLayout(2,false));
		GridData gridData = new GridData();
		gridData.verticalSpan = 2;
		detailsGroup.setLayoutData(gridData);
		
		CLabel plabel = new CLabel(detailsGroup,SWT.NONE);
		plabel.setText(Messages.getString("policy.policyId"));
		Text idText = new Text(detailsGroup,SWT.BORDER);
		idText.setText(policy.getPolicyID());
		idText.setEditable(false);
		CLabel ptype = new CLabel(detailsGroup,SWT.NONE);
		ptype.setText(Messages.getString("policy.policyType"));
		Text typeText = new Text(detailsGroup,SWT.BORDER);
		typeText.setText(Messages.getString("type." + policy.getType().toString()));
		typeText.setEditable(false);

		CLabel customerLabel = new CLabel(detailsGroup,SWT.NONE);
		customerLabel.setText(Messages.getString("policy.customer"));
		customerName = new Text(detailsGroup,SWT.BORDER);
		customerName.setText(policy.getCustomer().toString());
		customerName.setEditable(false);
		
		CLabel startDateLabel = new CLabel(detailsGroup,SWT.NONE);
		startDateLabel.setText(Messages.getString("policy.startDate"));
		startDate = createText(detailsGroup);
		CLabel endDateLabel = new CLabel(detailsGroup,SWT.NONE);
		endDateLabel.setText(Messages.getString("policy.endDate"));
		endDate = createText(detailsGroup);
		CLabel policyValueLabel = new CLabel(detailsGroup,SWT.NONE);
		policyValueLabel.setText(Messages.getString("policy.value"));
		policyValue = createText(detailsGroup);
		CLabel isQuoteLabel = new CLabel(detailsGroup,SWT.NONE);
		isQuoteLabel.setText(Messages.getString("policy.isQuote"));
		isQuote = new Button(detailsGroup,SWT.CHECK);
		
//		CLabel schedule = new CLabel(detailsGroup,SWT.NONE);
//		schedule.setText("Payment Schedule:");
		Group scheduleGroup = new Group(dialogContent,SWT.NONE);
		scheduleGroup.setText(Messages.getString("policy.schedule"));
		scheduleGroup.setLayout(new GridLayout(2,false));
//		scheduleGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		CLabel numPayments = new CLabel(scheduleGroup,SWT.NONE);
		numPayments.setText(Messages.getString("policy.numPayments"));
//		numPayments.setLayoutData(new GridData(GridData.FILL_BOTH));
		scheduleNumPayments = createText(scheduleGroup);
		CLabel payAmount = new CLabel(scheduleGroup,SWT.NONE);
		payAmount.setText(Messages.getString("policy.paymentAmount"));
		schedulePaymentAmount = createText(scheduleGroup);
		CLabel payDate = new CLabel(scheduleGroup,SWT.NONE);
		payDate.setText(Messages.getString("policy.firstPaymentDue"));
		schedulePaymentDue = createText(scheduleGroup);
		
		/* Claims */
		Group claimsGroup = new Group(dialogContent,SWT.NONE);
		claimsGroup.setText(Messages.getString("policy.claims"));
		claimsGroup.setLayout( new GridLayout(2,false));
		claimsGroup.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL));
		claimsList = new List(claimsGroup,SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		gd.widthHint = 100;
		claimsList.setLayoutData (gd);
		
		Composite buttonsGroup = new Composite(claimsGroup,SWT.TOP);
		buttonsGroup.setLayout(new GridLayout(1,false));
		buttonsGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		Button addClaimButton = new Button(buttonsGroup,SWT.PUSH);
		addClaimButton.setText(Messages.getString("button.add"));
		if (isNew) addClaimButton.setEnabled(false);
		Button editClaimButton = new Button(buttonsGroup,SWT.PUSH);
		editClaimButton.setText(Messages.getString("button.edit"));
		if (isNew) editClaimButton.setEnabled(false);
		Button deleteClaimButton = new Button(buttonsGroup,SWT.PUSH);
		deleteClaimButton.setText(Messages.getString("button.delete"));
		if (isNew) deleteClaimButton.setEnabled(false);		
		
		// wiring
		deleteClaimButton.addSelectionListener(new DeleteClaimListener(claimsList,policy,myShell));
		addClaimButton.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent arg0) {
			Claim c = company.createClaim();
			new ClaimEditor(myShell,company,policy,c,true);
		}});
		editClaimButton.addSelectionListener(new EditClaimListener(claimsList,policy,myShell));
		return dialogContent;
	}
	
	

	protected void okPressed() {
		applyingChanges = true;
		if (applyChanges()) {
			if (isNew) {
				company.insertPolicy(policy);
				company.updateCustomer(policy.getCustomer());
			} else {
				company.updatePolicy(policy);
			}
			policy.removePolicyListener(this);
			policy.getCustomer().removeCustomerListener(this);
			super.okPressed();			
		}
		applyingChanges = false;
	}

	protected void cancelPressed() {
		if (isNew) {
			policy.getCustomer().removePolicy(policy);
		}
		policy.removePolicyListener(this);
		policy.getCustomer().removeCustomerListener(this);
		super.cancelPressed();
	}

	
	protected void populateFields() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (policy.getEndDate() != null) {
			endDate.setText(df.format(policy.getEndDate()));
		}
		if (policy.getStartDate() != null) {
			startDate.setText(df.format(policy.getStartDate()));
		}
		policyValue.setText("" + policy.getValue());
		isQuote.setSelection(policy.isQuote());
		Schedule s = policy.getSchedule();
		if (s == null) s = new Schedule(1,0,new Date());
		scheduleNumPayments.setText("" + s.getNumPayments());
		schedulePaymentAmount.setText("" + s.getPaymentAmount());
		schedulePaymentDue.setText(df.format(s.getFirstPaymentDue()));
		Set claims = policy.getClaims();
		if (claims == null) claims = new HashSet();
		String[] items = new String[claims.size()];
		Iterator it = claims.iterator();
		for (int i = 0; i < items.length; i++) {
			items[i] = it.next().toString();
		}
		claimsList.setItems(items);
		claimsList.setData(claims.toArray());
	}

	protected boolean applyChanges() {
		boolean ok = true;
		Color red = new Color(myShell.getDisplay(),255,0,0);
		Color black = new Color(myShell.getDisplay(),0,0,0);
		startDate.setForeground(black);
		endDate.setForeground(black);
		policyValue.setForeground(black);
		scheduleNumPayments.setForeground(black);
		schedulePaymentAmount.setForeground(black);
		schedulePaymentDue.setForeground(black);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date sDate = null;
		Date eDate = null;
		double value = 0;
		try {
			sDate = df.parse(startDate.getText());
		} catch (ParseException pex) {
			startDate.setForeground(red);
			if (startDate.getText().equals("")) startDate.setText(Messages.getString("validation.dateFormat"));
			ok = false;
		}
		try {
			eDate = df.parse(endDate.getText());
		} catch (ParseException pex) {
			endDate.setForeground(red);
			if (endDate.getText().equals("")) endDate.setText(Messages.getString("validation.dateFormat"));
			ok = false;
		}
		try {
			value = Double.parseDouble(policyValue.getText());
		} catch (NumberFormatException ex) {
			policyValue.setForeground(red);
			if (policyValue.getText().equals("")) policyValue.setText(Messages.getString("validation.requiredShort"));
			ok = false;
		}
		long numPayments = 0;
		double payValue = 0;
		Date payDate = new Date();
		try {
			numPayments = Long.parseLong(scheduleNumPayments.getText());
		} catch (NumberFormatException ex) {
			scheduleNumPayments.setForeground(red);
			if (scheduleNumPayments.getText().equals("")) scheduleNumPayments.setText(Messages.getString("validation.requiredShort"));
			ok = false;
		}
		try {
			payValue = Double.parseDouble(schedulePaymentAmount.getText());
		} catch (NumberFormatException ex) {
			schedulePaymentAmount.setForeground(red);
			if (schedulePaymentAmount.getText().equals("")) schedulePaymentAmount.setText(Messages.getString("validation.requiredShort"));
			ok = false;
		}
		try {
			payDate = df.parse(schedulePaymentDue.getText());
		} catch (ParseException pex) {
			schedulePaymentDue.setForeground(red);
			if (schedulePaymentDue.getText().equals("")) schedulePaymentDue.setText(Messages.getString("validation.dateFormat"));
			ok = false;
		}
		if (ok) {
			policy.setIsQuote(isQuote.getSelection());
			policy.setValue(value);
			policy.setStartDate(sDate);
			policy.setEndDate(eDate);
			Schedule s = new Schedule(numPayments,payValue,payDate);
			policy.setSchedule(s);
		}
		return ok;
	}


	public void policyUpdated(Policy policy) {
		if ((policy == this.policy) && !applyingChanges) {
			populateFields();
		}
	}

	public void customerUpdated(Customer c) {
		if ((c == policy.getCustomer())) {
			customerName.setText(c.toString());
		}		
	}
	
}

class DeleteClaimListener extends SelectionAdapter {

	private List claimList;
	private Policy policy;
	private Shell shell;
	
	public DeleteClaimListener(List claimList, Policy policy, Shell shell) {
		this.claimList = claimList;
		this.policy = policy;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		String[] ids = claimList.getSelection();
		if (ids.length == 1) {
			boolean confirmed = MessageDialog.openConfirm(shell,
					Messages.getString("confirm.claimDeleteTitle"),
					Messages.getString("confirm.claimDeleteMessage", ids[0]));
			if (confirmed) {
				System.out.println(Messages.getString("log.deleting"));
				Object[] claims = (Object[]) claimList.getData();
				int index = claimList.getSelectionIndices()[0];
				Claim c = (Claim) claims[index];
				policy.removeClaim(c);
				// should be wired into company as a listener to update
				// GUI
			}
		}
	}
}

class EditClaimListener extends SelectionAdapter {

	private List claimList;
	private Policy policy;
	private Shell shell;
	
	public EditClaimListener(List claimList, Policy policy, Shell shell) {
		this.claimList = claimList;
		this.policy = policy;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		String[] ids = claimList.getSelection();
		if (ids.length == 1) {
			Object[] claims = (Object[]) claimList.getData();
			int index = claimList.getSelectionIndices()[0];
			Claim c = (Claim) claims[index];
			new ClaimEditor(shell,SimpleInsuranceApp.getCompany(),policy,c,false);
		}
	}
}
