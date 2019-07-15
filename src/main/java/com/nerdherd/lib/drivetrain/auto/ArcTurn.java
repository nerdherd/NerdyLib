package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arc turning
 */

public class ArcTurn extends SendableCommandBase {

    private double m_desiredAngle;
    private boolean m_isRightPowered;

    private double m_startTime, m_timeout;
    private double m_error;

    private double m_sign, m_rotP, m_rotMinPower, m_rotMaxPower;
    private AbstractDrivetrain m_drive;

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param timeout
     * @param sign
     *            (+1.0 or -1.0)
     */
    public ArcTurn(AbstractDrivetrain drive,double desiredAngle, boolean isRightPowered, double timeout, double sign, double rotP, double maxPower, double minPower) {
    m_drive = drive;
    m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
    m_timeout = timeout;
    m_rotP = rotP;
    m_rotMaxPower = maxPower;
    m_rotMinPower = minPower;
	m_sign = Math.signum(sign);

	addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "ArcTurn");
	
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
	double robotAngle = (360 - m_drive.getRawYaw()) % 360;
	m_error = -m_desiredAngle - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	double rotPower = m_rotP * m_error * 1.3; // multiplied by 2 because the rotational component is
							      // only added to one side of the drivetrain

	double rawSign = Math.signum(rotPower);
	rotPower = NerdyMath.threshold(Math.abs(rotPower), m_rotMinPower, m_rotMaxPower)
		* rawSign;
	rotPower = Math.abs(rotPower) * m_sign;

	if (m_isRightPowered) {
	    m_drive.setPower(0, rotPower);
	} else if (!m_isRightPowered) {
	    m_drive.setPower(-rotPower, 0);
	}
    }

    @Override
    public boolean isFinished() {
	return Math.abs(m_error) < 3
		|| Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

    @Override
    public void end(boolean interrupted) {
	m_drive.setPowerZero();
    }

}