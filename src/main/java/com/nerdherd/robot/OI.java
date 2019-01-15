/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.motor.commands.SetMotorPositionPID;
import com.nerdherd.lib.motor.commands.SetMotorPower;
import com.nerdherd.lib.oi.DefaultOI;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends DefaultOI{
  
  
  JoystickButton trigger_1;
  
  public OI() {
    super();
    trigger_1 = new JoystickButton(super.operatorJoy, 1);
    SmartDashboard.putData("Set Climber Wheel Position", new SetMotorPositionPID(Robot.climberWheelLeft, 1024));
    SmartDashboard.putData("Retract Climber Wheel", new SetMotorPower(Robot.climberWheelLeft, -0.1));
  }
}
