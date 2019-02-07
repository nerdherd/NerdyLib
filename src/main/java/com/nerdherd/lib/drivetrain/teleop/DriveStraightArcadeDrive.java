/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.oi.AbstractOI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightArcadeDrive extends Command {

	private double m_leftPower, m_rightPower, m_rotP, m_deadband;
    private AbstractDrivetrain m_drive;
    private AbstractOI m_oi;
    
    public DriveStraightArcadeDrive(AbstractDrivetrain drive, AbstractOI oi, double rotP, double deadband) {
        m_drive = drive;
        m_oi = oi;
        m_rotP = rotP;
        m_deadband = deadband;
    	requires(m_drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Current Command", "Arcade Drive");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
