package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset gyro with a command so we do not have to enable/disable every time
 */

public class ResetGyro extends CommandBase {

    private AbstractDrivetrain m_drive;

    public ResetGyro(AbstractDrivetrain drive) {
        m_drive = drive;
	    addRequirements(m_drive);
    }

    @Override
    public void initialize() {
	SmartDashboard.putString("Current Drive Command", "ResetGyro");
    m_drive.resetYaw();
    m_drive.setXY(0, 0);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
	return false;
    }

    @Override
    public void end(boolean interrupted) {
    }

        

}
