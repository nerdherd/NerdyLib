/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;


/**
 * Add your docs here.
 */
public class TalonTach extends BooleanSensor {

  private final TalonSRX m_limit;
  private boolean m_isForward;

  public TalonTach(int talonID, String sensorName, boolean isInverted, boolean isForward) {
    super(sensorName, isInverted);
    m_limit = new TalonSRX(talonID);
    m_isForward = isForward;
  }

  public TalonTach(SingleMotorTalonSRX talonSubsystem, String sensorName, 
                    boolean isInverted, boolean isForward) {
    super(sensorName, isInverted);
    m_limit = talonSubsystem.motor;
    m_isForward = isForward;
  }

  public TalonTach(TalonSRX talon, String sensorName, boolean isInverted, boolean isForward) {
    super(sensorName, isInverted);
    m_limit = talon;
    m_isForward = isForward;
  }

  @Override
  public boolean get() {
    if (m_isForward) {
      return m_limit.getSensorCollection().isFwdLimitSwitchClosed();
    } else {
      return m_limit.getSensorCollection().isRevLimitSwitchClosed();
    }
  }
}
