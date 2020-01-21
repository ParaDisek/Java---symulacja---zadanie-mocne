package smo;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

/**
 * Description: Sk³adowa aktywnoœci generatora zg³oszeñ. Tworzy obiekt - zg³oszenie co losowy czas.
 * 
 * @author Dariusz Pierzchala

 */
public class Zglaszaj extends BasicSimEvent<Otoczenie, Object>
{
    private SimGenerator generator;
    private Otoczenie parent;

    public Zglaszaj(Otoczenie parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
    }

    public Zglaszaj(Otoczenie parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
        parent = getSimObj();
        Zgloszenie zgl = new Zgloszenie(simTime(), parent.smo);
        parent.smo.dodaj(zgl);
        System.out.println(simDate(SimDateField.HOUR24)+" - "+simDate(SimDateField.MINUTE)+" - "+simDate(SimDateField.SECOND)+" - "+simDate(SimDateField.MILLISECOND)+": Otoczenie: Dodano nowe zgl. nr: " + zgl.getTenNr());
        // Aktywuj obs³ugê, je¿eli kolejka by³a pusta (gniazdo "spa³o")
        if (parent.smo.liczbaZgl()==1 && parent.smo.isWolne()) {
        	parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo);
        }
        // Wygeneruj czas do kolejnego zg³oszenia
        double odstep = generator.normal(5.0, 1.0);
        parent.MVczasy_miedzy_zgl.setValue(odstep);
        parent.zglaszaj = new Zglaszaj(parent, odstep);
	}
}