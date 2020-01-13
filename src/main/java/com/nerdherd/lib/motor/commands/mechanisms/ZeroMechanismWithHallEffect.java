/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.single.mechanisms.StaticFrictionMechanism;
import com.nerdherd.lib.sensor.digital.BooleanSensor;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class ZeroMechanismWithHallEffect extends CommandBase {

  private StaticFrictionMechanism m_mechanism;
  private BooleanSensor m_hallSensor;
  private double m_rate;
  private boolean m_useFF;

  public ZeroMechanismWithHallEffect(StaticFrictionMechanism mechanism, BooleanSensor hallEffectSensor, double descentRate) {
    this(mechanism, hallEffectSensor, descentRate, false);
  }

  public ZeroMechanismWithHallEffect(StaticFrictionMechanism mechanism, 
    BooleanSensor hallEffectSensor, double descentRate, boolean useFF) {
      m_mechanism = mechanism;
      m_hallSensor = hallEffectSensor;
      m_rate = descentRate;
      m_useFF = useFF;
      addRequirements(m_mechanism);

  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (m_useFF) {
      m_mechanism.setVoltageWithFF(m_rate);
    } else {
      m_mechanism.setVoltage(m_rate);
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_hallSensor.getValue();
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_mechanism.resetEncoder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
