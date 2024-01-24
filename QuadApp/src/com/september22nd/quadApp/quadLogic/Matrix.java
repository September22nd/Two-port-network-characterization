package com.september22nd.quadApp.quadLogic;

import java.io.Serializable;

public class Matrix implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359952810607748120L;
	
	private double[][] arr;
	
	public Matrix(double a11, double a12, double a21, double a22) {
		arr = new double[2][2];
		arr[0][0] = a11;
		arr[0][1] = a12;
		arr[1][0] = a21;
		arr[1][1] = a22;
	}
	
	String string = new String("strnig");
	
	public Matrix(Matrix matrix) {
		if (matrix != null) {
			arr = new double[2][2];
			arr[0][0] = matrix.arr[0][0];
			arr[0][1] = matrix.arr[0][1];
			arr[1][0] = matrix.arr[1][0];
			arr[1][1] = matrix.arr[1][1];
		}
	}
	
	public double get11() {
		return arr[0][0];
	}
	
	public double get12() {
		return arr[0][1];
	}
	
	public double get21() {
		return arr[1][0];
	}
	
	public double get22() {
		return arr[1][1];
	}
	
	public double get(int i, int j) {
		return arr[i][j];
	}
	
	public void set11(double a11) {
		arr[0][0] = a11;
	}
	
	public void set12(double a12) {
		arr[0][1] = a12;
	}
	
	public void set21(double a21) {
		arr[1][0] = a21;
	}
	
	public void set22(double a22) {
		arr[1][1] = a22;
	}
	
	public void set(double a11, double a12, double a21, double a22) {
		arr[0][0] = a11;
		arr[0][1] = a12;
		arr[1][0] = a21;
		arr[1][1] = a22;
	}
	
	public void set(Matrix matrix) {
		arr[0][0] = matrix.arr[0][0];
		arr[0][1] = matrix.arr[0][1];
		arr[1][0] = matrix.arr[1][0];
		arr[1][1] = matrix.arr[1][1];
	}

	
	public double det() {
		return arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
	}
	
	public Matrix inv() {
		if (this.det() == 0.0) {
			return null; //no inverse
		}
		
		return new Matrix(arr[1][1] / (this.det()), -arr[0][1] / (this.det()), -arr[1][0] / (this.det()), arr[0][0] / (this.det()));
	}
	
}