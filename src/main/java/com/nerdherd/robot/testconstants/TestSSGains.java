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
        {{1.0, 0.009955012505682832},
         {0.0, 0.9910160139098992}}
    );
    public static final Matrix B = new Matrix( new double[][]
        {{0.0023285164193457305},
         {0.46500387362071116}}
    );
    public static final Matrix C = new Matrix( new double[][]
        {{651.8986469044033, 0.0},
         {0.0, 65.18986469044033}}
    );
    public static final Matrix D = new Matrix( new double[][]
        {{0.0},
         {0.0}}
    );
    public static final Matrix Q_noise = new Matrix( new double[][]
        {{3.0692916133149742e-06, 0.00030969460621344246},
         {0.0003096946062134424, 0.06193934161714947}}
    );
    public static final Matrix R_noise = new Matrix( new double[][]
        {{0.09, 0.0},
         {0.0, 121.00000000000001}}
    );
    public static final Matrix K = new Matrix( new double[][]
        {{0.5804118568397637, 0.13978035346974577}}
    );
    public static final Matrix L = new Matrix( new double[][]
        {{0.002057459242068762, 8.820200451664257e-05},
         {0.06258548133753324, 0.008312845457613656}}
    );
    public static final Matrix Kff = new Matrix( new double[][]
        {{0.00083826401373399, 6.696040625089098e-07}}
    );
    public static final Matrix U_min = new Matrix( new double[][]
        {{-12.0}}
    );
    public static final Matrix U_max = new Matrix( new double[][]
        {{12.0}}
    );
    public static final double dt = 0.01;

    public static DiscreteSSGains testGains = new DiscreteSSGains(A, B, C, D, Q_noise, R_noise, K, L, Kff, U_min, U_max, dt, false);
}
