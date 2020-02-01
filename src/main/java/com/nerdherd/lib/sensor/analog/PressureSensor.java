/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor.analog;

import com.nerdherd.lib.logging.Loggable;
import com.nerdherd.lib.logging.NerdyBadlog;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class PressureSensor extends LinearAnalogSensor {
  
    public PressureSensor(String name, int port, double pressureOffset) {
      this(name, port);
      super.configOffset(5.083 - pressureOffset);
    }

    public PressureSensor(String name, int port) {
        super(name, port, 41.83, 5.083);
    }
    
    public double getPressure() {
      return getScaled();
      // return 3.51336 * (this.getVoltage() * this.getVoltage())+ 30.3481 * (this.getVoltage()) + 2.62798;
    }

    @Override
    public void reportToSmartDashboard() {
      SmartDashboard.putNumber(super.name + " pressure", this.getPressure());
      SmartDashboard.putNumber(super.name + " voltage", this.getRaw());
    }
  
    @Override
    public void initLoggingData() {
      NerdyBadlog.createTopic(super.name + "/Pressure", () -> this.getPressure());
      NerdyBadlog.createTopic(super.name + "/Raw", () -> this.getRaw());
    }
}
