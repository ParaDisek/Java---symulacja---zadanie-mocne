package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Aktywno�� gniazda obs�ugi. Realizuje obs�ug� przez losowy czas obiekt�w - zg�osze�.
 */

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimEventBarrier;
import dissimlab.simcore.SimControlException;

public class ZakonczObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;

    public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
    }

    public ZakonczObsluge(Smo parent, SimEventBarrier semafor, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, semafor, zgl);
        this.smoParent = parent;
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
		// TODO
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
        if (smoParent.getSmo2().dodaj(transitionParams)) {
    		smoParent.setWolne(true);
            System.out.println(simTime()+": Koniec obs�ugi zgl. nr: " + transitionParams.getTenNr());
            smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia());
        	
            if (smoParent.getSmo2().liczbaZgl()==1 && smoParent.getSmo2().isWolne()) {
            	smoParent.getSmo2().rozpocznijObsluge = new RozpocznijObslugeBis(smoParent.getSmo2());
            }
        	// Zaplanuj dalsza obs�uge w tym gnie�dzie
        	if (smoParent.liczbaZgl() > 0)
        	{
        		smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent);        	
        	}	
        } else {
            System.out.println(simTime()+": Oczekiwanie na semaforze - zgl. nr: " + transitionParams.getTenNr());
        	smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, smoParent.getSemafor(), transitionParams);        	
        }
	}
}