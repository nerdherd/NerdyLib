package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset encoders
 */

public class ResetDriveEncoders extends CommandBase {

    private AbstractDrivetrain m_drive;

    public ResetDriveEncoders(AbstractDrivetrain drive) {
        m_drive = drive;
	    addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "ResetDriveEncoders");
    m_drive.resetEncoders();
    // m_drive.resetXY();
    }

    @Override
    public void execute() {
	m_drive.resetEncoders();
    }

    @Override
    public boolean isFinished() {
	return m_drive.getAverageEncoderPosition() == 0;
    }

    @Override
    public void end(boolean interrupted) {
    }

    

}
