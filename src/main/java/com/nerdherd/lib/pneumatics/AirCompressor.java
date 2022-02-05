/*----------------------------------------------------------------------------*/
/* Copyright (c) 2022 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.pneumatics;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.CompressorConfigType;
import edu.wpi.first.util.sendable.SendableBuilder;

public class AirCompressor extends SubsystemBase {

    private Compressor m_compressor;

    public AirCompressor(int port, PneumaticsModuleType moduleType) {
        m_compressor = new Compressor(port, moduleType);
    }

    public void close() {
        m_compressor.close();
    }

    public void disable() {
        m_compressor.disable();
    }

    public boolean enabled() {
        return m_compressor.enabled();
    }

    public void enableDigital() {
        m_compressor.enableDigital();
    }

    public void enableHybrid(double minPressure, double maxPressure) {
        m_compressor.enableHybrid(minPressure, maxPressure);
    }

    public double getAnalogVoltage() {
        return m_compressor.getAnalogVoltage();
    }

    public CompressorConfigType getConfigType() {
        return m_compressor.getConfigType();
    }

    public double getCurrent() {
        return m_compressor.getCurrent();
    }

    public double getPressure() {
        return m_compressor.getPressure();
    }

    public boolean getPressureSwitchValue() {
        return m_compressor.getPressureSwitchValue();
    }

    public void initSendable(SendableBuilder sendableBuilder) {
        m_compressor.initSendable(sendableBuilder);
    }   

    @Override
    public void reportToSmartDashboard() {
        super.reportToSmartDashboard();
        SmartDashboard.putNumber(name + " Analog Voltage", getAnalogVoltage());
        SmartDashboard.putNumber(name + " Current", getCurrent());
        SmartDashboard.putNumber(name + " Pressure", getPressure());
    }
}