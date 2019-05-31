/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi;

import com.nerdherd.lib.oi.AbstractOI;

import edu.wpi.first.wpilibj.Joystick;


public class GameOI extends AbstractOI{

    public Joystick gameJoy;
    public Joystick operatorJoy;

    public GameOI(){
        gameJoy = new Joystick(0);
        operatorJoy = new Joystick(1);
    }

    public double getDriveJoyLeftY(){
        return -gameJoy.getRawAxis(1);
    }
    public double getDriveJoyRightY(){
        return -gameJoy.getRawAxis(3);
    }
    public double getDriveJoyLeftX(){
        return -gameJoy.getRawAxis(0);
    }
    public double getDriveJoyRightX(){
        return -gameJoy.getRawAxis(2);
    }

    public double getOperatorJoyX(){
        return operatorJoy.getX();
    }
    public double getOperatorJoyY(){
        return operatorJoy.getY();
    }

    public void initLoggingData(){

    }


}
