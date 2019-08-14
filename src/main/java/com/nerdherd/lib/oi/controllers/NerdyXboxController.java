/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.oi.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Add your docs here.
 */
public class NerdyXboxController extends XboxController{

    /**
     * 
     * ALL BUTTON NUMBERS ARE PLACE HOLDERS
     *  */
    public JoystickButton startButton = new JoystickButton(this, 0);
    public JoystickButton selectButton = new JoystickButton(this, 0);
    public JoystickButton aButton = new JoystickButton(this, 0);
    public JoystickButton bButton = new JoystickButton(this, 0);
    public JoystickButton yButton = new JoystickButton(this, 0);
    public JoystickButton xButton = new JoystickButton(this, 0);
    public JoystickButton upDPad = new JoystickButton(this, 0);
    public JoystickButton downDPad = new JoystickButton(this, 0);
    public JoystickButton leftDPad = new JoystickButton(this, 0);
    public JoystickButton rightDPad = new JoystickButton(this, 0);
    public JoystickButton leftBumper = new JoystickButton(this, 0);
    public JoystickButton rightBumper = new JoystickButton(this, 0);

    public NerdyXboxController(int port) {
        super(port);

    }


}
