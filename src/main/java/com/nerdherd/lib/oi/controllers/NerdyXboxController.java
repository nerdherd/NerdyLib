/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi.controllers;

import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.buttons.JoystickButton;
// import edu.wpi.first.wpilibj.buttons.POVButton;

/**
 * Add your docs here.
 */
public class NerdyXboxController extends XboxController{

    /**
     * 
     * ALL BUTTON NUMBERS ARE PLACE HOLDERS
     *  */
    // public JoystickButton startButton = new JoystickButton(this, 7);
    // public JoystickButton backButton = new JoystickButton(this, 8);
    // public JoystickButton aButton = new JoystickButton(this, 1);
    // public JoystickButton bButton = new JoystickButton(this, 2);
    // public JoystickButton yButton = new JoystickButton(this, 3);
    // public JoystickButton xButton = new JoystickButton(this, 4);
    // public JoystickButton leftBumper = new JoystickButton(this, 5);
    // public JoystickButton rightBumper = new JoystickButton(this, 6);
    // public POVButton upDPad = new POVButton(this, 0);
    // public POVButton downDPadupDPad = new POVButton(this, 0);
    // public POVButton leftDPad = new POVButton(this, 0);
    // public POVButton rightDPad = new POVButton(this, 0);

    public NerdyXboxController(int port) {
        super(port);
    }

    public static enum Hand {
        kRight, kLeft;
    }

}
