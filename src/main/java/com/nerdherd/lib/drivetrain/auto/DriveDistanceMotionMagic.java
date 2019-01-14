package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceMotionMagic extends Command {

	private double m_distance;
    private double m_error;
    private int m_accel, m_velocity;
    private AbstractDrivetrain m_drive;

    public DriveDistanceMotionMagic(AbstractDrivetrain drive, double distance, double accel, double velocity) {
        m_drive = drive;
        m_distance = distance;
        accel = (int) accel;
        velocity = (int) velocity;
       requires(m_drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_error = m_distance - m_drive.getAverageEncoderPosition();
    	m_drive.setPositionMotionMagic(m_distance, m_distance, m_accel, m_velocity);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // return NerdyMath.errorTolerance(m_error, m_DriveTolerance);
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_drive.setPowerZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
