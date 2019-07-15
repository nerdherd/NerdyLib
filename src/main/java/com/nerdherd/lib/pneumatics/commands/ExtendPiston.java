/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.pneumatics.commands;

import com.nerdherd.lib.pneumatics.Piston;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class ExtendPiston extends SendableCommandBase {

  private Piston m_piston;
  
  public ExtendPiston(Piston piston) {
    m_piston = piston;
    addRequirements(m_piston);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_piston.setForwards();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return true;
  }
}
