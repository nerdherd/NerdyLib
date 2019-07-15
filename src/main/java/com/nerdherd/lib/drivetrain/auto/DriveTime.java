package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive for a specified time
 */

public class DriveTime extends SendableCommandBase {

    private double m_straightPower;
    private double m_timeout;
    private double m_startTime;
    private AbstractDrivetrain m_drive;

    /**
     * @param straightPower
     * @param timeout
     */
    public DriveTime(AbstractDrivetrain drive, double straightPower, double timeout) {
    m_drive = drive;
	m_straightPower = straightPower;
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
	m_drive.setPower(m_straightPower, m_straightPower);
    }
// so, like, Dipsy made some comments on ur code. Hah. Hahaha. Good luck figuring out what we changed in your drive system. ;)
    @Override
    public boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

    @Override
    public void end(boolean interrupted) {
	m_drive.setPowerZero();
    }

    
}
