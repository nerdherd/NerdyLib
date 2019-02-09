/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.nerdherd.lib.sensor;

import com.nerdherd.lib.misc.Loggable;
import com.nerdherd.lib.misc.NerdyMath;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Add your docs here.
 */
public class HallSensor extends BooleanSensor implements Loggable {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private DigitalInput sensor;
  
  public HallSensor(int portNum, String sensorName, boolean isInverted){
    super(sensorName, isInverted);
    sensor = new DigitalInput(portNum);
  }

  @Override
  public boolean get() {
    return sensor.get();
  }

}