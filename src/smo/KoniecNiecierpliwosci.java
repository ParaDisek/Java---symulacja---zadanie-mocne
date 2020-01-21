package smo;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 * 
 * @author Dariusz Pierzchala

 */
public class KoniecNiecierpliwosci extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public KoniecNiecierpliwosci(Zgloszenie parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
        this.parent = parent;
    }

    public KoniecNiecierpliwosci(Zgloszenie parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
        this.parent = parent;
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
        System.out.println(simTime()+": Przerwanie niecierpliwoœci zgl. nr: " + parent.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
        System.out.println(simTime()+": Koniec niecierpliwoœci zgl. nr: " + parent.getTenNr());
        if (parent.smo.usunWskazany(parent)){
            System.out.println(simTime()+": Usuniêto z kolejki zgl. nr: " + parent.getTenNr());
            double lutrac = parent.smo.MVutraconeZgl.getValue();
            parent.smo.MVutraconeZgl.setValue(lutrac++);
        }
        else
            System.out.println(simTime()+": Problem z usuniêciem z kolejki zgl. nr: " + parent.getTenNr());       	
	}
}