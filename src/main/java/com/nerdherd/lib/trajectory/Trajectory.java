/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.trajectory;

import java.util.ArrayList;

import com.nerdherd.lib.trajectory.geometry.Arc2D;
import com.nerdherd.lib.trajectory.geometry.Pose2D;
import com.nerdherd.lib.trajectory.geometry.QuinticHermiteSpline;

/**
 * Add your docs here.
 */
public class Trajectory {

    private ArrayList<QuinticHermiteSpline> splines;
    private ArrayList<Arc2D> arcs;
    private double m_acceleration, m_startVelocity, m_endVelocity, m_centripetalAcceleration;


    public Trajectory(double acceleration, double startVelocity, double endVelocity, double centripetalAcceleration, Pose2D... controlPoints) {
        splines = new ArrayList<>();
        arcs = new ArrayList<>();
        for (int i = 0; i < controlPoints.length - 1; i++){
            splines.add(new QuinticHermiteSpline(controlPoints[i], controlPoints[i+1]));
            splines.get(i).arcParameterize(0, 0.01, 0.075);
            arcs.addAll(splines.get(i).arcParameterizedSpline);
        }
    }

    public void generateMotinProfileFirstPass() {
        
    }
}

