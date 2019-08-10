package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive straight without setting power to 0 when it reaches goal. No heading
 * adjustment, all open loop. Use with extreme caution and a finger on the disable key
 */

public class DriveStraightContinuous extends Command {

    private double m_distance;
    private double m_straightPower;
    private AbstractDrivetrain m_drive;

    /**
     * @param distance
     * @param straightPower
     */
    public DriveStraightContinuous(AbstractDrivetrain drive, double distance, double straightPower) {
        m_drive = drive;
	m_distance = distance;
	m_straightPower = straightPower;

	requires(m_drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveStraightContinuous");
    }

    @Override
    protected void execute() {
	m_drive.setPower(m_straightPower, m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return m_drive.getAverageEncoderPosition() > m_distance;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
	end();
    }

}
