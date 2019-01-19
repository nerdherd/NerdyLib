package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset gyro with a command so we do not have to enable/disable every time
 */

public class ResetGyro extends Command {

    private AbstractDrivetrain m_drive;

    public ResetGyro(AbstractDrivetrain drive) {
        m_drive = drive;
	    requires(m_drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ResetGyro");
    m_drive.resetYaw();
    m_drive.resetXY();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
