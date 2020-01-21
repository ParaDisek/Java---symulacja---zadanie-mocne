package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Aktywnoœæ gniazda obs³ugi. Realizuje obs³ugê przez losowy czas obiektów - zg³oszeñ.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class RozpocznijObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;
    private SimGenerator generator;

    public RozpocznijObsluge(Smo parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
        this.smoParent = parent;
    }

    public RozpocznijObsluge(Smo parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
        this.smoParent = parent;
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
        if (smoParent.liczbaZgl() > 0)
        {
        	// Zablokuj gniazdo
        	smoParent.setWolne(false);
        	// Pobierz zg³oszenie
        	Zgloszenie zgl = smoParent.usun();
        	// Przerwanie niecierpliwosci
        	zgl.koniecNiecierpliwosci.interrupt();
        	// Wygeneruj czas obs³ugi
        	double czasObslugi = generator.normal(9.0, 1.0);
            // Zapamiêtaj dane monitorowane
            smoParent.MVczasy_oczekiwania.setValue(simTime() - zgl.getCzasOdniesienia());
            zgl.setCzasOdniesienia(simTime());
            System.out.println(simTime()+": Pocz¹tek obs³ugi zgl. nr: " + zgl.getTenNr());
        	// Zaplanuj koniec obs³ugi
        	smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, czasObslugi, zgl);        	
        }
		
	}
}