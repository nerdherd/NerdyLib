/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.shifting;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.nerdherd.lib.drivetrain.singlespeed.Drivetrain;
import com.nerdherd.lib.motor.motorcontrollers.NerdyTalon;
import com.nerdherd.lib.pneumatics.Piston;

/**
 * Drivetrain with gear shift, assumes that the gear shifter uses one double solenoid
 * Is compatible with all other drivetrain commands
 */
public class ShiftingDrivetrain extends Drivetrain {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Piston  m_shifter;

  /**
	 * Standard Tank Drive with a gear shifter
	 * 
	 * @param leftTalonMasterID  ID for left master talon
	 * @param rightTalonMasterID ID for right master talon
	 * @param leftSlaves         array of NerdyTalons to follow left master
	 * @param rightSlaves        array of NerdyTalons to follow right master
	 * @param leftInversion      inversion of left side of drivetrain
	 * @param rightInversion     inversion of right side of drivetrain
   * @param shifter             piston for the gear shifter
	 */
  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, NerdyTalon[] leftSlaves, NerdyTalon[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston shifter) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_shifter = shifter;
  }

  /**
	 * Standard Tank Drive with a gear shifter
	 * 
	 * @param leftTalonMasterID  ID for left master talon
	 * @param rightTalonMasterID ID for right master talon
	 * @param leftSlaves         array of VictorSPXs to follow left master
	 * @param rightSlaves        array of VictorSPXs to follow right master
	 * @param leftInversion      inversion of left side of drivetrain
	 * @param rightInversion     inversion of right side of drivetrain
   * @param shifter             piston for the gear shifter
	 */
  public ShiftingDrivetrain(int leftTalonMasterID, int rightTalonMasterID, VictorSPX[] leftSlaves, VictorSPX[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston shifter) {
    super(leftTalonMasterID, rightTalonMasterID, leftSlaves, rightSlaves, leftInversion, rightInversion);
    m_shifter = shifter;
  }

  /**
   * Shift into high gear
   */
  public void shiftHigh() {
    m_shifter.setForwards();
  }
  
  /**
   * Shift into low gear
   */
  public void shiftLow() {
    m_shifter.setReverse();
  }

  /**
   * Shift from high gear to low gear or vice versa
   */
  public void switchShift() {
    m_shifter.switchPosition();
  }

}
