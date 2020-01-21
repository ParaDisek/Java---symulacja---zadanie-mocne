package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.ArrayList;
import java.util.Random;

public class DepartureEvent extends BasicSimEvent<Lotnisko, Object> {

    private SimGenerator generator;
    private Lotnisko parent;
    Random rand = new Random();

    public DepartureEvent(Lotnisko parent, double delay) throws SimControlException {
        super(parent, delay);
        generator = new SimGenerator();
    }

    @Override
    protected void stateChange() throws SimControlException {
        parent = getSimObj();

        if (parent.onTheGround > 0) {
            if (parent.departureRunwayFree) {
                parent.departureRunwayFree = false;
                parent.mvDepartureRunwayFree.setValue(0);
                parent.flewAwayEvent = new FlewAwayEvent(parent, parent.departureTime);
            }

            if (parent.onTheGround > 0) {
                double delay = parent.departureQueue.get(getPlaneIndex(parent.departureQueue)).departureInterval;
                parent.departureQueue.remove(getPlaneIndex(parent.departureQueue));
                parent.departureEvent = new DepartureEvent(parent, delay);
            }
        }
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    protected int getPlaneIndex(ArrayList<Plane> queue) {
        int index = 0;
        double smallest = queue.get(0).departureInterval;

        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).departureInterval < smallest) {
                index = i;
            }
        }

        return index;
    }
}
