package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drive forward for a distance using a PID on position
 */
public class DriveDistancePID extends CommandBase {

	private double m_distance;
	private double m_error;
	private double m_power, m_rotP;
    private AbstractDrivetrain m_drive;

    public DriveDistancePID(AbstractDrivetrain drive, double distance, double rotP) {
        m_drive = drive;
        m_distance = distance;
        m_rotP = rotP;
        addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	m_error = m_distance - m_drive.getAverageEncoderPosition();
    	m_power = m_error * m_rotP;    	
    	m_drive.setPower(m_power, m_power);   	
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        // return NerdyMath.errorTolerance(m_error, m_DriveTolerance);
        return false;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    	m_drive.setPowerZero();
    }
}
