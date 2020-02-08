/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.experimental;

import com.nerdherd.lib.motor.motorcontrollers.CANMotorController;
import com.nerdherd.lib.motor.motorcontrollers.SmartCANMotorController;
import com.nerdherd.lib.pneumatics.Piston;

/**
 * Add your docs here.
 */
public class ShiftingDrivetrain extends Drivetrain implements TwoSpeedDrivetrain{

    protected Piston m_shifter;
    
    public ShiftingDrivetrain(SmartCANMotorController leftMaster, SmartCANMotorController rightMaster, CANMotorController[] leftSlaves, CANMotorController[] rightSlaves, boolean leftInversion, boolean rightInversion, Piston shifter, double trackwidth) {
        super(leftMaster, rightMaster, leftSlaves, rightSlaves, leftInversion, rightInversion, trackwidth);
        m_shifter = shifter;
    }

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

      public void autoShift() {
        if (super.getLeftMasterCurrent() > 50 && super.getLeftMasterCurrent() > 50) {
          shiftHigh(); 
        }
        else {
          shiftLow();
        }
    }
}
