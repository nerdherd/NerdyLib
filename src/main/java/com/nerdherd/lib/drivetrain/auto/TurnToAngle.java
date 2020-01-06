package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute)
 * @author tedklin
 * modified by dylan barva
 */

public class TurnToAngle extends CommandBase {

    private double m_desiredAngle;
    private double m_startTime, m_timeout;
    private double m_error;
    private double m_prevTimestamp;
    private double m_prevError;
    private double m_dTerm, m_rotP, m_rotD;
    private AbstractDrivetrain m_drive;

    private int m_counter;
    private int m_tolerance;

    /**
     * @param angle
     * @param timeout
     * @param tolerance
     */
    public TurnToAngle(AbstractDrivetrain drive, double angle, int tolerance, double timeout, double rotP, double rotD) {
        m_drive = drive;
        m_desiredAngle = angle;
        m_tolerance = tolerance;
        m_rotP = rotP;
        m_rotD = rotD;
        m_timeout = timeout;
	    addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "TurnToAngle");
	m_startTime = Timer.getFPGATimestamp();
	m_counter = 0;
    }

    @Override
    public void execute() {
        m_error = m_desiredAngle - m_drive.getRawYaw();
        m_dTerm = (m_prevError - m_error) / (m_prevTimestamp - Timer.getFPGATimestamp());
        m_prevTimestamp = Timer.getFPGATimestamp();
        double power = m_rotP * m_error + m_rotD * m_dTerm;
        m_drive.setPowerFeedforward(-power, power);
        if (Math.abs(m_error) <= 2) {
            m_counter += 1;
        } else {
            m_counter = 0;
        }
        m_prevError = m_error;
    }

    @Override
    public boolean isFinished() {
	return m_counter > m_tolerance || Timer.getFPGATimestamp() - m_startTime > m_timeout;
	// return false;
    }

    @Override
    public void end(boolean interrupted) {
	m_drive.setPowerZero();
    }

    @Override
    protected void interrupted() {
	end();
    }

}
