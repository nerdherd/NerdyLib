/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.statespace;

import Jama.Matrix;

/**
 * Add your docs here.
 */
public class DiscreteSSGains {

        public boolean isAugmented;
        public Matrix A, B, C, D, Q, R, K, L, Kff, u_min, u_max;
        public double dt;
        public int p, q, n;

        public DiscreteSSGains(Matrix A, Matrix B, Matrix C, Matrix D, Matrix Q, Matrix R, Matrix K, Matrix L,
                        Matrix Kff, Matrix u_min, Matrix u_max, double dt, boolean isAugmented) {
                this.isAugmented = isAugmented;

                this.A = A;
                this.B = B;
                this.C = C;
                this.D = D;
                this.Q = Q;
                this.R = R;
                this.K = K;
                this.L = L;
                this.Kff = Kff;
                this.u_min = u_min;
                this.u_max = u_max;

                this.dt = dt;

                // Number of states
                this.n = this.A.getRowDimension();
                // Number of inputs
                this.p = this.B.getColumnDimension();
                // Number of sensor inputs (measurements)
                this.q = this.C.getRowDimension();

                this.checkSystemValidity();
        }

    private void checkSystemValidity() {
        assert this.A.getColumnDimension() == this.A.getRowDimension() :
                "A must be square";

        assert this.B.getRowDimension() == this.n :
                "B must have the same number of rows as there are states";

        assert this.C.getColumnDimension() == this.n :
                "C must have as many columns as there are states";

        assert this.D.getRowDimension() == this.q :
                "D must have the same number of rows as there are sensor inputs";
        assert this.D.getColumnDimension() == this.p :
                "D must have the same number of columns as there are inputs";

        assert this.Q.getRowDimension() == this.Q.getColumnDimension() :
                "Q must be square";
        assert this.Q.getRowDimension() == this.n :
                "Q must have the same dimensions as A";

        assert this.R.getRowDimension() == this.R.getColumnDimension() :
                "R must be square";
        assert this.R.getRowDimension() == this.q :
                "R must have the same dimensions as C";

        assert this.K.getRowDimension() == this.p :
                "K must have the same number of rows as there are inputs";
        assert this.K.getColumnDimension() == this.n :
                "K must have the same number of columns as there are states";

        assert this.L.getRowDimension() == this.n :
                "L must have the same number of rows as there are states";
        assert this.L.getColumnDimension() == this.q :
                "L must have the same number of columns as there are sensor inputs";

        assert this.Kff.getColumnDimension() == this.n :
                "Kff must have the same number of columns as there are states";
        assert this.Kff.getRowDimension() == this.p :
                "Kff must have the same number of rows as there are inputs";
        
        assert this.u_min.getRowDimension() == this.p :
                "u_min must have the same number of rows as there are inputs";
        assert this.u_max.getRowDimension() == this.p :
                "u_max must have the same number of rows as there are inputs";
    }

}
