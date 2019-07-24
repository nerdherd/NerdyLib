/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.motorcontrollers;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;

import org.ghrobotics.lib.mathematics.units.derivedunits.Velocity;

/**
 * Add your docs here.
 */
public class NerdySparkMax extends CANSparkMax implements SmartCANMotorController {

    public CANPIDController PIDController;
    public CANEncoder encoder;

    public NerdySparkMax(int deviceID, MotorType type) {
        super(deviceID, type);
        PIDController = super.getPIDController();
        encoder = new CANEncoder(this);
        super.restoreFactoryDefaults();
    }

    @Override
    public void setPower(double power) {
        super.set(power);
    }

    @Override
    public void setPower(double power, double arbitraryFF) {
        PIDController.setReference(power, ControlType.kDutyCycle, 0, arbitraryFF);
    }

    @Override
    public void setPositionPID(double pos) {
        PIDController.setReference(pos, ControlType.kPosition);

    }

    @Override
    public void setPositionPID(double pos, double arbitraryFF) {
        PIDController.setReference(pos, ControlType.kPosition, 0, arbitraryFF);
    }

    @Override
    public void setPositionMotionMagic(double pos) {
        PIDController.setReference(pos, ControlType.kSmartMotion);

    }

    @Override
    public void setPositionMotionMagic(double pos, double arbitraryFF) {
        PIDController.setReference(pos, ControlType.kSmartMotion, 0, arbitraryFF);
    }

    @Override
    public void setVelocity(double velocity) {
        PIDController.setReference(velocity, ControlType.kVelocity);
    }

    @Override
    public void setVelocity(double velocity, double arbitraryFF) {
        PIDController.setReference(velocity, ControlType.kVelocity, 0, arbitraryFF);
    }

    @Override
    public void setVoltage(double voltage) {
        PIDController.setReference(voltage, ControlType.kVoltage);
    }

    @Override
    public void setVoltage(double voltage, double arbitraryFF) {
        PIDController.setReference(voltage, ControlType.kVoltage, 0, arbitraryFF);
    }
    /**
     * Set smart current limit
     */
    @Override
    public void configCurrentLimitContinuous(double current) {
        super.setSmartCurrentLimit((int) current);
    }

    @Override
    public void setInversion(boolean inversion) {
        super.setInverted(inversion);
    }

    @Override
    public double getVoltage() {
        return super.getAppliedOutput() * 12;
    }

    @Override
    public void configDefaultSettings() {
        super.enableVoltageCompensation(12);
        super.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public double getCurrent() {
        return super.getOutputCurrent();
    }

    @Override
    public void configPIDF(double p, double i, double d, double f, int slot) {
        PIDController.setP(p, slot);
        PIDController.setI(i, slot);
        PIDController.setD(d, slot);
        PIDController.setFF(f, slot);
    }

    @Override
    public void configMotionMagic(int accel, int vel) {
        PIDController.setSmartMotionMaxAccel(accel, 0);
        PIDController.setSmartMotionMaxVelocity(vel, 0);
        PIDController.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);
    }

    @Override
    public void configCurrentLimitPeak(double current) {

    }

    @Override
    public void configVoltageCompensation(double voltage) {
        super.enableVoltageCompensation(voltage);
    }

    @Override
    public void setBrakeMode() {
        super.setIdleMode(IdleMode.kBrake);
    }

    @Override
    public void setCoastMode() {
        super.setIdleMode(IdleMode.kCoast);
    }

    @Override
    public void resetEncoder() {
        encoder.setPosition(0);
    }

    @Override
    public double getPosition() {
        return encoder.getPosition();
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public void setSensorPhase(boolean phase) {
        if (phase) {
            encoder.setPositionConversionFactor(1);
            encoder.setVelocityConversionFactor(1);
        } else {
            encoder.setPositionConversionFactor(-1);
            encoder.setVelocityConversionFactor(-1);
        }
    }
    

    



    


    

    
}
