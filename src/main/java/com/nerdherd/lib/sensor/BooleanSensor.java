/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor;

import com.nerdherd.lib.logging.Loggable;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public abstract class BooleanSensor implements Loggable {

  protected String m_name;
  protected boolean m_inverted;

  public BooleanSensor(String name, boolean isInverted) {
    m_name = name;
    m_inverted = isInverted;
  }

  public void configInversion(boolean isInverted) {
    m_inverted = isInverted;
  }

  public abstract boolean get();

  public boolean getValue(){
    // I have done the truth tables, and all signs point to an xor being good for this
    return m_inverted ^ this.get();      
  }
  
  public void reportToSmartDashboard(){
    SmartDashboard.putBoolean(m_name + " value: ", getValue());
  }

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(m_name + "/Value", () -> NerdyMath.boolToDouble(this.getValue()));
  }

}
