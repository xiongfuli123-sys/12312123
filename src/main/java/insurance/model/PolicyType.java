/*******************************************************************************
 * Copyright (c) 2004 Colyer, Clement, Harley, Webster and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *******************************************************************************/
package insurance.model;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PolicyType implements Serializable {

    private String type;

    public static final PolicyType AUTO = new PolicyType("auto");
    public static final PolicyType HOUSE = new PolicyType("house");
    public static final PolicyType LIFE = new PolicyType("life");

    private static final Map INSTANCES = new HashMap();

    static {
        INSTANCES.put(AUTO.toString(), AUTO);
        INSTANCES.put(HOUSE.toString(), HOUSE);
        INSTANCES.put(LIFE.toString(), LIFE);
    }

    private PolicyType(String name) {
        this.type = name;
    }

    public String toString() {
        return type;
    }

    Object readResolve() throws ObjectStreamException {
        return getInstance(type);
    }

    public static PolicyType getInstance(String name) {
        return (PolicyType) INSTANCES.get(name);
    }
}