package com.nerdherd.lib.motor.motorcontrollers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * 
 * @author dbarv
 *Basic wrapper for TalonSRXs 
 */

public class NerdyTalon extends TalonSRX implements SmartCANMotorController{
	
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
	public void configCurrentLimitPeak(double current) {
		super.configPeakCurrentLimit( (int) current, 0);
		super.enableCurrentLimit(true);
	}
	
	/**
	 * Configure a continuous current limit
	 * @param current continuous current limits in amps
	 */
	public void configCurrentLimitContinuous(double current) {
		super.configContinuousCurrentLimit( (int) current, 0);
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

	  @Override
	  public double getCurrent() {
		  return super.getOutputCurrent();
	  }

	  @Override
	  public void setPositionMotionMagic(double pos) {
		  super.set(ControlMode.MotionMagic, pos);
	  }

	  @Override
	  public void setPositionMotionMagic(double pos, double arbitraryFF) {
		super.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, arbitraryFF);
	  }

	  @Override
	  public void setPositionPID(double pos) {
		super.set(ControlMode.Position, pos);
	  }

	  @Override
	  public void setPositionPID(double pos, double arbitraryFF) {
		super.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, arbitraryFF);
	  }

	  @Override
	  public void setVelocity(double vel) {
		super.set(ControlMode.Velocity, vel);
	  }

	  @Override
	  public void setVelocity(double vel, double arbitraryFF) {
		super.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, arbitraryFF);
	  }

	  @Override
	  public void setPower(double pow) {
			super.set(ControlMode.PercentOutput, pow);
	  }

	  @Override
	  public void setPower(double pow, double arbitraryFF) {
		super.set(ControlMode.PercentOutput, pow, DemandType.ArbitraryFeedForward, arbitraryFF);
	  }

	  @Override
	  public void setVoltage(double voltage) {
			super.set(ControlMode.PercentOutput, voltage/12);
	  }

	  @Override
	  public void setVoltage(double voltage, double arbitraryFF) {
		super.set(ControlMode.PercentOutput, voltage/12, DemandType.ArbitraryFeedForward, arbitraryFF);
	  }

	  @Override
	  public double getVoltage() {
		  return super.getMotorOutputVoltage();
		}

	@Override
	public void setInversion(boolean inversion) {
		super.setInverted(inversion);
	}

	@Override
	public void setBrakeMode() {
		super.setNeutralMode(NeutralMode.Brake);
	}

	@Override
	public void setCoastMode() {
		super.setNeutralMode(NeutralMode.Coast);

	}

	@Override
	public void resetEncoder() {
		super.setSelectedSensorPosition(0);
	}

	@Override
	public double getPosition() {
		return super.getSelectedSensorPosition();
	}

	@Override
	public double getVelocity() {
		return super.getSelectedSensorVelocity();
	}
		
	@Override
    /**
     * Only works with CTRE Motor Controllers
     */
    public void followCANMotorController(CANMotorController master) {
        if (master instanceof NerdyTalon || master instanceof NerdyVictorSPX) {
            super.follow( (IMotorController) master);
        }
	}
	
	@Override
    public int getID() {
        return super.getDeviceID();
    }
		
	@Override
    public boolean getInversion() {
        return super.getInverted();
    }
	
}
