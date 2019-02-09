/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.nerdherd.lib.misc.Loggable;
import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class TalonTach extends BooleanSensor implements Loggable {

  private final TalonSRX m_limit;

  public TalonTach(int talonID, String sensorName, boolean isInverted) {
    super(sensorName, isInverted);
    m_limit = new TalonSRX(talonID);
  }

  public TalonTach(SingleMotorTalonSRX talonSubsystem, String sensorName, boolean isInverted) {
    super(sensorName, isInverted);
    m_limit = talonSubsystem.motor;
  }

  public TalonTach(TalonSRX talon, String sensorName, boolean isInverted) {
    super(sensorName, isInverted);
    m_limit = talon;
  }

  @Override
  public boolean get() {
    return m_limit.getSensorCollection().isFwdLimitSwitchClosed();
  }
}
