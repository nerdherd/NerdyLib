package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.oi.AbstractOI;
import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Teleop Swerve Drive, open loop
 */
public class SwerveDrive extends CommandBase {

    private double m_frontLeftPower, m_frontRightPower, m_backLeftPower, m_backRightPower;
    private double m_frontLeftAngle, m_frontRightAngle, m_backLeftAngle, m_backRightAngle;
    
    private AbstractDrivetrain m_drive;
    private AbstractOI m_oi;
    private double m_mult;

    private double robotWidth = 27 * 0.0254;
    private double robotLength = 32 * 0.0254;
    
    public SwerveDrive(AbstractDrivetrain drive, AbstractOI oi) {
        m_drive = drive;
        m_oi = oi;
        addRequirements(m_drive);
        m_mult = 1;
    }

    public SwerveDrive(AbstractDrivetrain drive, AbstractOI oi, double mult) {
        m_drive = drive;
        m_oi = oi;
        addRequirements(m_drive);
        m_mult = mult;
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	SmartDashboard.putString("Current Command", "Swerve Drive");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        
        double r = Math.sqrt(robotWidth + robotLength);

        double a = m_oi.getDriveJoyLeftX() - m_oi.getDriveJoyRightX() * (robotLength / r);
        double b = m_oi.getDriveJoyLeftX() + m_oi.getDriveJoyRightX() * (robotLength / r);
        double c = -m_oi.getDriveJoyLeftY() - m_oi.getDriveJoyRightX() * (robotWidth / r);
        double d = -m_oi.getDriveJoyLeftY() + m_oi.getDriveJoyRightX() * (robotWidth / r);

        m_frontLeftPower = Math.sqrt((b * b) + (c * c));
        m_frontRightPower = Math.sqrt((b * b) + (d * d));
        m_backLeftPower = Math.sqrt((a * a) + (c * c));
        m_backRightPower = Math.sqrt((a * a) + (d * d));

        m_drive.setSwervePower(m_frontLeftPower, m_frontRightPower, m_backLeftPower, m_backRightPower);
        
        m_frontLeftAngle = Math.atan2(b, c) / Math.PI;
        m_frontRightAngle = Math.atan2(b, d) / Math.PI;
        m_backLeftAngle = Math.atan2(a, c) / Math.PI;
        m_backRightAngle = Math.atan2(a, d) / Math.PI;

        m_drive.setSwerveAnglePower(m_frontLeftAngle, m_frontRightAngle, m_backLeftAngle, m_backRightAngle);

    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
