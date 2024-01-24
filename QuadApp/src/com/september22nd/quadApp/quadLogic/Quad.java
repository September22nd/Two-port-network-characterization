package com.september22nd.quadApp.quadLogic;

import java.io.Serializable;

public class Quad implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6734943924850375440L;

	private Matrix openCircuitP;
	
	private Matrix shortCircuitP;
	
	private Matrix hybridP;
	
	private Matrix invHybridP;
	
	private Matrix transP;
	
	private Matrix invTransP;
	
	public Quad(Matrix m, ParamType paramType) {
		switch(paramType) {
		case OPEN:
			openCircuitP = new Matrix(m);
			completeFromOpen();
			break;
		case SHORT:
			shortCircuitP = new Matrix(m);
			completeFromShort();
			break;
		case HYBRID:
			hybridP = new Matrix(m);
			completeFromHybrid();
			break;
		case INV_HYBRID:
			invHybridP = new Matrix(m);
			completeFromInvHybrid();
			break;
		case TRANS:
			transP = new Matrix(m);
			completeFromTrans();
			break;
		case INV_TRANS:
			invTransP = new Matrix(m);
			completeFromInvTrans();
		}
	}
	
	public Matrix getOpenCircuitParams() {
		return openCircuitP;
	}
	
	public Matrix getShortCircuitParams() {
		return shortCircuitP;
	}
	
	public Matrix getHybridParams() {
		return hybridP;
	}
	
	public Matrix getInvHybridParams() {
		return invHybridP;
	}
	
	public Matrix getTransParams() {
		return transP;
	}
	
	public Matrix getInvTransParams() {
		return invTransP;
	}

	private void completeFromOpen() {
		Matrix z = new Matrix(openCircuitP);
		shortCircuitP = new Matrix(z.inv());
		hybridP = (z.get22() == 0.0) ? null : new Matrix(z.det() / z.get22(), z.get12() / z.get22(), -z.get21() / z.get22(), 1.0 / z.get22());
		invHybridP = (z.get11() == 0.0) ? null : new Matrix(1.0 / z.get22(), -z.get12() / z.get22(), z.get21() / z.get11(), z.det() / z.get11());
		transP = (z.get21() == 0.0) ? null : new Matrix(z.get11() / z.get21(), z.det() / z.get21(), 1.0 / z.get21(), z.get22() / z.get21());
		invTransP = (z.get12() == 0.0) ? null : new Matrix(z.get22() / z.get12(), z.det() / z.get12(), 1.0 / z.get12(), z.get11() / z.get12());
		
	}
	
	private void completeFromShort() {
		Matrix y = new Matrix(shortCircuitP);
		openCircuitP = new Matrix(y.inv());
		hybridP = (y.get11() == 0.0) ? null : new Matrix(1.0 / y.get11(), -y.get12() / y.get11(), y.get21() / y.get11(), y.det() / y.get11());
		invHybridP = (y.get22() == 0.0) ? null : new Matrix(y.det() / y.get22(), y.get12() / y.get22(), -y.get21() / y.get22(), 1.0 / y.get22());
		transP = (y.get21() == 0.0) ? null : new Matrix(-y.get22() / y.get21(), -1.0 / y.get21(), -y.det() / y.get21(), -y.get11() /y.get21());
		invTransP = (y.get12() == 0.0) ? null : new Matrix(-y.get11() / y.get12(), -1.0 / y.get12(), -y.det() / y.get12(), -y.get22() / y.get12());
	}
	
	private void completeFromHybrid() {
		Matrix h = new Matrix(hybridP);
		invHybridP = new Matrix(h.inv());
		openCircuitP = (h.get22() == 0.0) ? null : new Matrix(h.det() / h.get22(), h.get12() / h.get22(), -h.get21() / h.get22(), 1.0 / h.get22());
		shortCircuitP = (h.get11() == 0.0) ? null : new Matrix(1.0 / h.get11(), -h.get12() / h.get11(), h.get21() / h.get11(), h.det() / h.get11());
		transP = (h.get21() == 0.0) ? null : new Matrix(-h.det() / h.get21(), -h.get11() / h.get21(), -h.get22() / h.get12(), -1.0 / h.get21());
		invTransP = (h.get12() == 0.0) ? null : new Matrix(1 / h.get12(), h.get11() / h.get12(), h.get22() / h.get12(), h.det() / h.get12());
	}
	
	private void completeFromInvHybrid() {
		Matrix g = new Matrix(invHybridP);
		hybridP = new Matrix(g.inv());
		openCircuitP = (g.get11() == 0.0) ? null : new Matrix(1.0 / g.get11(), -g.get12() / g.get11(), g.get21() / g.get11(), g.det() / g.get11());
		shortCircuitP = (g.get22() == 0.0) ? null : new Matrix(g.det() / g.get22(), g.get12() / g.get22(), -g.get21() / g.get22(), 1.0 / g.get22());;
		transP = (g.get21() == 0.0) ? null : new Matrix(1.0 / g.get21(), g.get22() / g.get22(), g.get11() / g.get21(), g.det() / g.get21());
		invTransP = (g.get12() == 0.0) ? null : new Matrix(-g.det() / g.get12(), -g.get22() / g.get12(), -g.get11() / g.get12(), -1.0 / g.get12());
	}
	
	private void completeFromTrans() {
		Matrix t = new Matrix(transP);
		invTransP = (t.det() == 0.0) ? null : new Matrix(t.get22() / t.det(), t.get12() / t.det(), t.get21() / t.det(), t.get11() / t.det());
		openCircuitP = (t.get21() == 0.0) ? null : new Matrix(t.get11() / t.get21(), t.det() / t.get21(), 1.0 / t.get21(), t.get22() / t.get21());
		shortCircuitP = (t.get12() == 0.0) ? null : new Matrix(t.get22() / t.get12(), -t.det() / t.get12(), -1.0 / t.get12(), t.get11() / t.get12());
		hybridP = (t.get22() == 0.0) ? null : new Matrix(t.get12() / t.get22(), t.det() / t.get22(), -1.0 / t.get22(), t.get21() / t.get22());
		invHybridP = (t.get11() == 0.0) ? null : new Matrix(t.get21() / t.get11(), -t.det() / t.get11(), 1.0 / t.get11(), t.get12() / t.get11());
	}
	
	private void completeFromInvTrans() {
		Matrix it = new Matrix(invTransP);
		transP = (it.det() == 0.0) ? null : new Matrix(it.get22() / it.det(), it.get12() / it.det(), it.get21() / it.det(), it.get11() / it.det());
		openCircuitP = (it.get21() == 0.0) ? null : new Matrix(it.get22() / it.get21(), 1.0 / it.get21(), it.det() / it.get21(), it.get11() / it.get21());
		shortCircuitP = (it.get12() == 0.0) ? null : new Matrix(it.get11() / it.get12(), -1.0 / it.get12(), -it.det() / it.get12(), it.get22() / it.get12());
		hybridP = (it.get11() == 0.0) ? null : new Matrix(it.get12() / it.get11(), 1.0 / it.get11(), it.det() / it.get11(), it.get21() / it.get11());
		invHybridP = (it.get22() == 0.0) ? null : new Matrix(it.get21() / it.get22(), -1.0 / it.get22(), it.det() / it.get22(), -it.get12() / it.get22());
	}

}
