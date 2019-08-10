/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;

/**
 * Turn to an absolute angle using motion magic, still untested
 */
public class TurnToAngleMotionMagic extends Command {
  private double m_angle;
  private int m_velocity, m_accel;
  private double m_error;
  private double m_radius;
  private double m_distanceError;
  private double m_leftPos, m_rightPos;
  private AbstractDrivetrain m_drive;

  public TurnToAngleMotionMagic(AbstractDrivetrain drive, double angle, double accel, double velocity, double radius) {
    m_drive = drive;
    m_angle = angle;
    m_accel = (int) accel;
    m_velocity = (int) velocity;
    m_radius = radius;
    requires(m_drive);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_error = m_angle - m_drive.getRawYaw();
    m_error = Pathfinder.boundHalfDegrees(m_error);
    m_distanceError = (Math.toRadians(m_error))* m_radius;
    m_leftPos = m_drive.getLeftMasterPosition() - m_distanceError;
    m_rightPos = m_drive.getRightMasterPosition() + m_distanceError;
    SmartDashboard.putNumber("Rotational Error", m_error );
    SmartDashboard.putNumber("Distance Error", m_distanceError);
    SmartDashboard.putNumber("Left Position", m_leftPos);
    SmartDashboard.putNumber("Right Postion", m_rightPos);
    m_drive.setPositionMotionMagic(m_leftPos, m_rightPos, m_accel, m_velocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_error < 1;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_drive.setPowerZero();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
