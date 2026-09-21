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

import insurance.model.Claim;
import insurance.model.Policy;
import insurance.model.SimpleInsurance;
import insurance.model.listeners.ClaimListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ClaimEditor extends EntityEditor implements ClaimListener {

	private Claim claim;
	private Policy policy;
	private boolean applyingChanges = false;
	private Text amount;
	private Text created;
	private Text status;
	private Text comments;
	
	/**
	 * @param parent
	 * @param company
	 * @param isNew
	 */
	public ClaimEditor(Shell parent, SimpleInsurance company, Policy policy, Claim claim, boolean isNew) {
		super(parent, company, isNew);
		this.claim = claim;
		this.policy = policy;
		if (isNew) {
			myShell.setText(Messages.getString("claim.addTitle"));
		} else {
			myShell.setText(Messages.getString("claim.editTitle"));
		}
		this.applyingChanges = false;
		claim.addClaimListener(this);
		createContents();
	}

	/* (non-Javadoc)
	 * @see insurance.ui.EntityEditor#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent,SWT.NONE);
		composite.setLayout(new GridLayout(2,false));
		new CLabel(composite,SWT.NONE).setText(Messages.getString("claim.claimId"));
		Text id = new Text(composite,SWT.BORDER);
		id.setEditable(false);
		id.setText(claim.getClaimId());
		new CLabel(composite,SWT.NONE).setText(Messages.getString("claim.amount"));
		amount = createText(composite);
		new CLabel(composite,SWT.NONE).setText(Messages.getString("claim.created"));
		created = createText(composite);
		new CLabel(composite,SWT.NONE).setText(Messages.getString("claim.status"));
		status = createText(composite);
		new CLabel(composite,SWT.NONE).setText(Messages.getString("claim.comments"));
		comments = createText(composite);
		comments.setSize(80,10);
		populateFields();
		return composite;
	}

	
	
	/* (non-Javadoc)
	 * @see insurance.ui.EntityEditor#cancelPressed()
	 */
	protected void cancelPressed() {
		if (isNew) {
			policy.removeClaim(claim);
		}
		claim.removeClaimListener(this);
		super.cancelPressed();
	}
	
	/* (non-Javadoc)
	 * @see insurance.ui.EntityEditor#okPressed()
	 */
	protected void okPressed() {
		if (applyChanges()) {
			if (isNew) {
				company.insertClaim(policy, claim);
			}  else {
				company.updateClaim(claim);
			}
			claim.removeClaimListener(this);
			super.okPressed();
		}
	}
	
	/* (non-Javadoc)
	 * @see insurance.ui.EntityEditor#applyChanges()
	 */
	protected boolean applyChanges() {
		boolean ok = true;
		applyingChanges = true;
		Color red = new Color(myShell.getDisplay(),255,0,0);
		Color black = new Color(myShell.getDisplay(),0,0,0);
		created.setForeground(black);
		amount.setForeground(black);
		Date createdDate = null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			createdDate = df.parse(created.getText());
		} catch (ParseException pex) {
			created.setForeground(red);
			if (created.getText().equals("")) created.setText(Messages.getString("validation.dateFormat"));
			ok = false;
		}
		double amountVal = 0.0;
		try {
			amountVal = Double.parseDouble(amount.getText());
		} catch (NumberFormatException ex) {
			amount.setForeground(red);
			ok = false;
		}
		if (ok) {
			claim.setAmount(amountVal);
			claim.setCreated(createdDate);
			claim.setStatus(status.getText());
			claim.setComments(comments.getText());
		}		
		applyingChanges = false;
		return ok;
	}

	/* (non-Javadoc)
	 * @see insurance.model.listeners.ClaimListener#claimUpdated(insurance.model.Claim)
	 */
	public void claimUpdated(Claim claim) {
		if (claim.equals(this.claim) && !applyingChanges) {
			populateFields();
		}
		
	}
	
	private void populateFields() {
		amount.setText(""+ claim.getAmount());
		created.setText(new SimpleDateFormat("dd/MM/yyyy").format(claim.getCreated()));
		status.setText(claim.getStatus() == null ? "" : claim.getStatus());
		comments.setText(claim.getComments() == null ? "" : claim.getComments());
	}
	
	/*
	 * DisposeListener interface implementation
	 */ 
	public void widgetDisposed(DisposeEvent arg0) {
		claim.removeClaimListener(this);
	}

}
