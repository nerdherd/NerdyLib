/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot.testconstants;

import com.nerdherd.lib.motor.statespace.DiscreteSSGains;

import Jama.Matrix;

/**
 * Add your docs here.
 */
public class TestSSGains {

  public static final Matrix A = new Matrix( new double[][]
        {{1.0, 0.018754053524133254},
         {0.0, 0.8780479988407994}}
    );
    public static final Matrix B = new Matrix( new double[][]
        {{0.015126667615452397},
         {1.4805831729578884}}
    );
    public static final Matrix C = new Matrix( new double[][]
        {{6175.881918041717, 0.0},
         {0.0, 617.5881918041716}}
    );
    public static final Matrix D = new Matrix( new double[][]
        {{0.0},
         {0.0}}
    );
    public static final Matrix Q_noise = new Matrix( new double[][]
        {{1.713524423939017e-05, 0.0010991078862064218},
         {0.0010991078862064216, 0.11006566465984906}}
    );
    public static final Matrix R_noise = new Matrix( new double[][]
        {{0.045, 0.0},
         {0.0, 60.50000000000001}}
    );
    public static final Matrix K = new Matrix( new double[][]
        {{0.9264515394429866, 0.0930904218144866}}
    );
    public static final Matrix L = new Matrix( new double[][]
        {{0.00016266581683326202, 3.0248504728341782e-05},
         {3.6353482326551185e-05, 0.0014160637342987457}}
    );
    public static final Matrix Kff = new Matrix( new double[][]
        {{0.015123074606134731, 5.920925971125776e-06}}
    );
    public static final Matrix U_min = new Matrix( new double[][]
        {{-12.0}}
    );
    public static final Matrix U_max = new Matrix( new double[][]
        {{12.0}}
    );
    public static final double dt = 0.02;

    public static DiscreteSSGains testGains = new DiscreteSSGains(A, B, C, D, Q_noise, R_noise, K, L, Kff, U_min, U_max, dt, false);
}
