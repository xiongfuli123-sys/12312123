/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

import java.util.Date;

public class Schedule {
    private long numPayments;
    private double paymentAmount;
    private Date firstPaymentDue;
    
    /*
     * Populate in constructor, immutable thereafter
     */
    public Schedule(long numPayments,
					double paymentAmount,
					Date firstPaymentDue) {
    	this.numPayments = numPayments;
    	this.paymentAmount = paymentAmount;
    	this.firstPaymentDue = firstPaymentDue;
    }
    
    /**
     * @return Returns the numPayments.
     */
    public long getNumPayments() {
        return numPayments;
    }

    /**
     * @return Returns the paymentAmount.
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @return Returns the firstPaymentDue.
     */
    public Date getFirstPaymentDue() {
        return firstPaymentDue;
    }
}
