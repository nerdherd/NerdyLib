/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.commands.ResetSingleMotorEncoder;
import com.nerdherd.lib.motor.single.mechanisms.StaticFrictionMechanism;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class ZeroMechanismsManually extends Command {

    private StaticFrictionMechanism m_mechanism;
    private StaticFrictionMechanism[] m_mechanisms;
    private double m_rate;
    private boolean m_useFF;

    public ZeroMechanismsManually(double descentRate, StaticFrictionMechanism mechanism, 
    StaticFrictionMechanism... additionalMechanisms) {
        this(descentRate, false, mechanism, additionalMechanisms);
    }

    public ZeroMechanismsManually(double descentRate, boolean useFF, 
        StaticFrictionMechanism mechanism, StaticFrictionMechanism... additionalMechanisms) {
        m_mechanism = mechanism;
        m_mechanisms = additionalMechanisms;
        m_rate = descentRate;
        m_useFF = useFF;
        requires(m_mechanism);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_useFF) {
        m_mechanism.setVoltageWithFF(m_rate);
        } else {
        m_mechanism.setVoltage(m_rate);
        }
        for (StaticFrictionMechanism additionalMechanism : m_mechanisms) {
            if (m_useFF) {
                additionalMechanism.setVoltageWithFF(m_rate);
            } else {
                additionalMechanism.setVoltage(m_rate);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_mechanism.setVoltage(0);
        Scheduler.getInstance().add(new ResetSingleMotorEncoder(m_mechanism));
        for (StaticFrictionMechanism additionalMechanism : m_mechanisms) {
            additionalMechanism.setVoltage(0);
            Scheduler.getInstance().add(new ResetSingleMotorEncoder(additionalMechanism));
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
