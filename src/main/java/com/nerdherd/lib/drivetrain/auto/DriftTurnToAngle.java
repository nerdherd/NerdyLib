package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An arc turn that drift turns to an angle near the end of a specified
 * distance. Similar to the logic behind 973's hopper auto controller. Loop is
 * closed on heading but not on straight power.
 */

public class DriftTurnToAngle extends SendableCommandBase {

    private double m_straightPower;
    private double m_desiredAngle;
    private double m_distance;
    private double m_kRotP;
    private AbstractDrivetrain m_drive;
    /**
     * @param straightPower
     * @param angle
     * @param distance
     *            (absolute value)
     * @param kRotP
     */
    public DriftTurnToAngle(AbstractDrivetrain drive,double straightPower, double angle, double distance, double kRotP) {
	m_straightPower = straightPower;
	m_desiredAngle = angle;
	m_distance = distance;
	m_kRotP = kRotP;
    m_drive = drive;
	addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriftTurnToAngle");
    }

    @Override
    public void execute() {
	double doneness = Math.abs(m_drive.getAverageEncoderPosition() / m_distance);
	doneness = Math.sqrt(doneness); // this makes the arc turn more gradual
	double yaw = m_drive.getRawYaw();
	if (m_straightPower < 0) {
	    yaw += 180;
	}
	double robotAngle = (360 - yaw) % 360;
	double rotError = -m_desiredAngle - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_kRotP * rotError * doneness;

	m_drive.setPower((m_straightPower) - rotPower, m_straightPower + rotPower);
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
