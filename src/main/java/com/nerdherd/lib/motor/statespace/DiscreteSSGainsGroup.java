/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.statespace;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Add your docs here.
 */
public class DiscreteSSGainsGroup {

    private ArrayList<DiscreteSSGains> gainsList;

    public DiscreteSSGainsGroup(DiscreteSSGains... gains) {
        if (gains != null) {
            this.gainsList = new ArrayList<DiscreteSSGains>(Arrays.asList(gains));
        } else {
            this.gainsList = new ArrayList<DiscreteSSGains>();
        }
    }

    public void addGains(DiscreteSSGains... gains) {
        this.gainsList.addAll(Arrays.asList(gains));
    }

    public DiscreteSSGains getGains(int i) {
        return this.gainsList.get(i);
    }


}
