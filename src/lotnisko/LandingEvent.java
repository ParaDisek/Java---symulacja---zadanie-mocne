package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.ArrayList;
import java.util.Random;

public class LandingEvent extends BasicSimEvent<Lotnisko, Object> {

    private SimGenerator generator;
    private Lotnisko parent;
    Random rand = new Random();

    public LandingEvent(Lotnisko parent, double delay) throws SimControlException {
        super(parent, delay);
        generator = new SimGenerator();
    }

    @Override
    protected void stateChange() throws SimControlException {
        parent = getSimObj();
        if (parent.inTheAir > 0) {
            parent.inTheAir--;
            parent.mvInTheAir.setValue(parent.inTheAir);
            parent.onTheGround++;
            parent.mvOnTheGround.setValue(parent.onTheGround);



            System.out.println(simTime() + " - Wylądował samolot. Na płycie altualnie jest: " + parent.onTheGround
                    + " a w powietrzu " + parent.inTheAir + " samolot(ów)");

            if (parent.passengerQueue.size() == 0) {
                for (int i = 0; i < parent.departureQueue.get(0).passengers; i++) {
                    parent.passengerQueue.add(new Passenger(simTime()));
                    parent.queueLength++;
                    parent.mvQueueLength.setValue(parent.queueLength);
                }
                parent.startServiceEvent = new StartServiceEvent(parent, 0);
                System.out.println("Rozpoczeto obsluge pasazerow");
            }

            else {
                for (int i = 0; i < parent.departureQueue.get(0).passengers; i++) {
                    parent.passengerQueue.add(new Passenger(simTime()));
                    parent.queueLength++;
                    parent.mvQueueLength.setValue(parent.queueLength);
                }
            }

            if (parent.inTheAir > 0) {
                parent.departureQueue.add(parent.landingQueue.get(0));
                System.out.println(simTime() + " - Zaplanowano lądowanie");
                double delay = parent.landingQueue.get(0).landingDuration;
                parent.landingQueue.remove(0);
                parent.landingEvent = new LandingEvent(parent, delay);
            } else {
                parent.runwayFree = true;
                parent.mvRunwayFree.setValue(1);
                System.out.println(simTime() + " - Zwolniono pas lądowania");
            }

            if (parent.onTheGround == 1) {
                parent.departureEvent = new DepartureEvent(parent, parent.departureQueue.get(getPlaneIndex(parent.departureQueue)).departureInterval);
                //parent.departureQueue.remove(getPlaneIndex(parent.departureQueue));
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
