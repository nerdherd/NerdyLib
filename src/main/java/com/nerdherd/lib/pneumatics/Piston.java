/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class Piston extends SubsystemBase {
 
  private DoubleSolenoid m_piston;

  public Piston(int port1, int port2) {
    m_piston = new DoubleSolenoid(port1, port2);
  }

  public void setForwards() {
    m_piston.set(Value.kForward);
  }

  public void setReverse() {
    m_piston.set(Value.kReverse);
  }

  public void setOff() {
    m_piston.set(Value.kOff);
  }

  public void switchPosition() {
    if (getValue() == Value.kForward) {
      m_piston.set(Value.kReverse);
    }
    else if (getValue() == Value.kReverse) {
      m_piston.set(Value.kForward);
    }
  }

  public DoubleSolenoid.Value getValue() {
    return m_piston.get();
  }

  public boolean isForwards() {
    return Value.kForward == getValue();
  }

  public boolean isReverse() {
    return Value.kReverse == getValue();
  }

  @Override
  public void initDefaultCommand() {
  }
}
