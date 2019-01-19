/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
MB1013 HRLV-MaxSonar-EZ1
Ultrasonic Sensor
*/
package com.nerdherd.lib.sensor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UltrasonicSensor {
  
  private final AnalogInput m_ultra;

  public UltrasonicSensor(int portNumber) {
    m_ultra = new AnalogInput(portNumber);

    m_ultra.setOversampleBits(2);
    m_ultra.setAverageBits(5);
  }
  

  public double getAvgVoltage() {
    return m_ultra.getAverageVoltage();
  }

  public double getInches() {
  //accurate from 12 to about 29 
  //subtract 12 later 
  return Math.round( ((getAvgVoltage() * 1024 / 10) + 1) * .393701) ; 
  }

  public void reportToSmartDashboard() {
    SmartDashboard.putNumber("Average Voltage", getAvgVoltage());
    SmartDashboard.putNumber("Distance", getInches());
    
  } 
}
