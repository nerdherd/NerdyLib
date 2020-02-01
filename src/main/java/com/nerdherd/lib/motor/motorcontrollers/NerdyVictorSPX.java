/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.motorcontrollers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
/**
 * Add your docs here.
 */
public class NerdyVictorSPX extends VictorSPX implements CANMotorController {

    public NerdyVictorSPX(int victorID) {
        super(victorID);
        super.configFactoryDefault();
    }   
    @Override
    public void setPower(double power) {
        super.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void setPower(double power, double arbitraryFF) {
		super.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, arbitraryFF);
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
    public void configDefaultSettings() {
        configVoltageCompensation(12);
        setBrakeMode();
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
    public void configVoltageCompensation(double voltage) {
        super.configVoltageCompSaturation(12);
        super.enableVoltageCompensation(true);
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
