package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drive to an XY point using a rotational PID and an optional straight PID
 */
public class DriveToXY extends CommandBase {

	private double m_desiredX;
	private double m_desiredY;
	private double m_desiredAngle;
	private double m_straightError;
	private double m_rotationalError;
	private double m_straightPower;
	private double m_rotationalPower;
	private double m_currentX;
	private double m_currentY;
	private boolean m_useStraightPID;
	private double m_direction, m_rotP, m_distP;
	private AbstractDrivetrain m_drive;

	
    public DriveToXY(AbstractDrivetrain drive, double x, double y, double straightPower, boolean useStraightPID, double rotP, double straightP) {
		m_drive = drive;
		m_desiredX = x;
    	m_desiredY = y;
    	m_useStraightPID = useStraightPID;
		m_straightPower = straightPower;
		m_rotP = rotP;
		m_distP = straightP;
        addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	m_direction = Math.signum(m_straightPower);
    	m_currentX = m_drive.getXpos();
    	m_currentY = m_drive.getYpos(); 	
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	m_currentX = m_drive.getXpos();
    	m_currentY = m_drive.getYpos();
    	m_desiredAngle = Math.atan2(m_desiredX - m_currentX, m_desiredY - m_currentY);
    	if (m_direction == -1) {
    		m_desiredAngle += 180;
    	}
    	m_rotationalError = -m_desiredAngle - m_drive.getRawYaw();
    	m_rotationalPower = m_rotationalError * m_rotP;
    	if (m_rotationalError >= 180) {
    		m_rotationalError -= 360;
    	}
    	if (m_rotationalError <= -180) {
    		m_rotationalError += 360;
    	}
    	
    	if (m_useStraightPID) {
    		m_straightError = NerdyMath.distanceFormula(m_currentX, m_currentY, m_desiredX, m_desiredY);
        	m_straightPower = m_straightError * m_distP;
    	}
  
    	m_drive.setPower(m_straightPower - m_rotationalPower, m_straightPower + m_rotationalPower);
    	 	
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return NerdyMath.distanceFormula(m_currentX, m_currentY, m_desiredX, m_desiredY) < 1;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    	m_drive.setPowerZero();
    }

      
}
