package com.nerdherd.lib.sensor;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class VexUltrasonic {
  private final Ultrasonic m_ultra;

  public VexUltrasonic(int ping, int echo){
    m_ultra = new Ultrasonic(ping, echo);
    m_ultra.setAutomaticMode(true);
  }

  public double getInches(){
    //accurate from 2 to 40 (could not accuratly test > 40)
    return m_ultra.getRangeInches();
  } 

  public void reportToSmartDashboard(){
    SmartDashboard.putNumber("Inches", getInches());
  }

}
