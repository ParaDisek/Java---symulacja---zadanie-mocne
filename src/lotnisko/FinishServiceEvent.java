package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class FinishServiceEvent extends BasicSimEvent<Lotnisko, Object> {

    private SimGenerator generator;
    private Lotnisko parent;

    public FinishServiceEvent(Lotnisko parent, double delay) throws SimControlException {
        super(parent, delay);
        generator = new SimGenerator();

    }

    @Override
    protected void stateChange() throws SimControlException {
        parent = getSimObj();
        System.out.println("Zakończono obsługę pasażera w " + simTime());
        if (parent.passengerQueue.size() > 0) {
            parent.startServiceEvent = new StartServiceEvent(parent, 0);
        }
        //parent.sum += (simTime() - parent.passenger.landingTime);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
