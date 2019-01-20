package com.nerdherd.lib.drivetrain.auto;


import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn in place for a specified time
 */

public class TurnTime extends Command {

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

	requires(m_drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveTime");
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	m_drive.setPower(-m_rotPower, m_rotPower);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

    @Override
    protected void end() {
	m_drive.setPowerZero();
    }

    @Override
    protected void interrupted() {
	end();
    }
}
