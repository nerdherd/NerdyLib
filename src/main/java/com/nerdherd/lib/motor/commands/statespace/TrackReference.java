/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.statespace;

import com.nerdherd.lib.motor.statespace.SSTalonSRXPos;

import Jama.Matrix;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TrackReference extends CommandBase {

    private SSTalonSRXPos m_motor;
    private Matrix m_reference;

    public TrackReference(SSTalonSRXPos motor, Matrix reference) {
        m_motor = motor;
        addRequirements(m_motor);
        m_reference = reference;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_motor.update(m_reference);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_motor.update();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
    return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        // m_motor.u = new Matrix(0, 0);
    }

    // Called when another command which addRequirements one or more of the same
    // subsystems is scheduled to run
        
}
