/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.auto;

import java.io.File;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class DriveTrajectoryPathfinder extends Command {
  private Trajectory m_leftTrajectory, m_rightTrajectory, m_sourceTrajectory;
  private DistanceFollower m_leftFollower, m_rightFollower;
  private double m_leftOutput, m_rightOutput, m_turn, m_angularError, m_rotP;
  private TankModifier m_modifier;
  private AbstractDrivetrain m_drive;
  double m_LeftVelocityP, m_LeftVelocityD, m_LeftV, m_LeftStatic;
  double m_RightVelocityP, m_RightVelocityD, m_RightV, m_RightStatic;

  public DriveTrajectoryPathfinder(AbstractDrivetrain drive, Trajectory traj, double drivetrainWidth,
            double leftVelP, double rightVelP, double leftVelD, double rightVelD, double leftV, 
            double rightV, double leftStatic, double rightStatic, double rotP) {
    m_LeftVelocityP = leftVelP;
    m_LeftVelocityD = leftVelD;
    m_LeftStatic = leftStatic;
    m_LeftV = leftV;

    m_RightVelocityP = rightVelP;
    m_RightVelocityD = rightVelD;
    m_RightStatic = rightStatic;
    m_RightV = rightV;

    m_rotP = rotP;
    m_drive = drive;
    m_sourceTrajectory = traj;
    m_modifier = new TankModifier(m_sourceTrajectory);
    m_modifier.modify(drivetrainWidth);    
    m_leftTrajectory = m_modifier.getLeftTrajectory();
    m_rightTrajectory = m_modifier.getRightTrajectory();
    requires(m_drive);
  }

  // public DriveTrajectoryPathfinder(AbstractDrivetrain drive, String file) {
  //   m_drive = drive;
  //   File traj = new File("/home/lvuser/paths/" + file + "_source.traj");
  //   SmartDashboard.putBoolean("Source exists", true);
  //   m_sourceTrajectory = Pathfinder.readFromFile(traj);
  //   File leftTraj = new File("/home/lvuser/paths/" + file + "_left.traj");
  //   SmartDashboard.putBoolean("Left exists", true);
  //   m_leftTrajectory = Pathfinder.readFromFile(leftTraj);
  //   File rightTraj = new File("/home/lvuser/paths/" + file + "_right.traj");
  //   SmartDashboard.putBoolean("Right exists", true);
  //   m_rightTrajectory = Pathfinder.readFromFile(rightTraj);
    
  // }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_leftFollower = new DistanceFollower(m_leftTrajectory);
    m_rightFollower = new DistanceFollower(m_rightTrajectory);
    m_leftFollower.configurePIDVA(m_LeftVelocityP, 0, m_LeftVelocityD, m_LeftV, 0);
    m_rightFollower.configurePIDVA(m_RightVelocityP, 0, m_RightVelocityD, m_RightV, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_leftOutput = m_leftFollower.calculate(m_drive.getLeftPositionFeet()) + m_LeftStatic;
    m_rightOutput = m_rightFollower.calculate(m_drive.getRightPositionFeet()) + m_RightStatic;
    m_angularError = Pathfinder.boundHalfDegrees(Pathfinder.r2d(-m_leftFollower.getHeading()) - m_drive.getRawYaw());
    m_turn = m_rotP * m_angularError;
    m_drive.addDesiredVelocities(m_leftFollower.getSegment().velocity, m_rightFollower.getSegment().velocity);
    m_drive.setPower(m_leftOutput - m_turn, m_rightOutput + m_turn);
    SmartDashboard.putNumber("Velocity", m_leftFollower.getSegment().velocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_rightFollower.isFinished() && m_leftFollower.isFinished();
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
  }
}
