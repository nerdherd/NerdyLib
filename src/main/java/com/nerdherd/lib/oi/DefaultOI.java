/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;


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

    // TODO: implement OI logging again
    @Override
    public void initLoggingData() {
        // for (int i = 1; i < 12; i++) {
        //     int current_number = i;

        // }
    }
}
