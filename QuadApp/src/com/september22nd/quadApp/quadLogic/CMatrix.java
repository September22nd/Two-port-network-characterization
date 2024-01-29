package com.september22nd.quadApp.quadLogic;

import java.io.Serializable;

public class CMatrix implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6923756163040427980L;
	
	private Complex[][] arr;
	
	public CMatrix(Complex c00, Complex c01, Complex c10, Complex c11) {
		arr = new Complex[2][2];
		arr[0][0] = c00;
		arr[0][1] = c01;
		arr[1][0] = c10;
		arr[1][1] = c11;
	}
	
	public CMatrix(CMatrix matrix) {
		if (matrix != null) {
			arr = new Complex[2][2];
			arr[0][0] = matrix.arr[0][0];
			arr[0][1] = matrix.arr[0][1];
			arr[1][0] = matrix.arr[1][0];
			arr[1][1] = matrix.arr[1][1];
		}
	}
	
	public Complex get(int i, int j) {
		return this.arr[i][j];
	}
	
	public Complex det() {
		return Complex.subt(Complex.prod(arr[0][0], arr[1][1]), Complex.prod(arr[0][1], arr[1][0]));
	}
	
	public CMatrix inv() {
		if (this.det().isZero()) {
			return null;
		}
		return new CMatrix(Complex.div(arr[1][1], det()), Complex.div(arr[0][1].neg(), det()), Complex.div(arr[1][0].neg(), det()), Complex.div(arr[0][0], det()));
	}
	
}
