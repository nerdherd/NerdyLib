/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

/**
 * Add your docs here.
 */
public class SingleMotorTalonSRXWithLimitSwitch extends SingleMotorTalonSRX {

    private boolean m_invertLimitSwitch, m_useForwardLimitSwitch;
    public SingleMotorTalonSRXWithLimitSwitch(int talonID, String subsystemName, boolean inversion, boolean sensorPhase, boolean invertLimitSwitch, boolean useForwardLimitSwitch) {
        super(talonID, subsystemName, inversion, sensorPhase);
        m_invertLimitSwitch = invertLimitSwitch;
        m_useForwardLimitSwitch = useForwardLimitSwitch;
    }

    public boolean getLimitSwitch() {
        if (m_useForwardLimitSwitch) {
            if (m_invertLimitSwitch) {
                return super.motor.getSensorCollection().isFwdLimitSwitchClosed();
            } else {
                return !super.motor.getSensorCollection().isFwdLimitSwitchClosed();
            }
        } else {
            if (m_invertLimitSwitch) {
                return super.motor.getSensorCollection().isRevLimitSwitchClosed();
            } else {
                return !super.motor.getSensorCollection().isRevLimitSwitchClosed();
            }
        }
    }
}
