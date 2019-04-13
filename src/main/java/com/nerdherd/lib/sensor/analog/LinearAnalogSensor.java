/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor.analog;

/**
 * Add your docs here.
 */
public class LinearAnalogSensor extends AbstractAnalogSensor {

    private double m_scaleFactor, m_offset;

    public LinearAnalogSensor(String name, int port, int oversampleBits, int avgBits,
                                double scalingFactor, double offset) {
        super(name, port, oversampleBits, avgBits);
        this.configConversions(scalingFactor, offset);
    }
    
    public LinearAnalogSensor(String name, int port, double scalingFactor, double offset) {
        super(name, port);
        this.configConversions(scalingFactor, offset);
    }

    public LinearAnalogSensor(String name, int port) {
        super(name, port);
        this.configConversions(1, 0);
    }

    public void configScaleFactor(double scalingFactor) {
        m_scaleFactor = scalingFactor;
    }

    public void configOffset(double offset) {
        m_offset = offset;
    }

    public void configConversions(double scalingFactor, double offset) {
        this.configScaleFactor(scalingFactor);
        this.configOffset(offset);
    }

    @Override
    public double getScaled() {
        return this.m_scaleFactor * this.getRaw() - m_offset;
    }

}
