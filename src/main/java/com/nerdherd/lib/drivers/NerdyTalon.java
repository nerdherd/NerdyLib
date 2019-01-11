package com.nerdherd.lib.drivers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
/**
 * 
 * @author dbarv
 *Basic wrapper for TalonSRXs to make it easier to set PIDFs
 */

public class NerdyTalon extends TalonSRX {
	
	public NerdyTalon(int talonID) {
		super(talonID);
		super.configFactoryDefault();
	}

	public void configDefaultSettings() {
		configVoltageCompensation(12);
		super.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
		super.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
		super.configPeakOutputForward(1, 0);
		super.configPeakOutputReverse(-1, 0);
		super.configClosedloopRamp(0, 0);
		super.configOpenloopRamp(0, 0);	
		super.setNeutralMode(NeutralMode.Brake);
	}
	
	public void configPIDF(double p, double i, double d, double f, int slot) {
		super.config_kP(slot, p, 0);
		super.config_kI(slot, i, 0);
		super.config_kD(slot, d, 0);
		super.config_kF(slot, f, 0);
	}

	public void configVoltageCompensation(double voltage) {
		super.configVoltageCompSaturation(voltage, 0);
		super.enableVoltageCompensation(true);
	}
	
	public void configCurrentLimitPeak(int current) {
		super.configPeakCurrentLimit(current, 0);
		super.enableCurrentLimit(true);
	}
	
	public void configCurrentLimitContinuous(int current) {
		super.configContinuousCurrentLimit(current, 0);
		super.enableCurrentLimit(true);
	}

	public void configMotionMagic(int accel, int cruise_vel) {
		super.configMotionAcceleration(accel, 0);
		super.configMotionCruiseVelocity(cruise_vel, 0);
	}
	
}
