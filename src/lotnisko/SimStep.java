package lotnisko;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class SimStep extends BasicSimEvent<Lotnisko, Object> {


	public SimStep(Lotnisko lotnisko, double period) throws SimControlException {
		super(lotnisko, null, period);
	}

	@Override
	protected void stateChange() throws SimControlException {

	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onInterruption() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

}
