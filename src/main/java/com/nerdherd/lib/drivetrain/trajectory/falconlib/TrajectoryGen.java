/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.trajectory.falconlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2d;
import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2dWithCurvature;
import org.ghrobotics.lib.mathematics.twodim.trajectory.TrajectoryGenerator;
import org.ghrobotics.lib.mathematics.twodim.trajectory.TrajectoryGeneratorKt;
import org.ghrobotics.lib.mathematics.twodim.trajectory.TrajectoryIterator;
import org.ghrobotics.lib.mathematics.twodim.trajectory.constraints.CentripetalAccelerationConstraint;
import org.ghrobotics.lib.mathematics.twodim.trajectory.types.TimedEntry;
import org.ghrobotics.lib.mathematics.twodim.trajectory.types.TimedTrajectory;
import org.ghrobotics.lib.mathematics.twodim.trajectory.types.TrajectorySamplePoint;
import org.ghrobotics.lib.mathematics.units.LengthKt;
import org.ghrobotics.lib.mathematics.units.TimeUnitsKt;
import org.ghrobotics.lib.mathematics.units.derivedunits.AccelerationKt;
import org.ghrobotics.lib.mathematics.units.derivedunits.VelocityKt;


/**
@author DylanB, FRC 687
FalconLib is written by FRC 5190
 */
// public class TrajectoryGen {

//     TrajectoryGenerator m_gen;

//     public TrajectoryGen(double maxDX, double maxDY, double maxDTheta) {
//         TrajectoryGenerator m_gen = new TrajectoryGenerator(new Length(maxDX), new Length(maxDY), new Rotation2d(Math.toRadians(maxDTheta)));
//     }

//     public static TrajectoryGen getDefaulTrajectoryGen() {
//         return new TrajectoryGen(2/12, 0.25/12, 5);
//     }

//     public Trajectory generateTrajectory(List<Pose2d> waypoints, double maxCentripetalAccel, double startVelocity, double endVelocity, double maxVelocity, double maxAcceleration, boolean reversed) {
//         return m_gen.generateTrajectory(waypoints, Arrays.asList(new CentripetalAccelerationConstraint(maxCentripetalAccel)), new Velocity<Length>(startVelocity, new Length(startVelocity)), new Velocity<Length>(endVelocity, new Length(endVelocity)),
//          new Velocity<Length>(maxVelocity, new Length(maxVelocity)), new Acceleration<Length>(maxAcceleration, new Length(maxAcceleration)), reversed);
//     }

    
// }

public class TrajectoryGen {

    TrajectoryGenerator m_gen;
    public TrajectoryGen() {
        m_gen = TrajectoryGeneratorKt.getDefaultTrajectoryGenerator();
    }

    public ArrayList<TrajectoryPoint> generateTrajectory(List<Pose2d> waypoints, double centripetalAccel, double startVelocity, double endVelocity, double maxVelocity, double accel, boolean reversed) {
        TimedTrajectory<Pose2dWithCurvature> falconTraj = m_gen.generateTrajectory(waypoints, 
        Collections.singletonList(
            new CentripetalAccelerationConstraint(AccelerationKt.getAcceleration(LengthKt.getFeet(centripetalAccel)))),
             VelocityKt.getVelocity(LengthKt.getFeet(startVelocity)),
             VelocityKt.getVelocity(LengthKt.getFeet(endVelocity)),
              VelocityKt.getVelocity(LengthKt.getFeet(maxVelocity)), 
              AccelerationKt.getAcceleration(LengthKt.getFeet(accel)), reversed);
        TrajectoryIterator iterator = falconTraj.iterator();
        ArrayList<TrajectoryPoint> trajPoints = new ArrayList<>();
        while (!iterator.isDone()) {
            TrajectorySamplePoint point = iterator.getCurrentState();
            TimedEntry point2 = (TimedEntry) point.component1();
            Pose2dWithCurvature point3 = (Pose2dWithCurvature) point2.getState();
            double x = point3.component1().getTranslation().getX().getFeet();
            double y = point3.component1().getTranslation().getY().getFeet();
            double theta = point3.component1().getRotation().getDegree();
            double velocity = point2.getVelocity().getValue();
            double acceleration = point2.getAcceleration().getValue();
            trajPoints.add(new TrajectoryPoint(x, y, theta, acceleration, velocity));
            iterator.advance(TimeUnitsKt.getSecond(0.02));
        }
        return trajPoints;
      }
}

