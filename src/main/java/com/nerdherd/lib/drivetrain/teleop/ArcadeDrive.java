package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.oi.AbstractOI;
import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Teleop Arcade Drive, open loop
 */
public class ArcadeDrive extends Command {

	private double m_leftPower, m_rightPower;
    private AbstractDrivetrain m_drive;
    private AbstractOI m_oi;
    
    public ArcadeDrive(AbstractDrivetrain drive, AbstractOI oi) {
        m_drive = drive;
        m_oi = oi;
    	requires(m_drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Current Command", "Arcade Drive");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_leftPower = m_oi.getDriveJoyRightX() + m_oi.getDriveJoyLeftY();
    	m_rightPower = -m_oi.getDriveJoyRightX() + m_oi.getDriveJoyLeftY();
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
