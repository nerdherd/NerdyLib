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
        {{1.0, 0.00968179937797901},
         {0.0, 0.9370421542496363}}
    );
    public static final Matrix B = new Matrix( new double[][]
        {{0.003863179628959037},
         {0.7643525824720272}}
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
        {{2.9847416675898895e-06, 0.00029292887248573353},
         {0.00029292887248573353, 0.0586064172629164}}
    );
    public static final Matrix R_noise = new Matrix( new double[][]
        {{0.09, 0.0},
         {0.0, 121.00000000000001}}
    );
    public static final Matrix K = new Matrix( new double[][]
        {{0.5845936817636895, 0.06503986584022446}}
    );
    public static final Matrix L = new Matrix( new double[][]
        {{0.00016328462381645637, 1.5524607563424378e-05},
         {0.00015604930805537993, 0.0015013329830863655}}
    );
    public static final Matrix Kff = new Matrix( new double[][]
        {{0.001390736024399, 1.1006608792588736e-06}}
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
