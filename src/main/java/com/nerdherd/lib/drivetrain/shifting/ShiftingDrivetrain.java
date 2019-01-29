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
 * Drivetrain with gear shift, assumes that the gear shifter uses one double solenoid
 * Is compatible with all other drivetrain commands
 */
public class ShiftingDrivetrain extends Drivetrain {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Piston  m_shifter;

  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, NerdyTalon[] leftSlaves, NerdyTalon[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston shifter) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_shifter = shifter;
  }

  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, VictorSPX[] leftSlaves, VictorSPX[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston shifter) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_shifter = shifter;
  }

  public void shiftHigh() {
    m_shifter.setForwards();
  }
  
  public void shiftLow() {
    m_shifter.setReverse();
  }

  public void switchShift() {
    m_shifter.switchPosition();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
