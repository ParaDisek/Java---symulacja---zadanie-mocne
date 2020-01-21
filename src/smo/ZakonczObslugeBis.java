package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Aktywnoœæ gniazda obs³ugi. Realizuje obs³ugê przez losowy czas obiektów - zg³oszeñ.
 */

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class ZakonczObslugeBis extends BasicSimEvent<SmoBis, Zgloszenie>
{
    private SmoBis smoParent;

    public ZakonczObslugeBis(SmoBis parent, double delay, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
    }

	@Override
	protected void onInterruption() throws SimControlException {
        System.out.println(simTime()+": !Przerwanie obs³ugi przy zgl. nr: " + transitionParams.getTenNr());			
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
		// Odblokuj gniazdo
		smoParent.setWolne(true);
        System.out.println(simTime()+": SMO2-Koniec obs³ugi zgl. nr: " + transitionParams.getTenNr());
		// Zaplanuj dalsza obs³uge
        if (smoParent.liczbaZgl() > 0)
        {
        	smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent);        	
        }		
	}
}