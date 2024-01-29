package com.september22nd.quadApp.quadLogic;

import java.io.Serializable;

public class CQuad implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2295770648726749997L;
	
	private CMatrix openCircuitP;
	
	private CMatrix shortCircuitP;
	
	private CMatrix hybridP;
	
	private CMatrix invHybridP;
	
	private CMatrix transP;
	
	private CMatrix invTransP;

	public CQuad(CMatrix m, ParamType paramType) {
		switch(paramType) {
		case OPEN:
			openCircuitP = new CMatrix(m);
			completeFromOpen();
			break;
		case SHORT:
			shortCircuitP = new CMatrix(m);
			completeFromShort();
			break;
		case HYBRID:
			hybridP = new CMatrix(m);
			completeFromHybrid();
			break;
		case INV_HYBRID:
			invHybridP = new CMatrix(m);
			completeFromInvHybrid();
			break;
		case TRANS:
			transP = new CMatrix(m);
			completeFromTrans();
			break;
		case INV_TRANS:
			invTransP = new CMatrix(m);
			completeFromInvTrans();
		}
	}
	
	public CMatrix getOpenCircuitParams() {
		return openCircuitP;
	}
	
	public CMatrix getShortCircuitParams() {
		return shortCircuitP;
	}
	
	public CMatrix getHybridParams() {
		return hybridP;
	}
	
	public CMatrix getInvHybridParams() {
		return invHybridP;
	}
	
	public CMatrix getTransParams() {
		return transP;
	}
	
	public CMatrix getInvTransParams() {
		return invTransP;
	}

	private void completeFromOpen() {
		CMatrix z = new CMatrix(openCircuitP);
		shortCircuitP = new CMatrix(z.inv());
		hybridP = (z.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(z.det(),z.get(1,1)),
				Complex.div(z.get(0,1),z.get(1,1)),
				Complex.div(z.get(1,0).neg(),z.get(1,1)),
				Complex.div(Complex.ONE,z.get(1,1)));
		invHybridP = (z.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,z.get(0,0)),
				Complex.div(z.get(0,1).neg(),z.get(0,0)),
				Complex.div(z.get(1,0),z.get(0,0)),
				Complex.div(z.det(),z.get(0,0)));
		transP = (z.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(z.get(0,0),z.get(1,0)),
				Complex.div(z.det(),z.get(1,0)),
				Complex.div(Complex.ONE,z.get(1,0)),
				Complex.div(z.get(1,1),z.get(1,0)));
		invTransP = (z.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(z.get(1,1),z.get(0,1)),
				Complex.div(z.det(),z.get(0,1)),
				Complex.div(Complex.ONE,z.get(0,1)),
				Complex.div(z.get(0,0),z.get(0,1)));
	}
	
	private void completeFromShort() {
		CMatrix y = new CMatrix(shortCircuitP);
		openCircuitP = new CMatrix(y.inv());
		hybridP = (y.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,y.get(0,0)),
				Complex.div(y.get(0,1).neg(),y.get(0,0)),
				Complex.div(y.get(1,0),y.get(0,0)),
				Complex.div(y.det(),y.get(0,0)));
		invHybridP = (y.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(y.det(),y.get(1,1)),
				Complex.div(y.get(0,1),y.get(1,1)),
				Complex.div(y.get(1,0).neg(),y.get(1,1)),
				Complex.div(Complex.ONE,y.get(1,1)));
		transP = (y.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(y.get(1,1).neg(),y.get(1,0)),
				Complex.div(Complex.ONE.neg(),y.get(1,0)),
				Complex.div(y.det().neg(),y.get(1,0)),
				Complex.div(y.get(0,0).neg() ,y.get(1,0)));
		invTransP = (y.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(y.get(0,0).neg(),y.get(0,1)),
				Complex.div(Complex.ONE.neg(),y.get(0,1)),
				Complex.div(y.det().neg(),y.get(0,1)),
				Complex.div(y.get(1,1).neg(),y.get(0,1)));
	}
	
	private void completeFromHybrid() {
		CMatrix h = new CMatrix(hybridP);
		invHybridP = new CMatrix(h.inv());
		openCircuitP = (h.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(h.det(),h.get(1,1)),
				Complex.div(h.get(0,1),h.get(1,1)),
				Complex.div(h.get(1,0).neg(),h.get(1,1)),
				Complex.div(Complex.ONE,h.get(1,1)));
		shortCircuitP = (h.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,h.get(0,0)),
				Complex.div(h.get(0,1).neg(),h.get(0,0)),
				Complex.div(h.get(1,0),h.get(0,0)),
				Complex.div(h.det(),h.get(0,0)));
		transP = (h.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(h.det().neg(),h.get(1,0)),
				Complex.div(h.get(0,0).neg(),h.get(1,0)),
				Complex.div(h.get(1,1).neg(),h.get(1,0)),
				Complex.div(Complex.ONE.neg(),h.get(1,0)));
		invTransP = (h.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,h.get(0,1)),
				Complex.div(h.get(0,0),h.get(0,1)),
				Complex.div(h.get(1,1),h.get(0,1)),
				Complex.div(h.det(),h.get(0,1)));
	}
	
	private void completeFromInvHybrid() {
		CMatrix g = new CMatrix(invHybridP);
		hybridP = new CMatrix(g.inv());
		openCircuitP = (g.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,g.get(0,0)),
				Complex.div(g.get(0,1).neg(),g.get(0,0)),
				Complex.div(g.get(1,0),g.get(0,0)),
				Complex.div(g.det(),g.get(0,0)));
		shortCircuitP = (g.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(g.det(),g.get(1,1)),
				Complex.div(g.get(0,1),g.get(1,1)),
				Complex.div(g.get(1,0).neg(),g.get(1,1)),
				Complex.div(Complex.ONE,g.get(1,1)));
		transP = (g.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(Complex.ONE,g.get(1,0)),
				Complex.div(g.get(1,1),g.get(1,0)),
				Complex.div(g.get(0,0),g.get(1,0)),
				Complex.div(g.det(),g.get(1,0)));
		invTransP = (g.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(g.det().neg(),g.get(0,1)),
				Complex.div(g.get(1,1).neg(),g.get(0,1)),
				Complex.div(g.get(0,0).neg(),g.get(0,1)),
				Complex.div(Complex.ONE.neg(),g.get(0,1)));
	}
	
	private void completeFromTrans() {
		CMatrix t = new CMatrix(transP);
		invTransP = (t.det().isZero()) ? null : new CMatrix(
				Complex.div(t.get(1,1),t.det()),
				Complex.div(t.get(0,1),t.det()),
				Complex.div(t.get(1,0),t.det()),
				Complex.div(t.get(0,0),t.det()));
		openCircuitP = (t.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(t.get(0,0),t.get(1,0)),
				Complex.div(t.det(),t.get(1,0)),
				Complex.div(Complex.ONE,t.get(1,0)),
				Complex.div(t.get(1,1),t.get(1,0)));
		shortCircuitP = (t.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(t.get(1,1),t.get(0,1)),
				Complex.div(t.det().neg(),t.get(0,1)),
				Complex.div(Complex.ONE.neg(),t.get(0,1)),
				Complex.div(t.get(0,0),t.get(0,1)));
		hybridP = (t.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(t.get(0,1),t.get(1,1)),
				Complex.div(t.det(),t.get(1,1)),
				Complex.div(Complex.ONE.neg(),t.get(1,1)),
				Complex.div(t.get(1,0),t.get(1,1)));
		invHybridP = (t.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(t.get(1,0),t.get(0,0)),
				Complex.div(t.det().neg(),t.get(0,0)),
				Complex.div(Complex.ONE,t.get(0,0)),
				Complex.div(t.get(0,1),t.get(0,0)));
	}
	
	private void completeFromInvTrans() {
		CMatrix it = new CMatrix(invTransP);
		transP = (it.det().isZero()) ? null : new CMatrix(
				Complex.div(it.get(1,1),it.det()),
				Complex.div(it.get(0,1),it.det()),
				Complex.div(it.get(1,0),it.det()),
				Complex.div(it.get(0,0),it.det()));
		openCircuitP = (it.get(1,0).isZero()) ? null : new CMatrix(
				Complex.div(it.get(1,1),it.get(1,0)),
				Complex.div(Complex.ONE,it.get(1,0)),
				Complex.div(it.det(),it.get(1,0)), 
				Complex.div(it.get(0,0),it.get(1,0)));
		shortCircuitP = (it.get(0,1).isZero()) ? null : new CMatrix(
				Complex.div(it.get(0,0),it.get(0,1)),
				Complex.div(Complex.ONE.neg(),it.get(0,1)),
				Complex.div(it.det().neg(),it.get(0,1)),
				Complex.div(it.get(1,1),it.get(0,1)));
		hybridP = (it.get(0,0).isZero()) ? null : new CMatrix(
				Complex.div(it.get(0,1),it.get(0,0)),
				Complex.div(Complex.ONE,it.get(0,0)),
				Complex.div(it.det(),it.get(0,0)),
				Complex.div(it.get(1,0),it.get(0,0)));
		invHybridP = (it.get(1,1).isZero()) ? null : new CMatrix(
				Complex.div(it.get(1,0),it.get(1,1)),
				Complex.div(Complex.ONE.neg(),it.get(1,1)),
				Complex.div(it.det(),it.get(1,1)),
				Complex.div(it.get(0,1).neg(),it.get(1,1)));
	}
}
