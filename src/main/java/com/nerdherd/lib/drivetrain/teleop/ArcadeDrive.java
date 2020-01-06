package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.oi.AbstractOI;
import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Teleop Arcade Drive, open loop
 */
public class ArcadeDrive extends CommandBase {

	private double m_leftPower, m_rightPower;
    private AbstractDrivetrain m_drive;
    private AbstractOI m_oi;
    
    public ArcadeDrive(AbstractDrivetrain drive, AbstractOI oi) {
        m_drive = drive;
        m_oi = oi;
    	addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	SmartDashboard.putString("Current Command", "Arcade Drive");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	m_leftPower = NerdyMath.squareInput(m_oi.getDriveJoyLeftX()) + NerdyMath.squareInput(m_oi.getDriveJoyRightY());
    	m_rightPower = -NerdyMath.squareInput(m_oi.getDriveJoyLeftX()) +  NerdyMath.squareInput(m_oi.getDriveJoyRightY());
    	m_drive.setPower(m_leftPower, m_rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
