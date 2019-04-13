/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor.analog;

import com.nerdherd.lib.logging.Loggable;
import com.nerdherd.lib.logging.NerdyBadlog;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public abstract class AbstractAnalogSensor extends AnalogInput implements Loggable {

    public String name;

    public AbstractAnalogSensor(String name, int port, int oversampleBits, int avgBits) {
        super(port);
        super.setOversampleBits(oversampleBits);
        super.setAverageBits(avgBits);
        this.name = name;
    }

    public AbstractAnalogSensor(String name, int port) {
        this(name, port, 2, 5);
    }

    public double getRaw() {
        return this.getAverageVoltage();
    }

    public abstract double getScaled();

    public void reportToSmartDashboard() {
        SmartDashboard.putNumber(this.name + " Raw Output", this.getRaw());
        SmartDashboard.putNumber(this.name + " Scaled Output", this.getScaled());
    }

    public void initLoggingData() {
        NerdyBadlog.createTopic(this.name + "/RawOutput", () -> this.getRaw());
        NerdyBadlog.createTopic(this.name + "/ScaledOutput", () -> this.getScaled());
    }

}
