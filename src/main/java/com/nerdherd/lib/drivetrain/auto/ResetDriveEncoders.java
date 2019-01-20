package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset encoders
 */

public class ResetDriveEncoders extends Command {

    private AbstractDrivetrain m_drive;

    public ResetDriveEncoders(AbstractDrivetrain drive) {
        m_drive = drive;
	    requires(m_drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ResetDriveEncoders");
	m_drive.resetEncoders();
    }

    @Override
    protected void execute() {
	m_drive.resetEncoders();
    }

    @Override
    protected boolean isFinished() {
	return m_drive.getAverageEncoderPosition() == 0;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
