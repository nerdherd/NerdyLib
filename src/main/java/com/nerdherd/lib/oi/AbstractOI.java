/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.logging.Loggable;

/**
 * Add your docs here.
 */
public abstract class AbstractOI implements Loggable {

    public abstract double getDriveJoyLeftX();
    public abstract double getDriveJoyLeftY();

    public abstract double getDriveJoyRightX();
    public abstract double getDriveJoyRightY();

    public abstract double getOperatorJoyX();
    public abstract double getOperatorJoyY();
    
}
