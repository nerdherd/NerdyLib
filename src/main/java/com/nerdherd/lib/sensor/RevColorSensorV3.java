/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.sensor;

import com.nerdherd.lib.logging.Loggable;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

/**
 * Add your docs here.
 */
public class RevColorSensorV3 extends ColorSensorV3 implements Loggable {

    private final ColorMatch m_colorMatcher = new ColorMatch();
    public String name;

    public RevColorSensorV3(I2C.Port port, String name) {
        super(port);

       this.name = name;

    }

    public void addColor(double r, double g, double b) {
      m_colorMatcher.addColorMatch(new Color(r, g, b));
    }

    public Color getMatchedColor() {
      return m_colorMatcher.matchClosestColor(super.getColor()).color;
    }  

    //get currently matched color 
  //   private Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  //  m_colorMatcher.addColorMatch(kBlueTarget);

    // private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    // private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    // private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    @Override
    public void initLoggingData() {
        NerdyBadlog.createTopic(name + "/Red", () -> (double) this.getRed());
        NerdyBadlog.createTopic(name + "/Green", () -> (double) this.getGreen());
        NerdyBadlog.createTopic(name + "/Blue", () -> (double) this.getBlue());
        NerdyBadlog.createTopic(name + "/IR", () -> (double) this.getIR());
        NerdyBadlog.createTopic(name + "/Proximity", () -> (double) this.getProximity());
    }

    public void reportToSmartDashboard(){
        SmartDashboard.putNumber(name + " Red", this.getRed());
        SmartDashboard.putNumber(name + " Blue", this.getBlue());
        SmartDashboard.putNumber(name + " Green", this.getGreen());
        SmartDashboard.putNumber(name + " Matched Red", this.getColor().red);
        SmartDashboard.putNumber(name + " Matched Green", this.getColor().green);
        SmartDashboard.putNumber(name + " Matched Blue", this.getColor().blue);
        SmartDashboard.putNumber(name + " IR", this.getIR());
        SmartDashboard.putNumber(name + " Proximity", this.getProximity());
      }

}
