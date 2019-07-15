/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.dual;

import com.nerdherd.lib.logging.Loggable;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;

/**
 * Add your docs here.
 */
public abstract class AbstractDualMotorIntake extends SendableSubsystemBase implements Loggable {

    public abstract void setPower(double leftPower, double rightPower);
}
