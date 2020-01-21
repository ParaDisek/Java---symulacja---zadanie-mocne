package smo;

import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;


public class Otoczenie extends BasicSimObj {
    public Zglaszaj zglaszaj;
    public MonitoredVar MVczasy_miedzy_zgl;
    public Smo smo;

    public Otoczenie(Smo smo, SimManager simMgr) throws SimControlException {
        // Powo�anie instancji pierwszego zdarzenia
    	zglaszaj = new Zglaszaj(this, 0.0);
        // Deklaracja zmiennych monitorowanych
        MVczasy_miedzy_zgl = new MonitoredVar(simMgr);
        // SMO dla zg�osze�
        this.smo = smo;
	}
}
