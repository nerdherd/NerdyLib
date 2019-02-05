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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HallSensor implements Loggable{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private DigitalInput sensor;
  private String m_name;
  private boolean m_inverted;
  public HallSensor(int portNum, String sensorName, boolean isInverted){
    sensor = new DigitalInput(portNum);
    m_name = sensorName;
    m_inverted = isInverted;
  }


  public boolean getValue(){
    // I have done the truth tables, and all signs point to an xor being good for this
    return m_inverted ^ sensor.get();        
  }
 
  public void reportToDash(){
    SmartDashboard.putBoolean(m_name + " value: ", getValue());
  }

@Override
public void initLoggingData() {
	BadLog.createTopic(m_name + "/Value", "bool", () -> NerdyMath.boolToDouble(this.getValue()));
}

}