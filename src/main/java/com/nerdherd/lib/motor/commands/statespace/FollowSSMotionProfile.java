/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.statespace;

import com.nerdherd.lib.logging.SubscribedLoggable;
import com.nerdherd.lib.motor.statespace.SSMotionProfile;
import com.nerdherd.lib.motor.statespace.SSTalonSRXPos;
import com.nerdherd.robot.Robot;

import Jama.Matrix;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class FollowSSMotionProfile extends CommandBase {

    private SSTalonSRXPos m_motor;
    private SSMotionProfile m_motProf;
    private double m_startTime;
    private SubscribedLoggable m_motProfVel, m_motProfPos;

    public FollowSSMotionProfile(SSTalonSRXPos motor, SSMotionProfile motProf) {
        m_motor = motor;
        m_motProf = motProf;
        addRequirements(m_motor);
        m_motProfPos = null;
        m_motProfVel = null;
    }

    public FollowSSMotionProfile(SSTalonSRXPos motor, SSMotionProfile motProf, 
                                    SubscribedLoggable motProfPos, SubscribedLoggable motProfVel) {
        this(motor, motProf);
        m_motProfPos = motProfPos;
        m_motProfVel = motProfVel;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_motor.update(new Matrix( new double[][] {
            {m_motProf.getPosAtTime(Timer.getFPGATimestamp() - m_startTime)},
            {m_motProf.getVelAtTime(Timer.getFPGATimestamp() - m_startTime)}
        }));
        if (m_motProfPos != null) {
            m_motProfPos.publish(m_motProf.getPosAtTime(Timer.getFPGATimestamp() - m_startTime));
        }
        if (m_motProfVel != null) {
            m_motProfVel.publish(m_motProf.getVelAtTime(Timer.getFPGATimestamp() - m_startTime));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
    return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
}
