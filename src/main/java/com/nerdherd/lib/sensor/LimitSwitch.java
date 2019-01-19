/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class LimitSwitch extends Subsystem {

  private final TalonSRX m_limit; 
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public LimitSwitch() {
    m_limit = new TalonSRX(14);
  
  }

  public void reportToSmartDashboard(){
    SmartDashboard.putBoolean("Limit Switch Forward", m_limit.getSensorCollection().isFwdLimitSwitchClosed());
    SmartDashboard.putBoolean("Limit Switch Reverse", m_limit.getSensorCollection().isRevLimitSwitchClosed());
  }

  @Override
  protected void initDefaultCommand() {

  }
}
