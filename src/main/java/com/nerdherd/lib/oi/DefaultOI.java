/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.misc.NerdyMath;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class DefaultOI extends AbstractOI{

    
    public Joystick driveJoyLeft;
    public Joystick driveJoyRight;
    public Joystick operatorJoy;

    public DefaultOI() {
        driveJoyLeft = new Joystick(0);
        driveJoyRight = new Joystick(1);
        operatorJoy = new Joystick(2);

    }

    
    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyLeftY() {
        // return -gamepadJoy.getRawAxis(1);
        return -driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyRightY() {
        // return -gamepadJoy.getRawAxis(3);
        return -driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyLeftX() {
        // return gamepadJoy.getRawAxis(0);
        return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    @Override
    public double getDriveJoyRightX() {
        // return gamepadJoy.getRawAxis(2);
        return driveJoyRight.getX();
    }

    @Override
    public double getOperatorJoyX() {
        return operatorJoy.getX();
    }

    @Override
    public double getOperatorJoyY() {
        return -operatorJoy.getY();
    }

    // TODO: do something about the whole OI logging slowing things down and other issues thing
    @Override
    public void initLoggingData() {
        // for (int i = 1; i < 12; i++) {
        //     int current_number = i;
        //     BadLog.createTopic("OI/Left Joystick/Button " + i, "bool", () -> NerdyMath.boolToDouble(driveJoyLeft.getRawButton(current_number)));
        //     BadLog.createTopic("OI/Right Joystick/Button " + i, "bool", () -> NerdyMath.boolToDouble(driveJoyRight.getRawButton(current_number)));
        //     BadLog.createTopic("OI/Operator Joystick/Button " + i, "bool", () -> NerdyMath.boolToDouble(operatorJoy.getRawButton(current_number)));
        // }
    }
}
