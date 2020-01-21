package smo;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 * 
 * @author Dariusz Pierzchala

 */
public class StartNiecierpliwosci extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public StartNiecierpliwosci(Zgloszenie parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
        this.parent = parent;
    }

    public StartNiecierpliwosci(Zgloszenie parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
        this.parent = parent;
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
        System.out.println(simTime()+": Pocz¹tek niecierpliwoœci zgl. nr: " + parent.getTenNr());
        double odstep = generator.normal(15.0, 1.0);
        parent.koniecNiecierpliwosci = new KoniecNiecierpliwosci(parent, odstep);
	}
}