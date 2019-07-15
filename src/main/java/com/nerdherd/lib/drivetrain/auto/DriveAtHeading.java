package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive at a specified heading (turns to an angle near the beginning of a
 * specified distance). Loop is closed on heading but not on straight power.
 */

public class DriveAtHeading extends SendableCommandBase {

    private double m_straightPower;
    private double m_heading, m_distance;
    private double m_kRotP;
    private AbstractDrivetrain m_drive;

    /**
     * @param straightPower
     *            (determines direction and magnitude)
     * @param heading
     * @param distance
     *            (absolute value)
     * @param kRotP
     */
    public DriveAtHeading(double straightPower, double heading, double distance, double kRotP) {
	m_straightPower = straightPower;
	m_heading = heading;
	m_distance = distance;
	m_kRotP = kRotP;

	addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveAtHeading");
    }

    @Override
    public void execute() {
	double yaw = m_drive.getRawYaw();
	if (m_straightPower < 0) {
	    yaw += 180;
	}
	double robotAngle = (360 - yaw) % 360;
	double rotError = -m_heading - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_kRotP * rotError;

	m_drive.setPower(m_straightPower - rotPower, m_straightPower + rotPower);
    }

    @Override
    public boolean isFinished() {
	return Math.abs(m_drive.getAverageEncoderPosition()) >= m_distance;
    }

    @Override
    public void end(boolean interrupted) {
	m_drive.setPowerZero();
    }

}