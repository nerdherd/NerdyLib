/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.statespace;

import com.nerdherd.lib.motor.statespace.SSMotionProfile;
import com.nerdherd.lib.motor.statespace.SSTalonSRXPos;
import com.nerdherd.robot.Robot;

import Jama.Matrix;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class FollowSSMotionProfile extends Command {

    private SSTalonSRXPos m_motor;
    private SSMotionProfile m_motProf;
    private double m_startTime;

    public FollowSSMotionProfile(SSTalonSRXPos motor, SSMotionProfile motProf) {
        m_motor = motor;
        m_motProf = motProf;
        requires(m_motor);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_motor.update(new Matrix( new double[][] {
            {m_motProf.getPosAtTime(Timer.getFPGATimestamp() - m_startTime)},
            {m_motProf.getVelAtTime(Timer.getFPGATimestamp() - m_startTime)}
        }));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
