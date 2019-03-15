/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor;

import com.nerdherd.lib.logging.Loggable;
import com.nerdherd.lib.logging.NerdyBadlog;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class PressureSensor implements Loggable {
  
    private final AnalogInput m_analogSensor;
    private final String m_name;
  
    public PressureSensor(String name, int port) {
        m_name = name;
  
        m_analogSensor = new AnalogInput(port);
    }
    
    public double getVoltage() {
      return m_analogSensor.getVoltage();
    }
    
    public double getPressure() {
      return 3.51336 * (this.getVoltage() * this.getVoltage())+ 30.3481 * (this.getVoltage()) + 2.62798;
    }

    public void reportToSmartDashboard() {
      SmartDashboard.putNumber(m_name + " pressure", getPressure());
    }
  
    public void initLoggingData() {
      NerdyBadlog.createTopic(m_name + "/Pressure", () -> getPressure());
    }
}
