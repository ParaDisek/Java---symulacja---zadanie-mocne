package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class StartServiceEvent extends BasicSimEvent<Lotnisko, Object> {

    double serviceTime;
    SimGenerator generatorr = new SimGenerator();
    private SimGenerator generator;
    private Lotnisko parent;

    public StartServiceEvent(Lotnisko parent, double delay) throws SimControlException {

        super(parent, delay);
        generator = new SimGenerator();
    }

    @Override
    protected void stateChange() throws SimControlException {
        parent = getSimObj();
        do {
            this.serviceTime = generatorr.normal(0.5, 1);
        } while (this.serviceTime <= 0);

        System.out.println("Czas serwisowania " + this.serviceTime);
        //parent.passenger = parent.passengerQueue.get(0);
        parent.passengerQueue.remove(0);
        parent.queueLength--;
        parent.mvQueueLength.setValue(parent.queueLength);
        parent.finishServiceEvent = new FinishServiceEvent(parent, this.serviceTime);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
