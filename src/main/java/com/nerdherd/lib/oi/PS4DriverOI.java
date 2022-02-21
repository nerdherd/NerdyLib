/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;

/**
 * OI with an PS4 controller for driver and a joystick for operator
 */
public class PS4DriverOI extends AbstractOI {

    public PS4Controller driverController;
    public Joystick operatorJoy;
    private double m_joystickDeadband;
    private boolean m_isLerping;

    public PS4DriverOI() {
        this(0);
    }

    public PS4DriverOI(double deadband) {
        driverController = new PS4Controller(0);
        operatorJoy = new Joystick(1);
        configJoystickDeadband(deadband);
        configLerping(false);
    }

    @Override
    public void initLoggingData() {
        
    }

    @Override
    public double getDriveJoyLeftX() {
        // return driverController.getX(Hand.kLeft);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(driverController.getLeftX(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(driverController.getLeftX(), m_joystickDeadband);
        }
    }

    @Override
    public double getDriveJoyLeftY() {
        // return -driverController.getY(Hand.kLeft);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(-driverController.getLeftY(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(-driverController.getLeftY(), m_joystickDeadband);
        }
    }

    @Override
    public double getDriveJoyRightX() {
        // return driverController.getX(Hand.kRight);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(driverController.getRightX(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(driverController.getRightX(), m_joystickDeadband);
        }
    }

    @Override
    public double getDriveJoyRightY() {
        // return -driverController.getY(Hand.kRight);
        // return -driverController.getY(Hand.kRight);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(-driverController.getRightY(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(-driverController.getRightY(), m_joystickDeadband);
        }
    }

    @Override
    public double getOperatorJoyX() {
        return operatorJoy.getX();
    }

    @Override
    public double getOperatorJoyY() {
        return -operatorJoy.getY();
    } 

    public void configJoystickDeadband(double deadband) {
        m_joystickDeadband = deadband;
    }

    public double getJoystickDeadband() {
        return m_joystickDeadband;
    }

    public void configLerping(boolean isLerping) {
        m_isLerping = isLerping;
    }
}
