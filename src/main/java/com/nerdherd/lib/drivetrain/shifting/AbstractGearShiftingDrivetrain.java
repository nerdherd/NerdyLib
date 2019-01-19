/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.shifting;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

/**
 * Add your docs here.
 */
public abstract class AbstractGearShiftingDrivetrain extends AbstractDrivetrain{

    public abstract void shiftHigh();

    public abstract void shiftLow();

    public abstract void switchShift();

    public abstract boolean isHighGear();
}

