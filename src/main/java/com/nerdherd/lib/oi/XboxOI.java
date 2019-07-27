/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class XboxOI extends AbstractOI {

    public XboxController xbox;
    public Joystick operatorStick;

    @Override
    public void initLoggingData() {
        xbox = new XboxController(0);
        operatorStick = new Joystick(1);
    }

    @Override
    public double getDriveJoyLeftX() {
        return xbox.getX(Hand.kLeft);
    }

    @Override
    public double getDriveJoyLeftY() {
        return xbox.getY(Hand.kLeft);
    }

    @Override
    public double getDriveJoyRightX() {
        return xbox.getX(Hand.kRight);
    }

    @Override
    public double getDriveJoyRightY() {
        return xbox.getY(Hand.kRight);
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
