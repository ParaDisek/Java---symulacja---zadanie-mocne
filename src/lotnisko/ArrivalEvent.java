package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.Random;

public class ArrivalEvent extends BasicSimEvent<Lotnisko, Object> {

    private SimGenerator generator;
    private Lotnisko parent;
    Random rand = new Random();

    public ArrivalEvent(Lotnisko parent, double delay) throws SimControlException {
        super(parent, delay);
        generator = new SimGenerator();
    }

    protected void stateChange() throws SimControlException {
        parent = getSimObj();
        Plane plane = new Plane(rand.nextInt(3) + 1);
        parent.landingQueue.add(plane);

        System.out.println("Category - " + plane.category);
        System.out.println(("LandingDuration - " + plane.landingDuration));
        System.out.println(("DepartureInterval - \n" + plane.departureInterval));
        parent.inTheAir++;
        parent.mvInTheAir.setValue(parent.inTheAir);

        do {
            parent.arrivalInterval = generator.exponential(3);
        } while (parent.arrivalInterval <= 0);

        System.out.println(simTime() + " - Przybył samolot. Nad lotniskiem altualnie jest: " + parent.inTheAir + " samolot(ów)");

        if (parent.runwayFree) {
            parent.runwayFree = false;
            parent.mvRunwayFree.setValue(0);
            parent.departureQueue.add(plane);
            parent.landingEvent = new LandingEvent(parent, parent.landingQueue.get(0).landingDuration);
            parent.landingQueue.remove(0);
        }
        parent.arrivalEvent = new ArrivalEvent(parent, parent.arrivalInterval);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
