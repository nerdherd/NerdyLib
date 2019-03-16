/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.dual;

import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.single.AbstractSingleMotor;

/**
 * A dual motor intake, such as a 2018 cube intake
 * takes two SingleMotor subsystems as arguments
 */
public class DualMotorIntake extends AbstractDualMotorIntake {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private AbstractSingleMotor m_leftMotor, m_rightMotor;

  /**
   * 
   * @param left left or top motor on intake
   * @param right right or bottom motor on intake
   */
  public DualMotorIntake(AbstractSingleMotor left, AbstractSingleMotor right) {
    m_leftMotor = left;
    m_rightMotor = right;
  }

  @Override
  public void setPower(double leftPower, double rightPower) {
    m_leftMotor.setPower(leftPower);
    m_rightMotor.setPower(rightPower);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void initLoggingData() {
    m_rightMotor.initLoggingData();
    m_leftMotor.initLoggingData();
  }  
}
