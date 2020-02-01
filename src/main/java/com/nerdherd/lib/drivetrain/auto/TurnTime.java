package com.nerdherd.lib.drivetrain.auto;


import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn in place for a specified time
 */

public class TurnTime extends CommandBase {

    private double m_rotPower;
    private double m_timeout;
    private double m_startTime;
    private AbstractDrivetrain m_drive;

    /**
     * @param straightPower
     * @param timeout
     */
    public TurnTime(AbstractDrivetrain drive,double rotPower, double timeout) {
	m_rotPower = rotPower;
	m_timeout = timeout;

	addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveTime");
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
	m_drive.setPower(-m_rotPower, m_rotPower);
    }

    @Override
    public boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

    @Override
    public void end(boolean interrupted) {
	m_drive.setPowerZero();
    }

    
}
