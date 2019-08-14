/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.oi.controllers.NerdyXboxController;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

/**
 * OI with an xbox controller for driver and a joystick for operator
 */
public class XboxDriverOI extends AbstractOI {

    public NerdyXboxController driverController;
    public Joystick operatorStick;

    public XboxDriverOI() {
        driverController = new NerdyXboxController(0);
        operatorStick = new Joystick(1);
    }

    @Override
    public void initLoggingData() {
        
    }

    @Override
    public double getDriveJoyLeftX() {
        return driverController.getX(Hand.kLeft);
    }

    @Override
    public double getDriveJoyLeftY() {
        return driverController.getY(Hand.kLeft);
    }

    @Override
    public double getDriveJoyRightX() {
        return driverController.getX(Hand.kRight);
    }

    @Override
    public double getDriveJoyRightY() {
        return driverController.getY(Hand.kRight);
    }

    @Override
    public double getOperatorJoyX() {
        return operatorStick.getX();
    }

    @Override
    public double getOperatorJoyY() {
        return -operatorStick.getY();
    } 
}
