/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public class SIException extends RuntimeException {

	/**
	 * @param arg0
	 */
	public SIException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public SIException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SIException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
