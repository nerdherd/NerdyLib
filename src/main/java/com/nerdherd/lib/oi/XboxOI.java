/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.oi.controllers.NerdyXboxController;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * OI for using an Xbox Controller for Operator and Driver
 * 
 */
public class XboxOI extends XboxDriverOI{

    public NerdyXboxController driverController, operatorController;

    public XboxOI() {
        driverController = new NerdyXboxController(0);
        operatorController = new NerdyXboxController(1);
    }

    @Override
    public double getOperatorJoyX() {
        return operatorController.getX(Hand.kRight);
    }

    @Override
    public double getOperatorJoyY() {
        return operatorController.getY(Hand.kRight);
    }

    public double getLeftOperatorY() {
        return operatorController.getY(Hand.kLeft);
    }

    public double getLeftOperatorX() {
        return operatorController.getX(Hand.kLeft);
    }
}
