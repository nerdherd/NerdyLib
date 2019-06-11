package com.nerdherd.lib.motor.motorcontrollers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * 
 * @author dbarv
 *Basic wrapper for TalonSRXs 
 */

public class NerdyTalon extends TalonSRX {
	
	/**
	 * @param talonID
	 */
	public NerdyTalon(int talonID) {
		super(talonID);
		super.configFactoryDefault();
	}

	/**
	 * configure default settings:
	 * 12 volt voltage compensation
	 * Brake Mode
	 * Peak output of 100%
	 */
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
	
	/**
	 * Config talon onboard PIDF
	 * @param p kP 
	 * @param i kI
	 * @param d kD
	 * @param f kF velocity feedforward
	 * @param slot slot for PIDF, if multiple PID constants are needed
	 */
	public void configPIDF(double p, double i, double d, double f, int slot) {
		super.config_kP(slot, p, 0);
		super.config_kI(slot, i, 0);
		super.config_kD(slot, d, 0);
		super.config_kF(slot, f, 0);
	}

	/**
	 * Limit maximum voltage applied by the TalonSRX, is 12 Volts by default
	 * @param voltage 
	 */
	public void configVoltageCompensation(double voltage) {
		super.configVoltageCompSaturation(voltage, 0);
		super.enableVoltageCompensation(true);
	}
	
	/**
	 * Configure a peak current limit
	 * @param current peak current limit in amps
	 */
	public void configCurrentLimitPeak(int current) {
		super.configPeakCurrentLimit(current, 0);
		super.enableCurrentLimit(true);
	}
	
	/**
	 * Configure a continuous current limit
	 * @param current continuous current limits in amps
	 */
	public void configCurrentLimitContinuous(int current) {
		super.configContinuousCurrentLimit(current, 0);
		super.enableCurrentLimit(true);
	}

	/**
	 * Configure acceleration and velocity for motion magic
	 * @param accel acceleration in ticks/decisecond/second
	 * @param cruise_vel cruise velocity in ticks/decisecond
	 */
	public void configMotionMagic(int accel, int cruise_vel) {
		super.configMotionAcceleration(accel, 0);
		super.configMotionCruiseVelocity(cruise_vel, 0);
	}
	
	/**
	 * Add follower VictorSPXs
	 * @param followers an array of VictorSPXs
	 */
	public void configFollowerVictors(VictorSPX[] followers) {
		for (VictorSPX controller : followers) {
			controller.configFactoryDefault();
		 	 controller.follow(this);
		  	controller.setInverted(this.getInverted());
		 	 controller.setNeutralMode(NeutralMode.Brake);
		}
	  }
	
	  /**
	   * Add follower NerdyTalons
	   * @param followers an array of NerdyTalons
	   */
	  public void configFollowerTalons(NerdyTalon[] followers) {
		for (NerdyTalon controller : followers) {
		  controller.follow(this);
		  controller.setInverted(this.getInverted());
		  controller.setNeutralMode(NeutralMode.Brake);
		  controller.configDefaultSettings();
		}
	  }
	
}
