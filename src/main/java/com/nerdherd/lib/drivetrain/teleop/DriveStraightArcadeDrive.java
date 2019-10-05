/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.oi.AbstractOI;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightArcadeDrive extends CommandBase {

	private double m_leftPower, m_rightPower, m_rotP, m_deadband;
    private AbstractDrivetrain m_drive;
    private AbstractOI m_oi;
    
    public DriveStraightArcadeDrive(AbstractDrivetrain drive, AbstractOI oi, double rotP, double deadband) {
        m_drive = drive;
        m_oi = oi;
        m_rotP = rotP;
        m_deadband = deadband;
    	addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	SmartDashboard.putString("Current Command", "Arcade Drive");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
      if (Math.abs(m_oi.getDriveJoyLeftX()) <= m_deadband) {
        m_leftPower = -m_rotP * m_drive.getAngularVelocity() + m_oi.getDriveJoyRightY();
        m_rightPower = m_rotP * m_drive.getAngularVelocity() + m_oi.getDriveJoyRightY();
      } else {
        m_leftPower = m_oi.getDriveJoyLeftX() + m_oi.getDriveJoyRightY();
        m_rightPower = -m_oi.getDriveJoyLeftX() + m_oi.getDriveJoyRightY();
      }
    	m_drive.setPower(m_leftPower, m_rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    }

    // Called when another command which addRequirements one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
