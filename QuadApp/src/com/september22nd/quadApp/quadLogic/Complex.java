package com.september22nd.quadApp.quadLogic;

import java.io.Serializable;

public class Complex implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2119354068710616050L;
	
	public static final Complex ONE = new Complex(1.0,0.0);
	
	private double re;
	
	private double im;
	
	private double norm;
	
	private double arg;
	
	/**
	 * Generates a new complex number.
	 * @param re the real part.
	 * @param im the imaginary part.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
		this.norm = Math.sqrt(re * re + im * im);
		this.arg = Math.atan2(im, re);
	}
	
	/**
	 * Generates a new complex number.
	 * @param a the real part if <code>c</code> equals <code>DisplayForm.RECT</code>
	 * or the modulus if <code>c</code> equals <code>DisplayForm.POLAR</code>
	 * @param b the imaginary part if <code>c</code> equals <code>DisplayForm.RECT</code>
	 * or the argument in radians if <code>c</code> equals <code>DisplayForm.POLAR</code>
	 * @param c the form in which the data are inputted. If <code>c</code> equals
	 * <code>DisplayForm.RECT</code>, the constructor is the same as <code>Complex(double,double)</code>
	 */
	public Complex(double a, double b, DisplayForm c) {
		if (c == DisplayForm.RECT) {
			this.re = a;
			this.im = b;
			this.norm = Math.sqrt(a * a + b * b);
			this.arg = Math.atan2(b, a);
		}
		else {
			this.norm = a;
			this.arg = b;
			this.re = a * Math.cos(b);
			this.im = a * Math.sin(b);
			//now the following corrects silly floating point precision errors
			//I have considered this is a fairly low amount
			if (Math.abs(im) <= 1.0E-14 * Math.abs(re)) {
				im = 0.0;
			}
		}
	}

	public Complex neg() {
		return new Complex(-this.re, -this.im);
	}
	
	public String toRectString() {
		if (im == 0.0) {
			return String.format("%f", re);
		}
		else if (re == 0.0) {
			return (im > 0.0) ? String.format("j%f", im) : String.format("-j%f", -im);
		}
		return (im > 0.0) ? String.format("%f+j%f", this.re, this.im) : String.format("%f-j%f", this.re, -this.im);
	}
	
	public String toRectString(int prec) {
		if (im == 0.0) {
			return String.format("%." + prec + "f", re);
		}
		else if (re == 0.0) {
			return (im > 0.0) ? String.format("j%." + prec + "f", im) : String.format("-j%." + prec + "f", -im);
		}
		return (im > 0.0) ? String.format("%." + prec + "f+j%." + prec +"f", this.re, this.im) : String.format("%." + prec + "f-j%." + prec +"f", this.re, -this.im);
	}
	
	public String toPolarString(AngUnit unit) {
		if (im == 0.0) {
			return String.format("%f", re);
		}
		else {
			if (unit == AngUnit.DEG) {
				return String.format("%f<%f°", this.norm, Math.toDegrees(cleanArgument(this.arg)));
			}
		return String.format("%f<%f", this.norm, cleanArgument(this.arg));
		}
	}
	
	public String toPolarString(AngUnit unit, int normPrec, int angPrec) {
		if (im == 0.0) {
			return String.format("%." + normPrec + "f", re);
		}
		else {
			if (unit == AngUnit.DEG) {
				return String.format("%." + normPrec + "f<%." + angPrec + "f°", this.norm, Math.toDegrees(cleanArgument(this.arg)));
			}
		return String.format("%." + normPrec + "f<%." + angPrec +"f", this.norm, cleanArgument(this.arg));
		}
	}
	
	public boolean isZero() {
		return re == 0.0 && im == 0.0;
	}
	
	public static Complex add(Complex c1, Complex c2) {
		return new Complex(c1.re + c2.re, c1.im + c2.im);
	}
	
	public static Complex subt(Complex c1, Complex c2) {
		return add(c1, c2.neg());
	}
	
	public static Complex prod(Complex c1, Complex c2) {
		return new Complex(c1.norm * c2.norm, c1.arg + c2.arg, DisplayForm.POLAR);
	}
	
	public static Complex div(Complex c1, Complex c2) {
		if (c2.isZero()) {
			throw new ArithmeticException("Cannot divide by zero");
		}
		return new Complex(c1.norm / c2.norm, c1.arg - c2.arg, DisplayForm.POLAR);
	}
	
	public static Complex parseComplex(String s, AngUnit angUnit) {
		if (s.isEmpty()) {
			throw new NumberFormatException();
		}
		Complex c;
		if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')') {
			String[] data = s.substring(1, s.length() - 1).split(",");
			double[] data1 = {suffDouble(data[0]), suffDouble(data[1])};
			c = new Complex(data1[0], data1[1]);
		}
		else {
			String[] s1 = s.split("<");
			if (s1.length == 2) {
				double[] data2 = {suffDouble(s1[0]), Double.parseDouble(s1[1])};
				c = (angUnit == AngUnit.RAD) ?
						new Complex(data2[0], data2[1], DisplayForm.POLAR):
						new Complex(data2[0], Math.toRadians(data2[1]), DisplayForm.POLAR);
			}
			else if (s1.length == 1) {
				c = new Complex(suffDouble(s), 0.0);
			}
			else {
				throw new NumberFormatException();
			}
		}
		return c;
	}
	
	/**
	 * Reads a string and expects a SI unit prefix.
	 * Inspired but not necessarily equal to LTSpice managing of prefixes,
	 * this one is case sensitive and only accepts lowercase except for the
	 * MEGA prefix. Includes 
	 * <code>u</code> for micro (e-6), <code>m</code> for mili (e-3), <code>k</code> for kilo (e3)
	 * and <code>M</code> for mega (e6).
	 * @param str the string to be read.
	 */
	private static double suffDouble(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch (NumberFormatException e) {
			String s1 = str.substring(0, str.length() - 1);
			switch(str.charAt(str.length() - 1)) {
//			case 'p': //pico
//				return Double.parseDouble(s1) * 0.000000000001;
//			case 'n': //nano
//				return Double.parseDouble(s1) * 0.000000001;
			case 'u': //micro
				return Double.parseDouble(s1) * 0.000001;
			case 'm': //mili
				return Double.parseDouble(s1) * 0.001;
			case 'k': //kilo
				return Double.parseDouble(s1) * 1000.0;
			case 'M': //mega
				return Double.parseDouble(s1) * 1000000.0;
			default:
				throw new NumberFormatException();	
			}
		}
	}
	
	private static double cleanArgument(double d) {
		return Math.atan2(Math.sin(d), Math.cos(d));
	}
	
}
