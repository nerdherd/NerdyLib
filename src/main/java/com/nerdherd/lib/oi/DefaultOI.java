/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;


import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class DefaultOI extends AbstractOI{

    
    public Joystick driveJoyLeft;
    public Joystick driveJoyRight;
    public Joystick operatorJoy;
    private double m_joystickDeadband;
    private boolean m_isLerping;

    public DefaultOI() {
        this(0);
    }

    public DefaultOI(double deadband) {
        driveJoyLeft = new Joystick(0);
        driveJoyRight = new Joystick(1);
        operatorJoy = new Joystick(2);
        this.configJoystickDeadband(deadband);
        this.configLerping(false);
    }

    
    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyLeftY() {
        // return -gamepadJoy.getRawAxis(1);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(-driveJoyLeft.getY(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(-driveJoyLeft.getY(), m_joystickDeadband);
        }
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyRightY() {
        // return -gamepadJoy.getRawAxis(3);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(-driveJoyRight.getY(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(-driveJoyRight.getY(), m_joystickDeadband);
        }
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyLeftX() {
        // return gamepadJoy.getRawAxis(0);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(driveJoyLeft.getX(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(driveJoyLeft.getX(), m_joystickDeadband);
        }
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyRightX() {
        // return gamepadJoy.getRawAxis(2);
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(driveJoyRight.getX(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(driveJoyRight.getX(), m_joystickDeadband);
        }
    }

    @Override
    public double getOperatorJoyX() {
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(operatorJoy.getX(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(operatorJoy.getX(), m_joystickDeadband);
        }
    }

    @Override
    public double getOperatorJoyY() {
        if (m_isLerping) {
            return NerdyMath.lerpJoystickDeadband(-operatorJoy.getY(), m_joystickDeadband);
        } else {
            return NerdyMath.handleDeadband(-operatorJoy.getY(), m_joystickDeadband);
        }
    }

    // TODO: implement OI logging again
    @Override
    public void initLoggingData() {
        // for (int i = 1; i < 12; i++) {
        //     int current_number = i;

        // }
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
