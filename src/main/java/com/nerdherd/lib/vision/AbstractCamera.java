/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.nerdherd.lib.logging.Loggable;


/**
 * Add your docs here.
 */
public abstract class AbstractCamera extends SubsystemBase {

    public abstract void reportToSmartDashboard();
    public abstract double getXThetaOffset();
    public abstract double getYThetaOffset();
    public abstract double getDistance();

    public abstract double getContourNum();
    
}
