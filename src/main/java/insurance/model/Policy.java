/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

import insurance.model.listeners.PolicyListener;

import java.util.Date;
import java.util.Set;

/**
 * Represents an insurance policy sold by the 
 * SimpleInsurance company
 */
public interface Policy {
	public abstract String getPolicyID();

	/**
	 * Date the policy came into effect.
	 * @param startDate The startDate to set.
	 */
	public abstract void setStartDate(Date startDate);

	/**
	 * Date the policy came into effect.
	 * @return Returns the startDate.
	 */
	public abstract Date getStartDate();

	/**
	 * The date that the cover provided by this 
	 * policy expires.
	 * @param endDate The endDate to set.
	 */
	public abstract void setEndDate(Date endDate);

	/**
	 * The date that the cover provided by this 
	 * policy expires.
	 * @return Returns the endDate.
	 */
	public abstract Date getEndDate();

	/**
	 * The current market value of the policy.
	 * @param value The market value to set.
	 */
	public abstract void setValue(double value);

	/**
	 * The current market value of the policy.
	 * @return Returns the market value.
	 */
	public abstract double getValue();

	/**
	 * Is this policy a quotation, or has it been
	 * activated?
	 * @param quote The quote to set.
	 */
	public abstract void setIsQuote(boolean isQuote);

	/**
	 * Is this policy a quotation, or has it been
	 * activated?
	 * @return Returns the quote.
	 */
	public abstract boolean isQuote();

	/**
	 * The schedule of payments due from the customer
	 * for this policy.
	 * @param schedule The schedule to set.
	 */
	public abstract void setSchedule(Schedule schedule);

	/**
	 * The schedule of payments due from the customer
	 * for this policy.
	 * @return Returns the schedule.
	 */
	public abstract Schedule getSchedule();

	/**
	 * The customer that took out this policy, or
	 * on whose behalf the quotation was made. 
	 * @return Returns the customer.
	 */
	public abstract Customer getCustomer();

	/**
	 * The set of claims that have been made against
	 * this insurance policy.
	 * @return Returns the claims.
	 */
	public abstract Set getClaims();

	/**
	 * Add a new claim against this policy.
	 * @param claim
	 */
	public abstract void addClaim(Claim claim);

	/**
	 * Remove a claim from the policy.
	 * @param claim
	 */
	public abstract void removeClaim(Claim claim);

	public abstract void addPolicyListener(PolicyListener l);

	public abstract boolean removePolicyListener(PolicyListener l);

	/**
	 * @return Returns the type.
	 */
	public abstract PolicyType getType();
}