/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.SimpleInsurance;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.ShellAdapter;
//import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public abstract class EntityEditor implements DisposeListener {

	protected SimpleInsurance company;
	protected boolean isNew;
	protected Shell myShell;
	
	public EntityEditor(Shell parent,SimpleInsurance company, boolean isNew) {
		this.company = company;
		this.isNew = isNew;
		myShell = new Shell(parent);
		myShell.setLayout(new FillLayout());
		myShell.addDisposeListener(this);
	}
	
	protected void createContents() {
		Composite composite = new Composite(myShell,SWT.NONE);
		composite.setLayout(new GridLayout(1,false));
		Control content = createDialogArea(composite);
		Composite buttons = new Composite(composite,SWT.NONE);
		buttons.setLayout(new GridLayout(2,false));
		Button ok = new Button(buttons,SWT.NONE);
		ok.setText(" " + Messages.getString("button.ok") + " ");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				okPressed();
			}});
		Button cancel = new Button(buttons,SWT.NONE);
		cancel.setText(" " + Messages.getString("button.cancel") + " ");
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				cancelPressed();
			}});
		
		/* Avoid "Widget is disposed" SWTException if user simply closes window */
//		myShell.addShellListener(new ShellAdapter() {
//			public void shellClosed(ShellEvent arg0) {
//				cancelPressed();
//			}
//		});
		Display d = Display.getDefault();
		setShellSize(d,myShell);
		myShell.open();
	}

	protected Text createText(Composite group) {
		Text text = new Text(group,SWT.BORDER);
		text.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		return text;
	}
	
	protected void okPressed() {
		myShell.close();
		myShell.dispose();
	}
	
	protected void cancelPressed() {
		myShell.close();
		myShell.dispose();
	}
	
	protected abstract Control createDialogArea(Composite parent);
	
	protected abstract boolean applyChanges();
	
	/**
	 * Sets the size of the shell to it's "packed" size,
	 * unless that makes it bigger than the display,
	 * in which case set it to 9/10 of display size.
	 */
	static void setShellSize (Display display, Shell shell) {
		Rectangle bounds = display.getBounds();
		Point size = shell.computeSize (SWT.DEFAULT, SWT.DEFAULT);
		if (size.x > bounds.width) size.x = bounds.width * 9 / 10;
		if (size.y > bounds.height) size.y = bounds.height * 9 / 10;
		shell.setSize (size);
	}
	
}
