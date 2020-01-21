package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class FlewAwayEvent extends BasicSimEvent<Lotnisko, Object> {

    private SimGenerator generator;
    private Lotnisko parent;

    public FlewAwayEvent(Lotnisko parent, double delay) throws SimControlException {
        super(parent, delay);
        generator = new SimGenerator();
    }

    @Override
    protected void stateChange() throws SimControlException {
        parent = getSimObj();

        parent.onTheGround--;
        parent.mvOnTheGround.setValue(parent.onTheGround);
        parent.departureRunwayFree = true;
        parent.mvDepartureRunwayFree.setValue(1);
        if (parent.onTheGround == 0) {

        }
        System.out.println(simTime() + " - Odleciał samolot. Na płycie altualnie jest: " + parent.onTheGround + " samolot(ów)");
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
