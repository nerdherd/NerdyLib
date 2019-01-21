/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.shifting;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.nerdherd.lib.drivetrain.singlespeed.Drivetrain;
import com.nerdherd.lib.motor.NerdyTalon;
import com.nerdherd.lib.pneumatics.Piston;

/**
 * Add your docs here.
 */
public class ShiftingDrivetrain extends Drivetrain {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Piston m_leftShift, m_rightShift;

  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, NerdyTalon[] leftSlaves, NerdyTalon[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston leftShift, Piston rightShift) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_leftShift = leftShift;
    m_rightShift = rightShift;
  }

  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, VictorSPX[] leftSlaves, VictorSPX[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston leftShift, Piston rightShift) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_leftShift = leftShift;
    m_rightShift = rightShift;
  }

  public void shiftHigh() {
    m_leftShift.setForwards();
    m_rightShift.setForwards();
  }
  
  public void shiftLow() {
    m_leftShift.setReverse();
    m_rightShift.setReverse();
  }

  public void switchShift() {
    m_leftShift.switchPosition();
    m_rightShift.switchPosition();
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
