/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model.impl;

import insurance.model.Claim;
import insurance.model.listeners.ClaimListener;

import java.util.HashSet;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ClaimImpl implements Claim {
    
    private static int uid_counter = 1;
    private Date created;
    private double amount;
    private String status;
    private String comments;
    private String claimId;
    private transient Set listeners = new HashSet();
    
    public ClaimImpl(Date created, double amount, String status, String comments) {
        this.created = created;
        this.amount = amount;
        this.status = status;
        this.comments = comments;
        createClaimID();
    }
    
    public String getClaimId() {
    	return claimId;
    }
    
    public String toString() {
    	return getClaimId() + getStatus();
    }
    
    /**
     * @param created The created to set.
     */
    public void setCreated(Date created) {
        this.created = created;
        notifyListeners();
    }
    
    /**
     * @return Returns the created.
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
        notifyListeners();
    }

    /**
     * @return Returns the amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
        notifyListeners();
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
        notifyListeners();
    }

    /**
     * @return Returns the comments.
     */
    public String getComments() {
        return comments;
    }

    private void createClaimID() {
    	StringBuffer id = new StringBuffer("C");
    	id.append(System.currentTimeMillis());
    	id.append(".");
    	id.append(uid_counter++);
    	this.claimId = id.toString();
    }
    /**
     * @param l
     */
    public void addClaimListener(ClaimListener l) {
    	if (listeners == null) listeners = new HashSet();
        this.listeners.add(l);
    }
    
    /**
     * @param l
     * @return
     */
    public boolean removeClaimListener(ClaimListener l) {
        return this.listeners.remove(l);
    }
    
    /**
     * 
     */
    protected void notifyListeners() {
    	if (listeners == null) return;
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            ClaimListener l = (ClaimListener) iter.next();
            l.claimUpdated(this);
        }
    }
}
