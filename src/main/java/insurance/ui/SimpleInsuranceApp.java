/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.ui;

import insurance.model.Customer;
import insurance.model.Policy;
import insurance.model.PolicyType;
import insurance.model.SimpleInsurance;
import insurance.model.SimpleInsuranceFactory;
import insurance.model.listeners.InsuranceCompanyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class SimpleInsuranceApp implements InsuranceCompanyListener {

	private static SimpleInsurance company;
	private static Shell shell;
	private TabFolder tabFolder;
	private TabItem   policiesTab;
	private TabItem   customersTab;
	private List      customerList;
	private List      policiesList;

	private CLabel langLabel;
	private Button zhButton;
	private Button enButton;

	private Group customersFindByGroup;
	private CLabel customersFindByIdLabel;
	private CLabel customersFindByNameLabel;
	private Group customersResultsGroup;
	private Button addCustomerButton;
	private Button editCustomerButton;
	private Button deleteCustomerButton;

	private Group policiesFindByGroup;
	private CLabel policiesFindByPolicyIdLabel;
	private CLabel policiesFindByCustomerIdLabel;
	private CLabel policiesFindByNameLabel;
	private Group policiesResultsGroup;
	private Button editPolicyButton;
	private Button deletePolicyButton;

	public static SimpleInsurance getCompany() { return company; }
	
	public SimpleInsuranceApp(Composite parent) {
		company = SimpleInsuranceFactory.createSimpleInsurance();
		loadTestData(company);
		createLanguageBar(parent);
		tabFolder = new TabFolder (parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		customersTab = new TabItem (tabFolder, SWT.NONE);
		customersTab.setControl(createCustomersControl());
		customersTab.setData(company);
		policiesTab = new TabItem(tabFolder,SWT.NONE);
		policiesTab.setControl(createPoliciesControl());
		policiesTab.setData(company);
		company.addListener(this);
		applyMainTexts();
		updateLanguageToggleSelection();
	}

	private void createLanguageBar(Composite parent) {
		Composite langBar = new Composite(parent, SWT.NONE);
		langBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		langBar.setLayout(new GridLayout(3, false));

		langLabel = new CLabel(langBar, SWT.NONE);
		zhButton = new Button(langBar, SWT.TOGGLE);
		enButton = new Button(langBar, SWT.TOGGLE);

		zhButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				switchLocale(Locale.SIMPLIFIED_CHINESE);
			}
		});
		enButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				switchLocale(Locale.ENGLISH);
			}
		});
	}

	private void switchLocale(Locale locale) {
		Messages.setLocale(locale);
		applyMainTexts();
		refreshListItems();
		updateLanguageToggleSelection();
	}

	private void updateLanguageToggleSelection() {
		boolean zh = "zh".equals(Messages.getLocale().getLanguage());
		zhButton.setSelection(zh);
		enButton.setSelection(!zh);
	}

	private void applyMainTexts() {
		if (shell != null && !shell.isDisposed()) {
			shell.setText(Messages.getString("app.title"));
		}
		langLabel.setText(Messages.getString("lang.label"));
		zhButton.setText(Messages.getString("lang.zh"));
		enButton.setText(Messages.getString("lang.en"));
		customersTab.setText(Messages.getString("tab.customers"));
		policiesTab.setText(Messages.getString("tab.policies"));

		customersFindByGroup.setText(Messages.getString("customers.findBy"));
		customersFindByIdLabel.setText(Messages.getString("customer.id"));
		customersFindByNameLabel.setText(Messages.getString("customer.lastName"));
		customersResultsGroup.setText(Messages.getString("results"));
		addCustomerButton.setText(Messages.getString("button.add"));
		editCustomerButton.setText(Messages.getString("button.edit"));
		deleteCustomerButton.setText(Messages.getString("button.delete"));

		policiesFindByGroup.setText(Messages.getString("policies.findBy"));
		policiesFindByPolicyIdLabel.setText(Messages.getString("policy.id"));
		policiesFindByCustomerIdLabel.setText(Messages.getString("customer.id"));
		policiesFindByNameLabel.setText(Messages.getString("customer.lastName"));
		policiesResultsGroup.setText(Messages.getString("results"));
		editPolicyButton.setText(Messages.getString("button.edit"));
		deletePolicyButton.setText(Messages.getString("button.delete"));
	}

	private void refreshListItems() {
		Object[] custs = (Object[]) customerList.getData();
		if (custs != null) {
			String[] items = new String[custs.length];
			for (int i = 0; i < custs.length; i++) {
				items[i] = custs[i].toString();
			}
			int selection = customerList.getSelectionIndex();
			customerList.setItems(items);
			if (selection >= 0 && selection < items.length) {
				customerList.setSelection(selection);
			}
		}
		Object[] pols = (Object[]) policiesList.getData();
		if (pols != null) {
			String[] items = new String[pols.length];
			for (int i = 0; i < pols.length; i++) {
				items[i] = pols[i].toString();
			}
			int selection = policiesList.getSelectionIndex();
			policiesList.setItems(items);
			if (selection >= 0 && selection < items.length) {
				policiesList.setSelection(selection);
			}
		}
	}
	
	public static void main(String[] args) {
		Messages.setLocale(Locale.SIMPLIFIED_CHINESE);
		Display display = new Display();
		shell = new Shell(display);
		GridLayout gridLayout = new GridLayout(1,true);
		shell.setLayout(gridLayout);
		SimpleInsuranceApp instance = new SimpleInsuranceApp(shell);
		setShellSize(display, shell);
		shell.open();
		while (! shell.isDisposed()) {
			if (! display.readAndDispatch()) display.sleep();
		}
		instance.dispose();
	}
	
	public static Shell getShell() {
		return shell;
	}
	
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

	/**
	 * @return
	 */
	private Control createPoliciesControl() {
		Composite policiesPage = new Composite(tabFolder,SWT.NONE);
		policiesPage.setLayout(new GridLayout(2,false));
		
		policiesFindByGroup = new Group(policiesPage,SWT.NONE | SWT.TOP);
		policiesFindByGroup.setLayout (new GridLayout (3, false));
		policiesFindByGroup.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		policiesFindByPolicyIdLabel = new CLabel(policiesFindByGroup,SWT.RIGHT);
		Text policyIdText = new Text(policiesFindByGroup,SWT.BORDER);
		policyIdText.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		Button findByPolicyIdButton = new Button(policiesFindByGroup,SWT.PUSH);
		findByPolicyIdButton.setText(">");
		policiesFindByCustomerIdLabel = new CLabel(policiesFindByGroup,SWT.RIGHT);
		Text idText = new Text(policiesFindByGroup,SWT.BORDER);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		gd.widthHint = 100;
		idText.setLayoutData (gd);
		Button findByCustomerIdButton = new Button(policiesFindByGroup,SWT.PUSH);
		findByCustomerIdButton.setText(">");
		policiesFindByNameLabel = new CLabel(policiesFindByGroup,SWT.RIGHT);
		Text lastNameText = new Text(policiesFindByGroup,SWT.BORDER);
		lastNameText.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		Button findByNameButton = new Button(policiesFindByGroup,SWT.PUSH);
		findByNameButton.setText(">");
		
		policiesResultsGroup = new Group(policiesPage,SWT.NONE);
		policiesResultsGroup.setLayout( new GridLayout(2,false));
		policiesResultsGroup.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		policiesList = new List(policiesResultsGroup,SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		policiesList.setItems(new String[] {"                                                                    ","","","","","","","",""});
		gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		gd.widthHint = 200;
		policiesList.setLayoutData (gd);
		
		Composite buttonsGroup = new Composite(policiesResultsGroup,SWT.TOP);
		buttonsGroup.setLayout(new GridLayout(1,false));
		buttonsGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		editPolicyButton = new Button(buttonsGroup,SWT.PUSH);
		deletePolicyButton = new Button(buttonsGroup,SWT.PUSH);
		
		// wiring...
		findByPolicyIdButton.addSelectionListener(new FindPolicyByIdListener(policyIdText,policiesList));
		findByCustomerIdButton.addSelectionListener(new FindPolicyByCustomerIdListener(idText,policiesList));
		findByNameButton.addSelectionListener(new FindPolicyByCustomerNameListener(lastNameText,policiesList));
		editPolicyButton.addSelectionListener(new EditPolicyListener(policiesList,shell));
		deletePolicyButton.addSelectionListener(new DeletePolicyListener(policiesList,shell));
		return policiesPage;
	}

	/**
	 * @return
	 */
	private Control createCustomersControl() {
		Composite customersPage = new Composite(tabFolder,SWT.NONE);
		customersPage.setLayout(new GridLayout(2,false));
		
		customersFindByGroup = new Group(customersPage,SWT.NONE | SWT.TOP);
		customersFindByGroup.setLayout (new GridLayout (3, false));
		customersFindByGroup.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

		customersFindByIdLabel = new CLabel(customersFindByGroup,SWT.RIGHT);
		Text idText = new Text(customersFindByGroup,SWT.BORDER);
		GridData gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL);
		gd.widthHint = 100;
		idText.setLayoutData (gd);
		Button findByIdButton = new Button(customersFindByGroup,SWT.PUSH);
		findByIdButton.setText(">");
		customersFindByNameLabel = new CLabel(customersFindByGroup,SWT.RIGHT);
		Text lastNameText = new Text(customersFindByGroup,SWT.BORDER);
		lastNameText.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		Button findByNameButton = new Button(customersFindByGroup,SWT.PUSH);
		findByNameButton.setText(">");
		
		customersResultsGroup = new Group(customersPage,SWT.NONE);
		customersResultsGroup.setLayout( new GridLayout(2,false));
		customersResultsGroup.setLayoutData (new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		
		customerList = new List(customersResultsGroup,SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		customerList.setItems(new String[] {"                                                                    ","","","","","","","",""});
		customerList.setData(null);
		gd = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		gd.widthHint = 200;
		customerList.setLayoutData (gd);
		
		Composite buttonsGroup = new Composite(customersResultsGroup,SWT.TOP);
		buttonsGroup.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		buttonsGroup.setLayout(new GridLayout(1,false));
		addCustomerButton = new Button(buttonsGroup,SWT.PUSH);
		editCustomerButton = new Button(buttonsGroup,SWT.PUSH);
		deleteCustomerButton = new Button(buttonsGroup,SWT.PUSH);

		// wiring
		findByIdButton.addSelectionListener(new FindCustomerByIdListener(idText,customerList));
		findByNameButton.addSelectionListener(new FindCustomerByNameListener(lastNameText,customerList));
		addCustomerButton.addSelectionListener(new AddCustomerListener(customersPage.getShell()));
		editCustomerButton.addSelectionListener(new EditCustomerListener(customerList,customersPage.getShell()));
		deleteCustomerButton.addSelectionListener(new DeleteCustomerListener(customerList,customersPage.getShell()));		
		return customersPage;
	}

	/**
	 * Disposes of all resources associated with a particular
	 * instance of the ControlExample.
	 */	
	public void dispose() {
	}
	
	private void loadTestData(SimpleInsurance company) {
		Customer c = company.createCustomer();
		c.setFirstName("Adrian");
		c.setLastName("Colyer");
		c.setDob(new Date());
		c.setAddress(null);
		company.insertCustomer(c);
		Customer c2 = company.createCustomer();
		c2.setFirstName("George");
		c2.setLastName("Harley");
		c2.setDob(new Date());
		c2.setAddress(null);
		company.insertCustomer(c2);
		System.out.println(c);
		System.out.println(c2);
		Policy p = company.createPolicy(c,PolicyType.LIFE);
		p.setStartDate(new Date());
		p.setEndDate(new Date());
		company.insertPolicy(p);
		System.out.println(p);
	}

	public void policyTerminated(Policy p) {
		System.out.println(Messages.getString("log.policyRemoved", p, p.getCustomer()));
		Object[] pols = (Object[]) policiesList.getData();
		if (pols != null) {
			java.util.List newPols = new ArrayList();
			for (int i = 0; i < pols.length; i++) {
				if (!pols[i].equals(p)) {
					newPols.add(pols[i]);
				}
			}
			policiesList.setData(newPols.toArray());
			String[] items = new String[newPols.size()];
			Iterator it = newPols.iterator();
			for (int i = 0; i < items.length; i++) {
				items[i] = ((Policy) it.next()).toString();
			}
			policiesList.setItems(items);
		}		
	}

	public void policyAdded(Policy p) {
		// TODO Auto-generated method stub
		
	}

	public void customerAdded(Customer c) {
		// TODO Auto-generated method stub
		
	}

	public void customerRemoved(Customer c) {
		System.out.println(Messages.getString("log.customerRemoved", c));
		Object[] custs = (Object[]) customerList.getData();
		if (custs != null) {
			java.util.List newCusts = new ArrayList();
			for (int i = 0; i < custs.length; i++) {
				if (!custs[i].equals(c)) {
					newCusts.add(custs[i]);
				}
			}
			customerList.setData(newCusts.toArray());
			String[] items = new String[newCusts.size()];
			Iterator it = newCusts.iterator();
			for (int i = 0; i < items.length; i++) {
				items[i] = ((Customer) it.next()).toString();
			}
			customerList.setItems(items);
		}		
	}
}

class FindCustomerByIdListener extends SelectionAdapter {
	
	private List customerList;
	private Text idText;
	
	public FindCustomerByIdListener(Text idText, List customerList) {
		this.customerList = customerList;
		this.idText = idText;
	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println(Messages.getString("log.findById", idText.getText()));
//		if (!((Button) event.widget).getSelection()) return;
		Set custs = SimpleInsuranceApp.getCompany().findCustomersById(idText.getText());
		if (custs == null) custs = Collections.EMPTY_SET;
		String[] custNames = new String[custs.size()];
		customerList.setData(custs.toArray());
		int index = 0;
		for (Iterator iter = custs.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			custNames[index++] = cust.toString();
			System.out.println(cust.toString());
		}
		customerList.setItems(custNames);
	}	
}

class FindCustomerByNameListener extends SelectionAdapter {
	
	private List customerList;
	private Text nameText;
	
	public FindCustomerByNameListener(Text nameText, List customerList) {
		this.customerList = customerList;
		this.nameText = nameText;
	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println(Messages.getString("log.findByName", nameText.getText()));
//		if (!((Button) event.widget).getSelection()) return;
		Set custs = SimpleInsuranceApp.getCompany().findCustomersByLastName(nameText.getText());
		String[] custNames = new String[custs.size()];
		customerList.setData(custs.toArray());
		int index = 0;
		for (Iterator iter = custs.iterator(); iter.hasNext();) {
			Customer cust = (Customer) iter.next();
			custNames[index++] = cust.toString();
			System.out.println(cust.toString());
		}
		customerList.setItems(custNames);
	}	
}

class FindPolicyByIdListener extends SelectionAdapter {
	
	private List policyList;
	private Text idText;
	
	public FindPolicyByIdListener(Text idText, List policyList) {
		this.policyList = policyList;
		this.idText = idText;
	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println(Messages.getString("log.findByPolicyId", idText.getText()));
//		if (!((Button) event.widget).getSelection()) return;
		Set pols = SimpleInsuranceApp.getCompany().findPoliciesById(idText.getText());
		String[] polNames = new String[pols.size()];
		policyList.setData(pols.toArray());
		int index = 0;
		for (Iterator iter = pols.iterator(); iter.hasNext();) {
			Policy p = (Policy) iter.next();
			polNames[index++] = p.toString();
			System.out.println(p.toString());
		}
		policyList.setItems(polNames);
	}	
}

class FindPolicyByCustomerIdListener extends SelectionAdapter {
	
	private List policyList;
	private Text idText;
	
	public FindPolicyByCustomerIdListener(Text idText, List policyList) {
		this.policyList = policyList;
		this.idText = idText;
	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println(Messages.getString("log.findByCustomerId", idText.getText()));
//		if (!((Button) event.widget).getSelection()) return;
		Set pols = SimpleInsuranceApp.getCompany().findPoliciesByCustomerId(idText.getText());
		String[] polNames = new String[pols.size()];
		policyList.setData(pols.toArray());
		int index = 0;
		for (Iterator iter = pols.iterator(); iter.hasNext();) {
			Policy p = (Policy) iter.next();
			polNames[index++] = p.toString();
			System.out.println(p.toString());
		}
		policyList.setItems(polNames);
	}	
}

class FindPolicyByCustomerNameListener extends SelectionAdapter {
	
	private List policyList;
	private Text nameText;
	
	public FindPolicyByCustomerNameListener(Text nameText, List policyList) {
		this.policyList = policyList;
		this.nameText = nameText;
	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println(Messages.getString("log.findByCustomerName", nameText.getText()));
//		if (!((Button) event.widget).getSelection()) return;
		Set pols = SimpleInsuranceApp.getCompany().findPoliciesByCustomerLastName(nameText.getText());
		String[] polNames = new String[pols.size()];
		policyList.setData(pols.toArray());
		int index = 0;
		for (Iterator iter = pols.iterator(); iter.hasNext();) {
			Policy p = (Policy) iter.next();
			polNames[index++] = p.toString();
			System.out.println(p.toString());
		}
		policyList.setItems(polNames);
	}	
}

class DeleteCustomerListener extends SelectionAdapter {

	private List customerList;
	private Shell shell;
	
	public DeleteCustomerListener(List customerList, Shell shell) {
		this.customerList = customerList;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		String[] ids = customerList.getSelection();
		if (ids.length == 1) {
			boolean confirmed = MessageDialog.openConfirm(shell,
					Messages.getString("confirm.customerDeleteTitle"),
					Messages.getString("confirm.customerDeleteMessage", ids[0]));
			if (confirmed) {
				System.out.println(Messages.getString("log.deleting"));
				Object[] custs = (Object[]) customerList.getData();
				int index = customerList.getSelectionIndices()[0];
				Customer c = (Customer) custs[index];
				SimpleInsuranceApp.getCompany().removeCustomer(c);
				// should be wired into company as a listener to update
				// GUI
			}
		}
	}
}

class AddCustomerListener extends SelectionAdapter {

	private Shell shell;
	
	public AddCustomerListener(Shell shell) {
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		Customer c = SimpleInsuranceApp.getCompany().createCustomer();
		CustomerEditor d = new CustomerEditor(shell,
				SimpleInsuranceApp.getCompany(),c,true);
	}
}

class EditCustomerListener extends SelectionAdapter {

	private List customerList;
	private Shell shell;
	
	public EditCustomerListener(List customerList, Shell shell) {
		this.customerList = customerList;
		this.shell = shell;
	}

	public void widgetSelected(SelectionEvent event) {
		int idx = customerList.getSelectionIndex();
		if (idx == -1) return;
		Object[] custs = (Object[]) customerList.getData();
		Customer c = (Customer) custs[idx];
		CustomerEditor d = new CustomerEditor(shell,
				SimpleInsuranceApp.getCompany(),c,false);
	}
}
