/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

public class Automobile {

    private String make;
    private String model;
    private String colour;
    private String plate;
    private double marketValue;
    private int insuranceGroup;
    
    /**
     * Populate in constructor, immutable thereafter
     */
    public Automobile(String make,
					  String model,
					  String colour,
					  String plate,
					  double marketValue,
					  int insuranceGroup) {
    	this.make = make;
    	this.model = model;
    	this.colour = colour;
    	this.plate = plate;
    	this.marketValue = marketValue;
    	this.insuranceGroup = insuranceGroup;
    }
    
    /**
     * @return Returns the make.
     */
    public String getMake() {
        return make;
    }
    
    /**
     * @return Returns the model.
     */
    public String getModel() {
        return model;
    }

    /**
     * @return Returns the colour.
     */
    public String getColour() {
        return colour;
    }

    /**
     * @return Returns the plate.
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @return Returns the market value.
     */
    public double getMarketValue() {
        return marketValue;
    }

    /**
     * @return Returns the insurance group.
     */
    public int getInsuranceGroup() {
        return insuranceGroup;
    }
}
