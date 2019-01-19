/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.nerdherd.lib.sensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HallSensor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static DigitalInput sensor;
  public HallSensor(int portNum){
    sensor = new DigitalInput(portNum);
  }
  public boolean getValue(){
    return !sensor.get();        
      }
 
  public void reportToDash(){
  SmartDashboard.putBoolean("Hallsensor value: ",getValue());
}

}